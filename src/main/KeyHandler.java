package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed;
    private boolean qPressed, ePressed, rPressed;
    private boolean fPressed; // Interaction key
    private boolean onePressed; // Sword toggle key
    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.getGameState() == GameState.PLAYING) {
            // Movement is always allowed
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) upPressed = true;
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) downPressed = true;
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) leftPressed = true;
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) rightPressed = true;

            // Attack key - only works if sword is equipped AND visible
            if (code == KeyEvent.VK_SPACE && !gp.character.isAttacking()) {
                if (gp.hasSword() && gp.isSwordVisible()) {
                    attackPressed = true;
                    String direction = getCurrentDirection();
                    gp.character.startAttack(direction);
                } else if (!gp.hasSword()) {
                    gp.showWarning("You need a sword to attack!");
                } else if (!gp.isSwordVisible()) {
                    gp.showWarning("Turn on your sword first! (Press 1)");
                }
            }

            // Skill Q - only works if sword is equipped AND visible
            if (code == KeyEvent.VK_Q && !qPressed) {
                if (gp.hasSword() && gp.isSwordVisible()) {
                    qPressed = true;
                    String direction = gp.character.getLastDirection();
                    gp.getSkillManager().activateSkillQ(direction);
                } else if (!gp.hasSword()) {
                    gp.showWarning("You need a sword to use skills!");
                } else if (!gp.isSwordVisible()) {
                    gp.showWarning("Turn on your sword first! (Press 1)");
                }
            }

            // Skill E - only works if sword is equipped AND visible
            if (code == KeyEvent.VK_E && !ePressed) {
                if (gp.hasSword() && gp.isSwordVisible()) {
                    ePressed = true;
                    String direction = gp.character.getLastDirection();
                    gp.getSkillManager().activateSkillE(direction);
                } else if (!gp.hasSword()) {
                    gp.showWarning("You need a sword to use skills!");
                } else if (!gp.isSwordVisible()) {
                    gp.showWarning("Turn on your sword first! (Press 1)");
                }
            }

            // Skill R - only works if sword is equipped AND visible
            if (code == KeyEvent.VK_R && !rPressed) {
                if (gp.hasSword() && gp.isSwordVisible()) {
                    rPressed = true;
                    String direction = gp.character.getLastDirection();
                    gp.getSkillManager().activateSkillR(direction);
                } else if (!gp.hasSword()) {
                    gp.showWarning("You need a sword to use skills!");
                } else if (!gp.isSwordVisible()) {
                    gp.showWarning("Turn on your sword first! (Press 1)");
                }
            }

            // F key for interaction
            if (code == KeyEvent.VK_F && !fPressed) {
                fPressed = true;
                System.out.println("F key pressed - attempting interaction...");
                gp.handleInteraction();
            }

            // 1 key to toggle sword visibility
            if (code == KeyEvent.VK_1 && !onePressed) {
                onePressed = true;
                gp.toggleSwordVisibility();
            }

            // M key to switch maps
            if (code == KeyEvent.VK_M) {
                gp.switchMap();
            }

            // C key to toggle coordinate display
            if (code == KeyEvent.VK_C) {
                gp.toggleCoordinateDisplay();
            }
        }

        // ESC key works in any state
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

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) rightPressed = false;
        if (code == KeyEvent.VK_SPACE) attackPressed = false;
        if (code == KeyEvent.VK_Q) qPressed = false;
        if (code == KeyEvent.VK_E) ePressed = false;
        if (code == KeyEvent.VK_R) rPressed = false;
        if (code == KeyEvent.VK_F) fPressed = false;
        if (code == KeyEvent.VK_1) onePressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

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
    public boolean isFPressed() { return fPressed; }
    public boolean isOnePressed() { return onePressed; }
}