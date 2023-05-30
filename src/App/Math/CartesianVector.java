package App.Math;

public class CartesianVector {
    private final double length;
    public double x, y, z;


    public CartesianVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        length = computeLength();
    }


    public CartesianVector(Vertex3D A, Vertex3D B) {
        x = B.x - A.x;
        y = B.y - A.y;
        z = B.z - A.z;

        length = computeLength();
    }


    public CartesianVector getNormalize() {
        return new CartesianVector(x / length, y / length, z / length);
    }
    
    
    private double computeLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }


    public double dotProduct(CartesianVector B) {
        double res = 0;

        res += x * B.x;
        res += y * B.y;
        res += z * B.z;

        return res;
    }


    public Vertex3D translate(Vertex3D v1, double offset) {
        return new Vertex3D(
                (v1.x + (x / length) * offset),
                (v1.y + (y / length) * offset),
                (v1.z + (z / length) * offset),
                v1.w);
    }


    public double getLength() {
        return length;
    }


    @Override
    public String toString() {
        return "Vector : (" + x + ", " + y + ", " + z + ")";
    }
}
