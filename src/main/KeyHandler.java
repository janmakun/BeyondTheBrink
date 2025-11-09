package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed;
    private boolean qPressed, ePressed, rPressed; // Skill keys
    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Movement and attack keys only work when playing
        if (gp.getGameState() == GameState.PLAYING) {
            if (code == KeyEvent.VK_UP) upPressed = true;
            if (code == KeyEvent.VK_DOWN) downPressed = true;
            if (code == KeyEvent.VK_LEFT) leftPressed = true;
            if (code == KeyEvent.VK_RIGHT) rightPressed = true;

            // Attack key (spacebar) - triggers attack in current direction
            if (code == KeyEvent.VK_SPACE && !gp.character.isAttacking()) {
                attackPressed = true;
                String direction = getCurrentDirection();
                gp.character.startAttack(direction);
            }

            // Skill Q - Sword Rotation
            if (code == KeyEvent.VK_Q && !qPressed) {
                qPressed = true;
                String direction = gp.character.getLastDirection();
                gp.getSkillManager().activateSkillQ(direction);
            }

            // Skill E - Second Skill
            if (code == KeyEvent.VK_E && !ePressed) {
                ePressed = true;
                String direction = gp.character.getLastDirection();
                gp.getSkillManager().activateSkillE(direction);
            }

            // Skill R - Power Up
            if (code == KeyEvent.VK_R && !rPressed) {
                rPressed = true;
                String direction = gp.character.getLastDirection();
                gp.getSkillManager().activateSkillR(direction);
            }

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
        if (code == KeyEvent.VK_SPACE) attackPressed = false;
        if (code == KeyEvent.VK_Q) qPressed = false;
        if (code == KeyEvent.VK_E) ePressed = false;
        if (code == KeyEvent.VK_R) rPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // Helper method to determine current direction based on pressed keys
    private String getCurrentDirection() {
        if (upPressed) return "up";
        if (downPressed) return "down";
        if (leftPressed) return "left";
        if (rightPressed) return "right";
        return gp.character.getLastDirection();
    }

    public boolean isUpPressed() { return upPressed; }
    public boolean isDownPressed() { return downPressed; }
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public boolean isAttackPressed() { return attackPressed; }
    public boolean isQPressed() { return qPressed; }
    public boolean isEPressed() { return ePressed; }
    public boolean isRPressed() { return rPressed; }
}