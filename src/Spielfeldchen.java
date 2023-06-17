public class Spielfeldchen {
    private int x,y;
    private Farben farbe;

    public Spielfeldchen(int x, int y) {
        this.farbe = Farben.WEISS;
        this.x = x;
        this.y = y;
    }

    public void setzeFeldFarbe(Farben farbe){

        this.farbe = farbe;
    }

    public Farben getFarbe() {
        return farbe;
    }

    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }
}
