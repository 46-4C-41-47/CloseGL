package Application;

import Geometry.Triangle;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.util.Random;

public class Canvas extends JPanel {
    private BufferedImage screen;

    public Canvas(Dimension d) {
        super();
        screen = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
    }


    private void drawTriangle(Graphics g, Triangle triangle) {
        g.drawLine(triangle.points[0].x, triangle.points[0].y, triangle.points[1].x, triangle.points[1].y);
        g.drawLine(triangle.points[1].x, triangle.points[1].y, triangle.points[2].x, triangle.points[2].y);
        g.drawLine(triangle.points[2].x, triangle.points[2].y, triangle.points[0].x, triangle.points[0].y);
    }


    private void clearScreen(Graphics g) {
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    }


    public void draw() {
        BufferedImage tmpScreen = new BufferedImage(screen.getWidth(), screen.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmpScreen.getGraphics();



        screen = tmpScreen;
        this.repaint();
    }


    public Graphics getCanvasGraphics() {
        return screen.getGraphics();
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(screen, 0, 0, null);
    }
}
