import javax.swing.*;

public class PlayingField extends JDialog {

    public PlayingField() {
        setModal(true);
        setTitle("Spielfeld");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Füge hier den Code für das Spielfeld hinzu

        // Beispiel: Füge ein JLabel mit einem Text hinzu
        JLabel label = new JLabel("Das ist das Spielfeld");
        add(label);

        // Setze das Fenster in den Vordergrund
        toFront();
    }
}
