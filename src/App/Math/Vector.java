package App.Math;

public class Vector {
    private final double[] coordinates;


    public Vector(double...values) {
        coordinates = values;
    }


    public Vector(Vertex A, Vertex B) {
        if (A.getDimension() != B.getDimension()) {
            throw new UnsupportedOperationException("Both vertex should have the same dimension");
        }

        coordinates = new double[A.getDimension()];

        for (int i = 0; i < A.getDimension(); i++) {
            coordinates[i] = B.getCoordinates()[i] - A.getCoordinates()[i];
        }
    }
    
    
    public double getLength() {
        double sum = 0;

        for (double coordinate : coordinates) {
            sum += Math.pow(coordinate, 2);
        }

        return Math.sqrt(sum);
    }


    public double[] getCoordinates() {
        return coordinates;
    }
}
