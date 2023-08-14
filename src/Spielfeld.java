import java.io.Serializable;

public class Spielfeld implements Serializable {
    private Feld[] spielfeld = new Feld[182];

    public Spielfeld() {
        for (int i = 0; i < 182; i++) {
            spielfeld[i] = new Feld(i);
        }
    }
    public Feld getFeld(int id) {
        return spielfeld[id];
    }

    public Feld[] getSpielfeldArray() {
        return spielfeld;
    }

}
