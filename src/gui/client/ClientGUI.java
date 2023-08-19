package gui.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Spiel;
import game.Spielfeld;
import game.Feld;

public class ClientGUI extends JFrame {
    private Spielfeld spielfeld;
    private Spiel spiel;
    private JButton[] fieldButtons;

    public ClientGUI() {
        setTitle("Game Client");
        setSize(800, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fieldButtons = new JButton[40];

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 40));

        for (int i = 0; i < 40; i++) {
            fieldButtons[i] = new JButton("Feld " + i);
            fieldButtons[i].addActionListener(new FieldButtonListener(i));
            buttonPanel.add(fieldButtons[i]);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    public void updateSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
        for (int i = 0; i < 40; i++) {
            if (spielfeld.getFeld(i).getIsOccupied()) {
                fieldButtons[i].setBackground(spielfeld.getFeld(i).getOccupier().getColor());
                fieldButtons[i].setEnabled(false); // Disable the button if occupied
            } else {
                fieldButtons[i].setText("Feld " + i);
                fieldButtons[i].setBackground(Color.WHITE);
                fieldButtons[i].setEnabled(true);
            }
        }
    }

    private class FieldButtonListener implements ActionListener {
        private int index;

        public FieldButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle button click for the specific field index
            System.out.println("Clicked on Feld " + index);
        }
    }
    public void updateGame(Spiel spiel) {
        // Update the GUI components with data from the received game object

        updateSpielfeld(spiel.getSpielfeld());

    }

}
