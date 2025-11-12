package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {

    private final int WIDTH = 960;
    private final int HEIGHT = 720;
    private PixelPosition pixelPosition;

    private Map1 map1;
    private Map2 map2;
    public int currentMap = 1;

    private KeyHandler keyHandler;
    private Thread gameThread;
    private Camera camera;
    private Collision collision;
    public CharacterLoad character;
    private SkillManager skillManager;
    private ResourceLoader resourceLoader;
    private MonsterManager monsterManager;

    // NPC and Chest system
    private NPC blueRogueNPC;
    private Chest swordChest;
    private SwordSelectionMenu swordSelectionMenu;
    private SwordType currentSword = SwordType.NONE;
    private boolean swordVisible = false;
    private boolean hasInteractedWithNPC = false;

    // Warning message system
    private String warningMessage = "";
    private int warningTimer = 0;
    private static final int WARNING_DURATION = 120; // 2 seconds at 60 FPS

    private GameState gameState = GameState.PLAYING;
    private PauseMenu pauseMenu;
    private Point mousePosition = new Point(0, 0);
    private boolean showCoordinates = true; // Debug helper

    public GamePanel(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        keyHandler = new KeyHandler(this);
        camera = new Camera(WIDTH, HEIGHT);

        character = new CharacterLoad(100, 100, resourceLoader);
        skillManager = new SkillManager(character, resourceLoader);
        monsterManager = new MonsterManager(resourceLoader.getSpriteSize());
        pauseMenu = new PauseMenu(WIDTH, HEIGHT, resourceLoader);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        map1 = new Map1(resourceLoader);
        map2 = new Map2(resourceLoader);

        collision = new Collision();
        monsterManager.loadMap1Monsters();

        // Initialize NPC and Chest with SAME coordinate system as monsters
        blueRogueNPC = new NPC(820, -552, resourceLoader.getSpriteArray("npcBlueRogue"),
                "Greetings, traveler! Open the chest ahead to claim your legendary sword. Choose wisely, for your choice will shape your destiny!");

        // Place Chest
        swordChest = new Chest(640, -122, resourceLoader.getSpriteArray("chest"));

        System.out.println("=== INITIALIZATION ===");
        System.out.println("Character starts at: (100, 100)");
        System.out.println("NPC placed at: (820, -552)");
        System.out.println("Chest placed at: (650, -122)");
        System.out.println("Press 'C' to toggle coordinate display");
        System.out.println("Press 'F' to interact when near NPC or Chest");

        // Initialize sword selection menu
        swordSelectionMenu = new SwordSelectionMenu(
                WIDTH, HEIGHT,
                resourceLoader.getImage("swordSelectionBg"),
                resourceLoader.getImage("blueSwordIcon"),
                resourceLoader.getImage("redSwordIcon"),
                resourceLoader.getImage("blueButton"),
                resourceLoader.getImage("redButton"),
                529, 606, 272, 102,  // Blue button: x, y, width, height
                191, 606, 272, 102   // Red button: x, y, width, height
        );

        gameThread = new Thread(this);
        gameThread.start();

        pixelPosition = new PixelPosition() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                handleMouseClick(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mousePosition.setLocation(e.getX(), e.getY());
            }
        };
        this.addMouseListener(pixelPosition);
        this.addMouseMotionListener(pixelPosition);
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        if (gameState == GameState.SWORD_SELECTION) {
            if (swordSelectionMenu.isBlueButtonClicked(mouseX, mouseY)) {
                selectSword(SwordType.BLUE_SWORD);
            } else if (swordSelectionMenu.isRedButtonClicked(mouseX, mouseY)) {
                selectSword(SwordType.RED_SWORD);
            }
        } else if (gameState == GameState.PLAYING) {
            if (pauseMenu.isPauseIconClicked(mouseX, mouseY)) {
                pauseGame();
            }
        } else if (gameState == GameState.PAUSED) {
            if (pauseMenu.isContinueButtonClicked(mouseX, mouseY)) {
                resumeGame();
            } else if (pauseMenu.isExitButtonClicked(mouseX, mouseY)) {
                System.exit(0);
            }
        }
    }

    public void handleInteraction() {
        // Check NPC interaction first - ONLY shows dialogue
        if (blueRogueNPC != null && blueRogueNPC.isPlayerNearby(character)) {
            blueRogueNPC.interact();
            hasInteractedWithNPC = true;
            System.out.println("NPC dialogue displayed. Now go find the chest!");
            return; // Don't open sword selection here
        }

        // Check chest interaction - THIS opens the sword selection menu
        if (swordChest != null && !swordChest.isOpen() && swordChest.isPlayerNearby(character)) {
            if (!hasInteractedWithNPC) {
                // Must talk to NPC first
                showWarning("Talk to the NPC first!");
                System.out.println("You need to talk to the NPC before opening the chest!");
            } else if (currentSword == SwordType.NONE) {
                // Has talked to NPC, can now open chest and choose sword
                System.out.println("Opening chest - sword selection menu!");
                gameState = GameState.SWORD_SELECTION;
                swordSelectionMenu.show();
            } else {
                // Already has a sword
                showWarning("You already have a sword!");
            }
        }
    }

    private void selectSword(SwordType swordType) {
        currentSword = swordType;
        swordVisible = true;

        // Tell character and skillManager which sword type was selected
        character.setSwordType(swordType);
        character.setSwordVisibility(true);
        skillManager.setSwordType(swordType); // ADD THIS LINE

        swordSelectionMenu.hide();
        swordChest.open();

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameState = GameState.PLAYING;
        }).start();

        String swordName = (swordType == SwordType.BLUE_SWORD) ? "Frost Bane" : "Hellfire Edge";
        System.out.println("Selected sword: " + swordName);
        System.out.println("Sword type: " + swordType);
        System.out.println("Skills and attacks updated for " + swordName);
        System.out.println("Press '1' to toggle sword on/off");
    }

    public void toggleSwordVisibility() {
        if (currentSword != SwordType.NONE) {
            swordVisible = !swordVisible;
            character.setSwordVisibility(swordVisible);
            String status = swordVisible ? "ON" : "OFF";
            System.out.println("Sword is now " + status);

            if (!swordVisible) {
                System.out.println("You cannot attack or use skills while sword is OFF");
            } else {
                System.out.println("You can now attack and use skills!");
            }
        } else {
            System.out.println("You must choose a sword first!");
        }
    }

    public void pauseGame() {
        if (gameState == GameState.PLAYING && !pauseMenu.isFading()) {
            gameState = GameState.PAUSED;
            pauseMenu.startFadeIn();
        }
    }

    public void resumeGame() {
        if (gameState == GameState.PAUSED) {
            pauseMenu.startFadeOut();
            new Thread(() -> {
                while (!pauseMenu.isFadedOut()) {
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gameState = GameState.PLAYING;
            }).start();
        }
    }

    @Override
    public void run() {
        while (true) {
            update();
            repaint();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        pauseMenu.update();
        swordSelectionMenu.update();

        if (blueRogueNPC != null) {
            blueRogueNPC.update();
        }
        if (swordChest != null) {
            swordChest.update();
        }

        if (warningTimer > 0) {
            warningTimer--;
            if (warningTimer <= 0) {
                warningMessage = "";
            }
        }

        if (gameState != GameState.PLAYING) {
            return;
        }

        int speed = 4;
        boolean moving = false;
        String direction = "";

        int oldX = character.getX();
        int oldY = character.getY();
        int nextX = character.getX();
        int nextY = character.getY();

        // Check if any skill is active - if so, prevent movement
        boolean skillActive = skillManager.isAnySkillActive();

        // Only allow movement if no skills are active and not attacking
        if (!skillActive && !character.isAttacking()) {
            if (keyHandler.isDownPressed()) {
                nextY += speed;
                moving = true;
                direction = "down";
            }
            if (keyHandler.isUpPressed()) {
                nextY -= speed;
                moving = true;
                direction = "up";
            }
            if (keyHandler.isRightPressed()) {
                nextX += speed;
                moving = true;
                direction = "right";
            }
            if (keyHandler.isLeftPressed()) {
                nextX -= speed;
                moving = true;
                direction = "left";
            }

            // Only update position if moving and no collision
            if (moving && !collision.checkCollision(nextX + 50, nextY + 35, character.getWidth() - 100, character.getHeight() - 65)) {
                character.setPosition(nextX, nextY);
            }
        }

        // Update character animation (but not position if skill is active)
        character.update(moving, direction);
        camera.followCharacter(character);
        monsterManager.update(character, collision);
        skillManager.update();

        if (character.isAttacking()) {
            checkPlayerAttackOnMonsters();
        }

        if (monsterManager.isAnyMonsterAttackingPlayer(character)) {
        }
    }

    private void checkPlayerAttackOnMonsters() {
        int playerHitX = character.getX() + 50;
        int playerHitY = character.getY() + 35;
        int playerHitW = character.getWidth() - 100;
        int playerHitH = character.getHeight() - 65;

        for (Monster monster : monsterManager.getMonsters()) {
            int monsterHitX = monster.getX() + 50;
            int monsterHitY = monster.getY() + 35;
            int monsterHitW = monster.getWidth() - 100;
            int monsterHitH = monster.getHeight() - 65;

            if (playerHitX < monsterHitX + monsterHitW &&
                    playerHitX + playerHitW > monsterHitX &&
                    playerHitY < monsterHitY + monsterHitH &&
                    playerHitY + playerHitH > monsterHitY) {
                System.out.println("Hit monster at: (" + monster.getX() + ", " + monster.getY() + ")");
            }
        }
    }

    public void switchMap() {
        if (currentMap == 1) {
            currentMap = 2;
            character.setPosition(-780, -900);
            collision.loadMap2Collisions();
            monsterManager.loadMap2Monsters();
            System.out.println("Switched to Map 2");
        } else {
            currentMap = 1;
            character.setPosition(100, 100);
            collision.loadMap1Collisions();
            monsterManager.loadMap1Monsters();
            System.out.println("Switched to Map 1");
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int cameraX = camera.getX();
        int cameraY = camera.getY();

        g2.translate(-cameraX, -cameraY);

        if (currentMap == 1) {
            map1.draw(g, 5000, 5000, 0, 0);
        } else if (currentMap == 2) {
            map2.draw(g, 4000, 4000, 0, 0);
        }

//        collision.drawWalls(g2);

        // Draw NPC (same drawing style as monsters - no camera offset)
        if (blueRogueNPC != null) {
            blueRogueNPC.draw(g, 0, 0);
        }

        // Draw chest (same drawing style as monsters - no camera offset)
        if (swordChest != null) {
            swordChest.draw(g, 0, 0);
        }

        // Draw "F to interact" prompt
        if (blueRogueNPC != null && blueRogueNPC.isPlayerNearby(character) && !blueRogueNPC.isShowingDialogue()) {
            drawInteractPrompt(g2, blueRogueNPC.getX(), blueRogueNPC.getY(), 0, 0);
        }
        if (swordChest != null && !swordChest.isOpen() && swordChest.isPlayerNearby(character)) {
            drawInteractPrompt(g2, swordChest.getX(), swordChest.getY(), 0, 0);
        }

        monsterManager.draw(g, cameraX, cameraY);

        if (!skillManager.getSkillQ().isActive() && !skillManager.getSkillE().isActive() && !skillManager.getSkillR().isActive()) {
            character.draw(g, 0, 0);
        }

        skillManager.draw(g, 0, 0);
        collision.drawCharacterHitbox(g2, character.getX(), character.getY(),
                character.getWidth(), character.getHeight());

        g2.translate(cameraX, cameraY);

        if (gameState == GameState.PLAYING) {
            pauseMenu.drawPauseIcon(g2);

            // Only draw skill cooldowns if player has chosen a sword
            if (currentSword != SwordType.NONE) {
                drawSkillCooldowns(g2);
            }

            drawSwordIndicator(g2);
            drawWarningMessage(g2);

            // Debug: Show coordinates
            if (showCoordinates) {
//                drawCoordinateInfo(g2);
            }
        }

        if (gameState == GameState.PAUSED || pauseMenu.getFadeAlpha() > 0) {
            pauseMenu.draw(g2);
        }

        if (gameState == GameState.SWORD_SELECTION || swordSelectionMenu.isFading()) {
            swordSelectionMenu.draw(g2);
        }
    }

    private void drawInteractPrompt(Graphics2D g2, int x, int y, int cameraX, int cameraY) {
        g2.setColor(new Color(255, 255, 255, 200));
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        String text = "Press F";
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2.drawString(text, x - cameraX + 50 - textWidth / 2, y - cameraY - 10);
    }

    private void drawSwordIndicator(Graphics2D g2) {
        if (currentSword == SwordType.NONE) return;

        int iconSize = 60;
        int padding = 10;
        int x = WIDTH - iconSize - padding;
        int y = HEIGHT - iconSize - padding;

        // Get the correct sword toggle icon
        String iconKey = (currentSword == SwordType.RED_SWORD) ? "redSwordToggleIcon" : "blueSwordToggleIcon";
        BufferedImage swordIcon = resourceLoader.getImage(iconKey);

        // Draw sword icon
        if (swordIcon != null) {
            g2.drawImage(swordIcon, x - 35, y - 43, 120, 120, null);
        } else {
            // Fallback
            Color swordColor = (currentSword == SwordType.BLUE_SWORD) ?
                    new Color(50, 150, 255, 200) : new Color(255, 50, 50, 200);
            g2.setColor(swordColor);
            g2.fillRoundRect(x, y-20, 120, 120, 10, 10);
        }

        // Dim overlay if sword is off
        if (!swordVisible) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRoundRect(x - 10, y-20, 70, 70, 10, 10);
        }

        // Draw border
        Color borderColor = swordVisible ?
                ((currentSword == SwordType.BLUE_SWORD) ? new Color(100, 200, 255) : new Color(255, 100, 100)) :
                new Color(150, 150, 150);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x - 10, y -20, 70, 70, 10, 10);

        // Draw "1" below the icon
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        String keyText = "1";
        int textX = x + (iconSize - fm.stringWidth(keyText)) / 2;
        int textY = y + iconSize + 20;

        // Background for text
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(textX - 10, textY - fm.getAscent() - 12,
                fm.stringWidth(keyText) + 10, fm.getHeight(), 5, 5);

        g2.setColor(Color.WHITE);
        g2.drawString(keyText, textX - 6, textY - 12);

        // Status text (ON/OFF) - optional, can be removed if you want cleaner UI
        g2.setFont(new Font("Arial", Font.BOLD, 10));
//        String status = swordVisible ? "ON" : "OFF";
        Color statusColor = swordVisible ? Color.GREEN : Color.RED;
        g2.setColor(statusColor);
//        int statusX = x + (iconSize - g2.getFontMetrics().stringWidth(status)) / 2;
//        g2.drawString(status, statusX, y + iconSize / 2 + 5);
    }

    private void drawWarningMessage(Graphics2D g2) {
        if (warningTimer <= 0 || warningMessage.isEmpty()) return;

        float alpha = Math.min(1.0f, warningTimer / 30.0f);

        g2.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(warningMessage);
        int x = (WIDTH - textWidth) / 2;
        int y = HEIGHT / 2 - 100;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.8f));
        g2.setColor(new Color(0, 0, 0));
        g2.fillRoundRect(x - 20, y - 30, textWidth + 40, 50, 15, 15);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(new Color(255, 200, 0));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x - 20, y - 30, textWidth + 40, 50, 15, 15);

        g2.setColor(new Color(255, 200, 0));
        g2.drawString(warningMessage, x, y);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    private void drawSkillCooldowns(Graphics2D g2) {
        int iconSize = 60;
        int padding = 10;
        int startX = padding;
        int startY = HEIGHT - iconSize - padding;

        // Get the correct icon prefix based on current sword
        String iconPrefix = (currentSword == SwordType.RED_SWORD) ? "red" : "blue";

        // Draw Q skill icon
        BufferedImage qIcon = resourceLoader.getImage(iconPrefix + "SkillQIcon");
        drawCooldownIcon(g2, padding, startY -20, iconSize, "Q",
                skillManager.getSkillQ().isOnCooldown(),
                skillManager.getSkillQ().getCooldownPercent(),
                qIcon);

        // Draw E skill icon
        BufferedImage eIcon = resourceLoader.getImage(iconPrefix + "SkillEIcon");
        drawCooldownIcon(g2, padding + iconSize + padding, startY -20, iconSize, "E",
                skillManager.getSkillE().isOnCooldown(),
                skillManager.getSkillE().getCooldownPercent(),
                eIcon);

        // Draw R skill icon
        BufferedImage rIcon = resourceLoader.getImage(iconPrefix + "SkillRIcon");
        drawCooldownIcon(g2, padding + (iconSize + padding) * 2, startY -20, iconSize, "R",
                skillManager.getSkillR().isOnCooldown(),
                skillManager.getSkillR().getCooldownPercent(),
                rIcon);
    }

    private void drawCooldownIcon(Graphics2D g2, int x, int y, int size, String key,
                                  boolean onCooldown, float cooldownPercent, BufferedImage icon) {
        // Draw the skill icon as background
        if (icon != null) {
            g2.drawImage(icon, x - 19, y - 18, 100, 100, null);
        } else {
            // Fallback if icon not loaded
            if (onCooldown) {
                g2.setColor(new Color(100, 100, 100, 180));
            } else {
                g2.setColor(new Color(50, 150, 255, 180));
            }
            g2.fillRoundRect(x, y, size, size, 10, 10);
        }

        // Draw cooldown overlay (dark overlay from bottom to top)
        if (onCooldown) {
            g2.setColor(new Color(0, 0, 0, 180));
            int cooldownHeight = (int)(size * cooldownPercent);
            g2.fillRoundRect(x, y + size - cooldownHeight, size, cooldownHeight, 10, 10);
        }

        // Draw border
        g2.setColor(onCooldown ? new Color(150, 150, 150) : Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, size, size, 10, 10);

        // Draw key letter below the icon
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2.getFontMetrics();
        int textX = x + (size - fm.stringWidth(key)) / 2;
        int textY = y + size + 20;

        // Background for text
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(textX - 5, textY - fm.getAscent(),
                fm.stringWidth(key) + 10, fm.getHeight(), 5, 5);

        g2.setColor(Color.WHITE);
        g2.drawString(key, textX, textY);
    }


    public GameState getGameState() {
        return gameState;
    }

    public boolean hasSword() {
        return currentSword != SwordType.NONE;
    }

    public boolean isSwordVisible() {
        return swordVisible;
    }

    public void showWarning(String message) {
        warningMessage = message;
        warningTimer = WARNING_DURATION;
    }

    public MonsterManager getMonsterManager() {
        return monsterManager;
    }

    public SkillManager getSkillManager() {
        return skillManager;
    }

    public void toggleCoordinateDisplay() {
        showCoordinates = !showCoordinates;
        System.out.println("Coordinate display: " + (showCoordinates ? "ON" : "OFF"));
    }

    private void drawCoordinateInfo(Graphics2D g2) {
        g2.setFont(new Font("Monospaced", Font.BOLD, 14));
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(10, 60, 320, 160);

        g2.setColor(Color.YELLOW);
        int yPos = 80;
        g2.drawString("Player X: " + character.getX(), 20, yPos);
        yPos += 20;
        g2.drawString("Player Y: " + character.getY(), 20, yPos);
        yPos += 20;
        g2.drawString("Camera X: " + camera.getX(), 20, yPos);
        yPos += 20;
        g2.drawString("Camera Y: " + camera.getY(), 20, yPos);
        yPos += 20;

        if (blueRogueNPC != null) {
            g2.drawString("NPC at: (" + blueRogueNPC.getX() + ", " + blueRogueNPC.getY() + ")", 20, yPos);
            yPos += 20;
            int distance = (int)Math.sqrt(
                    Math.pow(character.getX() - blueRogueNPC.getX(), 2) +
                            Math.pow(character.getY() - blueRogueNPC.getY(), 2)
            );
            g2.drawString("Dist to NPC: " + distance, 20, yPos);
            yPos += 20;
        }
        if (swordChest != null) {
            g2.drawString("Chest at: (" + swordChest.getX() + ", " + swordChest.getY() + ")", 20, yPos);
            yPos += 20;
        }

        g2.setColor(Color.WHITE);
        g2.drawString("Press 'C' to toggle", 20, yPos);
    }
}