package gui;

import client.Client;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LauncherGUI extends JFrame {
    private JRadioButton singleplayerButton;
    private JRadioButton multiplayerButton;
    private JRadioButton hostButton;
    private JRadioButton connectButton;
    private JComboBox<Integer> playerCountComboBox;
    private JTextField serverAddressField;
    private JTextField portField;
    private JButton startButton;
    private JComboBox<Integer> botCountComboBox;
    private JRadioButton hostOnlyButton;
    private JOptionPane tooManySelected;

    public LauncherGUI() {
        setTitle("Game Launcher");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 1));

        singleplayerButton = new JRadioButton("Singleplayer");
        multiplayerButton = new JRadioButton("Multiplayer");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(singleplayerButton);
        modeGroup.add(multiplayerButton);

        playerCountComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        playerCountComboBox.setEnabled(true);
        //Selection of bot Count does nothing currently
        //TODO: add 0 if you want 0 bots or players
        botCountComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        botCountComboBox.setEnabled(true);

        hostButton = new JRadioButton("Host + self client");
        hostOnlyButton = new JRadioButton("Host Only");
        connectButton = new JRadioButton("Connect");
        ButtonGroup connectionGroup = new ButtonGroup();
        connectionGroup.add(hostButton);
        connectionGroup.add(hostOnlyButton);
        connectionGroup.add(connectButton);

        serverAddressField = new JTextField();
        serverAddressField = new JTextField(20);
        serverAddressField.setEnabled(false);

        portField = new JTextField();
        portField = new JTextField(6);
        portField.setEnabled(true);

        startButton = new JButton("Start");
        startButton.setEnabled(true);

        add(new JLabel("Select Mode:"));
        JPanel modePanel = new JPanel();
        modePanel.add(singleplayerButton);
        modePanel.add(multiplayerButton);
        add(modePanel);

        JPanel playerCountPanel = new JPanel();
        playerCountPanel.add(new JLabel("Select Player Count:"));
        playerCountPanel.add(playerCountComboBox);
        add(playerCountPanel);

        JPanel botCountPanel = new JPanel();
        playerCountPanel.add(new JLabel("Select Bot Count:"));
        playerCountPanel.add(botCountComboBox);
        add(botCountPanel);

        add(new JLabel("Select Connection Type (Multiplayer):"));
        JPanel connectionPanel = new JPanel();
        connectionPanel.add(hostButton);
        connectionPanel.add(hostOnlyButton);
        connectionPanel.add(connectButton);
        add(connectionPanel);

        JPanel serverPanel = new JPanel();
        serverPanel.add(new JLabel("Server Address:"));
        serverPanel.add(serverAddressField);
        add(serverPanel);

        JPanel portPanel = new JPanel();
        portPanel.add(new JLabel("Port:"));
        portPanel.add(portField);
        add(portPanel);

        JPanel startPanel = new JPanel();
        startPanel.add(startButton);
        add(startPanel);

        singleplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(false);
                portField.setEnabled(false);
                startButton.setEnabled(true);
                hostButton.setEnabled(false);
                hostOnlyButton.setEnabled(false);
                connectButton.setEnabled(false);
            }
        });

        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(true);
                portField.setEnabled(true);
                startButton.setEnabled(true);
                connectButton.setEnabled(true);
                hostOnlyButton.setEnabled(true);
                hostButton.setEnabled(true);
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(true);
                portField.setEnabled(true);
                playerCountComboBox.setEnabled(false);
            }
        });

        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(false);
                portField.setEnabled(false);
            }
        });

        hostOnlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(false);
                portField.setEnabled(false);
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call your start game function here
                boolean doStartGame = startGame();
                if(doStartGame) {
                    dispose();
                }
            }
        });
    }

    private boolean startGame() {
        // Perform actions to start the game based on user selections
        int playerCount = (int) playerCountComboBox.getSelectedItem();
        int botCount = (int) botCountComboBox.getSelectedItem();

        if((playerCount + botCount) > 4){
            JOptionPane.showMessageDialog(null, "Die Summe aus Spielern und Bots muss kleiner gleich 4 sein!");
            return false;
        }else {
            if (singleplayerButton.isSelected()) {
                Server server = new Server(true, playerCount);

                Thread serverThread = new Thread(server);

                serverThread.start();

                Client client = new Client("localhost", 8080);

                Thread clientThread = new Thread(client);

                clientThread.start();
            } else if (multiplayerButton.isSelected()) {
                if (hostButton.isSelected()) {
                    Server server = new Server(true, playerCount);

                    Thread serverThread = new Thread(server);

                    serverThread.start();

                    Client client = new Client("localhost", 8080);

                    Thread clientThread = new Thread(client);

                    clientThread.start();
                } else if (connectButton.isSelected()) {
                    String serverAddress = serverAddressField.getText();
                    int port = Integer.parseInt(portField.getText());
                    Client client = new Client(serverAddress, port);

                    Thread clientThread = new Thread(client);

                    clientThread.start();
                } else if(hostOnlyButton.isSelected()) {
                    Server server = new Server(true, playerCount);

                    Thread serverThread = new Thread(server);

                    serverThread.start();
                }
            }
        }

        // Start singleplayer game with the selected player count
        /*
        if(playerCount < 1 || playerCount > 4) {
            throw new IllegalArgumentException("Number of players must be between 1 and 4");
        }*/

        return true;

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LauncherGUI().setVisible(true);
            }
        });
    }
}
