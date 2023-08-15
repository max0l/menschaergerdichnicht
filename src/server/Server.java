package server;
import game.*;

import java.awt.*;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server implements Runnable{
    private String ip;
    private int port;
    private int numPlayers;
    private boolean isLocal;
    private Spiel spiel;

    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private transient List<ClientHandler> clients = new ArrayList<>();
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
            System.out.println("SERVER:\t\tStarting Server on Port: " + port);
            ServerSocket server = new ServerSocket(port);
            System.out.println("SERVER:\t\tServer Started!");

            while(clients.size() < numPlayers){
                System.out.println("SERVER:\t\tWaiting for clients...");
                System.out.println("SERVER:\t\tClients connected: " + clients.size() + "/" + numPlayers);
                clients.add(new ClientHandler(server.accept(), server));
                System.out.println("SERVER:\t\tA client connected.");
            }

            System.out.println("SERVER:\t\tAll clients connected!");


            //Generate game requirements
            System.out.println("SERVER:\t\tGenerating game requirements...");
            spiel = new Spiel(isLocal, numPlayers, clients, colors);
            System.out.println("SERVER:\t\tDone generating game requirements");

            startGame();



        } catch (Exception e) {
            System.out.println("SERVER:\t\tServer could not be started!");
            e.printStackTrace();
        }
    }

    private void startGame() {

        while(spiel.isGameIsRunning()){
            for(Team team : spiel.getTeams()) {
                System.out.println("\n\nSERVER:\tCurrently playing: " + team.getColor() + " is bot: " + team.getIsBot());
                play(team);
                //doBroadcastToAllClients(spiel);
            }
        }

    }

    private void play(Team team) {
        int diceRoll;
        spiel.setCurrentlyPlaying(team);
        if (team.checkIfAllPiecesAreInStart())
        {
            //doBroadcastToAllClients(spiel);
            spiel.tryToGetOutOfSpawn(team);
            return;
        }
        spiel.setCurrentlyPlaying(team);
        if (!team.getIsFinished())
        {
            System.out.println("SERVER:\tTeam " + team.getColor() + " is playing");
            if(spiel.getLastDiceRoll() == null)
            {
                diceRoll = spiel.rollDice();
                spiel.setLastDiceRoll(diceRoll);
                System.out.println("SERVER:\tDice rolled: " + spiel.getLastDiceRoll());
            }
            else
            {
                System.out.println("SERVER:\tLast dice roll was null, now: " + spiel.getLastDiceRoll());
                diceRoll = spiel.getLastDiceRoll();
            }
            spiel.setCurrentlyPlaying(team);
            spiel.setLastDiceRoll(diceRoll);
            doBroadcastToAllClients(spiel);

            if(team.getClient() == null)
            {
                spiel.moveSpielstein(botSelection(spiel.selectPiece(team, spiel.getLastDiceRoll())), spiel.getLastDiceRoll(), team);
            } else {
                System.out.println("SERVER:\t\tTeam is a Player; Waiting for client to select stone");
                //doBroadcastToAllClients(spiel);
                int recivedSpielsteinNumber = clientsSelectStone(spiel.selectPiece(team, spiel.getLastDiceRoll()), team.getClient(), spiel, team);
                System.out.println("SERVER:\t\tSpielstein steht auf: "+ team.getSpielsteine().get(recivedSpielsteinNumber).getFieldId() + "; " +
                        "Walked fields pf spielstein: " + team.getSpielsteine().get(recivedSpielsteinNumber).getWalkedFields());
                spiel.moveSpielstein(team.getSpielsteine().get(recivedSpielsteinNumber), spiel.getLastDiceRoll(), team);
            }

            //TODO: wait till the movement on server is done, to ensure syncronisation
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(team.getIsFinished())
            {
                spiel.setIsGameRunning(false);
            }

            spiel.setLastDiceRoll(null);

            if (diceRoll == 6)
            {
                play(team);
            }
        }

        spiel.setLastDiceRoll(null);
    }

    private void doBroadcastToAllClients(Spiel spiel) {
        for(ClientHandler client : clients) {
            try {
                client.sendToClient(spiel);
            } catch (Exception e) {
                System.out.println("Could not send object to client!");
                e.printStackTrace();
//                client.getTeam().setIsBot(true);
//                clients.remove(client);
            }
        }
        System.out.println("SERVER:\t\tBroadcasted to all clients\n");

        //Now wait untill server recieves a confirmation (bool = true) from each client:
        for(ClientHandler client : clients) {
            try {
                boolean confirmation = (boolean) client.getInputStream().readObject();
                System.out.println("SERVER:\t\tRecived confirmation from client: " + confirmation);
            } catch (Exception e) {
                System.out.println("SERVER:\t\tCould not recive confirmation from client!");
                e.printStackTrace();
            }
        }
        System.out.println("SERVER:\t\tGot confirmation from all CLients\n");
    }

    private int clientsSelectStone(List<Spielstein> spielsteins, ClientHandler client, Spiel spiel, Team currentTeam) {
        //doBroadcastToAllClients(spiel);

        //doBroadcastToAllClients(spiel);
        System.out.println("SERVER:\t\tWaiting for client to select stone");
        try {
            return client.reciveSpielstein();
        } catch (Exception e) {
            System.out.println("SERVER:\t\tCould not recive object from client!");
            return 0;
        }
    }
    private void doBroadcastToOneCLient(ClientHandler client, Spiel spiel) {
        try {
            client.getOutputStream().writeObject(spiel);
            client.getOutputStream().flush();
            System.out.println("SERVER:\t\tBroadcasted to one client\n");
        } catch (Exception e) {
            System.out.println("SERVER:\t\tCould not send object to client!");
            e.printStackTrace();
//                client.getTeam().setIsBot(true);
//                clients.remove(client);
        }
    }

    private Spielstein botSelection(List<Spielstein> spielsteine) {
        //TODO: Better selction for bot + null handler
        if(spielsteine == null){
            System.out.println("SERVER:\t\tBot selection is null");
            return null;
        } else if(spielsteine.size() > 0){
            Random random = new Random();
            int rand = random.nextInt(spielsteine.size());
            return spielsteine.get(rand);
        } else {
            return null;
        }
    }


}