package FinalBuild;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DimensionalRender extends JPanel {

    private final BufferedImage image;
    private static Graphics2D graphics2D;
    private static final int WIDTH = Display.WIDTH / 2;
    private static final int HEIGHT = Display.HEIGHT;

    public DimensionalRender() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.setVisible(true);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2D = image.createGraphics();
    }

    public void draw3DWalls(int i, int max) {
        int off = max * 2 - i;
        if (i == 0) {
            graphics2D.setColor(new Color(45, 60, 120));
            graphics2D.fillRect(0, 0, WIDTH, HEIGHT / 2);
            graphics2D.setColor(new Color(25,25,25));
            graphics2D.fillRect(0,HEIGHT/2, WIDTH, HEIGHT);
        }

        double dist = Display.player.getDistance();

        int xSet = WIDTH / max;

        double lineH = (Display.xTILE * HEIGHT) / dist;
        int wallHeightMax = HEIGHT;
        if (lineH > wallHeightMax) {
            lineH = wallHeightMax;
        }
        double lineOffset = (HEIGHT / 2.0);
        graphics2D.setColor(new Color(60, 20, 120));
        graphics2D.setStroke(new BasicStroke(xSet));
        graphics2D.drawLine(off * xSet, (int) (lineOffset + lineH), off * xSet, (int) (lineOffset - lineH));
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
