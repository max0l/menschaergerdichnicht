package server;

import game.Spiel;
import game.Team;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler {
    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private volatile Team team;

    /**
     * The Constructor of the ClientHandler class.
     * @param client the client's socket.
     * @throws IOException
     */
    public ClientHandler(Socket client) throws IOException {
        System.out.println("Trying to creade a new client handler");
        this.client = client;
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        this.inputStream = new ObjectInputStream(client.getInputStream());
        System.out.println("Done");
    }

    /**
     * Gets the client of the ClientHandler.
     * @return the client's socket.
     */
    public Socket getClient() {
        return client;
    }

    /**
     * Gets the input stream of the ClientHandler.
     * @return the inputStream member variable.
     */
    public ObjectInputStream getInputStream() {
        return inputStream;
    }


    /**
     * Sends a clone of the game object to the client.
     * @param spiel the game object that will be cloned and sent.
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public void sendToClient(Spiel spiel) throws IOException, CloneNotSupportedException {
        System.out.println("SERVER:\tSending object to client");
        outputStream.writeObject(spiel.clone());
        outputStream.flush();
        System.out.println("SERVER:\tObject sent");
    }

    /**
     * Receives the index of the piece the client has selected.
     * @return the selected pieces index.
     * @throws IOException
     */
    public int receivePiece() throws IOException {
        int spielsteinNumber = inputStream.readInt();
        System.out.println("SERVER:\tSpielstein recived");
        return spielsteinNumber;
    }

    /**
     * Sets the Team for the ClientHandler. The Team will be played by a bot if this fails.
     * @param team the team that will be set.
     */
    public void setTeam(Team team) {
        this.team = team;
        try{
            outputStream.writeObject(team.getColor());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            team.setIsBot(true);
        }

    }

    /**
     * Gets the team of the ClientHandler
     * @return the Team.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sends the selected number to the output stream.
     * @param selection
     * @throws IOException
     */
    public void sendSelectionToClient(int selection) throws IOException {
        outputStream.writeInt(selection);
        outputStream.flush();
    }
}