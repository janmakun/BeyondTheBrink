package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import main.GamePanel;

public class Collision {
    private List<Rectangle> walls;
    private boolean showDebug = true;

    public Collision() {
        walls = new ArrayList<>();

        loadMap1Collisions();

    }

    public void loadMap1Collisions(){
        // TODO: Map out your walls by looking at your 5000x5000 map
        // You can click on the game to add walls and see coordinates in console

        // Example walls - replace these with your actual map walls

        walls.clear();
        walls.add(new Rectangle(30, -200, 40, 500));  // 3
        walls.add(new Rectangle(30, 280, 320, 40));  // 1
        walls.add(new Rectangle(300, -200, 40, 500));  // 2
        walls.add(new Rectangle(-40, -215, 80, 40));  // 4
        walls.add(new Rectangle(-290, -255, 270, 40));  // 5
        walls.add(new Rectangle(-300, -570, 40, 318)); // 6
        walls.add(new Rectangle(-300, -570, 520, 77));  // 7
        walls.add(new Rectangle(190, -570, 30, 241));  // 8
        walls.add(new Rectangle(190, -540, 160, 77));  // 9
        walls.add(new Rectangle(350, -560, 218, 77));  // 10
        walls.add(new Rectangle(538, -560, 30, 210));  // 11
        walls.add(new Rectangle(538, -430, 288, 77));  // 12
        walls.add(new Rectangle(800, -590, 30, 210));  // 13
        walls.add(new Rectangle(800, -590, 130, 80));  // 14
        walls.add(new Rectangle(900, -765, 30, 210));  // 15
        walls.add(new Rectangle(-420, -764, 1320, 35));  // 16
        walls.add(new Rectangle(-420, -1920, 30, 1195));  // 17
        walls.add(new Rectangle(1100, -1135, 30, 580)); // 18
        walls.add(new Rectangle(1100, -578, 388, 77)); // 19
        walls.add(new Rectangle(1457, -1515, 30, 940)); // 20
        walls.add(new Rectangle(1457, -1515, 578, 70)); // 21
        walls.add(new Rectangle(1457, -1267, 615, 70)); // 22
        walls.add(new Rectangle(1880, -1340 , 30, 70)); // 23
        walls.add(new Rectangle(300, -200, 300, 40));  // 24
        walls.add(new Rectangle(565, -250, 30, 80)); //25
        walls.add(new Rectangle(565, -250, 460, 30)); //26
        walls.add(new Rectangle(994, -250, 30, 150)); //27
        walls.add(new Rectangle(644, -170, 380, 70)); //28
        walls.add(new Rectangle(636, -170, 32, 250)); //29
        walls.add(new Rectangle(636, 31, 788, 30)); //30
        walls.add(new Rectangle(1395, -230, 30, 290)); /// 31
        walls.add(new Rectangle(1169, -230, 254, 80)); /// 32
        walls.add(new Rectangle(1169, -360, 30, 130)); /// 33
        walls.add(new Rectangle(1169, -360, 922, 30)); /// 34
        walls.add(new Rectangle(2061, -360, 30, 273)); // 35
        walls.add(new Rectangle(1540, -160, 551, 80)); // 36
        walls.add(new Rectangle(1540, -160, 30, 246)); //37
        walls.add(new Rectangle(1540, 52, 724, 30)); // 38
        walls.add(new Rectangle(2234, -920, 30, 1000)); // 39
        walls.add(new Rectangle(1817, -920, 450, 80)); // 40
        walls.add(new Rectangle(1817, -920, 30, 285)); // 41
        walls.add(new Rectangle(1817, -670, 195, 30)); // 41.5
        walls.add(new Rectangle(1980, -730, 32, 203)); // 42
        walls.add(new Rectangle(1687, -605, 325, 80)); // 43
        walls.add(new Rectangle(1687, -1050, 32, 445)); // 44
        walls.add(new Rectangle(1687, -1050, 543, 32)); // 45
        walls.add(new Rectangle(2198, -1970, 32, 920)); // 46
        walls.add(new Rectangle(1638, -1715, 396, 32)); // 47
        walls.add(new Rectangle(1641, -1815, 32, 100)); // 47A
        walls.add(new Rectangle(2002, -1715, 32, 197)); // 47.B
        walls.add(new Rectangle(1803, -1970, 400, 80)); // 48
        walls.add(new Rectangle(1793, -2530, 32, 580)); // 49
        walls.add(new Rectangle(1300, -2093, 500, 80)); // 50
        walls.add(new Rectangle(1103, -2530, 700, 80)); // 51
        walls.add(new Rectangle(1103, -2530, 32, 236)); // 52
        walls.add(new Rectangle(838, -2290, 295, 80)); // 53
        walls.add(new Rectangle(1071, -1815, 600, 32)); // 54
        walls.add(new Rectangle(838, -2290, 32, 775)); // 55
        walls.add(new Rectangle(1071, -1815, 32, 585)); // 56
        walls.add(new Rectangle(-185, -1255, 1290, 32)); // 57
        walls.add(new Rectangle(605, -1510, 265, 80)); // 58
        walls.add(new Rectangle(-420, -1920, 665, 80)); // 59
        walls.add(new Rectangle(-185, -1510, 525, 80)); // 60
        walls.add(new Rectangle(-185, -1255, 32, 265)); // 61
        walls.add(new Rectangle(-185, -985, 452, 80)); // 62
        walls.add(new Rectangle(235, -1135, 32, 150)); // 62.5
        walls.add(new Rectangle(235, -1135, 895, 80)); // 63
        walls.add(new Rectangle(-185, -1720, 32, 210)); // 64
        walls.add(new Rectangle(-185, -1720, 425, 32)); // 65
        walls.add(new Rectangle(210, -1920, 32, 200)); // 66
    }


    public void loadMap2Collisions(){
        walls.clear();
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
        int hitboxX = charX + 17;
        int hitboxY = charY + 10;
        int hitboxWidth = charWidth - 32;
        int hitboxHeight = charHeight - 20;

        // Draw hitbox outline
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(hitboxX, hitboxY, hitboxWidth, hitboxHeight);

        // Draw corner points
        g2.setColor(Color.CYAN);
        int pointSize = 6;
        g2.fillOval(hitboxX - pointSize/2, hitboxY - pointSize/2, pointSize, pointSize); // Top-left
        g2.fillOval(hitboxX + hitboxWidth - pointSize/2, hitboxY - pointSize/2, pointSize, pointSize); // Top-right
        g2.fillOval(hitboxX - pointSize/2, hitboxY + hitboxHeight - pointSize/2, pointSize, pointSize); // Bottom-left
        g2.fillOval(hitboxX + hitboxWidth - pointSize/2, hitboxY + hitboxHeight - pointSize/2, pointSize, pointSize); // Bottom-right
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