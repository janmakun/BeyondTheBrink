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
        walls.add(new Rectangle(538, -430, 292, 77));  // 12
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
        walls.add(new Rectangle(1793, -2530, 32, 600)); // 49
        walls.add(new Rectangle(1300, -2093, 500, 80)); // 50
        walls.add(new Rectangle(1103, -2530, 700, 80)); // 51
        walls.add(new Rectangle(1103, -2530, 32, 236)); // 52
        walls.add(new Rectangle(838, -2290, 295, 80)); // 53
        walls.add(new Rectangle(1071, -1815, 600, 32)); // 54
        walls.add(new Rectangle(838, -2290, 32, 775)); // 55
        walls.add(new Rectangle(1071, -1815, 32, 585)); // 56
        walls.add(new Rectangle(-185, -1255, 1290, 32)); // 57
        walls.add(new Rectangle(607, -1510, 263, 80)); // 58
        walls.add(new Rectangle(-420, -1920, 665, 80)); // 59
        walls.add(new Rectangle(-185, -1510, 530, 80)); // 60
        walls.add(new Rectangle(-185, -1255, 32, 265)); // 61
        walls.add(new Rectangle(-185, -985, 452, 80)); // 62
        walls.add(new Rectangle(235, -1135, 32, 150)); // 62.5
        walls.add(new Rectangle(235, -1135, 895, 80)); // 63
        walls.add(new Rectangle(-185, -1720, 32, 210)); // 64
        walls.add(new Rectangle(-185, -1720, 425, 32)); // 65
        walls.add(new Rectangle(210, -1920, 32, 200)); // 66
        walls.add(new Rectangle(300, -200, 300, 40));  // Upper Right
        walls.add(new Rectangle(670, -4275, 1550, 80));  // A
        walls.add(new Rectangle(2190, -4275, 30, 990));  // B
        walls.add(new Rectangle(670, -3318, 1550, 40));  // C
        walls.add(new Rectangle(670, -3570, 38, 280));  // D
        walls.add(new Rectangle(670, -4197, 38, 400)); // E
        walls.add(new Rectangle(-1243, -3795, 1949, 70));//F
        walls.add(new Rectangle(-1835, -3570, 2542, 30)); //G
        walls.add(new Rectangle(-1243, -3860, 600, 70));//H
        walls.add(new Rectangle(-710, -4180 , 40, 330)); // I
        walls.add(new Rectangle(-1440, -4180 , 750, 80)); // J
        walls.add(new Rectangle(-1440, -4180 , 35, 380)); // K
        walls.add(new Rectangle(-2000 , -3795 , 594, 80)); // L
        walls.add(new Rectangle(-2000 , -3795 , 30, 3760)); // M
        walls.add(new Rectangle(-1835 , -3570 , 30, 2530)); // O
        walls.add(new Rectangle(-1835 , -1170 , 198, 170)); // N
        walls.add(new Rectangle(-1835 , -880 , 198, 650)); // Q
        walls.add(new Rectangle(-1835 , -300 , 790, 70)); // R
        walls.add(new Rectangle(-1675 , -530 , 455, 70)); // T
        walls.add(new Rectangle(-2035 , -80 , 1580, 30)); //S
        walls.add(new Rectangle(-1650 , -2880 , 180, 1755)); // P
        walls.add(new Rectangle(-1178 , -2940 , 132, 1077)); // w
        walls.add(new Rectangle(-1502 , -2840 , 410, 45)); // V
        walls.add(new Rectangle(-1340 , -1732 , 294, 1430)); // K11
        walls.add(new Rectangle(-1340 , -2661 , 32, 1430)); // U
        walls.add(new Rectangle(-1178 , -2940 , 1932, 80)); // G7
        walls.add(new Rectangle(-842 , -2684 , 30, 2650)); //X
        walls.add(new Rectangle(-842 , -2684 , 460, 320)); //Y
        walls.add(new Rectangle(-219 , -2684 , 562, 320)); //H8, E5, F6
        walls.add(new Rectangle(-682 , -2384 , 76, 430)); //Z
        walls.add(new Rectangle(77 , -2384 , 76, 420)); //D4
        walls.add(new Rectangle(-347 , -2149 , 123, 220)); //B2
        walls.add(new Rectangle(-347 , -2056 , 440, 100)); // C3
        walls.add(new Rectangle(-682 , -1956 , 376, 40)); //A1
        walls.add(new Rectangle(240 , -2684 , 105, 1175)); //J10
        walls.add(new Rectangle(607 , -2940 , 105, 1430)); //I9
    }


    public void loadMap2Collisions(){

        walls.clear();

        walls.add(new Rectangle(-777, -970, 40,40 )); //t1
        walls.add(new Rectangle(-807, -900, 40,40 )); //t2
        walls.add(new Rectangle(-847, -972, 45,60 )); //s1
        walls.add(new Rectangle(-942, -899, 130,150 )); //r1
        walls.add(new Rectangle(-1653, -773, 870,150 )); //h1
        walls.add(new Rectangle(-1336, -822, 555,100 )); //h2
        walls.add(new Rectangle(-1036, -881, 85,100 )); //h3
        walls.add(new Rectangle(-1117, -874, 40,30 )); //t3
        walls.add(new Rectangle(-1204, -873, 65,100 )); //h4
        walls.add(new Rectangle(-1311, -886, 40,35 )); //t4
        walls.add(new Rectangle(-1382, -905, 45,60 )); //s2
        walls.add(new Rectangle(-1431, -787, 40,35 )); //t5
        walls.add(new Rectangle(-1556, -785, 40,35 )); //t6
        walls.add(new Rectangle(-1614, -786, 40,35 )); //t7
        walls.add(new Rectangle(-1816, -1852, 220,1200)); //h5
        walls.add(new Rectangle(-2283, -3320, 500,2500)); //h6
        walls.add(new Rectangle(-1574, -1293, 220,290 )); //r2
        walls.add(new Rectangle(-1310, -1011, 40,35 )); //t8
        walls.add(new Rectangle(-1147, -1048, 45,60 )); //s3
        walls.add(new Rectangle(-1122, -1231, 150,250 )); //r3
        walls.add(new Rectangle(-881, -1110, 40,35 )); //t9
        walls.add(new Rectangle(-751, -1107, 40,35 )); //t10
        walls.add(new Rectangle(-673, -1142, 45,64 )); //s4
        walls.add(new Rectangle(-1345, -1347, 45,64 )); //s5
        walls.add(new Rectangle(-1330, -1381, 200,150 )); //r4
        walls.add(new Rectangle(-1086, -1308, 45,64 )); //s6
        walls.add(new Rectangle(-1018, -1262, 40,35 )); //t11
        walls.add(new Rectangle(-1055, -1321, 40,35 )); //t12
        walls.add(new Rectangle(-1022, -1388, 40,35 )); //t13
        walls.add(new Rectangle(-870, -1385, 200,145 )); //r5
        walls.add(new Rectangle(-669, -1360, 100,220 )); //h7
        walls.add(new Rectangle(-565, -1327, 2500,800 )); //h8
        walls.add(new Rectangle(-1596, -1555, 210,145 )); //r6
        walls.add(new Rectangle(-1656, -1854, 130,200 )); //r7
        walls.add(new Rectangle(-1512, -1850, 126,200 )); //r8
        walls.add(new Rectangle(-1532, -1896, 40,35 )); //t14
        walls.add(new Rectangle(-1723, -2001, 40,35 )); //t15
        walls.add(new Rectangle(-1784, -2183, 100,155 )); //r9
        walls.add(new Rectangle(-1259, -1780, 210,278 )); //r10
        walls.add(new Rectangle(-987, -1663, 200,140 )); //r11
        walls.add(new Rectangle(-609, -1696, 65,200 )); //h9
        walls.add(new Rectangle(-430, -1544, 40,35 )); //t16
        walls.add(new Rectangle(-320, -1768, 210,278 )); //r12
        walls.add(new Rectangle(-76, -1655, 200,140 )); //r13
        walls.add(new Rectangle(161, -1689, 125,180 )); //r14
        walls.add(new Rectangle(318, -1686, 125,180 )); //r15
        walls.add(new Rectangle(473, -1690, 125,180 )); //r16
        walls.add(new Rectangle(-194, -1377, 1050,40 )); //h10
        walls.add(new Rectangle(722, -1526, 45,64 )); //s7
        walls.add(new Rectangle(716, -1698, 45,64 )); //s8
        walls.add(new Rectangle(403, -2001, 200,140 )); //r18
        walls.add(new Rectangle(725, -1900, 125,180 )); //r17
        walls.add(new Rectangle(760, -2099, 1000,1800 )); //h11
        walls.add(new Rectangle(387, -1861, 45,64 )); //s9
        walls.add(new Rectangle(557, -2089, 45,64 )); //s10
        walls.add(new Rectangle(712, -2095, 40,35 )); //t17
        walls.add(new Rectangle(783, -2148, 40,35 )); //t18
        walls.add(new Rectangle(715, -2294, 40,35 )); //t19
        walls.add(new Rectangle(790, -2299, 40,35 )); //t20
        walls.add(new Rectangle(721, -2218, 55,64 )); //s11
        walls.add(new Rectangle(1054, -2518, 800,447 )); //h13
        walls.add(new Rectangle(786, -2564, 45,64 )); //s12
        walls.add(new Rectangle(-118, -3123, 680,1050 )); //h15
        walls.add(new Rectangle(-1226, -2051, 1600,340 )); //h14
        walls.add(new Rectangle(562, -2950, 220,465 )); //h16
        walls.add(new Rectangle(999, -4312, 1000,1770 )); //h17
        walls.add(new Rectangle(931, -3113, 45,64 )); //s13
        walls.add(new Rectangle(946, -3193, 45,64 )); //s14
        walls.add(new Rectangle(640, -3230, 45,64 )); //s15
        walls.add(new Rectangle(632, -3348, 45,64 )); //s16
        walls.add(new Rectangle(658, -3419, 45,64 )); //s17
        walls.add(new Rectangle(698, -3566, 340,360 )); //h18
        walls.add(new Rectangle(-176, -4366, 1300,820 )); //h19
        walls.add(new Rectangle(-249, -3578, 40,35 )); //t21
        walls.add(new Rectangle(-311, -3636, 45,64 )); //s19
        walls.add(new Rectangle(-1317, -3391, 1800,380 )); //h20
        walls.add(new Rectangle(107, -3406, 45,64 )); //s18
        walls.add(new Rectangle(-549, -3661, 45,64 )); //s20
        walls.add(new Rectangle(-884, -3593, 45,80 )); //s21
        walls.add(new Rectangle(-794, -3520, 300,200 )); //h21
        walls.add(new Rectangle(-778, -4369, 220,270 )); //h23
        walls.add(new Rectangle(-1196, -4363, 1000,230 )); //h22
        walls.add(new Rectangle(-2090, -4216, 945,650 )); //h24
        walls.add(new Rectangle(-1979, -3698, 450,540  )); //h25
        walls.add(new Rectangle(-1586, -3152, 45,64 )); //s22
        walls.add(new Rectangle(-1380, -3079, 45,80 )); //s23
        walls.add(new Rectangle(-1625, -2813, 1300,447 )); //h26
        walls.add(new Rectangle(-1624, -2389, 68,60 )); //h27
        walls.add(new Rectangle(-1338, -2334, 200,140 )); //r19

        walls.add(new Rectangle(-1480, -2335, 71,245 )); //h28
        walls.add(new Rectangle(-1286, -2089, 40,35 )); //t22
        walls.add(new Rectangle(-1283, -1929, 40,35 )); //t23
        walls.add(new Rectangle(-1284, -1846, 40,35 )); //t24
        walls.add(new Rectangle(-1119, -2415, 71,200 )); //h29
        walls.add(new Rectangle(-1049, -2308, 40,60 )); //s26
        walls.add(new Rectangle(-974, -2385, 125,180 )); //r20
        walls.add(new Rectangle(-819, -2381, 125,180 )); //r21
        walls.add(new Rectangle(-662, -2380, 125,180 )); //r22
        walls.add(new Rectangle(-501, -2446, 210,265 )); //r23
        walls.add(new Rectangle(-772, -2911, 40,60 )); //s24
        walls.add(new Rectangle(-271, -2941, 40,60 )); //s25

        walls.add(new Rectangle(-1400, -2280, 40,75 )); //extra1
        walls.add(new Rectangle(-1045, -1727, 40,80 )); //extrta2


        System.out.println("Map 2 collisions loaded: " + walls.size() + " walls");
    }

//         Add a wall using x, y, width, height
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
        int hitboxX = charX + 50;
        int hitboxY = charY + 35;
        int hitboxWidth = charWidth - 100;
        int hitboxHeight = charHeight - 65;

        // Draw hitbox outline
//        g2.setColor(Color.GREEN);
//        g2.setStroke(new BasicStroke(2));
//        g2.drawRect(hitboxX, hitboxY, hitboxWidth, hitboxHeight);

        // Draw corner points
//        g2.setColor(Color.CYAN);
//        int pointSize = 6;
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