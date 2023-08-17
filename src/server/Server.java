package server;

import game.*;

import java.awt.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server implements Runnable{

    private int port;
    private final int numPlayers;
    private final int numBots;
    private Spiel spiel;
    private final int difficulty;
    private boolean isSavedGame = false;
    private final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private final transient List<ClientHandler> clients = new ArrayList<>();
    public Server(Spiel spiel, int numPlayers, int numBots, int difficulty, int port) {
        if(spiel == null){
            this.numPlayers = numPlayers;
            this.numBots = numBots;
            this.difficulty = difficulty;
            if(port == -1){
                this.port = 8080;
            } else {
                this.port = port;
            }
        } else {
            this.spiel = spiel;
            this.numBots = spiel.getNumBots();
            this.numPlayers = spiel.getNumPlayers();
            this.difficulty = spiel.getDifficulty();
            isSavedGame = true;
        }



//        if(!(numPlayers < 4 && numPlayers >= 1)){
//            throw new IllegalArgumentException("Number of players must be between 1 and 4");
//        }


    }

    @Override
    public void run()  {
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
            if(!isSavedGame) {
                spiel = new Spiel(numPlayers, numBots, clients, colors, difficulty);
            } else {
                spiel.setClients(clients, colors, isSavedGame);
            }
            System.out.println("SERVER:\t\tDone generating game requirements");

            System.out.println("SERVER:\t\tNumber of players: " + numPlayers);
            System.out.println("SERVER:\t\tNumber of Teams: " + spiel.getTeams().size());

            System.out.println(spiel.getSpielfeld().toString());
            for(Team team : spiel.getTeams()){
                System.out.println(team.toString());
            }


            Thread.sleep(2000);

            startGame();



        } catch (Exception e) {
            System.out.println("SERVER:\t\tServer could not be started!");
            e.printStackTrace();
        }
    }

    private void startGame() throws InterruptedException {
        System.out.println("SERVER:\t\tTeams:");
        for(Team team : spiel.getTeams()) {
            System.out.println(team.getColor());
        }


        if(isSavedGame) {
            System.out.println("SERVER:\t\tGame is saved. Continuing...");
            System.out.println("SERVER:\t\tCurrently playing: " + spiel.getCurrentlyPlaying().getColor());
            Thread.sleep(2000);
            int i = spiel.getTeams().indexOf(spiel.getCurrentlyPlaying());
            if(i != -1){
                for(int j = i; j < spiel.getTeams().size(); j++){
                    Thread.sleep(2000);
                    play(spiel.getTeams().get(j));
                }
            }
        }

        while(spiel.isGameIsRunning()) {
            for(int i = 0; i < spiel.getTeams().size(); i++){

                System.out.println("SERVER:\t\tTeam " + spiel.getTeams().get(i).getColor() + " ist am Zug! Nr.: " + i);
                play(spiel.getTeams().get(i));
                Thread.sleep(2000);
                if(!spiel.isGameIsRunning()){
                    break;
                }
            }
        }


    }

    private void play(Team team) {
        int diceRoll;
        spiel.setCurrentlyPlaying(team);
        if (team.checkIfAllPiecesAreInStart())
        {
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
            int selection = -1;

            if(team.getIsBot())
            {
                List<Spielstein> movablePieces = new ArrayList<>();
                movablePieces = spiel.selectPiece(team, spiel.getLastDiceRoll());
                selection = movablePieces.indexOf(botSelection(movablePieces));
                spiel.moveSpielstein(botSelection(movablePieces), spiel.getLastDiceRoll(), team);
            } else {
                System.out.println("SERVER:\t\tTeam is a Player; Waiting for client to select stone");
                //doBroadcastToAllClients(spiel);
                int recivedSpielsteinNumber = clientsSelectStone(team.getClient(), team);
                if(checkIfSelectionIsValid(recivedSpielsteinNumber, team)) {
                    selection = recivedSpielsteinNumber;
                    if (recivedSpielsteinNumber != -1) {
                        System.out.println("SERVER:\t\tSpielstein steht auf: "+ team.getSpielsteine().get(recivedSpielsteinNumber).getFieldId() + "; " +
                                "Walked fields pf spielstein: " + team.getSpielsteine().get(recivedSpielsteinNumber).getWalkedFields());
                        spiel.moveSpielstein(team.getSpielsteine().get(recivedSpielsteinNumber), spiel.getLastDiceRoll(), team);
                    }
                }
            }

            doBroadcastToAllClientsPieceSelection(selection);

            /*
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
             */

            if(spiel.checkIfGameIsFinished())
            {
                spiel.setIsGameRunning(false);
                return;
            }

            spiel.setLastDiceRoll(null);

            if (diceRoll == 6)
            {
                play(team);
            }
        }

        spiel.setLastDiceRoll(null);

    }

    private void doBroadcastToAllClientsPieceSelection(int selection) {
        for(ClientHandler client : clients) {
            try {
                client.sendSelectionToClient(selection);
            } catch (Exception e) {
                System.out.println("Could not send object to client!");
                e.printStackTrace();

                client.getTeam().setIsBot(true);
                clients.remove(client);
                try{
                    client.getClient().close();
                    return;
                } catch (Exception ex) {
                    System.out.println("Could not close client socket!");
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("SERVER:\t\tBroadcasted Selection to all clients\n");

        //Now wait untill server recieves a confirmation (bool = true) from each client:
        getConfirmationFromAllClients();
    }

    /**
     *
     * @param receivedSpielsteinNumber
     * @param team
     * @return
     */
    private boolean checkIfSelectionIsValid(int receivedSpielsteinNumber, Team team) {
        List<Spielstein> movablePieces = spiel.selectPiece(team, spiel.getLastDiceRoll());
        if(movablePieces == null){
            if(receivedSpielsteinNumber == -1){
                return true;
            } else {
                return false;
            }
        }

        if(receivedSpielsteinNumber <= movablePieces.size()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sends the game to all connected clients
     *
     * @param spiel   the game to be sent
     */
    private void doBroadcastToAllClients(Spiel spiel) {
        for(ClientHandler client : this.clients) {
            try {
                client.sendToClient(spiel);
            } catch (Exception e) {
                System.out.println("Could not send object to client!");
                e.printStackTrace();
                client.getTeam().setIsBot(true);
                clients.remove(client);
                try{
                    client.getClient().close();
                    return;
                } catch (Exception ex) {
                    System.out.println("Could not close client socket!");
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("SERVER:\t\tBroadcasted to all clients\n");

        //Now wait untill server recieves a confirmation (bool = true) from each client:
        getConfirmationFromAllClients();
    }

    private void getConfirmationFromAllClients() {
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

    private int clientsSelectStone(ClientHandler client, Team currentTeam) {
        System.out.println("SERVER:\t\tWaiting for client to select stone");
        try {
            return client.receivePiece();
        } catch (Exception e) {
            System.out.println("SERVER:\t\tCould not recive object from client!");
            currentTeam.setIsBot(true);
            return 0;
        }
    }

    private Spielstein botSelection(List<Spielstein> spielsteine) {
        if(spielsteine == null){
            System.out.println("SERVER:\t\tBot selection is null");
            return null;
        } else if(spielsteine.size() == 1) {;
            return spielsteine.get(0);
        } else if(spielsteine.size() > 1) {
            return selectPieceOnDifficulty(spielsteine);
        } else {
            return null;
        }
    }

    private Spielstein selectPieceOnDifficulty(List<Spielstein> spielsteine) {
        Spielstein walkingLeast = null;
        switch(difficulty){
            case 0:
                for (Spielstein spielstein : spielsteine) {
                    if (spielstein.getWalkedFields() < spielstein.getWalkedFields()) {
                        walkingLeast = spielstein;
                    }
                }
                return walkingLeast;
            case 1:
                Random random = new Random();
                int rand = random.nextInt(spielsteine.size());
                return spielsteine.get(rand);
            case 2:
                for (Spielstein spielstein : spielsteine) {
                    if (spielstein.getWalkedFields() > spielstein.getWalkedFields()) {
                        walkingLeast = spielstein;
                    }
                }
                return walkingLeast;
            default:
                return spielsteine.get(0);

        }
    }


}