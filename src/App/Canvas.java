package App;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import App.Math.Mesh;
import App.Math.Triangle;
import App.Math.Vertex;


public class Canvas extends JPanel {
    private final BufferedImage screen;


    public Canvas(Dimension d) {
        super();
        screen = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
    }


    private void drawTriangle(Graphics g, Triangle t) {
        g.setColor(Parameters.FOREGROUND_COLOR);
        Vertex p1 = t.getPoints()[0], p2 = t.getPoints()[1], p3 = t.getPoints()[2];

        g.drawLine(
                (int) (p1.getCoordinates()[0]),
                (int) (p1.getCoordinates()[1]),
                (int) (p2.getCoordinates()[0]),
                (int) (p2.getCoordinates()[1])
        );
        g.drawLine(
                (int) (p2.getCoordinates()[0]),
                (int) (p2.getCoordinates()[1]),
                (int) (p3.getCoordinates()[0]),
                (int) (p3.getCoordinates()[1])
        );
        g.drawLine(
                (int) (p3.getCoordinates()[0]),
                (int) (p3.getCoordinates()[1]),
                (int) (p1.getCoordinates()[0]),
                (int) (p1.getCoordinates()[1])
        );
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


    public void draw(Mesh mesh) {
        Graphics g = screen.getGraphics();
        clearScreen(g);

        for (Triangle triangle: mesh.getTriangles()) {
            drawTriangle(g, triangle);
        }

        repaint();
    }


    public Graphics getCanvasGraphics() {
        return screen.getGraphics();
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(screen, 0, 0, null);
    }
}
