package Geometry;

import java.util.Arrays;

public class Matrix {
    public float[][] matrix = new float[4][4];

    public Matrix() {
        for (int i = 0; i < 4; i++) {
            Arrays.fill(matrix[i], 0f);
        }
    }
}
