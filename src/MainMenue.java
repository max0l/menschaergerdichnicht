import javax.swing.*;
import java.awt.event.*;

public class MainMenue extends JDialog {
    private JPanel contentPane;
    private JButton buttonSettings;
    private JButton buttonStart;
    private JButton buttonExit;

    public MainMenue() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonStart);
        setLocationRelativeTo(null); // Zentriere das Fenster auf dem Bildschirm

        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onStart();
            }
        });

        buttonSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSettings();
            }
        });

        buttonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });

        // call onExit() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        // call onExit() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onStart() {
        // Schließe das Hauptmenü-Fenster
        //dispose();

        // Öffne das Spielfeld als eigenständiges Fenster
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PlayingField spielfeld = new PlayingField();
                spielfeld.setVisible(true);
            }
        });
    }

    private void onSettings() {
        // Öffne den Settings-Dialog
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.setVisible(true);
    }

    private void onExit() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MainMenue dialog = new MainMenue();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
