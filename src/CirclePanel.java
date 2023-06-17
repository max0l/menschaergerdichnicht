import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CirclePanel extends JPanel {
    private Spielfeld spielfeld;

    public CirclePanel(Spielfeld circles) {
        this.spielfeld = circles;
        setPreferredSize(new Dimension(500, 500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i<40; i++) {
            int x = spielfeld.getSpielfeldchenX(i);
            int y = spielfeld.getSpielfeldchenY(i);
            int radius = 15;
            switch(spielfeld.getSpielfeldchenFarbe(i)) {
                case WEISS:
                    g2d.setColor(Color.WHITE);
                    break;
                case ROT:
                    g2d.setColor(Color.RED);
                    break;
                case BLAU:
                    g2d.setColor(Color.BLUE);
                    break;
                case GRUEN:
                    g2d.setColor(Color.GREEN);
                    break;
                case GELB:
                    g2d.setColor(Color.YELLOW);
                    break;
                default:
                    g2d.setColor(Color.WHITE);
                    break;
            }
            g2d.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }
    }
}
