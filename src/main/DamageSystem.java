package main;

import java.awt.*;

/**
 * Handles damage calculation, health management, and hit detection
 */
public class DamageSystem {

    // Damage values for different attack types
    public static final int BASIC_ATTACK_DAMAGE = 10;
    public static final int SKILL_Q_DAMAGE = 30;
    public static final int SKILL_E_DAMAGE = 50;
    public static final int SKILL_R_DAMAGE = 70;

    // Blue sword multipliers
    public static final float BLUE_SWORD_MULTIPLIER = 2.0f;
    // Red sword multipliers
    public static final float RED_SWORD_MULTIPLIER = 2.0f;

    /**
     * Represents an entity that can take damage
     */
    public static class DamageableEntity {
        private int maxHealth;
        private int currentHealth;
        private boolean isDead;
        private long lastDamageTime;
        private long invulnerabilityDuration = 500; // 0.5 seconds invulnerability

        // Position and hitbox
        private int x, y;
        private int width, height;

        public DamageableEntity(int maxHealth, int x, int y, int width, int height) {
            this.maxHealth = maxHealth;
            this.currentHealth = maxHealth;
            this.isDead = false;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.lastDamageTime = 0;
        }

        /**
         * Apply damage to this entity
         */
        public boolean takeDamage(int damage) {
            // Check invulnerability frames
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastDamageTime < invulnerabilityDuration) {
                return false; // Still invulnerable
            }

            currentHealth -= damage;
            lastDamageTime = currentTime;

            if (currentHealth <= 0) {
                currentHealth = 0;
                isDead = true;
            }

            return true; // Damage was applied
        }

        public void heal(int amount) {
            currentHealth = Math.min(currentHealth + amount, maxHealth);
            if (currentHealth > 0) {
                isDead = false;
            }
        }

        public Rectangle getHitbox() {
            return new Rectangle(x, y, width, height);
        }

        public void updatePosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isInvulnerable() {
            long currentTime = System.currentTimeMillis();
            return currentTime - lastDamageTime < invulnerabilityDuration;
        }

        // Getters
        public int getCurrentHealth() { return currentHealth; }
        public int getMaxHealth() { return maxHealth; }
        public boolean isDead() { return isDead; }
        public float getHealthPercentage() { return (float) currentHealth / maxHealth; }

        // Setters
        public void setMaxHealth(int maxHealth) {
            this.maxHealth = maxHealth;
            if (currentHealth > maxHealth) {
                currentHealth = maxHealth;
            }
        }

        public void setInvulnerabilityDuration(long duration) {
            this.invulnerabilityDuration = duration;
        }
    }

    /**
     * Represents an attack hitbox
     */
    public static class AttackHitbox {
        private Rectangle hitbox;
        private int damage;
        private long creationTime;
        private long duration; // How long the hitbox is active
        private String attackType; // "basic", "skillQ", "skillE", "skillR"

        public AttackHitbox(int x, int y, int width, int height, int damage, long duration, String attackType) {
            this.hitbox = new Rectangle(x, y, width, height);
            this.damage = damage;
            this.creationTime = System.currentTimeMillis();
            this.duration = duration;
            this.attackType = attackType;
        }

        public boolean isActive() {
            return System.currentTimeMillis() - creationTime < duration;
        }

        public boolean collidesWith(Rectangle other) {
            return hitbox.intersects(other);
        }

        public Rectangle getHitbox() { return hitbox; }
        public int getDamage() { return damage; }
        public String getAttackType() { return attackType; }
    }

    /**
     * Calculate damage based on attack type and sword type
     */
    public static int calculateDamage(String attackType, boolean isRedSword) {
        int baseDamage;

        switch (attackType) {
            case "basic":
                baseDamage = BASIC_ATTACK_DAMAGE;
                break;
            case "skillQ":
                baseDamage = SKILL_Q_DAMAGE;
                break;
            case "skillE":
                baseDamage = SKILL_E_DAMAGE;
                break;
            case "skillR":
                baseDamage = SKILL_R_DAMAGE;
                break;
            default:
                baseDamage = BASIC_ATTACK_DAMAGE;
        }

        float multiplier = isRedSword ? RED_SWORD_MULTIPLIER : BLUE_SWORD_MULTIPLIER;
        return (int) (baseDamage * multiplier);
    }

    /**
     * Create an attack hitbox based on player position and direction
     */
    public static AttackHitbox createAttackHitbox(int playerX, int playerY, String direction,
                                                  String attackType, boolean isRedSword) {
        int damage = calculateDamage(attackType, isRedSword);
        int hitboxWidth = 80;
        int hitboxHeight = 80;
        long duration = 200; // 200ms active

        // Adjust hitbox size for skills
        if (attackType.equals("skillE")) {
            hitboxWidth = 500;
            hitboxHeight = 500;
            duration = 300;
        } else if (attackType.equals("skillR")) {
            hitboxWidth = 200;
            hitboxHeight = 200;
            duration = 400;
        }

        // Position hitbox based on direction
        int hitboxX = playerX;
        int hitboxY = playerY;

        switch (direction) {
            case "down":
                hitboxY = playerY + 50;
                break;
            case "up":
                hitboxY = playerY - hitboxHeight;
                break;
            case "left":
                hitboxX = playerX - hitboxWidth;
                break;
            case "right":
                hitboxX = playerX + 50;
                break;
        }

        return new AttackHitbox(hitboxX, hitboxY, hitboxWidth, hitboxHeight, damage, duration, attackType);
    }

    /**
     * Check collision and apply damage
     */
    public static boolean checkAndApplyDamage(AttackHitbox attack, DamageableEntity target) {
        if (!attack.isActive() || target.isDead()) {
            return false;
        }

        if (attack.collidesWith(target.getHitbox())) {
            return target.takeDamage(attack.getDamage());
        }

        return false;
    }

    /**
     * Draw health bar above entity
     */
    public static void drawHealthBar(Graphics2D g2, DamageableEntity entity, int screenX, int screenY) {
        if (entity.isDead()) return;

        int barWidth = 60;
        int barHeight = 8;
        int barX = screenX - barWidth / 2;
        int barY = screenY - 20;

        // Background
        g2.setColor(Color.BLACK);
        g2.fillRect(barX - 1, barY - 1, barWidth + 2, barHeight + 2);

        // Red background
        g2.setColor(Color.RED);
        g2.fillRect(barX, barY, barWidth, barHeight);

        // Green health
        int healthWidth = (int) (barWidth * entity.getHealthPercentage());
        g2.setColor(Color.GREEN);
        g2.fillRect(barX, barY, healthWidth, barHeight);

        // White border
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, barY, barWidth, barHeight);
    }

    /**
     * Draw damage numbers
     */
    public static class DamageNumber {
        private int damage;
        private int x, y;
        private long creationTime;
        private long duration = 1000; // 1 second

        public DamageNumber(int damage, int x, int y) {
            this.damage = damage;
            this.x = x;
            this.y = y;
            this.creationTime = System.currentTimeMillis();
        }

        public boolean isActive() {
            return System.currentTimeMillis() - creationTime < duration;
        }

        public void draw(Graphics2D g2) {
            long elapsed = System.currentTimeMillis() - creationTime;

            // FIX: Clamp alpha between 0.0 and 1.0
            float alpha = 1.0f - (float) elapsed / duration;
            alpha = Math.max(0.0f, Math.min(1.0f, alpha)); // Clamp to valid range

            int yOffset = (int) (elapsed / 10); // Float upwards

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setFont(new Font("Arial", Font.BOLD, 24));

            // Shadow
            g2.setColor(Color.BLACK);
            g2.drawString("-" + damage, x + 1, y - yOffset + 1);

            // Damage text
            g2.setColor(Color.RED);
            g2.drawString("-" + damage, x, y - yOffset);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}