package server;

import game.Spiel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientsBroadcast implements Serializable {
    private List<ClientHandler> clients = new ArrayList<>();

    public ClientsBroadcast(List<ClientHandler> clients) {
        this.clients = clients;
    }

    //Send gamestate to all clients
    public void broadcast(Spiel object) {
        for(ClientHandler client : clients) {
            try {
                client.getOutputStream().writeObject(object);
                client.getOutputStream().flush();
            } catch (Exception e) {
                System.out.println("Could not send object to client!");
            }
        }
    }
}