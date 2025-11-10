package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Monster2 {
    private int x, y;
    private int width, height;
    private int spriteSize = 150;

    // Movement
    private String direction = "left";
    private boolean moving = false;
    private int speed = 3; // Faster than Monster

    // Animation
    private BufferedImage[] leftWalkSprites;
    private BufferedImage[] rightWalkSprites;
    private BufferedImage[] leftAttackSprites;
    private BufferedImage[] rightAttackSprites;

    private int animationFrame = 0;
    private int animationCounter = 0;
    private int animationSpeed = 12; // Slightly faster animation

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
    private int aiChangeDirection = 90; // Change direction faster than Monster
    private int detectionRange = 350; // Longer detection range
    private int attackRange = 90;

    // Flying/Hovering behavior
    private int verticalOffset = 0;
    private int hoverCounter = 0;

    public Monster2(int x, int y, int spriteSize) {
        this.x = x;
        this.y = y;
        this.spriteSize = spriteSize;
        this.width = spriteSize;
        this.height = spriteSize;

        loadSprites();
    }

    private void loadSprites() {
        // Load walk sprites
        leftWalkSprites = new BufferedImage[2];
        leftWalkSprites[0] = loadAndScaleImage("res/Monster2/mons3left1.png");
        leftWalkSprites[1] = loadAndScaleImage("res/Monster2/mons3left2.png");

        rightWalkSprites = new BufferedImage[2];
        rightWalkSprites[0] = loadAndScaleImage("res/Monster2/dungmons1.png");
        rightWalkSprites[1] = loadAndScaleImage("res/Monster2/dungmons2.png");

        // Load attack sprites
        leftAttackSprites = new BufferedImage[2];
        leftAttackSprites[0] = loadAndScaleImage("res/Monster2/mons3left3.png");
        leftAttackSprites[1] = loadAndScaleImage("res/Monster2/mons3left4.png");

        rightAttackSprites = new BufferedImage[2];
        rightAttackSprites[0] = loadAndScaleImage("res/Monster2/dungmons3.png");
        rightAttackSprites[1] = loadAndScaleImage("res/Monster2/dungmons4.png");
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
            System.err.println("Failed to load Monster2 sprite: " + path);
            return null;
        }
    }

    /**
     * Update Monster2 AI, movement, and animation
     */
    public void update(CharacterLoad player, Collision collision) {
        // Update cooldown timer
        if (onCooldown) {
            attackCooldownCounter++;
            if (attackCooldownCounter >= attackCooldown) {
                onCooldown = false;
                attackCooldownCounter = 0;
            }
        }

        // Hovering animation (up and down movement)
        updateHover();

        if (attacking) {
            updateAttack();
            return;
        }

        if (preparingAttack) {
            updateAttackPreparation();
            // Face the player while preparing
            int dx = player.getX() - x;
            if (dx > 0) {
                direction = "right";
            } else {
                direction = "left";
            }
            return;
        }

        // Calculate distance to player
        int dx = player.getX() - x;
        int dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Check if player is in attack range and not on cooldown
        if (distance < attackRange && !onCooldown) {
            startAttackPreparation();
            // Face the player
            if (dx > 0) {
                direction = "right";
            } else {
                direction = "left";
            }
            moving = false;
            return;
        }

        // Check if player is in detection range
        if (distance < detectionRange) {
            // Chase player (now respects collision)
            chasePlayer(player, collision);
        } else {
            // Wander randomly (now respects collision)
            wander(collision);
        }

        updateAnimation();
    }

    private void updateHover() {
        hoverCounter++;
        // Create smooth hovering motion
        verticalOffset = (int)(Math.sin(hoverCounter * 0.05) * 5);
    }

    private void chasePlayer(CharacterLoad player, Collision collision) {
        moving = true;

        int dx = player.getX() - x;
        int dy = player.getY() - y;

        // Normalize movement
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            int moveX = (int) ((dx / distance) * speed);
            int moveY = (int) ((dy / distance) * speed);

            int nextX = x + moveX;
            int nextY = y + moveY;

            // Check collision before moving
            if (!collision.checkCollision(nextX + 50, nextY + verticalOffset + 35, width - 100, height - 65)) {
                x = nextX;
                y = nextY;

                // Update direction based on movement
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

            // Randomly choose to move or stay still
            int random = (int) (Math.random() * 4);
            if (random == 0) {
                moving = false;
            } else {
                moving = true;
                // Random direction
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

            // Check collision
            if (!collision.checkCollision(nextX + 50, nextY + verticalOffset + 35, width - 100, height - 65)) {
                x = nextX;
            } else {
                // Hit a wall, change direction
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

        // Switch between attack frames
        if (attackCounter % 15 == 0) {
            attackFrame = (attackFrame + 1) % 2;
        }

        // End attack
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
            // Draw attack sprite
            if (direction.equals("left")) {
                currentSprite = leftAttackSprites[attackFrame];
            } else {
                currentSprite = rightAttackSprites[attackFrame];
            }
        } else if (preparingAttack) {
            // Draw idle sprite with warning effect
            if (direction.equals("left")) {
                currentSprite = leftWalkSprites[0];
            } else {
                currentSprite = rightWalkSprites[0];
            }

            // Warning indicator (yellow glow for Monster2)
            if (attackPrepareCounter % 20 < 10) {
                g.setColor(new Color(255, 255, 0, 100));
                g.fillRect(x, y + verticalOffset, width, height);
            }
        } else if (moving) {
            // Draw walk sprite
            if (direction.equals("left")) {
                currentSprite = leftWalkSprites[animationFrame];
            } else {
                currentSprite = rightWalkSprites[animationFrame];
            }
        } else {
            // Draw idle sprite
            if (direction.equals("left")) {
                currentSprite = leftWalkSprites[0];
            } else {
                currentSprite = rightWalkSprites[0];
            }
        }

        if (currentSprite != null) {
            g.drawImage(currentSprite, x, y + verticalOffset, width, height, null);
        }

        // Debug: Draw hitbox
        // g.setColor(Color.YELLOW);
        // g.drawRect(x + 50, y + verticalOffset + 35, width - 100, height - 65);
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

    /**
     * Set custom attack timing (in seconds)
     */
    public void setAttackTiming(float prepareSeconds, float cooldownSeconds) {
        this.attackPrepareTime = (int)(prepareSeconds * 30);
        this.attackCooldown = (int)(cooldownSeconds * 30);
    }

    /**
     * Check if Monster2's attack hitbox overlaps with player
     */
    public boolean isAttackingPlayer(CharacterLoad player) {
        if (!attacking) return false;

        // Monster2 hitbox
        int monsterHitX = x + 50;
        int monsterHitY = y + verticalOffset + 35;
        int monsterHitW = width - 100;
        int monsterHitH = height - 65;

        // Player hitbox
        int playerHitX = player.getX() + 50;
        int playerHitY = player.getY() + 35;
        int playerHitW = player.getWidth() - 100;
        int playerHitH = player.getHeight() - 65;

        // Check overlap
        return monsterHitX < playerHitX + playerHitW &&
                monsterHitX + monsterHitW > playerHitX &&
                monsterHitY < playerHitY + playerHitH &&
                monsterHitY + monsterHitH > playerHitY;
    }
}