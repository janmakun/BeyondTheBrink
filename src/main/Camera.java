package main;

public class Camera {
    private int x;
    private int y;
    private int screenWidth;
    private int screenHeight;

    public Camera(int screenWidth, int screenHeight) {
        this.x = 0;
        this.y = 0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    // Make camera follow the character
    public void followCharacter(CharacterLoad character) {
        // Center the camera on the character
        // Camera position = character position - half of screen size
        x = character.getX() - (screenWidth / 2) + (character.getWidth() / 2);
        y = character.getY() - (screenHeight / 2) + (character.getHeight() / 2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}