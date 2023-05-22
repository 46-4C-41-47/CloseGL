package App;

import javax.swing.JPanel;

import java.awt.*;
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


    private void fillTriangle(Graphics g, Triangle t) {
        double scaledExposition = t.exposition * 0.75 + 0.25;

        g.setColor(new Color(
                (int) (Parameters.OBJECTS_COLOR.getRed()   * scaledExposition),
                (int) (Parameters.OBJECTS_COLOR.getGreen() * scaledExposition),
                (int) (Parameters.OBJECTS_COLOR.getBlue()  * scaledExposition)
        ));

        Polygon triangle = new Polygon(
                new int[]{
                        (int) t.getPoints()[0].getCoordinates()[0],
                        (int) t.getPoints()[1].getCoordinates()[0],
                        (int) t.getPoints()[2].getCoordinates()[0]
                },
                new int[]{
                        (int) t.getPoints()[0].getCoordinates()[1],
                        (int) t.getPoints()[1].getCoordinates()[1],
                        (int) t.getPoints()[2].getCoordinates()[1]
                },
                3
        );

        g.fillPolygon(triangle);
    }


    private void drawTriangle(Graphics g, Triangle t) {
        g.setColor(Parameters.FRAME_COLOR);
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

        if (Parameters.DRAW_FRAME) {
            for (Triangle triangle: mesh.getTriangles()) {
                fillTriangle(g, triangle);
                drawTriangle(g, triangle);
            }

        } else {
            for (Triangle triangle: mesh.getTriangles()) {
                fillTriangle(g, triangle);
            }
        }

        repaint();
    }


    public static int getRandomRGB() {
        return (int) (Math.random() * 255) << 16 | (int) (Math.random() * 255) << 8 | (int) (Math.random() * 255);
    }


    public static int getRandomARGB() {
        return 0xFF | (int) (Math.random() * 255) << 16 | (int) (Math.random() * 255) << 8 | (int) (Math.random() * 255);
    }


    public Graphics getCanvasGraphics() {
        return screen.getGraphics();
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(screen, 0, 0, null);
    }
}
