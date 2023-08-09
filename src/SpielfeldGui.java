import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpielfeldGui extends JPanel {

    String Symbol = "\u2654";
    private Spielfeld spielfeldInstance; // Add a reference to the Spielfeld instance
    private int currentFieldNumber; // Variable to keep track of the current field number

    public SpielfeldGui(Spielfeld spielfeldInstance) {
        this.spielfeldInstance = spielfeldInstance;
        this.currentFieldNumber = 0; // Initialize the current field number to 0
    }

    public void setOccupierForButtonNumber(int buttonNumber, Spielstein occupier) {
        Feld feld = spielfeldInstance.getFeld(buttonNumber);
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
        // red team
        for (int i = 1; i <= totalButtons; i++) {
            JButton button = new JButton();
            button.setBackground(Color.orange);


            if ((i >= 92 && i<= 104 && i != 98) || (i >= 66 && i<= 78 && i != 72) || (i >= 6 && i<= 8) ||(i >= 162 && i<= 164)||(i == 32)||(i == 45)||(i == 58)||(i == 34)||(i == 47)||(i == 60)||(i == 79)||(i == 91)||(i == 110)||(i == 123)||(i == 136)||(i == 149)||(i == 112)||(i == 21)||(i == 125)||(i == 138)||(i == 151)||(i == 19)){
                button.setBackground(Color.lightGray);

            }



            if ((i == 15) ||(i == 16) ||(i == 28)||(i == 29)||(i == 66)||(i >= 80 && i <= 83)) {
                button.setBackground(Color.RED);
            }

            if ((i == 141)||(i==142)||(i==154)||(i==155)||(i == 104)||(i >=87 && i<= 90)){
                button.setBackground(Color.green);
            }

            if ((i == 132)||(i==133)||(i==145)||(i==146)||(i == 162)||(i==150)||(i==137)||(i==124)||(i==111)){
                button.setBackground(Color.yellow);
            }

            if ((i == 24)||(i==25)||(i==37)||(i==38)||(i == 20)||(i==8)||(i==33)||(i==46)||(i==59)){
                button.setBackground(Color.BLUE);
            }


            if (button.getBackground().equals(Color.orange)) {
                button.setEnabled(false);
                button.setBorderPainted(false);
            }

            // Create and store a new Feld object for active buttons
            if (button.isEnabled()) {
                currentFieldNumber++; // Increment the current field number only for enabled buttons
                Feld feld = new Feld(i, currentFieldNumber); // Pass the current field number as the button number
                spielfeldInstance.getFeld(currentFieldNumber).setFeldObject(feld);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int number = feld.getButtonNumber(); // Get the button number from the Feld object
                        System.out.println(number);
                    }
                });
            }



            button.setPreferredSize(new Dimension(50, 50)); // Set the preferred size of the button to create a square shape
            add(button);


        }
    }

}