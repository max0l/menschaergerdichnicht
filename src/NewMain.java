import client.Client;
import server.Server;

public class NewMain {
    public static void main(String[] args) {
        System.out.println("Prohramm is Starting...");

        Server server = new Server(true, 1);

//        Thread serverThread = new Thread(server);
//
//        serverThread.start();

        server.run();

    }
}