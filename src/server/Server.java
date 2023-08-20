package server;

import game.Game;
import game.Piece;
import game.Team;

import java.awt.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server implements Runnable {

    private int port;
    private final int numPlayers;
    private final int numBots;
    private Game game;
    private final int difficulty;
    private boolean isSavedGame = false;
    private final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    private final transient List<ClientHandler> clients = new ArrayList<>();

    /**
     * The Constructor of the Server class. Initialized member variables.
     *
     * @param game       the game object.
     * @param numPlayers the number of players.
     * @param numBots    the number of bots.
     * @param difficulty the difficulty of bots.
     * @param port       the port the game uses.
     */
    public Server(Game game, int numPlayers, int numBots, int difficulty, int port) {
        if (game == null) {
            this.numPlayers = numPlayers;
            this.numBots = numBots;
            this.difficulty = difficulty;
            if (port == -1) {
                this.port = 8080;
            } else {
                this.port = port;
            }
        } else {
            this.game = game;
            this.numBots = game.getNumBots();
            this.numPlayers = game.getNumPlayers();
            this.difficulty = game.getDifficulty();
            isSavedGame = true;
        }
    }

    /**
     * Starts up the server and waits for clients to connect.
     * If all clients are connected, the game will be set up and started.
     */
    @Override
    public void run() {
        try {
            System.out.println("SERVER:\t\tStarting Server on Port: " + port);
            ServerSocket server = new ServerSocket(port);
            System.out.println("SERVER:\t\tServer Started!");

            while (clients.size() < numPlayers) {
                System.out.println("SERVER:\t\tWaiting for clients...");
                System.out.println("SERVER:\t\tClients connected: " + clients.size() + "/" + numPlayers);
                clients.add(new ClientHandler(server.accept()));
                System.out.println("SERVER:\t\tA client connected.");
            }

            System.out.println("SERVER:\t\tAll clients connected!");


            //Generate game requirements
            System.out.println("SERVER:\t\tGenerating game requirements...");
            if (!isSavedGame) {
                game = new Game(numPlayers, numBots, clients, colors, difficulty);
            } else {
                game.setClients(clients, colors, isSavedGame);
            }
            System.out.println("SERVER:\t\tDone generating game requirements");

            System.out.println("SERVER:\t\tNumber of players: " + numPlayers);
            System.out.println("SERVER:\t\tNumber of Teams: " + game.getTeams().size());

            startGame();

        } catch (Exception e) {
            System.out.println("SERVER:\t\tServer could not be started!");
            e.printStackTrace();
        }
    }

    /**
     * Starts the Game on the Server.
     */
    private void startGame() {
        if (isSavedGame) {
            System.out.println("SERVER:\t\tGame is saved. Continuing...");
            System.out.println("SERVER:\t\tCurrently playing: " + game.getCurrentlyPlaying().getColor());
            int i = game.getTeams().indexOf(game.getCurrentlyPlaying());
            if (i != -1) {
                decideHowManyDiceRolls(game.getTeams().get(i));
            }
        }

        while (game.isGameIsRunning()) {
            for (int i = 0; i < game.getTeams().size(); i++) {

                System.out.println("SERVER:\t\tTeam " + game.getTeams().get(i).getColor() + " ist am Zug! Nr.: " + i);
                decideHowManyDiceRolls(game.getTeams().get(i));
                if (!game.isGameIsRunning()) {
                    break;
                }
            }
        }
    }

    /**
     * Decides how many dice rolls a team gets. If all pieces are in start, the team gets 3 rolls.
     *
     * @param team the playing team.
     */
    private void decideHowManyDiceRolls(Team team) {
        if (team.checkIfAllPiecesAreInStart()) {
            for (int k = 0; k < 3; k++) {
                game.setLastDiceRoll(game.rollDice());
                if (game.getLastDiceRoll() == 6) {
                    play(team);
                    break;
                } else {
                    play(team);
                }

            }
        } else {
            play(team);
        }
    }

    /**
     * Lets a team play their move.
     *
     * @param team the playing team.
     */
    private void play(Team team) {
        int diceRoll;
        game.setCurrentlyPlaying(team);
        if (!team.getIsFinished()) {
            System.out.println("SERVER:\t\tTeam " + team.getColor() + " is playing");
            if (game.getLastDiceRoll() == null) {
                diceRoll = game.rollDice();
                game.setLastDiceRoll(diceRoll);
                System.out.println("SERVER:\t\tDice rolled: " + game.getLastDiceRoll());
            } else {
                System.out.println("SERVER:\t\tDice Roll is now: " + game.getLastDiceRoll());
                diceRoll = game.getLastDiceRoll();
            }
            game.setCurrentlyPlaying(team);
            game.setLastDiceRoll(diceRoll);
            try {
                doBroadcastToAllClients(game.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            int selection = -1;

            if (team.getIsBot()) {
                List<Piece> movablePieces;
                movablePieces = game.selectPiece(team, game.getLastDiceRoll());
                if (movablePieces != null) {
                    selection = movablePieces.indexOf(botSelection(movablePieces));
                }
                game.moveSpielstein(botSelection(movablePieces), game.getLastDiceRoll(), team);
            } else {
                System.out.println("SERVER:\t\tTeam is a Player; Waiting for client to select stone");
                int receivedSpielsteinNumber = clientsSelectStone(team.getClient(), team);
                if (checkIfSelectionIsValid(receivedSpielsteinNumber, team)) {
                    selection = receivedSpielsteinNumber;
                    if (receivedSpielsteinNumber != -1) {
                        game.moveSpielstein(team.getSpielsteine().get(receivedSpielsteinNumber), game.getLastDiceRoll(), team);
                    }
                }
            }

            doBroadcastToAllClientsPieceSelection(selection);

            if (game.checkIfGameIsFinished()) {
                game.setIsGameRunning(false);
                return;
            }

            game.setLastDiceRoll(null);

            if (diceRoll == 6) {
                game.setLastDiceRoll(null);
                play(team);
            }
        }

        game.setLastDiceRoll(null);

    }

    /**
     * Sends a piece selection message to all players. If a player does not
     * respond, he will be replaced with a bot.
     *
     * @param selection the index of the piece that was selected.
     */
    private void doBroadcastToAllClientsPieceSelection(int selection) {
        for (ClientHandler client : clients) {
            try {
                client.sendSelectionToClient(selection);
            } catch (Exception e) {
                System.out.println("SERVER:\t\tCould not send object to client!");
                e.printStackTrace();

                client.getTeam().setIsBot(true);
                clients.remove(client);
                try {
                    client.getClient().close();
                    return;
                } catch (Exception ex) {
                    System.out.println("SERVER:\t\tCould not close client socket!");
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("SERVER:\t\tBroadcasted Selection to all clients\n");
        getConfirmationFromAllClients();
    }

    /**
     * Checks whether the piece the client has selected for his turn is valid.
     *
     * @param receivedSpielsteinNumber the index of the piece that was selected.
     * @param team                     the team the piece will be checked for.
     * @return {@code true} if the selection was valid. Otherwise {@code false}.
     */
    private boolean checkIfSelectionIsValid(int receivedSpielsteinNumber, Team team) {
        List<Piece> movablePieces = game.selectPiece(team, game.getLastDiceRoll());
        if (movablePieces == null) {
            return receivedSpielsteinNumber == -1;
        }

        return receivedSpielsteinNumber <= movablePieces.size();
    }

    /**
     * Lets the client select a piece for his turn.
     * @param game the game object.
     */
    private void doBroadcastToAllClients(Game game) {
        for (ClientHandler client : this.clients) {
            try {
                client.sendToClient(game);

            } catch (Exception e) {
                System.out.println("SERVER:\t\tCould not send object to client!");
                clientConfirmationErrorHandler(client, e);
            }
        }
        System.out.println("SERVER:\t\tBroadcasted to all clients\n");
        getConfirmationFromAllClients();
    }

    /**
     * Checks if the Server can receive a confirmation from all connected clients.
     */
    private void getConfirmationFromAllClients() {
        System.out.println("SERVER:\t\tWaiting for confirmation from all clients");
        for (ClientHandler client : clients) {
            try {
                boolean confirmation = client.getInputStream().readBoolean();
                System.out.println("SERVER:\t\tRecived confirmation from client: " + confirmation);
            } catch (Exception e) {
                System.out.println("SERVER:\t\tCould not get confirmation from client!");
                clientConfirmationErrorHandler(client, e);
            }
        }
        System.out.println("SERVER:\t\tGot confirmation from all CLients\n");
    }

    /**
     * Handles the error that occurs when a client does not respond. The client will be replaced with a bot.
     *
     * @param client the client that did not respond.
     * @param e      the exception that was thrown.
     */
    private void clientConfirmationErrorHandler(ClientHandler client, Exception e) {
        e.printStackTrace();
        client.getTeam().setIsBot(true);
        clients.remove(client);
        try {
            client.getClient().close();
        } catch (Exception ex) {
            System.out.println("SERVER:\t\tCould not close client socket!");
            ex.printStackTrace();
        }
    }

    /**
     * Lets the Server wait for a client to select the piece to move with.
     *
     * @param client      the client that needs to move.
     * @param currentTeam the clients team.
     * @return the index of the selected piece.
     */
    private int clientsSelectStone(ClientHandler client, Team currentTeam) {
        System.out.println("SERVER:\t\tWaiting for client to select stone");
        try {
            return client.receivePiece();
        } catch (Exception e) {
            System.out.println("SERVER:\t\tCould not recive object from client!");
            currentTeam.setIsBot(true);
            return -1;
        }
    }

    /**
     * Lets the Bot select a piece to play with.
     *
     * @param spielsteine List of pieces that could be moved.
     * @return the piece that got selected.
     */
    private Piece botSelection(List<Piece> spielsteine) {
        if (spielsteine == null) {
            return null;
        } else if (spielsteine.size() == 1) {
            return spielsteine.get(0);
        } else if (spielsteine.size() > 1) {
            return selectPieceOnDifficulty(spielsteine);
        } else {
            return null;
        }
    }

    /**
     * Selects a piece based on the Bot difficulty.
     *
     * @param spielsteine List of pieces that could be moved.
     * @return the piece that got selected.
     */
    private Piece selectPieceOnDifficulty(List<Piece> spielsteine) {
        Piece walkingLeast = null;
        switch (difficulty) {
            case 0:
                for (Piece piece : spielsteine) {
                    if (piece.getWalkedFields() < piece.getWalkedFields()) {
                        walkingLeast = piece;
                    }
                }
                return walkingLeast;
            case 1:
                Random random = new Random();
                int rand = random.nextInt(spielsteine.size());
                return spielsteine.get(rand);
            case 2:
                for (Piece piece : spielsteine) {
                    if (piece.getWalkedFields() > piece.getWalkedFields()) {
                        walkingLeast = piece;
                    }
                }
                return walkingLeast;
            default:
                return spielsteine.get(0);

        }
    }
}