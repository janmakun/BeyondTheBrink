package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Monster {
    private int x, y;
    private int width, height;
    private int spriteSize = 150;

    // Movement
    private String direction = "left";
    private boolean moving = false;
    private int speed = 2;

    // Animation
    private BufferedImage[] leftWalkSprites;
    private BufferedImage[] rightWalkSprites;
    private BufferedImage[] leftAttackSprites;
    private BufferedImage[] rightAttackSprites;

    private int animationFrame = 0;
    private int animationCounter = 0;
    private int animationSpeed = 15;

    // Attack
    private boolean attacking = false;
    private boolean preparingAttack = false;
    private int attackFrame = 0;
    private int attackCounter = 0;
    private int attackDuration = 30;
    private int attackPrepareTime = 60;
    private int attackPrepareCounter = 0;
    private int attackCooldown = 60;
    private int attackCooldownCounter = 0;
    private boolean onCooldown = false;

    // AI Behavior
    private int aiCounter = 0;
    private int aiChangeDirection = 120;
    private int detectionRange = 300;
    private int attackRange = 80;

    // Health System
    private DamageSystem.DamageableEntity health;
    private int maxHealth = 100;
    private HealthBar healthBar;

    // Death Animation
    private boolean isDying = false;
    private int deathAnimCounter = 0;
    private int deathAnimDuration = 60; // 1 second at 60fps
    private int blinkInterval = 8; // Blink every 8 frames

    public Monster(int x, int y, int spriteSize) {
        this.x = x;
        this.y = y;
        this.spriteSize = spriteSize;
        this.width = spriteSize;
        this.height = spriteSize;

        // Initialize health system
        this.health = new DamageSystem.DamageableEntity(maxHealth, x, y, width, height);
        this.healthBar = new HealthBar(maxHealth, 60, 8);

        loadSprites();
    }

    private void loadSprites() {
        leftWalkSprites = new BufferedImage[2];
        leftWalkSprites[0] = loadAndScaleImage("res/Monster/mouthmonster.png");
        leftWalkSprites[1] = loadAndScaleImage("res/Monster/mouthmonsterleftwalk.png");

        rightWalkSprites = new BufferedImage[2];
        rightWalkSprites[0] = loadAndScaleImage("res/Monster/mouthmonsright.png");
        rightWalkSprites[1] = loadAndScaleImage("res/Monster/mouthmonsright2.png");

        leftAttackSprites = new BufferedImage[2];
        leftAttackSprites[0] = loadAndScaleImage("res/Monster/mouthmonster_kagat2.1.png");
        leftAttackSprites[1] = loadAndScaleImage("res/Monster/mouthmonster_kagat2.png");

        rightAttackSprites = new BufferedImage[2];
        rightAttackSprites[0] = loadAndScaleImage("res/Monster/mouthmonster_kagat1.png");
        rightAttackSprites[1] = loadAndScaleImage("res/Monster/mouthmonspakagat.png");
    }

    private BufferedImage loadAndScaleImage(String path) {
        try {
            BufferedImage raw = ImageIO.read(new File(path));
            if (raw == null) return null;

            BufferedImage scaled = new BufferedImage(spriteSize, spriteSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = scaled.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(raw, 0, 0, spriteSize, spriteSize, null);
            g2.dispose();

            return scaled;
        } catch (IOException e) {
            System.err.println("Failed to load monster sprite: " + path);
            return null;
        }
    }

    public void update(CharacterLoad player, Collision collision) {
        // Update health position
        health.updatePosition(x, y);

        // Handle death animation
        if (health.isDead() && !isDying) {
            isDying = true;
            deathAnimCounter = 0;
        }

        if (isDying) {
            deathAnimCounter++;
            return; // Don't update AI during death animation
        }

        if (onCooldown) {
            attackCooldownCounter++;
            if (attackCooldownCounter >= attackCooldown) {
                onCooldown = false;
                attackCooldownCounter = 0;
            }
        }

        if (attacking) {
            updateAttack();
            return;
        }

        if (preparingAttack) {
            updateAttackPreparation();
            int dx = player.getX() - x;
            if (dx > 0) {
                direction = "right";
            } else {
                direction = "left";
            }
            return;
        }

        int dx = player.getX() - x;
        int dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < attackRange && !onCooldown) {
            startAttackPreparation();
            if (dx > 0) {
                direction = "right";
            } else {
                direction = "left";
            }
            moving = false;
            return;
        }

        if (distance < detectionRange) {
            chasePlayer(player, collision);
        } else {
            wander(collision);
        }

        updateAnimation();
    }

    private void chasePlayer(CharacterLoad player, Collision collision) {
        moving = true;

        int dx = player.getX() - x;
        int dy = player.getY() - y;

        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            int moveX = (int) ((dx / distance) * speed);
            int moveY = (int) ((dy / distance) * speed);

            int nextX = x + moveX;
            int nextY = y + moveY;

            if (!collision.checkCollision(nextX + 50, nextY + 35, width - 100, height - 65)) {
                x = nextX;
                y = nextY;

                if (Math.abs(dx) > Math.abs(dy)) {
                    direction = dx > 0 ? "right" : "left";
                }
            }
        }
    }

    private void wander(Collision collision) {
        aiCounter++;

        if (aiCounter >= aiChangeDirection) {
            aiCounter = 0;

            int random = (int) (Math.random() * 4);
            if (random == 0) {
                moving = false;
            } else {
                moving = true;
                int dirRandom = (int) (Math.random() * 2);
                direction = dirRandom == 0 ? "left" : "right";
            }
        }

        if (moving) {
            int nextX = x;
            int nextY = y;

            if (direction.equals("left")) {
                nextX -= speed;
            } else {
                nextX += speed;
            }

            if (!collision.checkCollision(nextX + 50, nextY + 35, width - 100, height - 65)) {
                x = nextX;
            } else {
                direction = direction.equals("left") ? "right" : "left";
            }
        }

        updateAnimation();
    }

    private void updateAnimation() {
        if (!moving) return;

        animationCounter++;
        if (animationCounter >= animationSpeed) {
            animationCounter = 0;
            animationFrame = (animationFrame + 1) % 2;
        }
    }

    private void startAttackPreparation() {
        if (!preparingAttack && !attacking && !onCooldown) {
            preparingAttack = true;
            attackPrepareCounter = 0;
            moving = false;
        }
    }

    private void updateAttackPreparation() {
        attackPrepareCounter++;

        if (attackPrepareCounter >= attackPrepareTime) {
            preparingAttack = false;
            attackPrepareCounter = 0;
            startAttack();
        }
    }

    private void startAttack() {
        if (!attacking) {
            attacking = true;
            attackFrame = 0;
            attackCounter = 0;
        }
    }

    private void updateAttack() {
        attackCounter++;

        if (attackCounter % 15 == 0) {
            attackFrame = (attackFrame + 1) % 2;
        }

        if (attackCounter >= attackDuration) {
            attacking = false;
            attackCounter = 0;
            attackFrame = 0;
            onCooldown = true;
            attackCooldownCounter = 0;
        }
    }

    public void draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage currentSprite = null;

        if (attacking) {
            if (direction.equals("left")) {
                currentSprite = leftAttackSprites[attackFrame];
            } else {
                currentSprite = rightAttackSprites[attackFrame];
            }
        } else if (preparingAttack) {
            if (direction.equals("left")) {
                currentSprite = leftWalkSprites[0];
            } else {
                currentSprite = rightWalkSprites[0];
            }

            // COMMENTED OUT: Attack warning visual
            /*
            if (attackPrepareCounter % 20 < 10) {
                g.setColor(new Color(255, 0, 0, 100));
                g.fillRect(x, y, width, height);
            }
            */
        } else if (moving) {
            if (direction.equals("left")) {
                currentSprite = leftWalkSprites[animationFrame];
            } else {
                currentSprite = rightWalkSprites[animationFrame];
            }
        } else {
            if (direction.equals("left")) {
                currentSprite = leftWalkSprites[0];
            } else {
                currentSprite = rightWalkSprites[0];
            }
        }

        // Death animation
        if (isDying) {
            Graphics2D g2 = (Graphics2D) g;

            // Calculate fade progress (0.0 to 1.0)
            float fadeProgress = (float) deathAnimCounter / deathAnimDuration;

            // Blinking effect (first half of animation)
            boolean shouldShow = true;
            if (fadeProgress < 0.5f) {
                shouldShow = (deathAnimCounter / blinkInterval) % 2 == 0;
            }

            if (shouldShow && currentSprite != null) {
                // Create a white tinted version that fades
                BufferedImage tintedSprite = new BufferedImage(
                        currentSprite.getWidth(),
                        currentSprite.getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );

                Graphics2D g2d = tintedSprite.createGraphics();
                g2d.drawImage(currentSprite, 0, 0, null);

                // Apply white tint that increases over time
                float whiteTint = fadeProgress;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, whiteTint));
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, tintedSprite.getWidth(), tintedSprite.getHeight());
                g2d.dispose();

                // Draw with decreasing opacity
                float alpha = 1.0f - fadeProgress;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.drawImage(tintedSprite, x, y, width, height, null);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }
        } else if (currentSprite != null) {
            g.drawImage(currentSprite, x, y, width, height, null);
        }

        // Draw health bar if damaged and not dying
        if (!isDying && health.getCurrentHealth() < health.getMaxHealth()) {
            healthBar.draw((Graphics2D)g, x + width/2, y - 10, health.getHealthPercentage());
        }
    }

    // Getters and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isAttacking() { return attacking; }
    public boolean isPreparingAttack() { return preparingAttack; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setAttackTiming(float prepareSeconds, float cooldownSeconds) {
        this.attackPrepareTime = (int)(prepareSeconds * 30);
        this.attackCooldown = (int)(cooldownSeconds * 30);
    }

    public boolean isAttackingPlayer(CharacterLoad player) {
        if (!attacking) return false;

        int monsterHitX = x + 50;
        int monsterHitY = y + 35;
        int monsterHitW = width - 100;
        int monsterHitH = height - 65;

        int playerHitX = player.getX() + 50;
        int playerHitY = player.getY() + 35;
        int playerHitW = player.getWidth() - 100;
        int playerHitH = player.getHeight() - 65;

        return monsterHitX < playerHitX + playerHitW &&
                monsterHitX + monsterHitW > playerHitX &&
                monsterHitY < playerHitY + playerHitH &&
                monsterHitY + monsterHitH > playerHitY;
    }

    public boolean isDead() {
        return isDying && deathAnimCounter >= deathAnimDuration;
    }

    public boolean takeDamage(int damage) {
        return health.takeDamage(damage);
    }

    public Rectangle getHitbox() {
        return new Rectangle(x + 50, y + 35, width - 100, height - 65);
    }

    public DamageSystem.DamageableEntity getHealth() {
        return health;
    }
}