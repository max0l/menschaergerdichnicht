import java.io.Serializable;

public class Spielfeld implements Serializable {
    private Feld[] spielfeld = new Feld[40];

    public Spielfeld() {
        for (int i = 0; i < 40; i++) {
            spielfeld[i] = new Feld(i);
        }
    }
    public Feld getFeld(int id) {
        return spielfeld[id];
    }

}
