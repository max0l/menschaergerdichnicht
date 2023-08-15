package server;

import game.Spiel;
import game.Spielfeld;
import game.Spielstein;
import game.Team;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private volatile Team team;
    public ClientHandler(Socket client, ServerSocket server) throws IOException {
        System.out.println("Trying to creade a new client handler");
        this.client = client;
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        this.inputStream = new ObjectInputStream(client.getInputStream());
        System.out.println("Done");

    }

    public Socket getClient() {
        return client;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }


    //will recive a number from the client and return it
    public int reciveNumber() throws IOException {
        int number = inputStream.readInt();
        return number;
    }

    public void sendToClient(Spiel spiel) throws IOException, CloneNotSupportedException {
        System.out.println("SERVER:\tSending object to client");
        //System.out.println("SERVER:\t\t" + spiel.getSpielfeld().toString());
           outputStream.writeObject((Spiel) spiel.clone());
           outputStream.writeObject((Spielfeld) spiel.getSpielfeld().clone());
           for(int i = 0; i<spiel.getTeams().size(); i++) {
               outputStream.writeObject((Team) spiel.getTeams().get(i).clone());
               for(int j = 0; j<spiel.getTeams().get(i).getSpielsteine().size(); j++) {
                   outputStream.writeObject((Spielstein) spiel.getTeams().get(i).getSpielsteine().get(j).clone());
               }
           }
           outputStream.flush();
        System.out.println("SERVER:\tObject sent");
    }

    public int reciveSpielstein() throws IOException, ClassNotFoundException {
        int spielsteinNumber = inputStream.readInt();
        System.out.println("SERVER:\tSpielstein recived");
        return spielsteinNumber;
    }

    public void setTeam(Team team) {
        this.team = team;
        try{
            outputStream.writeObject(team.getColor());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: set to bot if fail
        }

    }

    public Team getTeam() {
        return team;
    }

    public void checkIfClientWouldRecieveCorrectData(Spiel spiel, Team currentTeam) {
        if(spiel.getCurrentlyPlaying() != currentTeam){
            System.out.println("SERVER:\t\t________________________________________________Client would recieve wrong data________________________________________________");
        }
    }
}