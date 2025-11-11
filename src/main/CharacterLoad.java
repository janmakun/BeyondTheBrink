package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CharacterLoad {

    private int frameIndex = 0;
    private int frameDelay = 10;
    private int frameDelayBasicAtt = 10;
    private int frameCount = 0;

    private BufferedImage[] upSprites;
    private BufferedImage[] rightSprites;
    private BufferedImage[] leftSprites;
    private BufferedImage[] downSprites;
    private BufferedImage upStaySprites, downStaySprites, leftStaySprites, rightStaySprites;

    // Blue sword walking sprites
    private BufferedImage[] blueSwordWalkDown;
    private BufferedImage[] blueSwordWalkUp;
    private BufferedImage[] blueSwordWalkLeft;
    private BufferedImage[] blueSwordWalkRight;

    private BufferedImage[] redSwordWalkDown;
    private BufferedImage[] redSwordWalkUp;
    private BufferedImage[] redSwordWalkLeft;
    private BufferedImage[] redSwordWalkRight;

    private SwordType currentSwordType = SwordType.NONE;

    private BufferedImage[] attackUpSprites;
    private BufferedImage[] attackDownSprites;
    private BufferedImage[] attackLeftSprites;
    private BufferedImage[] attackRightSprites;

    private BufferedImage[] redAttackUpSprites;
    private BufferedImage[] redAttackDownSprites;
    private BufferedImage[] redAttackLeftSprites;
    private BufferedImage[] redAttackRightSprites;


    private int x;
    private int y;
    private int width;
    private int height;

    private int attackWidth;
    private int attackHeight;
    private int drawSkill;
    private int swordWalk;

    private String currentDirection = "";
    private boolean isAttacking = false;
    private String attackDirection = "";
    private int attackFrameIndex = 0;
    private int attackFrameCount = 0;
    private String lastDirection = "down";

    private boolean showingSword = false; // For sword visibility toggle

    public CharacterLoad(int startX, int startY, ResourceLoader resourceLoader) {
        this.x = startX;
        this.y = startY;

        // Get size from ResourceLoader
        this.width = resourceLoader.getSpriteSize();
        this.height = resourceLoader.getSpriteSize();
        this.attackWidth = resourceLoader.getAttackSpriteSize();
        this.attackHeight = resourceLoader.getAttackSpriteSize();
        this.drawSkill = resourceLoader.getSkillSpriteSize();
        this.swordWalk = resourceLoader.getSwordWalkSize();

        // Normal walking sprites
        downSprites = resourceLoader.getSpriteArray("down");
        rightSprites = resourceLoader.getSpriteArray("right");
        upSprites = resourceLoader.getSpriteArray("up");
        leftSprites = resourceLoader.getSpriteArray("left");

        upStaySprites = resourceLoader.getImage("upStay");
        downStaySprites = resourceLoader.getImage("downStay");
        leftStaySprites = resourceLoader.getImage("leftStay");
        rightStaySprites = resourceLoader.getImage("rightStay");

        // Blue sword walking sprites
        blueSwordWalkDown = resourceLoader.getSpriteArray("blueSwordWalk_down");
        blueSwordWalkUp = resourceLoader.getSpriteArray("blueSwordWalk_up");
        blueSwordWalkLeft = resourceLoader.getSpriteArray("blueSwordWalk_left");
        blueSwordWalkRight = resourceLoader.getSpriteArray("blueSwordWalk_right");

        redSwordWalkDown = resourceLoader.getSpriteArray("redSwordWalk_down");
        redSwordWalkUp = resourceLoader.getSpriteArray("redSwordWalk_up");
        redSwordWalkLeft = resourceLoader.getSpriteArray("redSwordWalk_left");
        redSwordWalkRight = resourceLoader.getSpriteArray("redSwordWalk_right");

        // Attack sprites
        attackUpSprites = resourceLoader.getSpriteArray("attackUp");
        attackDownSprites = resourceLoader.getSpriteArray("attackDown");
        attackLeftSprites = resourceLoader.getSpriteArray("attackLeft");
        attackRightSprites = resourceLoader.getSpriteArray("attackRight");

        redAttackUpSprites = resourceLoader.getSpriteArray("redAttackUp");
        redAttackDownSprites = resourceLoader.getSpriteArray("redAttackDown");
        redAttackLeftSprites = resourceLoader.getSpriteArray("redAttackLeft");
        redAttackRightSprites = resourceLoader.getSpriteArray("redAttackRight");

        System.out.println("Character initialized!");
        System.out.println("  Walking sprite size: " + width + "x" + height);
        System.out.println("  Attack sprite size: " + attackWidth + "x" + attackHeight);
        System.out.println("  Blue sword walking sprites loaded: " + (blueSwordWalkDown != null));
    }

    public void setSwordType(SwordType swordType) {
        this.currentSwordType = swordType;
        System.out.println("Character sword type set to: " + swordType);
    }

    public void setSize(int newWidth, int newHeight) {
        this.width = newWidth;
        this.height = newHeight;
    }

    public void setAttackSize(int newWidth, int newHeight) {
        this.attackWidth = newWidth;
        this.attackHeight = newHeight;
    }

    public void setSwordVisibility(boolean visible) {
        this.showingSword = visible;
    }

    public boolean isSwordVisible() {
        return showingSword;
    }

    public void startAttack(String direction) {
        if (!isAttacking) {
            isAttacking = true;
            attackDirection = direction;
            attackFrameIndex = 0;
            attackFrameCount = 0;
        }
    }

    public void update(boolean moving, String direction) {
        if (isAttacking) {
            attackFrameCount++;
            if (attackFrameCount >= frameDelayBasicAtt) {
                attackFrameIndex++;
                if (attackFrameIndex >= 4) {
                    isAttacking = false;
                    attackDirection = "";
                    attackFrameIndex = 0;
                }
                attackFrameCount = 0;
            }
            return;
        }

        if (moving) {
            currentDirection = direction;
            lastDirection = direction;
            frameCount++;
            if (frameCount >= frameDelay) {
                // Choose the appropriate sprite array based on sword visibility
                BufferedImage[] currentSprites = getCurrentWalkingSprites(direction);

                if (currentSprites != null) {
                    frameIndex = (frameIndex + 1) % currentSprites.length;
                }
                frameCount = 0;
            }
        } else {
            frameIndex = 0;
        }
    }
    public int pubDrawWidth;
    public int pubDrawHeight;
    private BufferedImage[] getCurrentWalkingSprites(String direction) {
        if (showingSword) {
            pubDrawWidth = swordWalk;
            pubDrawHeight = swordWalk;

            // Choose sprite set based on current sword type
            if (currentSwordType == SwordType.BLUE_SWORD) {
                switch (direction) {
                    case "down": return blueSwordWalkDown;
                    case "up": return blueSwordWalkUp;
                    case "right": return blueSwordWalkRight;
                    case "left": return blueSwordWalkLeft;
                }
            } else if (currentSwordType == SwordType.RED_SWORD) {
                switch (direction) {
                    case "down": return redSwordWalkDown;
                    case "up": return redSwordWalkUp;
                    case "right": return redSwordWalkRight;
                    case "left": return redSwordWalkLeft;
                }
            }
        } else {
            pubDrawWidth = width;
            pubDrawHeight = height;
            // Use normal walking sprites
            switch (direction) {
                case "down": return downSprites;
                case "up": return upSprites;
                case "right": return rightSprites;
                case "left": return leftSprites;
            }
        }
        return downSprites; // fallback
    }

    public void draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage currentFrame = downStaySprites;
        int drawWidth = width;
        int drawHeight = height;

        if (isAttacking) {
            drawWidth = attackWidth;
            drawHeight = attackHeight;

            BufferedImage[] currentAttackSprites = null;

            // Choose attack sprites based on sword type
            if (currentSwordType == SwordType.RED_SWORD) {
                switch (attackDirection) {
                    case "up": currentAttackSprites = redAttackUpSprites; break;
                    case "down": currentAttackSprites = redAttackDownSprites; break;
                    case "left": currentAttackSprites = redAttackLeftSprites; break;
                    case "right": currentAttackSprites = redAttackRightSprites; break;
                    default:
                        if(currentDirection.equals("up")) currentAttackSprites = redAttackUpSprites;
                        else if (currentDirection.equals("down")) currentAttackSprites = redAttackDownSprites;
                        else if (currentDirection.equals("left")) currentAttackSprites = redAttackLeftSprites;
                        else if(currentDirection.equals("right")) currentAttackSprites = redAttackRightSprites;
                        break;
                }
            } else {
                // Blue sword or default
                switch (attackDirection) {
                    case "up": currentAttackSprites = attackUpSprites; break;
                    case "down": currentAttackSprites = attackDownSprites; break;
                    case "left": currentAttackSprites = attackLeftSprites; break;
                    case "right": currentAttackSprites = attackRightSprites; break;
                    default:
                        if(currentDirection.equals("up")) currentAttackSprites = attackUpSprites;
                        else if (currentDirection.equals("down")) currentAttackSprites = attackDownSprites;
                        else if (currentDirection.equals("left")) currentAttackSprites = attackLeftSprites;
                        else if(currentDirection.equals("right")) currentAttackSprites = attackRightSprites;
                        break;
                }
            }

            currentFrame = (currentAttackSprites != null && currentAttackSprites.length >= 4)
                    ? currentAttackSprites[attackFrameIndex] : null;
        } else {
            // Get current walking sprites based on sword visibility
            BufferedImage[] currentSprites = getCurrentWalkingSprites(currentDirection.isEmpty() ? lastDirection : currentDirection);
            drawWidth = pubDrawWidth;
            drawHeight = pubDrawHeight;
            if (!currentDirection.isEmpty() && currentSprites != null) {
                // Moving - use animated frames
                currentFrame = currentSprites[frameIndex];
            } else {
                // Standing still - use appropriate stay sprite
                // When showing sword and standing, use first frame of sword walk animation
                if (showingSword && currentSprites != null && currentSprites.length > 0) {
                    currentFrame = currentSprites[0];
                } else {
                    // Use normal stay sprites
                    if (lastDirection.equals("up")) {
                        currentFrame = upStaySprites;
                    } else if (lastDirection.equals("left")) {
                        currentFrame = leftStaySprites;
                    } else if (lastDirection.equals("right")) {
                        currentFrame = rightStaySprites;
                    } else {
                        currentFrame = downStaySprites;
                    }
                }
            }
        }

        if (currentFrame != null) {
            if(isAttacking) {
                g.drawImage(currentFrame, x - cameraX, y - cameraY, drawWidth, drawHeight, null);
            }
            else{
                if(showingSword) {
                    if(currentDirection.equals("down")) {
                        g.drawImage(currentFrame, x - cameraX + 25, y - cameraY + 45, drawWidth, drawHeight, null);
                    }
                    else if(currentDirection.equals("up")) {
                        if(currentSwordType == SwordType.RED_SWORD) {
                            g.drawImage(currentFrame, x - cameraX + 36, y - cameraY + 43, drawWidth, drawHeight, null); //karakal pelitan hays
                        }
                        else {
                            g.drawImage(currentFrame, x - cameraX + 28, y - cameraY + 35, drawWidth, drawHeight, null);
                        }
                    }
                    else if(currentDirection.equals("left")) {
                        g.drawImage(currentFrame, x - cameraX + 32, y - cameraY + 42, drawWidth, drawHeight, null);
                    }
                    else if(currentDirection.equals("right")) {
                        g.drawImage(currentFrame, x - cameraX + 40, y - cameraY + 43, drawWidth, drawHeight, null);
                    }
                }
                else {
                    g.drawImage(currentFrame, x - cameraX, y - cameraY, drawWidth, drawHeight, null);
                }
            }
        }
    }

    public void move(int dx, int dy) {
        if (!isAttacking) {
            x += dx;
            y += dy;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public boolean isAttacking() { return isAttacking; }
    public String setPosition() { return ""; }
    public String getLastDirection() {
        return lastDirection.isEmpty() ? "down" : lastDirection;
    }
}