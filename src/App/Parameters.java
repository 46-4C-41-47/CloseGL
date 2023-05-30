package App;

import java.awt.Color;
import java.awt.Dimension;

import App.Math.DoubleMatrix2D;
import App.J3D.Mesh;
import App.Math.Vector;
import App.Math.Vertex;


public class Parameters {

    // GRAPHICAL ENVIRONMENT
    public static final int FRAME_RATE = 75;
    public static final Dimension FRAME_SIZE = new Dimension(1500, 844);
    public static final Color OBJECTS_COLOR = Color.WHITE;
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final boolean DRAW_FRAME = false;


    // SCENE
    public static final int HORIZONTAL_FOV = 100;
    public static final double VERTICAL_FOV = HORIZONTAL_FOV * (double) FRAME_SIZE.height / FRAME_SIZE.width;
    public static final int Z_FAR = 1000;
    public static final double Z_NEAR = 0.1;
    public static final Vector Light = new Vector(0, 1, 0.2).normalize();
    private static DoubleMatrix2D getProjectionMatrix() {
        double v = 1/Math.tan(Math.toRadians(HORIZONTAL_FOV) / 2);
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
    public static final double MOVEMENT_SPEED = 0.02d;
    public static final double VERTICAL_MOUSE_SENSITIVITY = 0.25d;
    public static final double HORIZONTAL_MOUSE_SENSITIVITY = 0.5d;


    // OBJECTS
    public static final Mesh CUBE = initCube(), FLOOR = initFloor();
    public static final DoubleMatrix2D PROJECTION_MATRIX = getProjectionMatrix();
    public static final String pathToObj = ".\\res\\axis.obj";
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
    private static Mesh initFloor() {
        Mesh floor = new Mesh();

        floor.add(new Vertex[]{
                new Vertex(-100, -5, -100),
                new Vertex(-100, -5,  100),
                new Vertex( 100, -5, -100)}
        );
        floor.add(new Vertex[]{
                new Vertex(-100, -5,  100),
                new Vertex( 100, -5,  100),
                new Vertex( 100, -5, -100)});

        return floor;
    }
}
