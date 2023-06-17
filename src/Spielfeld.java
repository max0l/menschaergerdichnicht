import java.util.Arrays;

public class Spielfeld {
    private Spielfeldchen[] spielfeld = new Spielfeldchen[40];

    public Spielfeld() {
        int index = 0;
        int offset = 50;
        int count = 500;
        int delta = 50;
        for(int i = 0; i<count; i+=delta) {
            System.out.println(index);
            spielfeld[index] = new Spielfeldchen(i+offset, 0+offset);
            index++;
        }
        for(int i = 0; i<count; i+=delta) {
            System.out.println(index);
            spielfeld[index] = new Spielfeldchen(500+offset, i+offset);
            index++;
        }
        for(int i = count; i>0; i-=delta) {
            System.out.println(index);
            spielfeld[index] = new Spielfeldchen(i+offset, 500+offset);
            index++;
        }
        for(int i = count; i>0; i-=delta) {
            System.out.println(index);
            spielfeld[index] = new Spielfeldchen(0+offset, i+offset);
            index++;
        }
        int test = 0;
        for(Spielfeldchen sp : spielfeld){
            System.out.println("Test: " + test + " X: " + sp.getX() + " Y: " + sp.getY());
            test++;
        }

    }

    public Farben getSpielfeldchenFarbe(int i) {
        return spielfeld[i].getFarbe();
    }

    public void setzeSpielfeldchenFarbe(int i, Farben farbe) {
        spielfeld[i].setzeFeldFarbe(farbe);
    }
    public int getSpielfeldchenX(int i) {
        return spielfeld[i].getX();
    }
    public int getSpielfeldchenY(int i) {
        return spielfeld[i].getY();
    }


}
