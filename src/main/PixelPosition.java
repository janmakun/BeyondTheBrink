package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PixelPosition extends MouseAdapter {
    private int mouseX;
    private int mouseY;
    private boolean clicked;

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        clicked = true;
        System.out.println("Pixel clicked at: X=" + mouseX + ", Y=" + mouseY);
    }

    // Getters
    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }
    public boolean isClicked() {
        return clicked;
    }

    public void resetClick() {
        clicked = false;
    }

}
