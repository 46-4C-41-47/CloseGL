package App.Math;

public class Line {
    public double a, b;


    public Line(double a, double b) {
        this.a = a;
        this.b = b;
    }


    public Line(Vertex A, Vertex B) {
        Vector vector = new Vector(A, B);
        a = vector.y / vector.x;
        b = A.y - a * A.x;
    }


    public double getY(double x) {
        return a * x + b;
    }


    public Vertex intersect(Line line) {
        double divider = (this.a - line.a);

        if (divider == 0) {
            double x = (this.b - line.b) / divider;
            double y = this.a * x + this.b;

            return new Vertex(x, y, 0);

        } else {
            return null;
        }
    }


    public Vertex intersect(Vertex A, Vertex B) {
        Vertex v = intersect(new Line(A, B));

        if (v == null)
            return null;

        boolean isInIntervalX = A.x <= v.x && v.x <= B.x || B.x <= v.x && v.x <= A.x;
        boolean isInIntervalY = A.y <= v.y && v.y <= B.y || B.y <= v.y && v.y <= A.y;

        if (isInIntervalX && isInIntervalY) {
            return v;

        } else {
            return null;
        }
    }


    public double dist(Vertex v) {
        Vertex A = new Vertex(0, getY(0), 0);
        Vertex B = new Vertex(1, getY(1), 0);

        return (B.x - A.x) * (v.y - A.y) - (v.x - A.x) * (B.y - A.y);
    }


    @Override
    public String toString() {
        return a + "x + " + b;
    }
}
