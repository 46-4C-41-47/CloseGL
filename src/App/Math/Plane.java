package App.Math;


public class Plane {
    public Vector normal;
    public Vertex point;


    public Plane(Vector vector, Vertex point) {
        normal = vector;
        this.point = point;
    }


    public Plane(Vector vector) {
        normal = vector;
        point = new Vertex();
    }


    public Plane() {
        normal = new Vector(0, 0, 1);
        point = new Vertex();
    }


    public Vertex lineIntersection(Vertex A, Vertex B) {
        Vector lineDirection = new Vector(A, B);

        if (normal.dot(lineDirection.normalize()) == 0) {
            return null;
        }

        double t =
                (normal.dot(point.toVector()) - normal.dot(A.toVector()))
                /
                normal.dot(lineDirection.normalize());

        return A.plus(lineDirection.normalize().scale(t).toVertex());
    }


    public double getDistance(Vertex p) {
        Vector vector = normal.normalize();
        return (vector.x * p.x + vector.y * p.y + vector.z * p.z - vector.dot(point.toVector()));
    }


    /*auto dist = [&](vec3d &p)
    {
        vec3d n = Vector_Normalise(p);
        return (plane_n.x * p.x + plane_n.y * p.y + plane_n.z * p.z - Vector_DotProduct(plane_n, plane_p));
    };*/
}
