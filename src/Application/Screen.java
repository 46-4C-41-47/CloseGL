package Application;

import Geometry.CartesianPlane;
import Geometry.Point3D;

public record Screen(Point3D cam, CartesianPlane screenPlane, Point3D a, Point3D b, Point3D c, Point3D d) {
}
