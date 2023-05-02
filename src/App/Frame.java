package App;

import javax.swing.JFrame;


public class Frame extends JFrame {
    private final Canvas canvas;


    public Frame() {
        super();
        this.canvas = new Canvas(Parameters.FRAME_SIZE);

        this.setSize(Parameters.FRAME_SIZE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(canvas);
        this.setVisible(true);
    }


    public Canvas getCanvas() {
        return canvas;
    }
}
