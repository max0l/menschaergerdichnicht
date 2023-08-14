import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Menue {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
    private static Main mainInstance;


    static int rows = 15;   // Replace this with your array's row count
    static int cols = 15;   // Replace this with your array's column count
    // Function to create a grid with the size of an array

    private static JPanel createArrayGridPanel(int rows, int cols) {
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        // Assuming you have a 2D array of values, you can use nested loops to create buttons for each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton(""); // You can set the label or icon for each button as per your array values
                gridPanel.add(button);
            }
        }
        return gridPanel;
    }

    public static void Gui(String[] args) {

        mainInstance = new Main(); // Hier wird eine Instanz der Hauptklasse erstellt

        JFrame frame = new JFrame("Mensch ärgere Dich nicht!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        // Create the CardLayout and JPanel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create the cards
        JPanel card1 = new JPanel();
        card1.setBackground(Color.RED);
        cardPanel.add(card1, "Card 1");

        // Button to switch to the next card (Card 2)
        JButton btnNewButton = new JButton("Start");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.last(cardPanel);
            }
        });

        // Button to switch to the previous card (Card 1)
        JButton btnNewButton_2 = new JButton("Einstellungen");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(cardPanel);
            }
        });

        // Button to exit the application
        JButton btnNewButton_3 = new JButton("Verlassen");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // Label for displaying the game name
        JLabel lblNewLabel = new JLabel("Mensch ärgere Dich nicht!");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Constantia", Font.BOLD, 20));
        GroupLayout gl_card1 = new GroupLayout(card1);
        gl_card1.setHorizontalGroup(
                gl_card1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_card1.createSequentialGroup()
                                .addGap(100)
                                .addGroup(gl_card1.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                                        .addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 284, Short.MAX_VALUE)
                                        .addComponent(btnNewButton_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                                        .addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(100))
        );
        gl_card1.setVerticalGroup(
                gl_card1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_card1.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                .addGap(18)
                                .addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                .addGap(18)
                                .addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                .addGap(41))
        );
        card1.setLayout(gl_card1);

        JPanel card2 = new JPanel();
        card2.setBackground(Color.PINK);
        cardPanel.add(card2, "Card 2");

        // ComboBox for selecting difficulty level
        JComboBox<?> comboBox = new JComboBox();
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Leicht", "Mittel", "Schwer"}));
        comboBox.setMaximumRowCount(3);

        // Label for the difficulty level selection
        JLabel lblNewLabel_1 = new JLabel("Schwierigkeitsgrad");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // ComboBox for selecting players
        JComboBox players = new JComboBox();
        players.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));

        // CheckBox for enabling color-blind mode
        JCheckBox chckbxNewCheckBox = new JCheckBox("Farbenblindenmodus");
        chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // Slider for adjusting the volume
        JSlider slider = new JSlider();
        slider.setFont(new Font("Tahoma", Font.PLAIN, 14));
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(25);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        // Button to go back to the previous card (Card 1)
        JButton btnNewButton_1 = new JButton("Zurück");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Einstellungen von Card 2:");
                System.out.println("Schwierigkeitsgrad: " + comboBox.getSelectedItem());
                System.out.println("Spielerzahl: " + players.getSelectedItem());
                System.out.println("Farbenblindenmodus: " + chckbxNewCheckBox.isSelected());
                System.out.println("Lautstärke: " + slider.getValue());
                cardLayout.previous(cardPanel);
            }
        });

        // Label for displaying the settings title
        JLabel lblNewLabel_2 = new JLabel("Lautstärke");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // Label for displaying the settings title
        JLabel lblNewLabel_3 = new JLabel("Einstellungen");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);



        JLabel lblNewLabel_1_1 = new JLabel("Spieleranzahl");
        lblNewLabel_1_1.setFont(new Font("Dialog", Font.PLAIN, 14));
        GroupLayout gl_card2 = new GroupLayout(card2);
        gl_card2.setHorizontalGroup(
                gl_card2.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_card2.createSequentialGroup()
                                .addGap(100)
                                .addGroup(gl_card2.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_card2.createSequentialGroup()
                                                .addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(gl_card2.createSequentialGroup()
                                                .addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(Alignment.TRAILING, gl_card2.createSequentialGroup()
                                                .addGroup(gl_card2.createParallelGroup(Alignment.TRAILING)
                                                        .addComponent(slider, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                                                        .addComponent(comboBox, Alignment.LEADING, 0, 590, Short.MAX_VALUE)
                                                        .addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                                                        .addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                                                        .addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                                                        .addComponent(players, 0, 590, Short.MAX_VALUE)
                                                        .addComponent(chckbxNewCheckBox, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
                                                .addGap(100))))
        );
        gl_card2.setVerticalGroup(
                gl_card2.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_card2.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(lblNewLabel_1)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(players, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(chckbxNewCheckBox, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(slider, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
                                .addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                .addGap(41))
        );

        card2.setLayout(gl_card2);

        JPanel card3 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) card3.getLayout();
        flowLayout.setAlignment(FlowLayout.LEADING);
        card3.setBackground(Color.ORANGE);
        cardPanel.add(card3, "Card 3");
        Spielfeld spielfeld = new Spielfeld();
        SpielfeldGui spielfeldGui = new SpielfeldGui(spielfeld);
        spielfeldGui.test();

        JButton btnNewButton_4 = new JButton("Würfeln");
        btnNewButton_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Spiel spiel = new Spiel(); // Erstellen einer Instanz von Spiel
                spiel.rollDice();
            }
        });
        card3.add(btnNewButton_4);
        card3.add(spielfeldGui);


        // Add the cardPanel and button to the frame
        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }
}
