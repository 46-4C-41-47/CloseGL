package App;

import java.awt.Color;
import java.awt.Dimension;

import App.Math.DoubleMatrix2D;
import App.Math.Mesh;
import App.Math.Vector;
import App.Math.Vertex;


public class Parameters {

    // SCENE
    static final int FRAME_RATE = 75;
    static final int FOV = 90;
    static final int Z_FAR = 1000;
    static final double Z_NEAR = 0.1;
    static final Vertex Camera = new Vertex(0, 0, 0);
    static final Vector Light = new Vector(0, 1, 0.2).getNormalize();
    private static DoubleMatrix2D getProjectionMatrix() {
        double v = 1/Math.tan(Math.toRadians(FOV) / 2);
        double viewZone = Z_FAR / (Z_FAR - Z_NEAR);
        double aspectRatio = (double) FRAME_SIZE.height / FRAME_SIZE.width;

        return new DoubleMatrix2D(new double[][]{
                {  aspectRatio * v, 0,                  0, 0},
                {                0, v,                  0, 0},
                {                0, 0,           viewZone, 1},
                {                0, 0, -Z_NEAR * viewZone, 0}
        });
    }


    // PLAYER
    static final double MOVEMENT_SPEED = 0.05d;
    static final double VERTICAL_MOUSE_SENSITIVITY = 4d;
    static final double HORIZONTAL_MOUSE_SENSITIVITY = 7d;


    // GRAPHICAL ENVIRONMENT
    static final Dimension FRAME_SIZE = new Dimension(1500, 844);
    static final Color OBJECTS_COLOR = Color.WHITE;
    static final Color BACKGROUND_COLOR = Color.BLACK;
    static final Color FRAME_COLOR = Color.BLACK;
    static final boolean DRAW_FRAME = false;


    // OBJECTS
    static final Mesh CUBE = initCube();
    static final DoubleMatrix2D PROJECTION_MATRIX = getProjectionMatrix();
    static final String pathToObj = ".\\res\\axis.obj";
    private static Mesh initCube() {
        Mesh cube = new Mesh();

        cube.add(new Vertex[]{new Vertex(-1, -1, -1), new Vertex(-1,  1, -1), new Vertex( 1,  1, -1)});
        cube.add(new Vertex[]{new Vertex(-1, -1, -1), new Vertex( 1,  1, -1), new Vertex( 1, -1, -1)});

        // EAST
        cube.add(new Vertex[]{new Vertex( 1, -1, -1), new Vertex( 1,  1, -1), new Vertex( 1,  1,  1)});
        cube.add(new Vertex[]{new Vertex( 1, -1, -1), new Vertex( 1,  1,  1), new Vertex( 1, -1,  1)});

        // NORTH
        cube.add(new Vertex[]{new Vertex( 1, -1,  1), new Vertex( 1,  1,  1), new Vertex(-1,  1,  1)});
        cube.add(new Vertex[]{new Vertex( 1, -1,  1), new Vertex(-1,  1,  1), new Vertex(-1, -1,  1)});

        // WEST
        cube.add(new Vertex[]{new Vertex(-1, -1,  1), new Vertex(-1,  1,  1), new Vertex(-1,  1, -1)});
        cube.add(new Vertex[]{new Vertex(-1, -1,  1), new Vertex(-1,  1, -1), new Vertex(-1, -1, -1)});

        // TOP
        cube.add(new Vertex[]{new Vertex(-1,  1, -1), new Vertex(-1,  1,  1), new Vertex( 1,  1,  1)});
        cube.add(new Vertex[]{new Vertex(-1,  1, -1), new Vertex( 1,  1,  1), new Vertex( 1,  1, -1)});

        // BOTTOM
        cube.add(new Vertex[]{new Vertex( 1, -1,  1), new Vertex(-1, -1,  1), new Vertex(-1, -1, -1)});
        cube.add(new Vertex[]{new Vertex( 1, -1,  1), new Vertex(-1, -1, -1), new Vertex( 1, -1, -1)});

        return cube;
    }
}
