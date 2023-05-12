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

        canvas.draw(cube
                .rotateX(elapsedTime * 0.398)
                .rotateY(elapsedTime * 0.701)
                .rotateZ(elapsedTime * 0.545)
                .translate(2, 6)
                .filter(Parameters.Camera)
                .project(Parameters.PROJECTION_MATRIX).toScreen(Parameters.FRAME_SIZE)
        );
    }
}