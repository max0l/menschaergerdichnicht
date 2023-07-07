import javax.swing.*;

class SettingsDialog extends JDialog {

    private JPanel contentPanel;
    private JComboBox<String> difficulty;
    private JCheckBox colorblind;
    private JSlider volumeSlider;

    public SettingsDialog() {
        setTitle("Einstellungen");
        setModal(true);
        setLocationRelativeTo(null); // Zentriere das Fenster auf dem Bildschirm

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        String[] difficultyLevels = {"Einfach", "Mittel", "Schwer"};
        difficulty = new JComboBox<>(difficultyLevels);

        colorblind = new JCheckBox("Farbenblind");

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        contentPanel.add(new JLabel("Schwierigkeitsgrad:"));
        contentPanel.add(difficulty);
        contentPanel.add(Box.createVerticalStrut(10)); // Vertikaler Abstand
        contentPanel.add(colorblind);
        contentPanel.add(Box.createVerticalStrut(10)); // Vertikaler Abstand
        contentPanel.add(new JLabel("Lautstärke:"));
        contentPanel.add(volumeSlider);

        setContentPane(contentPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Zentriere den Dialog auf dem Bildschirm
    }
}