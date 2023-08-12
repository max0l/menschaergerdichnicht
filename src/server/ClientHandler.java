package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Serializable {
    private ServerSocket server;
    private Socket client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public ClientHandler(Socket client, ServerSocket server) throws IOException {
        System.out.println("Trying to creade a new client handler");
        this.client = client;
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        //this.inputStream = new ObjectInputStream(server.getInputStream());
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
}