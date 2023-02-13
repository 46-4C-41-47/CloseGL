package Application;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.util.Random;

public class Canvas extends JPanel {
    private final BufferedImage screen;

    public Canvas(Dimension d) {
        super();
        this.screen = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
    }


    public void draw(Graphics g) {
        // draw the screen here

        this.repaint();
    }


    public Graphics getCanvasGraphics() {
        return this.screen.getGraphics();
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.screen, 0, 0, null);
    }
}
