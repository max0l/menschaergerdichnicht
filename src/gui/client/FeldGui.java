package gui.client;

import game.Feld;

public class FeldGui {
    private Feld feld;
    private int x, y;

    public FeldGui(Feld feld, int x, int y) {
        this.feld = feld;
        this.x = x;
        this.y = y;
    }

    public Feld getFeldGui() {
        return feld;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSpielfeld(Feld feld) {
        this.feld = feld;
    }
}
