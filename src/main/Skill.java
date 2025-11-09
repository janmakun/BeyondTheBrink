package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Base class for character skills
 */
public class Skill {
    protected int x, y;
    protected int width, height;
    protected String direction;
    protected boolean active;
    protected int frameIndex;
    protected int frameCount;
    protected int frameDelay;
    protected int totalFrames;

    protected BufferedImage[] upFrames;
    protected BufferedImage[] downFrames;
    protected BufferedImage[] leftFrames;
    protected BufferedImage[] rightFrames;

    protected int cooldownTime;
    protected int cooldownCounter;
    protected boolean onCooldown;

    public Skill(int width, int height, int frameDelay, int totalFrames, int cooldownTime) {
        this.width = width;
        this.height = height;
        this.frameDelay = frameDelay;
        this.totalFrames = totalFrames;
        this.cooldownTime = cooldownTime;
        this.active = false;
        this.onCooldown = false;
        this.cooldownCounter = 0;
    }

    public void activate(int charX, int charY, String direction) {
        if (!active && !onCooldown) {
            this.active = true;
            this.direction = direction;
            this.frameIndex = 0;
            this.frameCount = 0;
            this.x = charX;
            this.y = charY;
        }
    }

    public void update(int charX, int charY) {
        // Update position to follow character
        if (active) {
            this.x = charX;
            this.y = charY;

            frameCount++;
            if (frameCount >= frameDelay) {
                frameIndex++;
                if (frameIndex >= totalFrames) {
                    active = false;
                    frameIndex = 0;
                    onCooldown = true;
                    cooldownCounter = 0;
                }
                frameCount = 0;
            }
        }

        if (onCooldown) {
            cooldownCounter++;
            if (cooldownCounter >= cooldownTime) {
                onCooldown = false;
                cooldownCounter = 0;
            }
        }
    }

    public void draw(Graphics g, int cameraX, int cameraY) {
        if (!active) return;

        BufferedImage currentFrame = null;

        switch (direction) {
            case "up":
                currentFrame = (upFrames != null && frameIndex < upFrames.length) ? upFrames[frameIndex] : null;
                break;
            case "down":
                currentFrame = (downFrames != null && frameIndex < downFrames.length) ? downFrames[frameIndex] : null;
                break;
            case "left":
                currentFrame = (leftFrames != null && frameIndex < leftFrames.length) ? leftFrames[frameIndex] : null;
                break;
            case "right":
                currentFrame = (rightFrames != null && frameIndex < rightFrames.length) ? rightFrames[frameIndex] : null;
                break;
        }

        if (currentFrame != null) {
            g.drawImage(currentFrame, x - cameraX, y - cameraY, width, height, null);
        }
    }

    public boolean isActive() { return active; }
    public boolean isOnCooldown() { return onCooldown; }
    public float getCooldownPercent() {
        return onCooldown ? (float) cooldownCounter / cooldownTime : 0f;
    }

    public void setFrames(BufferedImage[] up, BufferedImage[] down, BufferedImage[] left, BufferedImage[] right) {
        this.upFrames = up;
        this.downFrames = down;
        this.leftFrames = left;
        this.rightFrames = right;
    }
}