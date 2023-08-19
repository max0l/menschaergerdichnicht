package client;

import game.Spiel;
import game.Spielstein;
import game.Team;
import client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client implements Runnable{
    private volatile Spiel spiel;
    private final String address;
    private final int port;
    private ClientGUI clientGUI;

    /**
     * Constructor of the Client class.
     * Sets member variables to its arguments
     * @param address the address the client will connect to
     * @param port the port the client will connect on
     */
    public Client(String address, int port) {
        this.address = address;
        this.port = port;

        clientGUI = new ClientGUI();
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

        Color teamColor = null;
        Socket socket = null;
        try {
            socket = new Socket(address, port);
            System.out.println("CLIENT:\t\tConnected!");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            //recive team from server over socket

            teamColor = (Color) inputStream.readObject();
            System.out.println("CLIENT:\t\tTeam color: " + teamColor);

            while (!gameIsFinished) {
                try {
                    System.out.println("\nCLIENT:\t\tWaiting for server...");
                    spiel = null;
                    spiel = (Spiel) inputStream.readObject();
                    System.out.println("CLIENT:\t\tSpiel recived");
                    //Sending confirmation:
                    outputStream.writeBoolean(true);
                    outputStream.flush();
                    System.out.println("CLIENT:\t\tConfirmation sent");

                    SwingUtilities.invokeLater(() -> clientGUI.updateGame(spiel));

                    System.out.println(spiel.getSpielfeld().toString());
                    if (spiel.getLastDiceRoll() == null) {
                        System.out.println("CLIENT:\t\tgetLastDiceRoll is null");
                    } else {
                        System.out.println("CLIENT:\t\tgetLastDiceRoll is not null: " + spiel.getLastDiceRoll());
                    }

                    if (spiel == null || spiel.getCurrentlyPlaying() == null) {
                        if (spiel.getCurrentlyPlaying() == null) {
                            System.out.println("CLIENT:\t\tcurrently playing is null");
                        }
                        if (spiel.getLastDiceRoll() == null) {
                            System.out.println("CLIENT:\t\tlast dice roll is null");
                        }
                    }

                    if (spiel.checkIfGameIsFinished()) {
                        gameIsFinished = true;
                        System.out.println("CLIENT:\t\tGame is finished");
                        socket.close();
                    }

                    if (spiel.getCurrentlyPlaying().getColor() == teamColor && spiel.getLastDiceRoll() != null) {
                        System.out.println("CLIENT:\t\tIt's your turn!");
                        sendSelectionToServer(spiel, teamColor, outputStream);
                        //askForSave();
                    }else{
                        System.out.println("CLIENT:\t\t last dice roll: " + spiel.getLastDiceRoll());
                        System.out.println("CLIENT:\t\t currently playing: " + spiel.getCurrentlyPlaying().getColor());
                    }

                    //TODO: If all clients are still in spawn, just say that each client (including yourself)
                    //Tries to get out of spawn --> This is automatic

                    //get confirmation from server for collection nervertheless of which client, You'll get it from all
                    int seletion = inputStream.readInt();
                    System.out.println("CLIENT:\t\tServer confirmed selection: " + seletion);


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
     * Sends the index of the piece that the client wants to move to the server.
     * @param spiel the game object.
     * @param teamColor the clients team color.
     * @param out the output stream.
     */
    private void sendSelectionToServer(Spiel spiel, Color teamColor, ObjectOutputStream out) {
        Team team = spiel.getTeamByColor(teamColor);
        if(team == null){
            System.out.println("CLIENT:\t\tTeam is null");
        }

        List<Spielstein> movableStones = spiel.selectPiece(team, spiel.getLastDiceRoll());

        if(movableStones == null){
            System.out.println("CLIENT:\t\tmovableStones is null");
            try {
                out.writeInt(-1);
                out.flush();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Ask user for input for an entry in the list of movable stones
        for(int i = 0; i < movableStones.size(); i++){
            System.out.println("CLIENT:\t\t"+i + ": " + movableStones.get(i).getFieldId() + " Gelaufene Felder: " + movableStones.get(i).getWalkedFields());
        }
        System.out.println("CLIENT:\t\tSelect a stone to move: ");
        Scanner scanner = new Scanner(System.in);

        int minRange = 0;
        int maxRange = movableStones.size()-1;

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

        System.out.printf("CLIENT:\t\tou entered: %d\n", userInput);
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
    public void saveGame()
    {
        String userHome = System.getProperty("user.home");

        String persistentDirPath;
        if (System.getProperty("os.name").toLowerCase().contains("win"))
        {
            String appData = System.getenv("APPDATA");
            persistentDirPath = appData + File.separator + "menschaergerdichnicht";
        }
        else
        {
            persistentDirPath = userHome + File.separator + ".menschaergerdichnicht";
        }

        File persistentDir = new File(persistentDirPath);

        if (!persistentDir.exists())
        {
            if (persistentDir.mkdirs())
            {
                System.out.println("Directory created: " + persistentDirPath);
            }
            else
            {
                System.err.println("Failed to create directory: " + persistentDirPath);
            }
        }

        String fileName = "save_game";
        String filePath = persistentDirPath + File.separator + fileName;

        int i = 0;
        while(new File(filePath).exists())
        {
            i++;
            String newFileName = fileName + i;
            filePath = persistentDirPath + File.separator + newFileName;

        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath)))
        {
            outputStream.writeObject(spiel);
            System.out.println("Game saved at: " + filePath);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Asks if the game should be saved
     */
    void askForSave()
    {
        Character input = null;
        System.out.println("Do you want to save a game? (y/n)");

        try
        {
            input = (char) System.in.read();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        if(input == 'y')
        {
            saveGame();
        }
    }

}