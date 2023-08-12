package client;

import game.Spiel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        System.out.println("Connected!");
        Spiel spiel;
        boolean gameIsFinished = false;
        boolean doBroadcast = false;
        ObjectInputStream in;

        while(!gameIsFinished) {
            System.out.println("Waiting for server...");

            try {
                in = new ObjectInputStream(socket.getInputStream());
                if(in != null){
                    System.out.println("in is null");
                    spiel = (Spiel) in.readObject();
                    spiel.startGame(doBroadcast);
                    if(spiel.checkIfGameIsFinished()){
                        gameIsFinished = true;
                    }
                }else {
                    gameIsFinished = true;
                    socket.close();
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                gameIsFinished = true;
                socket.close();
            }
        }
        socket.close();

    }
}