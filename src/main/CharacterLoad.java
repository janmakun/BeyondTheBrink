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

    public CharacterLoad(int startX, int startY) {
        this.x = startX;
        this.y = startY;

        downSprites = new BufferedImage[2];
        try {
            downSprites[0] = ImageIO.read(new File("res/Player/Fronwalk1.png"));
            downSprites[1] = ImageIO.read(new File("res/Player/Frontwalk2.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading down walk sprites!");
        }

        rightSprites = new BufferedImage[2];
        try {
            rightSprites[0] = ImageIO.read(new File("res/Player/RightWalk1.png"));
            rightSprites[1] = ImageIO.read(new File("res/Player/RightWalk2.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading right walk sprites!");
        }

        upSprites = new BufferedImage[2];
        try {
            upSprites[0] = ImageIO.read(new File("res/Player/BackWalk1.png"));
            upSprites[1] = ImageIO.read(new File("res/Player/Backwalk2.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading up walk sprites!");
        }


        leftSprites = new BufferedImage[2];
        try {
            leftSprites[0] = ImageIO.read(new File("res/Player/LeftWalk1.png"));
            leftSprites[1] = ImageIO.read(new File("res/Player/LefWalk2.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading left walk sprites!");
        }

        try{
            upStaySprites = ImageIO.read(new File("res/Player/Back.png"));
            downStaySprites = ImageIO.read(new File("res/Player/Front.png"));
            leftStaySprites = ImageIO.read(new File("res/Player/LeftSide.png"));
            rightStaySprites = ImageIO.read(new File("res/Player/SideRight.png"));
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error loading stays sprites!");
        }

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
