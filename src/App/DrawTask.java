package App;

import App.Math.Mesh;
import App.Math.Triangle;
import App.Math.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class DrawTask extends TimerTask {
    private Mesh cube;
    private final Canvas canvas;
    private float elapsedTime = 0f;
    private int frameCount = 0;
    private List<Mesh> objectsToDraw = new ArrayList<>();


    public DrawTask(Canvas canvas, Mesh cube) {
        this.canvas = canvas;
        this.cube = cube;
        objectsToDraw.add(this.cube);
    }


    @Override
    public void run() {
        // Write here something to run
        elapsedTime += 1;
        frameCount += 1;

        if (frameCount == Parameters.FRAME_RATE) {
            frameCount = 0;
        }

        Mesh rotatedCube = cube
                .rotateX(elapsedTime * 0.547)
                .rotateY(elapsedTime * 0.398)
                .rotateZ(elapsedTime * 0.463);

        //Mesh projectedCube = cube.projectMesh(Parameters.PROJECTION_MATRIX);

        /*Mesh scaledMesh = new Mesh();

        for (Triangle triangle : Parameters.CUBE.getTriangles()) {
            Triangle scaledTriangle = new Triangle(
                    new Vertex(
                            triangle.getPoints()[0].getCoordinates()[0] + 1,
                            triangle.getPoints()[0].getCoordinates()[1] + 1
                    ).scaleVertex(Parameters.FRAME_SIZE.width / 2),
                    new Vertex(
                            triangle.getPoints()[1].getCoordinates()[0] + 1,
                            triangle.getPoints()[1].getCoordinates()[1] + 1
                    ).scaleVertex(Parameters.FRAME_SIZE.height / 2),
                    new Vertex(
                            triangle.getPoints()[2].getCoordinates()[0] + 1,
                            triangle.getPoints()[2].getCoordinates()[1] + 1
                    )
            );

            scaledMesh.add(scaledTriangle);
        }*/

        canvas.draw(rotatedCube.translate(2, 6).project(Parameters.PROJECTION_MATRIX).toScreen(Parameters.FRAME_SIZE));
    }
}