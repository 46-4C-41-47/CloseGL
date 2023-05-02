package App;

import Math.Mesh;

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
        this.cube = cube.scaleMesh(100);
        objectsToDraw.add(this.cube);
    }


    @Override
    public void run() {
        // Write here something to run
        elapsedTime += 1;
        frameCount += 1;

        if (frameCount == Parameters.FRAME_RATE) {
            frameCount = 0;
            //System.out.println(cube.rotateX(elapsedTime));
            //System.out.println("-----------------------------------------------------");
        }

        canvas.draw(cube
                .rotateX(elapsedTime * 0.547)
                .rotateY(elapsedTime * 0.398)
                .rotateZ(elapsedTime * 0.463)
                .translate(0, Parameters.FRAME_SIZE.width / 2)
                .translate(1, Parameters.FRAME_SIZE.height / 2));
    }
}