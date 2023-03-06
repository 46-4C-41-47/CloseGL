package Application;

import Geometry.CartesianPlane;
import Geometry.Vector3D;

import java.awt.Dimension;
import java.awt.Color;

public class Parameters {
    static final Dimension FRAME_SIZE = new Dimension(800, 600);
    static final int FRAME_RATE = 60;
    static final int SCALE_FACTOR = 20;
    static final float ZOOM_FACTOR = 1;
    static final float FOV = 90;
    static final float SCREEN_RATIO = 16f/9;
    static final Vector3D SCREEN_PLANE_VECTOR = new Vector3D(0, 0, 1);

    static final Color BACKGROUND_COLOR = Color.BLACK;
    static final Color FOREGROUND_COLOR = Color.WHITE;
}
