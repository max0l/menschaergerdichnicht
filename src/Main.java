import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();

        jFrame.getContentPane().setBackground(Color.lightGray);

        Spielfeld spielfeld = new Spielfeld();
        ZeichneSpielfeld ui = new ZeichneSpielfeld(spielfeld);
        CirclePanel circlePanel = new CirclePanel(spielfeld);
        circlePanel.setVisible(true);
        jFrame.add(circlePanel);
        jFrame.pack();
        jFrame.setVisible(true);
        int k = 0;
        while(true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spielfeld.setzeSpielfeldchenFarbe(k%40, Farben.WEISS);
            spielfeld.setzeSpielfeldchenFarbe((k+1)%40, Farben.GRUEN);
            spielfeld.setzeSpielfeldchenFarbe((k+2)%40, Farben.GELB);
            spielfeld.setzeSpielfeldchenFarbe((k+3)%40, Farben.BLAU);
            spielfeld.setzeSpielfeldchenFarbe((k+4)%40, Farben.ROT);
            jFrame.repaint();
            k++;
        }
    }
}