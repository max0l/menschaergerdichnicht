package client;

import game.Spiel;
import game.Spielfeld;
import game.Spielstein;
import game.Team;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

                    //System.out.println(spiel.getSpielfeld().toString());


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
                out.writeObject(null);
                out.flush();
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
                String invalidInput = scanner.next(); // Clear invalid input
                System.out.printf("CLIENT:\t\tInvalid input. Please enter a valid number between %d and %d: ", minRange, maxRange);
            }
        }

        System.out.printf("YCLIENT:\t\tou entered: %d\n", userInput);
        //Send selection to server

        try {
            out.writeObject(movableStones.get(userInput));
            out.flush();
            System.out.println("CLIENT:\t\tSpielstein sent to server\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}