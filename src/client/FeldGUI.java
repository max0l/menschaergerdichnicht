package client;
import game.Feld;
import game.Spiel;
import game.Spielfeld;
import game.Team;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FeldGUI extends JPanel {
        private String Symbol = "♟";
        private String Symbole= "♙";
        private Font buttonFont = new Font("Arial", Font.BOLD, 23);
        private int fieldNumber;
        int redOccupiedCount = 0;
        int blueOccupiedCount = 0;
        int greenOccupiedCount = 0;
        int yellowOccupiedCount = 0;
        int totalButtons = 13 * 13;
        private JButton[][] buttons;
        private Client client;
        private final Feld[] startFields = new Feld[4];
        private final Feld[] finishFields = new Feld[4];
        private final boolean[][] occupiedCells = new boolean[13][13];
        private final Color[] playerColors = {Color.red, Color.blue, Color.green, Color.yellow};
        private final int currentPlayer = 0;
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
        private static void printButtonTextAsInt(JButton buttons) {
            String buttonText = buttons.getText();
            try {
                int intValue = Integer.parseInt(buttonText);
                System.out.println("Button Text as Integer: " + intValue);
            } catch (NumberFormatException e) {
                System.out.println("Button Text is not a valid integer.");
            }
        }

            private ActionListener createButtonActionListener(int currentField) {
            return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button " + currentField + " wurde geklickt.");
                    client.receiveFieldNumber(currentField);
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
                if (buttonRow==1 && buttonCol == 1) {
                    button.setText(Symbole);
                }
                if (buttonRow==1 && buttonCol == 2) {
                    button.setText(Symbole);
                }
                if (buttonRow==2 && buttonCol == 1) {
                    button.setText(Symbole);
                }
                if (buttonRow==2 && buttonCol == 2) {
                    button.setText(Symbole);
                }

                if (buttonRow==1 && buttonCol == 10) {
                    button.setText(Symbole);
                }
                if (buttonRow==1 && buttonCol == 11) {
                    button.setText(Symbole);
                }
                if (buttonRow==2 && buttonCol == 10) {
                    button.setText(Symbole);
                }
                if (buttonRow==2 && buttonCol == 11) {
                    button.setText(Symbole);
                }

                if (buttonRow==10 && buttonCol == 10) {
                    button.setText(Symbole);
                }
                if (buttonRow==10 && buttonCol == 11) {
                    button.setText(Symbole);
                }
                if (buttonRow==11 && buttonCol == 10) {
                    button.setText(Symbole);
                }
                if (buttonRow==11 && buttonCol == 11) {
                    button.setText(Symbole);
                }

                if (buttonRow==10 && buttonCol == 1) {
                    button.setText(Symbole);
                }
                if (buttonRow==10 && buttonCol == 2) {
                    button.setText(Symbole);
                }
                if (buttonRow==11 && buttonCol == 1) {
                    button.setText(Symbole);
                }
                if (buttonRow==11 && buttonCol == 2) {
                    button.setText(Symbole);
                }
                if (button.getBackground() == Color.lightGray) {
                    int fieldNumber = j + 1; // Calculate the fieldNumber from the index j
                    JButton targetButton = getButtonForFieldNumber(fieldNumber);
                    if (targetButton != null) {
                        Feld feld = spielfeld.getFeld(j); // Assuming index i corresponds to the field in the Spielfeld
                        j++;
                        if (feld.getIsOccupied()) {
                            targetButton.setFont(buttonFont);
                            Color occupierColor = feld.getOccupier().getColor();
                            if (occupierColor.equals(Color.red)) {
                                targetButton.setForeground(Color.red);
                                targetButton.setText(Symbol);
                                redOccupiedCount++;
                            } else if (occupierColor.equals(Color.blue)) {
                                targetButton.setForeground(Color.blue);
                                targetButton.setText(Symbol);
                                blueOccupiedCount++;
                            } else if (occupierColor.equals(Color.green)) {
                                targetButton.setForeground(Color.green);
                                targetButton.setText(Symbol);
                                greenOccupiedCount++;
                            } else if (occupierColor.equals(Color.yellow)) {
                                targetButton.setForeground(Color.yellow);
                                targetButton.setText(Symbol);
                                yellowOccupiedCount++;
                            }
                        } else {
                            targetButton.setText("");
                        }


                    }
                }
            }
            handleRedTeam(redOccupiedCount);
            handleBlueTeam(blueOccupiedCount);
            handleGreenTeam(greenOccupiedCount);
            handleYellowTeam(yellowOccupiedCount);
            repaint(); // Repaint the GUI to reflect the changes
            redOccupiedCount=0;
            blueOccupiedCount=0;
            greenOccupiedCount=0;
            yellowOccupiedCount=0;
        }
        public void addPieceClickListener(ActionListener listener, int fieldNumber) {
            JButton targetButton = getButtonForFieldNumber(fieldNumber);
            if (targetButton != null) {
                targetButton.addActionListener(listener);
            }
        }
        private void handleRedTeam(int redOccupiedCount) {
            switch (redOccupiedCount) {
                case 0:
                    // Keine besetzten Felder für das rote Team
                    break;
                case 1:
                    setButtonText(2,11,"1");
                    break;
                case 2:
                    setButtonText(2,11,"1");
                    setButtonText(2,10,"2");
                    break;
                case 3:
                    setButtonText(2,11,"1");
                    setButtonText(2,10,"2");
                    setButtonText(1,11,"3");
                    break;
                case 4:
                    setButtonText(2,11,"1");
                    setButtonText(2,10,"2");
                    setButtonText(1,11,"3");
                    setButtonText(1,10,"4");
                    break;
                default:
                    // Fall, wenn die Anzahl der besetzten Felder nicht in den vorherigen Fällen abgedeckt ist
                    break;
            }
        }
        private void handleBlueTeam(int blueOccupiedCount) {
            switch (blueOccupiedCount) {
                case 0:
                    // Keine besetzten Felder für das rote Team
                    break;
                case 1:
                    setButtonText(11,11,"1");
                    break;
                case 2:
                    setButtonText(11,11,"1");
                    setButtonText(11,10,"2");
                    break;
                case 3:
                    setButtonText(11,11,"1");
                    setButtonText(11,10,"2");
                    setButtonText(10,11,"3");
                    break;
                case 4:
                    setButtonText(11,11,"1");
                    setButtonText(11,10,"2");
                    setButtonText(10,11,"3");
                    setButtonText(10,10,"4");
                    break;
                default:
                    // Fall, wenn die Anzahl der besetzten Felder nicht in den vorherigen Fällen abgedeckt ist
                    break;
            }
        }
        private void handleGreenTeam(int greenOccupiedCount) {
            switch (greenOccupiedCount) {
                case 0:
                    // Keine besetzten Felder für das rote Team
                    break;
                case 1:
                    setButtonText(11,2,"1");
                    break;
                case 2:
                    setButtonText(11,2,"1");
                    setButtonText(11,1,"2");
                    break;
                case 3:
                    setButtonText(11,2,"1");
                    setButtonText(11,1,"2");
                    setButtonText(10,2,"3");
                    break;
                case 4:
                    setButtonText(11,2,"1");
                    setButtonText(11,1,"2");
                    setButtonText(10,2,"3");
                    setButtonText(10,1,"4");
                    break;
                default:
                    // Fall, wenn die Anzahl der besetzten Felder nicht in den vorherigen Fällen abgedeckt ist
                    break;
            }
        }
        private void handleYellowTeam(int yellowOccupiedCount) {
            switch (yellowOccupiedCount) {
                case 0:
                    // Keine besetzten Felder für das rote Team
                    break;
                case 1:
                    setButtonText(2,2,"1");
                    break;
                case 2:
                    setButtonText(2,2,"1");
                    setButtonText(2,1,"2");
                    break;
                case 3:
                    setButtonText(2,2,"1");
                    setButtonText(2,1,"2");
                    setButtonText(1,2,"3");
                    break;
                case 4:
                    setButtonText(2,2,"1");
                    setButtonText(2,1,"2");
                    setButtonText(1,2,"3");
                    setButtonText(1,1,"4");
                    break;
                default:
                    // Fall, wenn die Anzahl der besetzten Felder nicht in den vorherigen Fällen abgedeckt ist
                    break;
            }
        }
        private void setButtonText(int row, int col, String newText) {
            if (row >= 0 && row < buttons.length && col >= 0 && col < buttons[row].length) {
                JButton button = buttons[row][col];
                button.setText(newText);
            } else {
                System.out.println("Ungültige Zeile oder Spalte.");
            }
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
            if ((fieldNumber == 24) || (fieldNumber == 25) || (fieldNumber == 37) || (fieldNumber == 38)){
                button.setText(Symbole);
            }
            if ((fieldNumber == 15) || (fieldNumber == 16) || (fieldNumber == 28) || (fieldNumber == 29)){
                button.setText(Symbole);
            }
            if ((fieldNumber == 141) || (fieldNumber == 142) || (fieldNumber == 154) || (fieldNumber == 155)){
                button.setText(Symbole);
            }
            if ((fieldNumber == 132) || (fieldNumber == 133) || (fieldNumber == 145) || (fieldNumber == 146)){
                button.setText(Symbole);
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
    private static void addHoverEffect(JButton button, JButton[][] buttonsArray) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Find the next occupied button within the buttonsArray
                for (int row = 0; row < buttonsArray.length; row++) {
                    for (int col = 0; col < buttonsArray[row].length; col++) {
                        if (buttonsArray[row][col] == button) {
                            for (int i = 1; i < buttonsArray.length * buttonsArray[row].length; i++) {
                                int nextRow = (row + i / buttonsArray[row].length) % buttonsArray.length;
                                int nextCol = (col + i) % buttonsArray[row].length;

                                if (buttonsArray[nextRow][nextCol].getBackground() == Color.lightGray) {
                                    buttonsArray[nextRow][nextCol].setBorder(BorderFactory.createLineBorder(Color.black));
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Reset the border when leaving
                for (int row = 0; row < buttonsArray.length; row++) {
                    for (int col = 0; col < buttonsArray[row].length; col++) {
                        if (buttonsArray[row][col] == button) {
                            for (int i = 1; i < buttonsArray.length * buttonsArray[row].length; i++) {
                                int nextRow = (row + i / buttonsArray[row].length) % buttonsArray.length;
                                int nextCol = (col + i) % buttonsArray[row].length;

                                if (buttonsArray[nextRow][nextCol].getBackground() == Color.lightGray) {
                                    buttonsArray[nextRow][nextCol].setBorder(BorderFactory.createEmptyBorder());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
    }


}


