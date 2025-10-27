package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CharacterLoad {

    private int frameIndex = 0;
    private int frameDelay = 10;
    private int frameCount = 0;

    private BufferedImage[] upSprites;
    private BufferedImage[] rightSprites;
    private BufferedImage[] leftSprites;
    private BufferedImage[] downSprites;
    private BufferedImage upStaySprites, downStaySprites, leftStaySprites, rightStaySprites;

    private int x;
    private int y;
    private int width = 75;
    private int height = 75;

    private String currentDirection = "";

    public CharacterLoad(int startX, int startY, ResourceLoader resourceLoader) {
        this.x = startX;
        this.y = startY;

        downSprites = resourceLoader.getSpriteArray("down");
        rightSprites = resourceLoader.getSpriteArray("right");
        upSprites = resourceLoader.getSpriteArray("up");
        leftSprites = resourceLoader.getSpriteArray("left");

        upStaySprites = resourceLoader.getImage("upStay");
        downStaySprites = resourceLoader.getImage("downStay");
        leftStaySprites = resourceLoader.getImage("leftStay");
        rightStaySprites = resourceLoader.getImage("rightStay");

        System.out.println("Character initialized with preloaded resources!");

    }


    public void update(boolean moving, String direction) {
        if (moving) {
            currentDirection = direction;
            frameCount++;
            if (frameCount >= frameDelay) {
                switch (direction) {
                    case "down":
                        frameIndex = (frameIndex + 1) % downSprites.length;
                        break;
                    case "up":
                        frameIndex = (frameIndex + 1) % upSprites.length;
                        break;
                    case "right":
                        frameIndex = (frameIndex + 1) % rightSprites.length;
                        break;
                    case "left":
                        frameIndex = (frameIndex + 1) % leftSprites.length;
                        break;
                }
                frameCount = 0;
            }
        } else {
            frameIndex = 0;
        }
    }


    public void draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage currentFrame = downStaySprites;

        switch (currentDirection) {
            case "down":
                currentFrame = downSprites[frameIndex];
                break;
            case "up":
                currentFrame = upSprites[frameIndex];
                break;
            case "right":
                currentFrame = rightSprites[frameIndex];
                break;
            case "left":
                currentFrame = leftSprites[frameIndex];
                break;
        }

        if (currentFrame != null) {
            g.drawImage(currentFrame, x - cameraX, y - cameraY, width, height, null);
        }
    }


    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }


    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}
