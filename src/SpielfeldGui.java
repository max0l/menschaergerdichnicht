import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class SpielfeldGui extends JPanel {
    Spielfeld spielfeldInstanz = new Spielfeld();
    Feld[] spielfeldArray = spielfeldInstanz.getSpielfeldArray();

    String Symbol = "\u2654";
    private Spielfeld spielfeldInstance; // Add a reference to the Spielfeld instance
    int fieldNumber;

    public SpielfeldGui(Spielfeld spielfeldInstance) {
        this.spielfeldInstance = spielfeldInstance;
        this.fieldNumber = 0;
    }

    public void setOccupierForButtonNumber(int Fieldnuber, Spielstein occupier) {
        Feld feld = spielfeldInstance.getFeld(Fieldnuber);
        feld.setOccupier(occupier);
    }

    public void setPriorityForButtonNumber(int buttonNumber, float priority) {
        Feld feld = spielfeldInstance.getFeld(buttonNumber);
        feld.setPriority(priority);
    }

    public void setIsOccupiedForButtonNumber(int buttonNumber, boolean isOccupied) {
        Feld feld = spielfeldInstance.getFeld(buttonNumber);
        feld.setIsOccupied(isOccupied);
    }

    public void setCustomObjectForButtonNumber(int buttonNumber, Object customObject) {
        Feld feld = spielfeldInstance.getFeld(buttonNumber);
        feld.setFeldObject(customObject);
    }



    public void test() {
        setLayout(new GridLayout(13, 14));

        int totalButtons = 13 * 13;

        for (int i = 1; i <= totalButtons; i++) {
            Feld currentFeld = spielfeldArray[i];
            JButton button = new JButton();
           /* int finalI = i;    //debug
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(finalI);
                }
            });*/
            button.setBackground(Color.orange);


            if ((i >= 93 && i<= 103 && i != 98) || (i >= 67 && i<= 77 && i != 72) ||(i == 32)||(i == 20)||(i == 45)||(i == 58)||(i == 34)||(i == 47)||(i == 60)||(i == 80)||(i == 90)||(i == 110)||(i == 123)||(i == 136)||(i == 149)||(i == 150)||(i == 112)||(i == 21)||(i == 125)||(i == 138)||(i == 151)||(i == 19)){
                button.setBackground(Color.lightGray);

            }

            if ((i == 15) ||(i == 16) ||(i == 28)||(i == 29)||(i == 67)||(i >= 81 && i <= 84)) {
                button.setBackground(Color.yellow);
            }

            if ((i == 141)||(i==142)||(i==154)||(i==155)||(i == 103)||(i >=86 && i<= 89)){
                button.setBackground(Color.blue);
            }

            if ((i == 132)||(i==133)||(i==145)||(i==146)||(i == 149)||(i==137)||(i==124)||(i==111)||(i == 98)){
                button.setBackground(Color.green);
            }

            if ((i == 24)||(i==25)||(i==37)||(i==38)||(i == 72)||(i==21)||(i==33)||(i==46)||(i==59)){
                button.setBackground(Color.red);
            }


            if (button.getBackground().equals(Color.orange)) {
                button.setEnabled(false);
                button.setBorderPainted(false);
            }
            if (currentFeld.isOccupied()) {
                button.setText(Symbol);
            }

            if (i==21) {
                fieldNumber = 1; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==34) {
                fieldNumber = 2; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==47) {
                fieldNumber = 3; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==60) {
                fieldNumber = 4; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==73) {
                fieldNumber = 5; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==74) {
                fieldNumber = 6; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==75) {
                fieldNumber = 7; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==76) {
                fieldNumber = 8; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }

                });
            }
            else if (i==77) {
                fieldNumber = 9; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==90) {
                fieldNumber = 10; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==103) {
                fieldNumber = 11; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==102) {
                fieldNumber = 12; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==101) {
                fieldNumber = 13; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==100) {
                fieldNumber = 14; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });

            }
            else if (i==99) {
                fieldNumber = 15; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==112) {
                fieldNumber = 16; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==125) {
                fieldNumber = 17; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==138) {
                fieldNumber = 18; // Increment the field number
                Feld feld = new Feld(i, fieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber; // Store the current field number for use in the ActionListener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i == 151) {
                fieldNumber=19; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 150) {
                fieldNumber= 20; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 149) {
                fieldNumber=21; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 136) {
                fieldNumber=22; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 123) {
                fieldNumber=23; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 110) {
                fieldNumber=24; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 97) {
                fieldNumber=25; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 96) {
                fieldNumber=26; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 95) {
                fieldNumber=27; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 94) {
                fieldNumber=28; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 93) {
                fieldNumber=29; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 80) {
                fieldNumber=30; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 67) {
                fieldNumber=31; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 68) {
                fieldNumber=32; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 69) {
                fieldNumber=33; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 70) {
                fieldNumber=34; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 71) {
                fieldNumber=35; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 58) {
                fieldNumber=36; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 45) {
                fieldNumber=37; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 32) {
                fieldNumber=38; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 19) {
                fieldNumber=39; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 20) {
                fieldNumber=40; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, fieldNumber);
                spielfeldInstance.getFeld(fieldNumber).setFeldObject(feld);

                final int currentField = fieldNumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }





            button.setPreferredSize(new Dimension(50, 50)); // Set the preferred size of the button to create a square shape
            add(button);


        }

    }

}