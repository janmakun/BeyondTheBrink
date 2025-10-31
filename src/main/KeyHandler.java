package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Movement keys only work when playing
        if (gp.getGameState() == GameState.PLAYING) {
            if (code == KeyEvent.VK_UP) upPressed = true;
            if (code == KeyEvent.VK_DOWN) downPressed = true;
            if (code == KeyEvent.VK_LEFT) leftPressed = true;
            if (code == KeyEvent.VK_RIGHT) rightPressed = true;

            if (code == KeyEvent.VK_M) {
                gp.switchMap();
            }
        }

        // ESC key toggles pause
        if (code == KeyEvent.VK_ESCAPE) {
            if (gp.getGameState() == GameState.PLAYING) {
                gp.pauseGame();
            } else if (gp.getGameState() == GameState.PAUSED) {
                gp.resumeGame();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isUpPressed() { return upPressed; }
    public boolean isDownPressed() { return downPressed; }
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }

}