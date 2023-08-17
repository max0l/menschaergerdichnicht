package game;

import java.io.Serializable;


public class Spielfeld implements Serializable, Cloneable
{
    private Feld[] spielfeld = new Feld[40];

    /**
     * The class constructor without any arguments.
     * Initializes the spielfeld array.
     */
    public Spielfeld() {
        for (int i = 0; i < 40; i++) {
            spielfeld[i] = new Feld();
        }
    }

    /**
     * Another variation of the class constructor that lets you provide an already existing Array of fields.
     * @param spielfeld the already existing Array of fields that will be set as the Array that will be used.
     */
    public Spielfeld(Feld[] spielfeld) {
        this.spielfeld = spielfeld;
    }

    /**
     * Ruft das Spielfeld am angegebenen Index ab.
     * @param index Der Index des abzurufenden Spielfelds.
     * @return Das Spielfeld am angegebenen Index auf dem Spielbrett.
     */
    public Feld getFeld(int index) {
        return spielfeld[index];
    }

    /**
     * Provides a way to print out information if the play field with one simple function.
     * @return A string that contains the play field information.
     */
    @Override
    public String toString() {
        String output = "";
        for(int i = 0; i<40; i++) {
            output += "Feld " + i + ": ";

            if(spielfeld[i].getIsOccupied())
            {
                output += spielfeld[i].getOccupier().getColor();
            }
            else
            {
                output += "False";
            }

            output += '\n';
        }
        return output;
    }

    /**
     * Sets the field array "spielfeld" to the provided array.
     * @param felder the array of fields that will be used.
     */
    public void setFelder(Feld[] felder) {
        this.spielfeld = felder;
    }

    /**
     * Clones the Spielfeld object
     * @return the cloned object.
     * @throws CloneNotSupportedException
     */
    @Override
    public Spielfeld clone() throws CloneNotSupportedException {
        Spielfeld clone = (Spielfeld) super.clone();
        Feld[] felder = new Feld[40];
        for(int i = 0; i<40; i++) {
            felder[i] = spielfeld[i].clone();
        }
        clone.setFelder(felder);
        return clone;
    }

}
