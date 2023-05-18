package App.Math;

public class Vector {
    private final double[] coordinates;
    private final double length;


    public Vector(double...values) {
        coordinates = values;
        length = computeLength();
    }


    public Vector(Vertex A, Vertex B) {
        if (A.getDimension() != B.getDimension()) {
            throw new UnsupportedOperationException("Both vertex should have the same dimension");
        }

        coordinates = new double[A.getDimension()];

        for (int i = 0; i < A.getDimension(); i++) {
            coordinates[i] = B.getCoordinates()[i] - A.getCoordinates()[i];
        }

        length = computeLength();
    }


    public Vector getNormalize() {
        double[] normalizedCoordinates = new double[coordinates.length];

        for (int i = 0; i < coordinates.length; i++) {
            normalizedCoordinates[i] = coordinates[i] / length;
        }

        return new Vector(normalizedCoordinates);
    }
    
    
    private double computeLength() {
        double sum = 0;

        for (double coordinate : coordinates) {
            sum += Math.pow(coordinate, 2);
        }

        return Math.sqrt(sum);
    }


    public double dotProduct(Vector B) {
        if (this.getDimension() != B.getDimension()) {
            throw new UnsupportedOperationException("Each vector should have the same dimension");
        }

        double res = 0;

        for (int i = 0; i < coordinates.length; i++) {
            res += this.coordinates[i] * B.getCoordinates()[i];
        }

        return res;
    }


    public double[] getCoordinates() {
        return coordinates;
    }


    public double getLength() {
        return length;
    }


    public int getDimension() {
        return coordinates.length;
    }


    @Override
    public String toString() {
        String str = "(";

        for (double coordinate : coordinates) {
            str += coordinate + ", ";
        }

        return str += ")";
    }
}
