package App.Math;

import java.util.Arrays;


public class DoubleMatrix2D {
    public final int width, height;
    public double[][] matrix;


    public DoubleMatrix2D(int width, int height) {
        this.height = height;
        this.width  = width;
        matrix = new double[height][width];

        for (double[] row : matrix) {
            Arrays.fill(row, 0d);
        }
    }


    public DoubleMatrix2D(double[][] matrix) {
        height = matrix.length;
        width  = matrix[0].length;
        this.matrix = matrix;
    }


    public double[][] getMatrix() {
        return matrix;
    }


    public static DoubleMatrix2D multiplyMatrix(DoubleMatrix2D matrixA, DoubleMatrix2D matrixB) throws IllegalArgumentException {
        DoubleMatrix2D matrixC;

        if (matrixA.width == matrixB.height) {
            matrixC = new DoubleMatrix2D(matrixB.width, matrixA.height);

        } else {
            throw new IllegalArgumentException("Inconsistent matrix size");
        }

        for (int i = 0; i < matrixA.height; i++) {
            for (int j = 0; j < matrixB.width; j++) {
                double sum = 0;

                for (int k = 0; k < matrixA.width; k++) {
                    sum += matrixA.getMatrix()[i][k] *  matrixB.getMatrix()[k][j];
                }

                matrixC.getMatrix()[i][j] = sum;
            }
        }

        return matrixC;
    }


    public static DoubleMatrix2D rotateX(DoubleMatrix2D vertex, double angle) {
        double theta = Math.toRadians(angle);

        DoubleMatrix2D rotationMatrix = new DoubleMatrix2D(new double[][]{
                {1,               0,                0},
                {0, Math.cos(theta), -Math.sin(theta)},
                {0, Math.sin(theta),  Math.cos(theta)}
        });

        return multiplyMatrix(vertex, rotationMatrix);
    }


    public static DoubleMatrix2D rotateY(DoubleMatrix2D vertex, double angle) {
        double theta = Math.toRadians(angle);

        DoubleMatrix2D rotationMatrix = new DoubleMatrix2D(new double[][]{
                { Math.cos(theta), 0, Math.sin(theta)},
                {               0, 1,               0},
                {-Math.sin(theta), 0, Math.cos(theta)}
        });

        return multiplyMatrix(vertex, rotationMatrix);
    }


    public static DoubleMatrix2D rotateZ(DoubleMatrix2D vertex, double angle) {
        double theta = Math.toRadians(angle);

        DoubleMatrix2D rotationMatrix = new DoubleMatrix2D(new double[][]{
                {Math.cos(theta), -Math.sin(theta), 0},
                {Math.sin(theta),  Math.cos(theta), 0},
                {              0,                0, 1}
        });

        return multiplyMatrix(vertex, rotationMatrix);
    }


    @Override
    public String toString() {
        String str = new String();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                str += matrix[i][j];

                if (i != height-1 || j != width-1) {
                     str += ", ";
                }
            }
            str += "\n";
        }

        return str;
    }
}
