package client;

import game.Game;
import game.Piece;
import game.PlayingField;
import game.Team;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client implements Runnable {
    private final String address;
    private final int port;
    private volatile Game game;
    private final ClientGUI clientGUI;

    /**
     * Constructor of the Client class.
     * Sets member variables to its arguments
     *
     * @param address the address the client will connect to
     * @param port    the port the client will connect on
     */
    public Client(String address, int port) {
        this.address = address;
        this.port = port;

        clientGUI = new ClientGUI(game);
        clientGUI.setVisible(true);
    }

    /**
     * The main client loop. The client tries to connect to the
     * provided address and port. If the connection was successful, this function runs until
     * the game is finished or either the client or the server closes the connection.
     */
    @Override
    public void run() {

        boolean gameIsFinished = false;

        Color teamColor;
        Socket socket;
        try {
            socket = new Socket(address, port);
            System.out.println("CLIENT:\t\tConnected!");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            //recive team from server over socket

            teamColor = (Color) inputStream.readObject();
            System.out.println("CLIENT:\t\tYour Team color: " + teamColor);
            clientGUI.setYourColor(teamColor);

            while (!gameIsFinished) {
                try {
                    System.out.println("\nCLIENT:\t\tWaiting for server...");
                    game = null;
                    game = (Game) inputStream.readObject();
                    System.out.println("CLIENT:\t\tGame recived");
                    //Sending confirmation:
                    outputStream.writeBoolean(true);
                    outputStream.flush();
                    System.out.println("CLIENT:\t\tConfirmation sent");

                    SwingUtilities.invokeLater(() -> clientGUI.updateGame(game));


                    if (game.checkIfGameIsFinished()) {
                        gameIsFinished = true;
                        System.out.println("CLIENT:\t\tGame is finished");
                        socket.close();
                    }

                    if (game.getCurrentlyPlaying().getColor() == teamColor && game.getLastDiceRoll() != null) {
                        System.out.println("CLIENT:\t\tIt's your turn!");
                        //AtomicReference<Spielstein> selction = new AtomicReference<>(clientGUI.selection(spiel));
                        //SwingUtilities.invokeLater(() -> selction.set(clientGUI.selection(spiel)));
                        sendSelectionToServer(game, teamColor, outputStream);
                        //askForSave();
                    } else {
                        System.out.println("CLIENT:\t\t last dice roll: " + game.getLastDiceRoll());
                        System.out.println("CLIENT:\t\t currently playing: " + game.getCurrentlyPlaying().getColor());
                    }

                    //TODO: If all clients are still in spawn, just say that each client (including yourself)
                    //Tries to get out of spawn --> This is automatic

                    //get confirmation from server for collection nervertheless of which client, You'll get it from all
                    int seletion = inputStream.readInt();
                    System.out.println("CLIENT:\t\tServer confirmed selection: " + seletion);

                    if (seletion != -1 && game.getLastDiceRoll() != null) {
                        movePieceStepByStep(seletion);
                    }

                    //movePiece(selection) -> Selction is int, so you have to convert it to a Spielstein from spiel.selectPiece()
                    //which well return a list of Spielstein, so you have to get the right one from the list
                    //if selection == -1 -> no piece can be moved

                    //send confirmation to server
                    //Send the confirmation only after the selection has been made and the stepByStep has been made
                    outputStream.writeBoolean(true);
                    outputStream.flush();

                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                    teamColor = null;
                    socket.close();
                    System.out.println("CLIENT:\t\tConnection to server lost!");
                    System.exit(0);
                }

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("CLIENT:\t\tCould not connect to server!");
            return;
        }

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Generates a selection request to the user through the concole and sends the selection to the server
     *
     * @param selection the selection the of the piece that should be moved
     */
    private void movePieceStepByStep(int selection) {
        System.out.println("CLIENT:\t\tMoving piece step by step");
        //print current spielfeld
        PlayingField playingField = game.getSpielfeld();
        Team currentTeam = game.getCurrentlyPlaying();
        int diceRoll = game.getLastDiceRoll();
        Piece selectedPiece = game.selectPiece(currentTeam, diceRoll).get(selection);
        if (selectedPiece.getFieldId() == -1) {
            game.movePieceOutOfSpawn(selectedPiece, currentTeam);
            return;
        }
        int currentField = selectedPiece.getFieldId();
        int moveToFeldID = (selectedPiece.getFieldId() + diceRoll) % 40;

        while (selectedPiece.getFieldId() != moveToFeldID) {
            int nextStepFeldId = (selectedPiece.getFieldId() + 1) % 40;
            if (playingField.getFeld(nextStepFeldId).getOccupier() != null && nextStepFeldId != moveToFeldID) {
                selectedPiece.setFieldId((selectedPiece.getFieldId() + 2) % 40);
            } else {
                selectedPiece.setFieldId((selectedPiece.getFieldId() + 1) % 40);
            }
            playingField.getFeld(currentField).setOccupier(null);
            playingField.getFeld(selectedPiece.getFieldId()).setOccupier(selectedPiece);
            currentField = selectedPiece.getFieldId();
            SwingUtilities.invokeLater(() -> {
                // Perform GUI updates and wait for them to complete
                SwingUtilities.invokeLater(() -> {
                    clientGUI.updateGame(game); // Replace spiel with your data
                    synchronized (clientGUI) {
                        clientGUI.notify(); // Notify waiting thread
                    }
                });
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }

    /**
     * Sends the index of the piece that the client wants to move to the server.
     *
     * @param game      the game object.
     * @param teamColor the clients team color.
     * @param out       the output stream.
     */
    private void sendSelectionToServer(Game game, Color teamColor, ObjectOutputStream out) {
        Team team = game.getTeamByColor(teamColor);

        List<Piece> movableStones = game.selectPiece(team, game.getLastDiceRoll());

        if (movableStones == null) {
            System.out.println("CLIENT:\t\tYou can't move any pieces");
            try {
                out.writeInt(-1);
                out.flush();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Ask user for input for an entry in the list of movable stones
        for (int i = 0; i < movableStones.size(); i++) {
            System.out.println("CLIENT:\t\t" + i + ": " + movableStones.get(i).getFieldId() + " Gelaufene Felder: " + movableStones.get(i).getWalkedFields());
        }
        System.out.println("CLIENT:\t\tSelect a Piece to move: ");
        Scanner scanner = new Scanner(System.in);

        int minRange = 0;
        int maxRange = movableStones.size() - 1;

        System.out.printf("CLIENT:\t\tPlease enter a number between %d and %d: ", minRange, maxRange);

        int userInput;

        while (true) {
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();
                if (userInput >= minRange && userInput <= maxRange) {
                    break;
                } else {
                    System.out.printf("CLIENT:\t\tNumber must be between %d and %d. Please try again: ", minRange, maxRange);
                }
            } else {
                scanner.next();
                System.out.printf("CLIENT:\t\tInvalid input. Please enter a valid number between %d and %d: ", minRange, maxRange);
            }
        }

        System.out.printf("CLIENT:\t\tYou entered: %d\n", userInput);
        //Send selection to server

        try {
            out.writeInt(userInput);
            out.flush();
            System.out.println("CLIENT:\t\tSpielstein sent to server\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the game to a persistent directory
     */
    public void saveGame() {
        String userHome = System.getProperty("user.home");

        String persistentDirPath;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            String appData = System.getenv("APPDATA");
            persistentDirPath = appData + File.separator + "menschaergerdichnicht";
        } else {
            persistentDirPath = userHome + File.separator + ".menschaergerdichnicht";
        }

        File persistentDir = new File(persistentDirPath);

        if (!persistentDir.exists()) {
            if (persistentDir.mkdirs()) {
                System.out.println("Directory created: " + persistentDirPath);
            } else {
                System.err.println("Failed to create directory: " + persistentDirPath);
            }
        }

        String fileName = "save_game";
        String filePath = persistentDirPath + File.separator + fileName;

        int i = 0;
        while (new File(filePath).exists()) {
            i++;
            String newFileName = fileName + i;
            filePath = persistentDirPath + File.separator + newFileName;

        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(game);
            System.out.println("Game saved at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asks if the game should be saved
     */
    void askForSave() {
        char input;
        System.out.println("Do you want to save a game? (y/n)");

        try {
            input = (char) System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (input == 'y') {
            saveGame();
        }
    }

}