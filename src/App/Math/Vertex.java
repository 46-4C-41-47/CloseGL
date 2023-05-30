package App.Math;

public class Vertex {
    public double x, y, z, w;


    public Vertex() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
    }


    public Vertex(Vertex v) {
        x = v.x;
        y = v.y;
        z = v.z;
        w = v.w;
    }


    public Vertex(double x, double y, double z, double w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    public Vertex(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 0;
    }


    public Vertex(DoubleMatrix2D matrix) {
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
            throw new IllegalArgumentException("Only matrix in line or column of length 4 can be converted to a Vertex object");
        }
    }


    public Vertex translateX(double offset) {return new Vertex(x + offset, y, z, w);}


    public Vertex translateY(double offset) {return new Vertex(x, y + offset, z, w);}


    public Vertex translateZ(double offset) {return new Vertex(x, y, z + offset, w);}


    public void translate(Vector vector, double offset) {
        Vector normalizedVector = vector.normalize();

        x += normalizedVector.x * offset;
        y += normalizedVector.y * offset;
        z += normalizedVector.z * offset;
    }


    public Vertex plus(Vertex v) {
        return new Vertex(x + v.x, y + v.y, z + v.z, v.w);
    }


    public Vector toVector() {
        return new Vector(x, y, z);
    }


    public DoubleMatrix2D toMatrix() {
        return new DoubleMatrix2D(new double[][]{{x, y, z, w}});
    }


    @Override
    public String toString() {
        return "Vertex : (" + x + ", " + y + ", " + z + ", " + w + ")";
    }
}
