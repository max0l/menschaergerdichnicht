package gui;

import client.Client;
import game.Spiel;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;

public class LauncherGUI extends JFrame {
    private final JRadioButton singleplayerButton;
    private final JRadioButton multiplayerButton;
    private final JComboBox<Integer> playerCountComboBox;
    private final JComboBox<Integer> botCountComboBox;
    private final JComboBox<String> difficultyComboBox;
    private final JRadioButton hostButton;
    private final JRadioButton hostOnlyButton;
    private final JRadioButton connectButton;
    private final JTextField serverAddressField;
    private final JTextField portField;
    private final JButton startButton;

    private final JLabel selectedFileLabel; // Added label to display selected file name

    private final JFileChooser fileChooser;
    private Spiel spiel = null;

    public LauncherGUI() {
        setTitle("Game Launcher");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1)); // Each panel on a new line

        JPanel gameSettingsTextPanel = new JPanel(); // Panel for "Game Settings" text
        gameSettingsTextPanel.add(new JLabel("Game Settings:"));
        add(gameSettingsTextPanel);

        JPanel playerBotCountPanel = new JPanel(); // Combined panel for player and bot count
        playerBotCountPanel.add(new JLabel("Select Player Count:"));
        playerCountComboBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4});
        playerCountComboBox.setSelectedIndex(1);
        playerBotCountPanel.add(playerCountComboBox);
        playerBotCountPanel.add(new JLabel("Select Bot Count:"));
        botCountComboBox = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4});
        botCountComboBox.setSelectedIndex(3);
        playerBotCountPanel.add(botCountComboBox);
        add(playerBotCountPanel);

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.add(new JLabel("Select Difficulty:"));
        difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Normal", "Hard"});
        difficultyComboBox.setSelectedIndex(1);
        difficultyPanel.add(difficultyComboBox);
        add(difficultyPanel);

        JPanel modeTextPanel = new JPanel(); // Panel for "Select mode" text
        modeTextPanel.add(new JLabel("Select Mode:"));
        add(modeTextPanel);

        JPanel modePanel = new JPanel(); // Panel for single/multiplayer radio buttons
        singleplayerButton = new JRadioButton("Singleplayer");
        multiplayerButton = new JRadioButton("Multiplayer");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(singleplayerButton);
        modeGroup.add(multiplayerButton);
        modePanel.add(singleplayerButton);
        modePanel.add(multiplayerButton);
        add(modePanel);

        JPanel connectionTextPanel = new JPanel(); // Panel for connection type text
        connectionTextPanel.add(new JLabel("Select Connection Type (Multiplayer):"));
        add(connectionTextPanel);

        JPanel connectionPanel = new JPanel(); // Panel for connection type radio buttons
        hostButton = new JRadioButton("Host + self client");
        hostOnlyButton = new JRadioButton("Host Only");
        connectButton = new JRadioButton("Connect");
        ButtonGroup connectionGroup = new ButtonGroup();
        connectionGroup.add(hostButton);
        connectionGroup.add(hostOnlyButton);
        connectionGroup.add(connectButton);
        connectionPanel.add(hostButton);
        connectionPanel.add(hostOnlyButton);
        connectionPanel.add(connectButton);
        add(connectionPanel);

        JPanel serverPanel = new JPanel();
        serverPanel.add(new JLabel("Server ip:"));
        serverAddressField = new JTextField(20);
        serverPanel.add(serverAddressField);
        add(serverPanel);

        JPanel portPanel = new JPanel();
        portPanel.add(new JLabel("Port:"));
        portField = new JTextField(6);
        portPanel.add(portField);
        add(portPanel);

        JPanel loadPanel = new JPanel();
        JButton loadButton = new JButton("Load Savegame (Optional)");
        loadPanel.add(loadButton);
        add(loadPanel);

        selectedFileLabel = new JLabel();
        add(selectedFileLabel);

        JPanel startPanel = new JPanel();
        startButton = new JButton("Start");
        startPanel.add(startButton);
        add(startPanel);

        // FileChooser setup
        fileChooser = new JFileChooser();

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(LauncherGUI.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFileLabel.setText("Selected File: " + selectedFile.getName());

                    loadSpielFromFile(selectedFile);
                }
            }
        });


        singleplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(false);
                portField.setEnabled(true);
                startButton.setEnabled(true);
                hostButton.setEnabled(false);
                hostOnlyButton.setEnabled(false);
                connectButton.setEnabled(false);
                playerCountComboBox.setEnabled(false);
                playerCountComboBox.setSelectedIndex(1);
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
                playerCountComboBox.setEnabled(true);
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(true);
                portField.setEnabled(true);
                playerCountComboBox.setEnabled(false);
                botCountComboBox.setEnabled(false);
                difficultyComboBox.setEnabled(false);
                playerCountComboBox.setEnabled(false);
            }
        });

        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(false);
                portField.setEnabled(true);
                playerCountComboBox.setEnabled(true);
                botCountComboBox.setEnabled(true);
                difficultyComboBox.setEnabled(true);
                serverAddressField.setEnabled(false);
            }
        });

        hostOnlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddressField.setEnabled(false);
                portField.setEnabled(true);
                playerCountComboBox.setEnabled(true);
                botCountComboBox.setEnabled(true);
                difficultyComboBox.setEnabled(true);
                serverAddressField.setEnabled(false);
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        int difficulty = difficultyComboBox.getSelectedIndex();

        System.out.println("PlayerCound: " + playerCount + ", botCount: " + botCount + ", difficulty: " + difficulty);

        if((playerCount + botCount) > 4 || (playerCount + botCount) == 0){
            JOptionPane.showMessageDialog(null, "Die Summe aus Spielern und Bots muss" +
                    "\nkleiner gleich 4 und größer 0 sein!");
            return false;
        } else if(!multiplayerButton.isSelected() && !singleplayerButton.isSelected()){
            JOptionPane.showMessageDialog(null, "Please select a mode!");
            return false;
        } else {
            int port = -1;

            String sPort = portField.getText();
            port = checkIfPortIsValid(sPort);
            if(portField.getText().equals("") || port == -1) {
                JOptionPane.showMessageDialog(null, "Please enter a Valid port!");
                return false;
            }

            if (singleplayerButton.isSelected()) {

                if(checkIfPortIsOccupied(port)){
                    JOptionPane.showMessageDialog(null, "Port is already in use!");
                    return false;
                }
                Server server = new Server(spiel, playerCount, botCount, difficulty, port);

                Thread serverThread = new Thread(server);

                serverThread.start();

                Client client = new Client("localhost", port);

                Thread clientThread = new Thread(client);

                clientThread.start();
            } else if (multiplayerButton.isSelected()) {


                if (hostButton.isSelected()) {

                    if(checkIfPortIsOccupied(port)){
                        JOptionPane.showMessageDialog(null, "Port is already in use!");
                        return false;
                    }
                    Server server = new Server(spiel, playerCount, botCount, difficulty, port);

                    Thread serverThread = new Thread(server);

                    serverThread.start();

                    Client client = new Client("localhost", port);

                    Thread clientThread = new Thread(client);

                    clientThread.start();
                } else if (connectButton.isSelected()) {

                    String serverAddressFromField = serverAddressField.getText();
                    if(!isServerAddressValid(serverAddressFromField)){
                        JOptionPane.showMessageDialog(null, "Please enter a valid server address!");
                        return false;
                    }
                    Client client = new Client(serverAddressFromField, port);

                    Thread clientThread = new Thread(client);

                    clientThread.start();
                } else if(hostOnlyButton.isSelected()) {

                    if(checkIfPortIsOccupied(port)){
                        JOptionPane.showMessageDialog(null, "Port is already in use!");
                        return false;
                    }

                    Server server = new Server(spiel, playerCount, botCount, difficulty, port);

                    Thread serverThread = new Thread(server);

                    serverThread.start();
                }
            }
        }

        return true;

    }



    private boolean checkIfPortIsOccupied(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
            return false; // Port is free
        } catch (IOException e) {
            return true; // Port is not free
        }
    }

    private boolean isServerAddressValid(String serverAddressFromField) {
        String ipv4Pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return serverAddressFromField.matches(ipv4Pattern) || serverAddressFromField.equals("localhost");
    }

    private int checkIfPortIsValid(String portStr) {
        try {
            int port = Integer.parseInt(portStr);
            if (port >= 0 && port <= 65535) {
                return port;
            } else {
                return -1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void loadSpielFromFile(File file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file)))
        {
            spiel = (Spiel) inputStream.readObject();
            System.out.println("Save Game loaded!");
            String old = selectedFileLabel.getText();
            selectedFileLabel.setText(old + " - Loaded Successfully!");
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            selectedFileLabel.setText("Error loading file!, Try again or start without");
        }
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
