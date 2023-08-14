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
    int Fieldnumber;

    public SpielfeldGui(Spielfeld spielfeldInstance) {
        this.spielfeldInstance = spielfeldInstance;
        this.Fieldnumber = 0; // Initialize the current field number to 0
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
            JButton button = new JButton();
            button.setBackground(Color.orange);


            if ((i >= 92 && i<= 104 && i != 98) || (i >= 66 && i<= 78 && i != 72) || (i >= 6 && i<= 8) ||(i >= 162 && i<= 164)||(i == 32)||(i == 45)||(i == 58)||(i == 34)||(i == 47)||(i == 60)||(i == 79)||(i == 91)||(i == 110)||(i == 123)||(i == 136)||(i == 149)||(i == 112)||(i == 21)||(i == 125)||(i == 138)||(i == 151)||(i == 19)){
                button.setBackground(Color.lightGray);

            }

            if ((i == 15) ||(i == 16) ||(i == 28)||(i == 29)||(i == 66)||(i >= 80 && i <= 83)) {
                button.setBackground(Color.blue);
            }

            if ((i == 141)||(i==142)||(i==154)||(i==155)||(i == 104)||(i >=87 && i<= 90)){
                button.setBackground(Color.green);
            }

            if ((i == 132)||(i==133)||(i==145)||(i==146)||(i == 162)||(i==150)||(i==137)||(i==124)||(i==111)){
                button.setBackground(Color.yellow);
            }

            if ((i == 24)||(i==25)||(i==37)||(i==38)||(i == 20)||(i==8)||(i==33)||(i==46)||(i==59)){
                button.setBackground(Color.red);
            }


            if (button.getBackground().equals(Color.orange)) {
                button.setEnabled(false);
                button.setBorderPainted(false);
            }

            //if ((i == 15) ||(i == 16) ||(i == 28)||(i == 29)||(i == 141)||(i==142)||(i==154)||(i==155)|| (i == 132)||(i==133)||(i==145)||(i==146)||(i == 24)||(i==25)||(i==37)||(i==38)){
          //      button.setText(Symbol);
            //}

            Feld currentFeld = spielfeldArray[i];
            if (currentFeld.isOccupied()) {
                // Hier können Sie Aktionen für ein besetztes Feld ausführen
                button.setText(Symbol);
            }
            if (i==8) {
                Fieldnumber = 1; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==21) {
                Fieldnumber = 2; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==34) {
                Fieldnumber = 3; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==47) {
                Fieldnumber = 4; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==60) {
                Fieldnumber = 5; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==73) {
                Fieldnumber = 6; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==74) {
                Fieldnumber = 7; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==75) {
                Fieldnumber = 8; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }

                });
            }
            else if (i==76) {
                Fieldnumber = 9; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==77) {
                Fieldnumber = 10; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==78) {
                Fieldnumber = 11; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==91) {
                Fieldnumber = 12; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==104) {
                Fieldnumber = 13; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==103) {
                Fieldnumber = 14; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });

            }
            else if (i==102) {
                Fieldnumber = 15; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==101) {
                Fieldnumber = 16; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==100) {
                Fieldnumber = 17; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==99) {
                Fieldnumber = 18; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i == 112) {
                Fieldnumber=19; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 125) {
                Fieldnumber= 20; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 138) {
                Fieldnumber=21; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 151) {
                Fieldnumber=22; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 164) {
                Fieldnumber=23; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 163) {
                Fieldnumber=24; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 162) {
                Fieldnumber=25; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 149) {
                Fieldnumber=26; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 136) {
                Fieldnumber=27; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 123) {
                Fieldnumber=28; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 110) {
                Fieldnumber=29; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 97) {
                Fieldnumber=30; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 96) {
                Fieldnumber=31; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 95) {
                Fieldnumber=32; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 94) {
                Fieldnumber=33; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 93) {
                Fieldnumber=34; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 92) {
                Fieldnumber=35; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 79) {
                Fieldnumber=36; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 66) {
                Fieldnumber=37; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 67) {
                Fieldnumber=38; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 68) {
                Fieldnumber=39; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 69) {
                Fieldnumber=40; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 70) {
                Fieldnumber=41; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 71) {
                Fieldnumber=42; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 58) {
                Fieldnumber=43; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 45) {
                Fieldnumber=44; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 32) {
                Fieldnumber=45; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i == 19) {
                Fieldnumber=46; // Fieldnumber bei jedem passenden Durchlauf um eins erhöhen

                Feld feld = new Feld(i, Fieldnumber);
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField);
                    }
                });
            }
            else if (i==6) {
                Fieldnumber = 47; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }
            else if (i==7) {
                Fieldnumber = 48; // Increment the field number
                Feld feld = new Feld(i, Fieldnumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(Fieldnumber).setFeldObject(feld);

                final int currentField = Fieldnumber; // Store the current field number for use in the ActionListener

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(currentField); // Print the current field number
                    }
                });
            }





            button.setPreferredSize(new Dimension(50, 50)); // Set the preferred size of the button to create a square shape
            add(button);


        }

    }

}