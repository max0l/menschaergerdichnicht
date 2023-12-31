package gui;

import client.Client;
import game.Game;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.Objects;

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
    private Game game = null;

    /**
     * The Constructor of the LauncherGUI class.
     * Sets up the launcher's gui and handles user interaction with action listeners.
     */
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
        portField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startButton.doClick(); // Programmatically trigger the button click
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

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

                    loadGameFromFile(selectedFile);
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
                if (doStartGame) {
                    dispose();
                }
            }
        });
    }

    /**
     * Performs checks on the selections of the user and displays an error dialog if the user
     * has made invalid selections. If all checks are met this function starts the client, the server or both and returns true.
     *
     * @return {@code true} if the game has been started successfully, otherwise {@code false}
     */
    private boolean startGame() {
        // Perform actions to start the game based on user selections
        int playerCount = (int) playerCountComboBox.getSelectedItem();
        int botCount = (int) botCountComboBox.getSelectedItem();
        int difficulty = difficultyComboBox.getSelectedIndex();

        System.out.println("PlayerCound: " + playerCount + ", botCount: " + botCount + ", difficulty: " + difficulty);

        if ((playerCount + botCount) > 4 || (playerCount + botCount) == 0) {
            JOptionPane.showMessageDialog(null, "Die Summe aus Spielern und Bots muss" +
                    "\nkleiner gleich 4 und größer 0 sein!");
            return false;
        } else if (!multiplayerButton.isSelected() && !singleplayerButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select a mode!");
            return false;
        } else {
            int port;

            String sPort = portField.getText();
            port = checkIfPortIsValid(sPort);
            if (port == -1) {
                JOptionPane.showMessageDialog(null, "Please enter a Valid port!");
                return false;
            }

            if (singleplayerButton.isSelected()) {

                if (checkIfPortIsOccupied(port)) {
                    JOptionPane.showMessageDialog(null, "Port is already in use!");
                    return false;
                }
                Server server = new Server(game, playerCount, botCount, difficulty, port);

                Thread serverThread = new Thread(server);

                serverThread.start();

                Client client = new Client("localhost", port);

                Thread clientThread = new Thread(client);

                clientThread.start();
            } else if (multiplayerButton.isSelected()) {
                if (!hostButton.isSelected() && !connectButton.isSelected() && !hostOnlyButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please select a Multiplayer mode!");
                    return false;
                }
                if (hostButton.isSelected()) {

                    if (checkIfPortIsOccupied(port)) {
                        JOptionPane.showMessageDialog(null, "Port is already in use!");
                        return false;
                    }
                    Server server = new Server(game, playerCount, botCount, difficulty, port);

                    Thread serverThread = new Thread(server);

                    serverThread.start();

                    Client client = new Client("localhost", port);

                    Thread clientThread = new Thread(client);

                    clientThread.start();
                } else if (connectButton.isSelected()) {

                    String serverAddressFromField = serverAddressField.getText();
                    if (!isServerAddressValid(serverAddressFromField)) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid server address!");
                        return false;
                    }
                    Client client = new Client(serverAddressFromField, port);

                    Thread clientThread = new Thread(client);

                    clientThread.start();
                } else if (hostOnlyButton.isSelected()) {

                    if (checkIfPortIsOccupied(port)) {
                        JOptionPane.showMessageDialog(null, "Port is already in use!");
                        return false;
                    }

                    Server server = new Server(game, playerCount, botCount, difficulty, port);

                    Thread serverThread = new Thread(server);

                    serverThread.start();
                }
            }
        }
        return true;
    }


    /**
     * Tries to create a ServerSocket on the specified port to check if the given
     * port is available or if it's already in use.
     *
     * @param port the port to be checked
     * @return {@code true} if the port is Occupied and {@code false} if it's not in use.
     */
    private boolean checkIfPortIsOccupied(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.close();
            return false; // Port is free
        } catch (IOException e) {
            return true; // Port is not free
        }
    }

    /**
     * Checks if the given server address is a valid address or not
     *
     * @param serverAddressFromField the address that has to be checked
     * @return {@code true} if the server address is a valid address, otherwise {@code false}
     */
    private boolean isServerAddressValid(String serverAddressFromField) {
        //Source: https://stackoverflow.com/Questions/5667371/validate-ipv4-address-in-java
        String ipv4Pattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return serverAddressFromField.matches(ipv4Pattern) || serverAddressFromField.equals("localhost");
    }

    /**
     * Checks if a given port is in a valid port range
     *
     * @param portStr the port that needs to be checked
     * @return the port if it's in a valid port range, otherwise {@code -1}
     */
    private int checkIfPortIsValid(String portStr) {
        int port;
        try {
            if (!Objects.equals(portStr, "")) {
                port = Integer.parseInt(portStr);
                if (port >= 0 && port <= 65535) {
                    return port;
                } else {
                    return -1;
                }

            } else {
                port = 47399;
                return port;
            }

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Tries to load a save game
     *
     * @param file the file to load the save game from
     */
    private void loadGameFromFile(File file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            game = (Game) inputStream.readObject();
            System.out.println("Save Game loaded!");
            String old = selectedFileLabel.getText();
            selectedFileLabel.setText(old + " - Loaded Successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            selectedFileLabel.setText("Error loading file!, Try again or start without");
        }
    }
}