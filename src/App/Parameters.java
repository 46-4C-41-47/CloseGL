package App;

import java.awt.Color;
import java.awt.Dimension;

import Math.DoubleMatrix2D;
import Math.Mesh;
import Math.Vertex;


public class Parameters {
    static final int FRAME_RATE = 60;
    static final Dimension FRAME_SIZE = new Dimension(1500, 844);
    static final Color FOREGROUND_COLOR = Color.WHITE;
    static final Color BACKGROUND_COLOR = Color.BLACK;
    static final Mesh CUBE = initCube();
    //static final DoubleMatrix2D PROJECTION_MATRIX = initProjectionMatrix();


    static DoubleMatrix2D initProjectionMatrix(double z) {
        return new DoubleMatrix2D(new double[][]{
                {1/z,   0, 0, 0},
                {  0, 1/z, 0, 0},
                {  0,   0, 1, 1},
                {  0,   0, 1, 0}
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
