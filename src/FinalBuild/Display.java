package FinalBuild;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame implements Runnable {

    protected static final int WIDTH = 1024;
    protected static final int HEIGHT = WIDTH / 2;
    protected static int yTILE, xTILE;

    protected static Map map;

    protected static Player player;
    private final JFrame jFrame;
    private Thread thread;
    private final String title = "Ray Caster Engine Demo | Leon V.";
    private MapRender mapRender;
    public static DimensionalRender dimensionalRender;
    private final InputControls inputControls;

    public static void main(String[] args) {
        Display display = new Display();
        display.start();
    }

    public Display() {
        player = new Player();
        inputControls = new InputControls();
        dimensionalRender = new DimensionalRender();
        mapRender = new MapRender();
        jFrame = new JFrame(title);

        jFrame.setResizable(false);
        jFrame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setTitle(title);
        jFrame.setLocationRelativeTo(null);
        jFrame.pack();

        jFrame.addKeyListener(inputControls);
        jFrame.addMouseMotionListener(inputControls);

        jFrame.setLayout(new GridLayout(1, 2));

        jFrame.add(mapRender);
        jFrame.add(dimensionalRender);

        map = new Map();

        yTILE = HEIGHT / map.getYLength();

        xTILE = WIDTH / map.getXLength();
        xTILE /= 2;
    }

    private boolean running;

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60;
        double delta = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                delta--;
                frames++;
                checkMovement();
                mapRender.renderMap();
                mapRender.renderPlayer();
                player.calculateRay();

                dimensionalRender.repaint();
                mapRender.repaint();
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.jFrame.setTitle(String.format("%s | %d fps", title, frames));
                frames = 0;
            }
        }
    }

    public synchronized void stop() {
        try {
            thread.join();
            System.out.println("I guess?!");
        }catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    private void checkMovement() {
        if (inputControls.isUp()) player.moveUp();
        if (inputControls.isDown()) player.moveDown();
        if (inputControls.isLeft()) player.rotateLeft();
        if (inputControls.isRight()) player.rotateRight();
        if (inputControls.isQuit()) this.stop();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread("display.renderer");
        this.run();
    }
}
