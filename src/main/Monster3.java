package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Monster3 {
    private int x, y;
    private int width, height;
    private int spriteSize = 150;

    // Movement
    private String direction = "left";
    private boolean moving = false;
    private int speed = 4; // FASTER than Monster and Monster2

    // Animation
    private BufferedImage[] leftWalkSprites;
    private BufferedImage[] rightWalkSprites;
    private BufferedImage[] leftAttackSprites;
    private BufferedImage[] rightAttackSprites;

    private int animationFrame = 0;
    private int animationCounter = 0;
    private int animationSpeed = 8; // Faster animation

    // Attack
    private boolean attacking = false;
    private boolean preparingAttack = false;
    private int attackFrame = 0;
    private int attackCounter = 0;
    private int attackDuration = 25; // Shorter attack duration
    private int attackPrepareTime = 30; // 1 second delay (faster than others)
    private int attackPrepareCounter = 0;
    private int attackCooldown = 45; // 1.5 seconds cooldown (faster than others)
    private int attackCooldownCounter = 0;
    private boolean onCooldown = false;

    // AI Behavior
    private int aiCounter = 0;
    private int aiChangeDirection = 60; // Change direction very frequently
    private int detectionRange = 400; // LONGEST detection range
    private int attackRange = 100;

    // Dash ability
    private boolean dashing = false;
    private int dashCounter = 0;
    private int dashDuration = 15;
    private int dashCooldown = 180; // 6 seconds between dashes
    private int dashCooldownCounter = 0;
    private int dashSpeed = 8;

    public Monster3(int x, int y, int spriteSize) {
        this.x = x;
        this.y = y;
        this.spriteSize = spriteSize;
        this.width = spriteSize;
        this.height = spriteSize;

        loadSprites();
    }

    private void loadSprites() {
        // Load walk sprites
        leftWalkSprites = new BufferedImage[3];
        leftWalkSprites[0] = loadAndScaleImage("res/Monster3/bombmonslleft.png");
        leftWalkSprites[1] = loadAndScaleImage("res/Monster3/bombmonsleft2.png");
        leftWalkSprites[2] = loadAndScaleImage("res/Monster3/bombmonsleft3.png");

        rightWalkSprites = new BufferedImage[3];
        rightWalkSprites[0] = loadAndScaleImage("res/Monster3/bombmonsright.png");
        rightWalkSprites[1] = loadAndScaleImage("res/Monster3/bombmonsright2.png");
        rightWalkSprites[2] = loadAndScaleImage("res/Monster3/bombmonsright3.png");

        // Load attack sprites
        leftAttackSprites = new BufferedImage[2];
        leftAttackSprites[0] = loadAndScaleImage("res/Monster3/bagosumabogleft.png");
        leftAttackSprites[1] = loadAndScaleImage("res/Monster3/sumabogna2.png");

        rightAttackSprites = new BufferedImage[2];
        rightAttackSprites[0] = loadAndScaleImage("res/Monster3/bagosumabog.png");
        rightAttackSprites[1] = loadAndScaleImage("res/Monster3/sumabogna.png");
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
            System.err.println("Failed to load Monster3 sprite: " + path);
            return null;
        }
    }

    /**
     * Update Monster3 AI, movement, and animation
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

        // Update dash cooldown
        if (dashCooldownCounter > 0) {
            dashCooldownCounter--;
        }

        if (dashing) {
            updateDash(collision);
            return;
        }

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
            // Try to dash if available and player is far enough
            if (distance > 200 && dashCooldownCounter == 0) {
                startDash(player);
            } else {
                // Chase player
                chasePlayer(player, collision);
            }
        } else {
            // Wander randomly
            wander(collision);
        }

        updateAnimation();
    }

    private void startDash(CharacterLoad player) {
        dashing = true;
        dashCounter = 0;
        dashCooldownCounter = dashCooldown;

        // Face player
        int dx = player.getX() - x;
        direction = dx > 0 ? "right" : "left";
    }

    private void updateDash(Collision collision) {
        dashCounter++;

        // Dash in the direction facing
        int dashMove = direction.equals("right") ? dashSpeed : -dashSpeed;
        int nextX = x + dashMove;

        // Check collision
        if (!collision.checkCollision(nextX + 50, y + 35, width - 100, height - 65)) {
            x = nextX;
        }

        // End dash
        if (dashCounter >= dashDuration) {
            dashing = false;
            dashCounter = 0;
        }
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
            if (!collision.checkCollision(nextX + 50, nextY + 35, width - 100, height - 65)) {
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
            if (!collision.checkCollision(nextX + 50, nextY + 35, width - 100, height - 65)) {
                x = nextX;
            } else {
                // Hit a wall, change direction
                direction = direction.equals("left") ? "right" : "left";
            }
        }

        updateAnimation();
    }

    private void updateAnimation() {
        if (!moving && !dashing) return;

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
        if (attackCounter % 12 == 0) {
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

        if (dashing) {
            // Draw dashing effect (could add motion blur here)
            if (direction.equals("left")) {
                currentSprite = leftWalkSprites[animationFrame];
            } else {
                currentSprite = rightWalkSprites[animationFrame];
            }

            // Dash visual effect (blur/trail)
            g.setColor(new Color(255, 100, 0, 80));
            g.fillRect(x, y, width, height);
        } else if (attacking) {
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

            // Warning indicator (orange glow for Monster3)
            if (attackPrepareCounter % 15 < 8) {
                g.setColor(new Color(255, 100, 0, 120));
                g.fillRect(x, y, width, height);
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
            g.drawImage(currentSprite, x, y, width, height, null);
        }

        // Debug: Draw hitbox
        // g.setColor(Color.ORANGE);
        // g.drawRect(x + 50, y + 35, width - 100, height - 65);
    }

    // Getters and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isAttacking() { return attacking; }
    public boolean isPreparingAttack() { return preparingAttack; }
    public boolean isDashing() { return dashing; }

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
     * Check if Monster3's attack hitbox overlaps with player
     */
    public boolean isAttackingPlayer(CharacterLoad player) {
        if (!attacking) return false;

        // Monster3 hitbox
        int monsterHitX = x + 50;
        int monsterHitY = y + 35;
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
