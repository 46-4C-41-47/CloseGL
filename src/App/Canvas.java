package App;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import App.J3D.Mesh;
import App.Math.Plane;
import App.Math.Triangle;
import App.Math.Vector;
import App.Math.Vertex;


public class Canvas extends JPanel {
    private final BufferedImage screen;


    public Canvas(Dimension d) {
        super();
        screen = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
    }


    private void fillTriangle(Graphics2D g, Triangle t) {
        double scaledExposition = t.exposition * 0.75 + 0.25;

        g.setColor(new Color(
                (int) (Parameters.OBJECTS_COLOR.getRed()   * scaledExposition),
                (int) (Parameters.OBJECTS_COLOR.getGreen() * scaledExposition),
                (int) (Parameters.OBJECTS_COLOR.getBlue()  * scaledExposition)
        ));

        Polygon triangle = new Polygon(
                new int[]{(int) t.getPoints()[0].x, (int) t.getPoints()[1].x, (int) t.getPoints()[2].x},
                new int[]{(int) t.getPoints()[0].y, (int) t.getPoints()[1].y, (int) t.getPoints()[2].y},
                3
        );

        g.fillPolygon(triangle);
    }


    private void drawTriangle(Graphics2D g, Triangle t) {
        g.setColor(Parameters.FRAME_COLOR);
        Vertex p1 = t.getPoints()[0], p2 = t.getPoints()[1], p3 = t.getPoints()[2];

        g.drawLine((int) (p1.x), (int) (p1.y), (int) (p2.x), (int) (p2.y));
        g.drawLine((int) (p2.x), (int) (p2.y), (int) (p3.x), (int) (p3.y));
        g.drawLine((int) (p3.x), (int) (p3.y), (int) (p1.x), (int) (p1.y));
    }


    private void drawCrossHair(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(Parameters.FRAME_SIZE.width / 2, Parameters.FRAME_SIZE.height / 2, 1, 2);
    }


    private void clearScreen(Graphics g) {
        g.setColor(Parameters.BACKGROUND_COLOR);
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
    }


    public void draw(List<Mesh> objects) {
        Graphics2D g = (Graphics2D) screen.getGraphics();
        clearScreen(g);

        for (Mesh mesh: objects) {
            for (Triangle triangle: mesh.getTriangles()) {
                drawTriangle(g, triangle);
            }
        }
    }


    public void draw(Mesh mesh) {
        Graphics2D g = (Graphics2D) screen.getGraphics();
        clearScreen(g);

        Mesh clippedMesh = mesh.clipOnScreen(new Plane[]{
                new Plane(new Vector( 1,  0, 0), new Vertex(0, 0, 0)),
                new Plane(new Vector(-1,  0, 0), new Vertex(Parameters.FRAME_SIZE.width, 0, 0)),
                new Plane(new Vector( 0,  1, 0), new Vertex(0, 0, 0)),
                new Plane(new Vector( 0, -1, 0), new Vertex(0, Parameters.FRAME_SIZE.height - 39, 0)),
        });

        for (Triangle triangle: clippedMesh.getTriangles()) {
            fillTriangle(g, triangle);

            if (Parameters.DRAW_FRAME)
                drawTriangle(g, triangle);

            drawCrossHair(g);
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
