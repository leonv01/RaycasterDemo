package FinalBuild;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapRender extends JPanel {

    private final BufferedImage image;
    private static Graphics2D graphics2D;

    private final int WIDTH = Display.WIDTH / 2;
    private final int HEIGHT = Display.HEIGHT;

    public MapRender() {
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        jPanel.setVisible(false);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2D = image.createGraphics();
    }

    public static void renderRays() {
        graphics2D.setColor(Color.BLUE);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawLine((int) Display.player.getX(), (int) Display.player.getY(), (int) Display.player.getxRay(), (int) Display.player.getyRay());

        graphics2D.setColor(Color.GREEN);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawLine((int) Display.player.getX(), (int) Display.player.getY(), (int) (Display.player.getX() + Display.player.getxDelta() * 10), (int) (Display.player.getY() - Display.player.getyDelta() * 10));
    }

    public void renderMap() {

        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(0, 0, WIDTH, HEIGHT);

        int xAlt, yAlt, x, y;
        xAlt = yAlt = 0;

        for (int i = 0; i < Display.map.getYLength(); i++) {
            for (int j = 0; j < Display.map.getXLength(); j++) {
                x = (WIDTH / Display.map.getXLength()) * (j + 1);
                y = (HEIGHT / Display.map.getYLength()) * (i + 1);
                graphics2D.setColor(Display.map.getValue(i, j) == 1 ? Color.BLACK : Color.WHITE);
                graphics2D.fillPolygon(
                        new int[]{xAlt + 1, x - 1, x - 1, xAlt + 1},
                        new int[]{y - 1, y - 1, yAlt + 1, yAlt + 1},
                        4
                );
                xAlt = x;
            }
            xAlt = 0;
            yAlt = (HEIGHT / Display.map.getYLength()) * (i + 1);
        }
    }

    public void renderPlayer() {
        graphics2D.setColor(Color.ORANGE);
        int[] coords = Display.player.playerModelCoords();
        graphics2D.fillPolygon(
                new int[]{coords[0], coords[1], coords[1], coords[0]},
                new int[]{coords[2], coords[2], coords[3], coords[3]},
                4
        );
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
