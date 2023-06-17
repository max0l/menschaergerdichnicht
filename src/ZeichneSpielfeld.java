import javax.swing.*;
import java.awt.*;

public class ZeichneSpielfeld extends JPanel {


    private Spielfeld spielfeld;
    int x = 0;
    int y = 0;
    int widht, hight;

    public ZeichneSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval(x, y, 10, 10);

    }

}
