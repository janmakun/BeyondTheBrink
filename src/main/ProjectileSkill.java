package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Ranged skill with animation centered on character
 * The frames contain the projectile animation within them
 */
public class ProjectileSkill extends Skill {
    private int maxRange; // Maximum range in pixels for damage detection

    public ProjectileSkill(int width, int height, int frameDelay, int totalFrames,
                           int cooldownTime, int maxRange) {
        super(width, height, frameDelay, totalFrames, cooldownTime);
        this.maxRange = maxRange;
    }

    @Override
    public void activate(int charX, int charY, String direction) {
        if (!active && !onCooldown) {
            this.active = true;
            this.direction = direction;
            this.frameIndex = 0;
            this.frameCount = 0;

            // Position animation centered on character
            this.x = charX;
            this.y = charY;

            System.out.println("Skill E activated at character position: (" + x + ", " + y + ")");
        }
    }

    @Override
    public void update(int charX, int charY) {
        if (active) {
            // Keep animation centered on character
            this.x = charX;
            this.y = charY;

            // Animate frames
            frameCount++;
            if (frameCount >= frameDelay) {
                frameIndex++;
                if (frameIndex >= totalFrames) {
                    // Animation finished, deactivate
                    active = false;
                    frameIndex = 0;
                    onCooldown = true;
                    cooldownCounter = 0;
                }
                frameCount = 0;
            }
        }

        // Handle cooldown
        if (onCooldown) {
            cooldownCounter++;
            if (cooldownCounter >= cooldownTime) {
                onCooldown = false;
                cooldownCounter = 0;
            }
        }
    }

    // Get projectile hitbox for collision detection (200 pixel range from character)
    public Rectangle getHitbox() {
        if (!active) return null;

        // Create hitbox extending 200 pixels in the skill direction
        switch(direction) {
            case "up":
                return new Rectangle(x + width/2 - 25, y - maxRange, 50, maxRange);
            case "down":
                return new Rectangle(x + width/2 - 25, y + height/2, 50, maxRange);
            case "left":
                return new Rectangle(x - maxRange, y + height/2 - 25, maxRange, 50);
            case "right":
                return new Rectangle(x + width/2, y + height/2 - 25, maxRange, 50);
            default:
                return null;
        }
    }

    public int getMaxRange() {
        return maxRange;
    }
}