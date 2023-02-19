package Application;

import Geometry.Mesh;
import Geometry.Triangle;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.util.List;

public class Canvas extends JPanel {
    private final BufferedImage screen;


    public Canvas(Dimension d) {
        super();
        screen = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
    }


    private void drawTriangle(Graphics g, Triangle t) {
        g.setColor(Parameters.FOREGROUND_COLOR);
        g.drawLine((int) t.points[0].x, (int) t.points[0].y, (int) t.points[1].x, (int) t.points[1].y);
        g.drawLine((int) t.points[1].x, (int) t.points[1].y, (int) t.points[2].x, (int) t.points[2].y);
        g.drawLine((int) t.points[2].x, (int) t.points[2].y, (int) t.points[0].x, (int) t.points[0].y);
    }


    private void clearScreen(Graphics g) {
        g.setColor(Parameters.BACKGROUND_COLOR);
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    }


    public void draw(List<Mesh> objects) {
        Graphics g = screen.getGraphics();
        clearScreen(g);

        for (Mesh mesh: objects) {
            for (Triangle triangle: mesh.getTriangles()) {
                drawTriangle(g, triangle);
            }
        }
    }


    public Graphics getCanvasGraphics() {
        return screen.getGraphics();
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(screen, 0, 0, null);
    }
}
