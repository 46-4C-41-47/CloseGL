package Application;

import java.util.TimerTask;

public class DrawTask extends TimerTask {
    private final Canvas canvas;


    public DrawTask(Canvas c) {
        canvas = c;
    }


    @Override
    public void run() {
        canvas.draw();
        canvas.paintComponent(canvas.getGraphics());
    }
}
