import client.Client;
import gui.LauncherGUI;
import server.Server;

public class NewMain {
    public static void main(String[] args) {
        System.out.println("Program is starting...");
        LauncherGUI launcherGUI = new LauncherGUI();
        launcherGUI.setVisible(true);
    }
}