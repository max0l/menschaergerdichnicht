package server;
import game.*;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{
    private String ip;
    private int port;
    private int numPlayers;
    private boolean isLocal;
    private List<ClientHandler> clients = new ArrayList<>();
    public Server(Boolean isLocal, int numPlayers) {
        this.numPlayers = numPlayers;
        this.isLocal = isLocal;

//        if(!(numPlayers < 4 && numPlayers >= 1)){
//            throw new IllegalArgumentException("Number of players must be between 1 and 4");
//        }

        //For debug always local
        if(isLocal){
            //Spiel spiel = new Spiel(isLocal, numPlayers);
            ip = "localhost";
            port = 8080;
        }


    }

    @Override
    public void run() {
        try {
            System.out.println("Starting Server on Port: " + port);
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server Started!");

            while(clients.size() < numPlayers){
                System.out.println("Waiting for clients...");
                System.out.println("Clients connected: " + clients.size() + "/" + numPlayers);
                clients.add(new ClientHandler(server.accept(), server));
                System.out.println("A client connected.");
            }

            System.out.println("All clients connected!");

            Spiel spiel = new Spiel(isLocal, numPlayers, clients);

            spiel.startGame(true);


        } catch (Exception e) {
            System.out.println("Server could not be started!");
            e.printStackTrace();
        }
    }
}