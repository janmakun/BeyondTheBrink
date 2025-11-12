package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC {
    private int x, y;
    private int width, height;
    private BufferedImage[] sprites; // Changed from single sprite to array
    private int currentFrame = 0;
    private int idleAnimationTimer = 0;
    private static final int IDLE_FRAME_DELAY = 15; // Adjust speed as needed

    private String dialogue;
    private boolean showDialogue = false;
    private int dialogueTimer = 0;
    private static final int DIALOGUE_DURATION = 300; // 5 seconds at 60 FPS

    public NPC(int x, int y, BufferedImage[] sprites, String dialogue) {
        this.x = x;
        this.y = y;
        this.sprites = sprites;
        this.dialogue = dialogue;
        this.width = 175;
        this.height = 175;
    }

    public void interact() {
        showDialogue = true;
        dialogueTimer = DIALOGUE_DURATION;
        System.out.println("NPC interaction triggered!");
    }

    public void update() {
        // Update dialogue timer
        if (showDialogue && dialogueTimer > 0) {
            dialogueTimer--;
            if (dialogueTimer <= 0) {
                showDialogue = false;
            }
        }

        // Update idle animation - cycle through frames
        idleAnimationTimer++;
        if (idleAnimationTimer >= IDLE_FRAME_DELAY) {
            currentFrame++;
            if (currentFrame >= sprites.length) {
                currentFrame = 0; // Loop back to first frame
            }
            idleAnimationTimer = 0;
        }
    }

    public boolean isPlayerNearby(CharacterLoad player) {
        int distance = 150; // Interaction distance (increased for easier interaction)

        // Get centers of both NPC and player
        int playerCenterX = player.getX() + player.getWidth() / 2;
        int playerCenterY = player.getY() + player.getHeight() / 2;
        int npcCenterX = x + width / 2;
        int npcCenterY = y + height / 2;

        int dx = playerCenterX - npcCenterX;
        int dy = playerCenterY - npcCenterY;

        double actualDistance = Math.sqrt(dx * dx + dy * dy);

        // Debug output
        if (actualDistance < distance + 50) { // Show when somewhat close
//            System.out.println("Player distance from NPC: " + (int)actualDistance + " (need < " + distance + ")");
        }

        return actualDistance < distance;
    }

    public void draw(Graphics g, int cameraX, int cameraY) {
        // Draw without camera offset (same as monsters)
        if (sprites != null && currentFrame < sprites.length && sprites[currentFrame] != null) {
            g.drawImage(sprites[currentFrame], x, y, width, height, null);
        }

        // Draw dialogue box if active
        if (showDialogue) {
            drawDialogue(g);
        }

        // Debug: Draw NPC hitbox
        // g.setColor(Color.GREEN);
        // g.drawRect(x, y, width, height);
    }

    private void drawDialogue(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Position above NPC (no camera offset needed)
        int boxX = x - 50;
        int boxY = y - 120;
        int boxWidth = 250;
        int boxHeight = 100;

        // Draw dialogue box background
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        // Draw border
        g2.setColor(new Color(255, 255, 255, 255));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        // Draw text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 12));

        // Word wrap the dialogue
        String[] words = dialogue.split(" ");
        StringBuilder line = new StringBuilder();
        int yOffset = boxY + 25;

        for (String word : words) {
            if (g2.getFontMetrics().stringWidth(line + word + " ") < boxWidth - 20) {
                line.append(word).append(" ");
            } else {
                g2.drawString(line.toString(), boxX + 10, yOffset);
                line = new StringBuilder(word + " ");
                yOffset += 20;
            }
        }
        g2.drawString(line.toString(), boxX + 10, yOffset);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isShowingDialogue() { return showDialogue; }
}