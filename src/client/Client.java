package client;

import game.Spiel;
import game.Spielfeld;
import game.Spielstein;
import game.Team;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client implements Runnable{
    private volatile Spiel spiel;
    private String address;
    private int port;

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        boolean gameIsFinished = false;
        boolean doBroadcast = false;

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
                    outputStream.writeObject(Boolean.TRUE);
                    outputStream.flush();
                    System.out.println("CLIENT:\t\tConfirmation sent");

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
                        sendSelectionToServer(spiel, teamColor, socket, outputStream);
                        askForSave();
                    }else{
                        System.out.println("CLIENT:\t\t last dice roll: " + spiel.getLastDiceRoll());
                        System.out.println("CLIENT:\t\t currently playing: " + spiel.getCurrentlyPlaying().getColor());
                    }
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                    teamColor = null;
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

    private void sendSelectionToServer(Spiel spiel, Color teamColor, Socket socket, ObjectOutputStream out) {
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

        System.out.printf("YCLIENT:\t\tou entered: %d\n", userInput);
        //Send selection to server

        try {
            out.writeInt(userInput);
            out.flush();
            System.out.println("CLIENT:\t\tSpielstein sent to server\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
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