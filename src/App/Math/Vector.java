package App.Math;


public class Vector {
    public double x, y, z;


    public Vector() {
        x = 0;
        y = 0;
        z = 0;
    }


    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vector(Vertex A, Vertex B) {
        x = B.x - A.x;
        y = B.y - A.y;
        z = B.z - A.z;
    }


    public Vector rotateX(double angle) {
        DoubleMatrix2D vectorToMatrix = new DoubleMatrix2D(new double[][] {{x, y, z, 0}});

        DoubleMatrix2D rotatedVector = DoubleMatrix2D.rotateX(vectorToMatrix, angle);

        return new Vector(rotatedVector.matrix[0][1], rotatedVector.matrix[0][2], rotatedVector.matrix[0][3]);
    }


    public Vector rotateY(double angle) {
        DoubleMatrix2D vectorToMatrix = new DoubleMatrix2D(new double[][] {{x, y, z, 0}});

        DoubleMatrix2D rotatedVector = DoubleMatrix2D.rotateY(vectorToMatrix, angle);

        return new Vector(rotatedVector.matrix[0][1], rotatedVector.matrix[0][2], rotatedVector.matrix[0][3]);
    }


    public Vector rotateZ(double angle) {
        DoubleMatrix2D vectorToMatrix = new DoubleMatrix2D(new double[][] {{x, y, z, 0}});

        DoubleMatrix2D rotatedVector = DoubleMatrix2D.rotateZ(vectorToMatrix, angle);

        return new Vector(rotatedVector.matrix[0][1], rotatedVector.matrix[0][2], rotatedVector.matrix[0][3]);
    }


    public Vector normalize() {
        double length = computeLength();
        return new Vector(x / length, y / length, z / length);
    }
    
    
    private double computeLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }


    public double dot(Vector B) {
        return x * B.x + y * B.y + z * B.z;
    }


    public Vector cross(Vector B) {
        return new Vector(
                (y * B.z) - (z * B.y),
                (z * B.x) - (x * B.z),
                (x * B.y) - (y * B.x)
        );
    }


    public double getLength() {
        return computeLength();
    }


    public Vertex translate(Vertex v1, double offset) {
        double length = computeLength();
        return new Vertex(
                (v1.x + (x / length) * offset),
                (v1.y + (y / length) * offset),
                (v1.z + (z / length) * offset),
                v1.w);
    }


    public Vector scale(double scaleFactor) {
        return new Vector(x * scaleFactor, y * scaleFactor, z * scaleFactor);
    }


    public Vector plus(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }


    public Vertex toVertex() {
        return new Vertex(x, y, z);
    }


    @Override
    public String toString() {
        return "Vector : (" + x + ", " + y + ", " + z + ")";
    }
}
