package Math;

import java.util.Arrays;

public class Vertex {
    private final double[] coordinates;


    public Vertex(double...values) {
        coordinates = values;
    }


    public Vertex(DoubleMatrix2D matrix) {
        if (matrix.height == 1) {
            coordinates = matrix.matrix[0];

        } else if (matrix.width == 1) {
            double[] values = new double[matrix.height];

            for (int i = 0; i < matrix.height; i++) {
                values[i] = matrix.matrix[i][1];
            }

            coordinates = values;

        } else {
            throw new IllegalArgumentException("Only matrix in line or column can be converted to a Vertex object");
        }
    }


    public Vertex(Vertex vertex) {
        coordinates = vertex.getCoordinates();
    }


    public int getDimension() {
        return coordinates.length;
    }


    public double[] getCoordinates() {
        return coordinates;
    }


    public Vertex scaleVertex(double scaleFactor) {
        double[] newCoordinates = new double[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {
            newCoordinates[i] = coordinates[i] * scaleFactor;
        }

        return new Vertex(newCoordinates);
    }


    public Vertex translate(int dimension, double shiftValue) {
        if (coordinates.length <= dimension) {
            throw new IllegalArgumentException();
        }

        double[] newCoordinates = new double[coordinates.length];
        System.arraycopy(coordinates, 0, newCoordinates, 0, coordinates.length);

        newCoordinates[dimension] = coordinates[dimension] + shiftValue;

        return new Vertex(newCoordinates);
    }


    public DoubleMatrix2D toMatrix(int matrixDimension) {
        double[] newCoordinates = new double[matrixDimension];

        for (int i = 0; i < newCoordinates.length; i++) {
            if (i == coordinates.length) {
                newCoordinates[i] = 1;
            }

            newCoordinates[i] = coordinates[i];
        }

        return new DoubleMatrix2D(new double[][]{newCoordinates});
    }


    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < coordinates.length; i++) {
            str += coordinates[i];

            if (i != coordinates.length - 1) {
                str += ", ";
            }
        }

        return str;
    }
}
