package main;

import java.awt.*;

/**
 * Dynamic health bar that calculates frames based on max health and damage
 */
public class HealthBar {
    private int maxHealth;
    private int barWidth;
    private int barHeight;

    public HealthBar(int maxHealth, int barWidth, int barHeight) {
        this.maxHealth = maxHealth;
        this.barWidth = barWidth;
        this.barHeight = barHeight;
    }

    /**
     * Draw health bar at specified position
     * @param g2 Graphics2D object
     * @param centerX Center X position
     * @param y Y position (top of bar)
     * @param healthPercent Health percentage (0.0 to 1.0)
     */
    public void draw(Graphics2D g2, int centerX, int y, float healthPercent) {
        int barX = centerX - barWidth / 2;

        // Background (black border)
        g2.setColor(Color.BLACK);
        g2.fillRect(barX - 1, y - 1, barWidth + 2, barHeight + 2);

        // Red background (empty health)
        g2.setColor(new Color(139, 0, 0)); // Dark red
        g2.fillRect(barX, y, barWidth, barHeight);

        // Green/Yellow/Red health (gradient based on health)
        int healthWidth = (int) (barWidth * healthPercent);
        Color healthColor;

        if (healthPercent > 0.6f) {
            healthColor = new Color(0, 200, 0); // Green
        } else if (healthPercent > 0.3f) {
            healthColor = new Color(255, 200, 0); // Yellow
        } else {
            healthColor = new Color(255, 50, 50); // Red
        }

        g2.setColor(healthColor);
        g2.fillRect(barX, y, healthWidth, barHeight);

        // White border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(barX, y, barWidth, barHeight);
    }

    /**
     * Calculate number of frames needed based on max health and damage per hit
     * For example: 100 HP with 20 damage = 5 frames
     */
    public static int calculateFrameCount(int maxHealth, int damagePerHit) {
        return (int) Math.ceil((double) maxHealth / damagePerHit);
    }
}