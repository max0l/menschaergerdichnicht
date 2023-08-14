package game;

import java.io.Serializable;


public class Spielfeld implements Serializable, Cloneable
{
    private Feld[] spielfeld = new Feld[40];

    /**
     * Der Klassenkonstruktor initialisiert die
     * Felder der "spielfeld" Array.
     */
    public Spielfeld() {
        for (int i = 0; i < 40; i++) {
            spielfeld[i] = new Feld(i);
        }
    }

    /**
     * Ruft das Spielfeld am angegebenen Index ab.
     * @param index Der Index des abzurufenden Spielfelds.
     * @return Das Spielfeld am angegebenen Index auf dem Spielbrett.
     */
    public Feld getFeld(int index) {
        return spielfeld[index];
    }

    @Override
    public String toString() {
        String output = "";
        for(int i = 0; i<40; i++) {
            output += spielfeld[i].toString() + "\n";
        }
        return output;
    }

    @Override
    public Spielfeld clone() throws CloneNotSupportedException {
        Spielfeld clone = (Spielfeld) super.clone();
        for(int i = 0; i<40; i++) {
            clone.spielfeld[i] = spielfeld[i].clone();
        }
        return clone;
    }

}
