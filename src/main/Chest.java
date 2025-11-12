package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chest {
    private int x, y;
    private int width, height;
    private BufferedImage[] sprites; // 3 frames: idle1, idle2, open
    private int currentFrame = 0;
    private boolean isOpen = false;
    private boolean isOpening = false;
    private int animationTimer = 0;
    private int idleAnimationTimer = 0;
    private static final int IDLE_FRAME_DELAY = 30;
    private static final int OPEN_ANIMATION_DELAY = 15;

    public Chest(int x, int y, BufferedImage[] sprites) {
        this.x = x;
        this.y = y;
        this.sprites = sprites;
        this.width = 150;
        this.height = 150;
    }

    public void open() {
        if (!isOpen && !isOpening) {
            isOpening = true;
            animationTimer = 0;
            System.out.println("Chest opening!");
        }
    }

    public void update() {
        if (isOpening) {
            animationTimer++;
            if (animationTimer >= OPEN_ANIMATION_DELAY) {
                currentFrame = 2; // Open frame
                isOpening = false;
                isOpen = true;
                System.out.println("Chest opened!");
            }
        } else if (!isOpen) {
            // Idle animation between frame 0 and 1
            idleAnimationTimer++;
            if (idleAnimationTimer >= IDLE_FRAME_DELAY) {
                currentFrame = (currentFrame == 0) ? 1 : 0;
                idleAnimationTimer = 0;
            }
        }
    }

    public boolean isPlayerNearby(CharacterLoad player) {
        int distance = 150; // Interaction distance (increased for easier interaction)

        // Get centers of both chest and player
        int playerCenterX = player.getX() + player.getWidth() / 2;
        int playerCenterY = player.getY() + player.getHeight() / 2;
        int chestCenterX = x + width / 2;
        int chestCenterY = y + height / 2;

        int dx = playerCenterX - chestCenterX;
        int dy = playerCenterY - chestCenterY;

        double actualDistance = Math.sqrt(dx * dx + dy * dy);

        // Debug output
        if (actualDistance < distance + 50) { // Show when somewhat close
//            System.out.println("Player distance from Chest: " + (int)actualDistance + " (need < " + distance + ")");
        }

        return actualDistance < distance;
    }

    public void draw(Graphics g, int cameraX, int cameraY) {
        // Draw without camera offset (same as monsters)
        if (sprites != null && currentFrame < sprites.length && sprites[currentFrame] != null) {
            g.drawImage(sprites[currentFrame], x, y, width, height, null);
        }

        // Debug: Draw chest hitbox
        // g.setColor(Color.CYAN);
        // g.drawRect(x, y, width, height);
    }

    public boolean isOpen() { return isOpen; }
    public int getX() { return x; }
    public int getY() { return y; }
}