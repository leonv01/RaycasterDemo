package FinalBuild;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class InputControls implements KeyListener, MouseMotionListener {

    private boolean up, down, left, right, quit;

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) quit = true;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w': up = true;break;
            case 's' : down = true;break;
            case 'a' : left = true;break;
            case 'd' : right = true; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w' : up = false;break;
            case 's' : down = false;break;
            case 'a' : left = false;break;
            case 'd' : right = false;break;
        }
    }

    public boolean isQuit() {
        return quit;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Dragging");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Moving");
    }
}
