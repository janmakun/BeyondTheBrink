package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Collision {
    private List<Rectangle> walls;
    private boolean showDebug = true;

    public Collision() {
        walls = new ArrayList<>();

        // TODO: Map out your walls by looking at your 5000x5000 map
        // You can click on the game to add walls and see coordinates in console

        // Example walls - replace these with your actual map walls
//        walls.add(new Rectangle(100, 100, 400, 10));  // Horizontal wall
        walls.add(new Rectangle(100, 100, 10, 300));  // Vertical wall
        walls.add(new Rectangle(100, 390, 400, 10));  // Bottom wall
        walls.add(new Rectangle(490, 100, 10, 300));  // Right wall

        // Add more walls here based on your map structure
    }

    // Add a wall using x, y, width, height
    public void addWall(int x, int y, int width, int height) {
        walls.add(new Rectangle(x, y, width, height));
        System.out.println("Wall added: x=" + x + ", y=" + y + ", w=" + width + ", h=" + height);
    }

    // Add a wall using two corner points (like your makeRectangle method)
    public void addWallFromCorners(int x1, int y1, int x2, int y2) {
        int left = Math.min(x1, x2);
        int top = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        // Ensure minimum size of 1 pixel
        if (width == 0) width = 1;
        if (height == 0) height = 1;

        walls.add(new Rectangle(left, top, width, height));
        System.out.println("Wall added: (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")");
    }

    // Check if character collides with any wall
    public boolean checkCollision(int charX, int charY, int charWidth, int charHeight) {
        // Create character hitbox with padding (10 pixels from each edge)
        Rectangle charRect = new Rectangle(charX + 10, charY + 10, charWidth - 20, charHeight - 20);

        // Check intersection with all walls
        for (Rectangle wall : walls) {
            if (charRect.intersects(wall)) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    // Get all walls (for drawing)
    public List<Rectangle> getWalls() {
        return walls;
    }

    // Remove all walls
    public void clearWalls() {
        walls.clear();
        System.out.println("All walls cleared");
    }

    // Remove the last added wall (undo feature)
    public void removeLastWall() {
        if (!walls.isEmpty()) {
            walls.remove(walls.size() - 1);
            System.out.println("Last wall removed. Total walls: " + walls.size());
        }
    }

    public void setShowDebug(boolean show) {
        this.showDebug = show;
    }

    public boolean isShowDebug() {
        return showDebug;
    }

    // Draw collision walls (call this with translated graphics)
    public void drawWalls(Graphics2D g2) {
        if (!showDebug) return;

        // Draw filled walls (semi-transparent red)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g2.setColor(Color.RED);
        for (Rectangle wall : walls) {
            g2.fillRect(wall.x, wall.y, wall.width, wall.height);
        }

//         Draw wall outlines (solid yellow)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(2));
        for (Rectangle wall : walls) {
            g2.drawRect(wall.x, wall.y, wall.width, wall.height);
        }
    }

    // Draw character hitbox (call this with translated graphics)
    public void drawCharacterHitbox(Graphics2D g2, int charX, int charY, int charWidth, int charHeight) {
        if (!showDebug) return;

        // Calculate hitbox with padding
        int hitboxX = charX + 10;
        int hitboxY = charY + 10;
        int hitboxWidth = charWidth - 20;
        int hitboxHeight = charHeight - 20;

        // Draw hitbox outline
//        g2.setColor(Color.GREEN);
//        g2.setStroke(new BasicStroke(2));
//        g2.drawRect(hitboxX, hitboxY, hitboxWidth, hitboxHeight);

        // Draw corner points
//        g2.setColor(Color.CYAN);
        int pointSize = 6;
//        g2.fillOval(hitboxX - pointSize/2, hitboxY - pointSize/2, pointSize, pointSize); // Top-left
//        g2.fillOval(hitboxX + hitboxWidth - pointSize/2, hitboxY - pointSize/2, pointSize, pointSize); // Top-right
//        g2.fillOval(hitboxX - pointSize/2, hitboxY + hitboxHeight - pointSize/2, pointSize, pointSize); // Bottom-left
//        g2.fillOval(hitboxX + hitboxWidth - pointSize/2, hitboxY + hitboxHeight - pointSize/2, pointSize, pointSize); // Bottom-right
    }

    // Print all wall coordinates (useful for copying to code)
    public void printAllWalls() {
        System.out.println("\n=== Current Walls (" + walls.size() + " total) ===");
        for (int i = 0; i < walls.size(); i++) {
            Rectangle w = walls.get(i);
            System.out.println("walls.add(new Rectangle(" + w.x + ", " + w.y + ", " + w.width + ", " + w.height + "));");
        }
        System.out.println("========================\n");
    }
}