package App;

import java.awt.Color;
import java.awt.Dimension;

import App.Math.DoubleMatrix2D;
import App.Math.Mesh;
import App.Math.Vertex;


public class Parameters {
    static final int FRAME_RATE = 60;
    static final int FOV = 100;
    static final int Z_FAR = 1000;
    static final double Z_NEAR = 0.1;

    static final Dimension FRAME_SIZE = new Dimension(1000, 564);
    static final Color FOREGROUND_COLOR = Color.WHITE;
    static final Color BACKGROUND_COLOR = Color.BLACK;
    static final Mesh CUBE = initCube();
    static final DoubleMatrix2D PROJECTION_MATRIX = getProjectionMatrix();


    private static DoubleMatrix2D getProjectionMatrix() {
        double v = 1/Math.tan(Math.toRadians(FOV) / 2);
        double viewZone = Z_FAR - Z_NEAR;
        double aspectRatio = (double) FRAME_SIZE.height / FRAME_SIZE.width;

        return new DoubleMatrix2D(new double[][]{
                {  aspectRatio * v, 0,                         0, 0},
                {                0, v,                         0, 0},
                {                0, 0,          Z_FAR / viewZone, 1},
                {                0, 0, -Z_FAR * Z_NEAR /viewZone, 0}
        });
    }


    private static Mesh initCube() {
        Mesh cube = new Mesh();

        // NORTH
        cube.add(new Vertex[]{new Vertex(-1, -1, -1), new Vertex( 1, -1, -1), new Vertex( 1,  1, -1)});
        cube.add(new Vertex[]{new Vertex( 1,  1, -1), new Vertex(-1,  1, -1), new Vertex(-1, -1, -1)});

        // SOUTH
        cube.add(new Vertex[]{new Vertex(-1, -1,  1), new Vertex( 1, -1,  1), new Vertex( 1,  1,  1)});
        cube.add(new Vertex[]{new Vertex( 1,  1,  1), new Vertex(-1,  1,  1), new Vertex(-1, -1,  1)});

        // TOP
        cube.add(new Vertex[]{new Vertex(-1, -1, -1), new Vertex( 1, -1, -1), new Vertex( 1, -1,  1)});
        cube.add(new Vertex[]{new Vertex( 1, -1,  1), new Vertex(-1, -1,  1), new Vertex(-1, -1, -1)});

        // BOTTOM
        cube.add(new Vertex[]{new Vertex(-1,  1, -1), new Vertex( 1,  1, -1), new Vertex( 1,  1,  1)});
        cube.add(new Vertex[]{new Vertex( 1,  1,  1), new Vertex(-1,  1,  1), new Vertex(-1,  1, -1)});

        // WEST
        cube.add(new Vertex[]{new Vertex(-1, -1, -1), new Vertex(-1, -1,  1), new Vertex(-1,  1,  1)});
        cube.add(new Vertex[]{new Vertex(-1,  1,  1), new Vertex(-1,  1, -1), new Vertex(-1, -1, -1)});

        // EAST
        cube.add(new Vertex[]{new Vertex( 1, -1,  1), new Vertex( 1, -1, -1), new Vertex( 1,  1, -1)});
        cube.add(new Vertex[]{new Vertex( 1,  1, -1), new Vertex( 1,  1,  1), new Vertex( 1, -1,  1)});

        return cube;
    }
}
