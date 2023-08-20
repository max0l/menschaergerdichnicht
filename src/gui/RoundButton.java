package gui;

import game.Spielstein;
import game.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoundButton extends JButton {
    private Color outlineColor;
    private Color innerColor; // Use an array to store inner colors
    private Spielstein spielstein;
    public RoundButton(Color outlineColor) {
        this.outlineColor = outlineColor;
        this.innerColor = Color.white; // Initialize the array
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Change the inner color to a new color
                innerColor = Color.GREEN; // Change this to the desired color
                repaint(); // Redraw the button with the new color
            }
        });
    }
    public RoundButton(Color outlineColor, Boolean b) {
        this.outlineColor = outlineColor;
        if(b){
            this.innerColor = outlineColor;
        }
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Change the inner color to a new color
                innerColor = Color.GREEN; // Change this to the desired color
                repaint(); // Redraw the button with the new color
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(outlineColor);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(1, 1, getWidth() - 3, getHeight() - 3);

        g2.setColor(innerColor);
        g2.fillOval(3, 3, getWidth() - 7, getHeight() - 7);

        g2.dispose();
    }

    @Override
    public void setBackground(Color innerColor) {
        this.innerColor = innerColor;
        repaint();
    }

    public void setInnerColor(Color innerColor) {
        this.innerColor = innerColor;
        repaint();
    }

    public void setSpielstein(Spielstein spielstein) {
        this.spielstein = spielstein;
    }

    public Spielstein getSpielstein() {
        return spielstein;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }
}