package client;

import game.Feld;
import game.Spielfeld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class FeldGUI extends JPanel {

        private String Symbolred = "♙";
        private String Symbolblue = "♟";
        private String Symbolgreen = "♛";
        private String Symbolyellow = "♕";

        private int buttonCounter = 0; // Die Zählervariable für die Buttons


        private Font buttonFont = new Font("Arial", Font.BOLD, 23);

        private Spielfeld spielfeld;
        private Feld feld;
        private int fieldNumber;
        int totalButtons = 13 * 13;

        private JButton[][] buttons;

        public FeldGUI() {
            setLayout(new GridLayout(13, 14)); // Set the layout to a 13x14 grid

            buttons = new JButton[13][13];

            for (int i = 1; i <= totalButtons; i++) {
                JButton button = new JButton();
                int row = (i - 1) / 13;
                int col = (i - 1) % 13;

                buttons[row][col] = button;
                configureButton(button,i);


                button.setPreferredSize(new Dimension(55, 55));
                add(button);
            }
        }
            public JButton getButtonForFieldNumber(int fieldNumber) {
                JButton targetButton = null;

                switch (fieldNumber) {
                    case 1:
                        targetButton = buttons[1][7];
                        break;
                    case 2:
                        targetButton = buttons[2][7];
                        break;
                    case 3:
                        targetButton = buttons[3][7];
                        break;
                    case 4:
                        targetButton = buttons[4][7];
                        break;
                    case 5:
                        targetButton = buttons[5][7];
                        break;
                    case 6:
                        targetButton = buttons[5][8];
                        break;
                    case 7:
                        targetButton = buttons[5][9];
                        break;
                    case 8:
                        targetButton = buttons[5][10];
                        break;
                    case 9:
                        targetButton = buttons[5][11];
                        break;
                    case 10:
                        targetButton = buttons[6][11];
                        break;
                    case 11:
                        targetButton = buttons[7][11];
                        break;
                    case 12:
                        targetButton = buttons[7][10];
                        break;
                    case 13:
                        targetButton = buttons[7][9];
                        break;
                    case 14:
                        targetButton = buttons[7][8];
                        break;
                    case 15:
                        targetButton = buttons[7][7];
                        break;
                    case 16:
                        targetButton = buttons[8][7];
                        break;
                    case 17:
                        targetButton = buttons[9][7];
                        break;
                    case 18:
                        targetButton = buttons[10][7];
                        break;
                    case 19:
                        targetButton = buttons[11][7];
                        break;
                    case 20:
                        targetButton = buttons[11][6];
                        break;
                    case 21:
                        targetButton = buttons[11][5];
                        break;
                    case 22:
                        targetButton = buttons[10][5];
                        break;
                    case 23:
                        targetButton = buttons[9][5];
                        break;
                    case 24:
                        targetButton = buttons[8][5];
                        break;
                    case 25:
                        targetButton = buttons[7][5];
                        break;
                    case 26:
                        targetButton = buttons[7][4];
                        break;
                    case 27:
                        targetButton = buttons[7][3];
                        break;
                    case 28:
                        targetButton = buttons[7][2];
                        break;
                    case 29:
                        targetButton = buttons[7][1];
                        break;
                    case 30:
                        targetButton = buttons[6][1];
                        break;
                    case 31:
                        targetButton = buttons[5][1];
                        break;
                    case 32:
                        targetButton = buttons[5][2];
                        break;
                    case 33:
                        targetButton = buttons[5][3];
                        break;
                    case 34:
                        targetButton = buttons[5][4];
                        break;
                    case 35:
                        targetButton = buttons[5][5];
                        break;
                    case 36:
                        targetButton = buttons[4][5];
                        break;
                    case 37:
                        targetButton = buttons[3][5];
                        break;
                    case 38:
                        targetButton = buttons[2][5];
                        break;
                    case 39:
                        targetButton = buttons[1][5];
                        break;
                    case 40:
                        targetButton = buttons[1][6];
                        break;
                    default:
                        break;
                }

                return targetButton;
            }

            private ActionListener createButtonActionListener(int currentField) {
            return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button " + currentField + " wurde geklickt.");
                }
            };
        }

        public void updateGUIWithSpielfeld(Spielfeld spielfeld) {
            int j =0;

            for (int i = 0; i < totalButtons; i++) {
                int buttonRow = i / 13;
                int buttonCol = i % 13;
                JButton button = buttons[buttonRow][buttonCol];


                if (buttonRow==1 && buttonCol == 7) {
                    fieldNumber = 1; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }

                if (buttonRow==2 && buttonCol == 7) {
                    fieldNumber = 2; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==3 && buttonCol == 7) {
                    fieldNumber = 3; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==4 && buttonCol == 7) {
                    fieldNumber = 4; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 7) {
                    fieldNumber = 5; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 8) {
                    fieldNumber = 6; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 9) {
                    fieldNumber = 7; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 10) {
                    fieldNumber = 8; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 11) {
                    fieldNumber = 9; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==6 && buttonCol == 11) {
                    fieldNumber = 10; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 11) {
                    fieldNumber = 11; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 10) {
                    fieldNumber = 12; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 9) {
                    fieldNumber = 13; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 8) {
                    fieldNumber = 14; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 7) {
                    fieldNumber = 15; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==8 && buttonCol == 7) {
                    fieldNumber = 16; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==9 && buttonCol == 7) {
                    fieldNumber = 17; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==10 && buttonCol == 7) {
                    fieldNumber = 18; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==11 && buttonCol == 7) {
                    fieldNumber = 19; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==11 && buttonCol == 6) {
                    fieldNumber = 20; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==11 && buttonCol == 5) {
                    fieldNumber = 21; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==10 && buttonCol == 5) {
                    fieldNumber = 22; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==9 && buttonCol == 5) {
                    fieldNumber = 23; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==8 && buttonCol == 5) {
                    fieldNumber = 24; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 5) {
                    fieldNumber = 25; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 4) {
                    fieldNumber = 26; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 3) {
                    fieldNumber = 27; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 2) {
                    fieldNumber = 28; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==7 && buttonCol == 1) {
                    fieldNumber = 29; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==6 && buttonCol == 1) {
                    fieldNumber = 30; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 1) {
                    fieldNumber = 31; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 2) {
                    fieldNumber = 32; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 3) {
                    fieldNumber = 33; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 4) {
                    fieldNumber = 34; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==5 && buttonCol == 5) {
                    fieldNumber = 35; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==4 && buttonCol == 5) {
                    fieldNumber = 36; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==3 && buttonCol == 5) {
                    fieldNumber = 37; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==2 && buttonCol == 5) {
                    fieldNumber = 38; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==1 && buttonCol == 5) {
                    fieldNumber = 39; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }
                if (buttonRow==1 && buttonCol == 6) {
                    fieldNumber = 40; // Increment the field number
                    final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                    button.addActionListener(createButtonActionListener(currentField));
                }









                if (button.getBackground() == Color.lightGray) {
                    int fieldNumber = j + 1; // Berechne die fieldNumber aus dem Index j
                    JButton targetButton = getButtonForFieldNumber(fieldNumber);

                    if (targetButton != null) {
                        Feld feld = spielfeld.getFeld(j); // Annahme: Index i entspricht dem Feld im Spielfeld
                        j++;

                        if (feld.getIsOccupied()) {
                            targetButton.setFont(buttonFont);
                            targetButton.setText(feld.getOccupier().getColor().equals(Color.red) ? Symbolred :
                                    feld.getOccupier().getColor().equals(Color.blue) ? Symbolblue :
                                            feld.getOccupier().getColor().equals(Color.green) ? Symbolgreen :
                                                    feld.getOccupier().getColor().equals(Color.yellow) ? Symbolyellow : Symbolyellow);
                        } else {
                            targetButton.setText("");
                        }
                    }
                }
            }
            repaint(); // Repaint the GUI to reflect the changes
        }


        private void configureButton(JButton button, final int fieldNumber) {

            if (fieldNumber < 183)
            {
                button.setBackground(Color.orange);
            }
            if ((fieldNumber >= 93 && fieldNumber <= 103 && fieldNumber != 98) || (fieldNumber >= 67 && fieldNumber <= 77 && fieldNumber != 72) || (fieldNumber == 32) || (fieldNumber == 20) || (fieldNumber == 45) || (fieldNumber == 58) || (fieldNumber == 34) || (fieldNumber == 47) || (fieldNumber == 60) || (fieldNumber == 80) || (fieldNumber == 90) || (fieldNumber == 110) || (fieldNumber == 123) || (fieldNumber == 136) || (fieldNumber == 149) || (fieldNumber == 150) || (fieldNumber == 112) || (fieldNumber == 21) || (fieldNumber == 125) || (fieldNumber == 138) || (fieldNumber == 151) || (fieldNumber == 19)) {
                button.setBackground(Color.lightGray);
            }
            if ((fieldNumber == 15) || (fieldNumber == 16) || (fieldNumber == 28) || (fieldNumber == 29) || (fieldNumber == 67) || (fieldNumber >= 81 && fieldNumber <= 84)) {
                button.setBackground(Color.yellow);
            }
            if ((fieldNumber == 141) || (fieldNumber == 142) || (fieldNumber == 154) || (fieldNumber == 155) || (fieldNumber == 103) || (fieldNumber >= 86 && fieldNumber <= 89)) {
                button.setBackground(Color.blue);
            }
            if ((fieldNumber == 132) || (fieldNumber == 133) || (fieldNumber == 145) || (fieldNumber == 146) || (fieldNumber == 149) || (fieldNumber == 137) || (fieldNumber == 124) || (fieldNumber == 111) || (fieldNumber == 98)) {
                button.setBackground(Color.green);
            }
            if ((fieldNumber == 24) || (fieldNumber == 25) || (fieldNumber == 37) || (fieldNumber == 38) || (fieldNumber == 72) || (fieldNumber == 21) || (fieldNumber == 33) || (fieldNumber == 46) || (fieldNumber == 59)) {
                button.setBackground(Color.red);
            }
            if (button.getBackground() == Color.orange)
            {
                button.setBorderPainted(false);
                button.setEnabled(false);
            }
            else
            {
                button.setEnabled(true);
            }


            }
    }


