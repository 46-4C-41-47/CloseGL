package App;

import App.Math.Mesh;
import App.Math.Triangle;
import App.Math.Vertex;

import java.util.Timer;


public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame();
        Timer timer = new Timer(true);
        DrawTask drawTask = new DrawTask(frame.getCanvas(), Parameters.CUBE);
        timer.scheduleAtFixedRate(drawTask, 0, 1000 / Parameters.FRAME_RATE);
    }
}
