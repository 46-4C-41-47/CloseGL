package App.Math;

public class Vertex3D {
    public double x, y, z, w;


    public Vertex3D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vertex3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 0;
    }


    public Vertex3D(DoubleMatrix2D matrix) {
        if (matrix.height == 1 && matrix.width == 4) {
            x = matrix.matrix[0][0];
            y = matrix.matrix[0][1];
            z = matrix.matrix[0][2];
            w = matrix.matrix[0][3];

        } else if (matrix.width == 1 && matrix.height == 4) {
            x = matrix.matrix[0][0];
            y = matrix.matrix[1][0];
            z = matrix.matrix[2][0];
            w = matrix.matrix[3][0];

        } else {
            System.out.println(matrix);
            throw new IllegalArgumentException("Only matrix in line or column can be converted to a Vertex object");
        }
    }


    public Vertex3D scaleVertex(double scaleFactor) {
        return new Vertex3D(x *= scaleFactor, y *= scaleFactor, z *= scaleFactor,  w *= scaleFactor);
    }


    public DoubleMatrix2D toMatrix() {
        return new DoubleMatrix2D(new double[][] {{x, y, z, w}});
    }


    @Override
    public String toString() {
        return "Vertex : (" + x + ", " + y + ", " + z + ", " + w + ")";
    }
}
