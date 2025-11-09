package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel implements Runnable {

    private final int WIDTH = 960;
    private final int HEIGHT = 720;
    private PixelPosition pixelPosition;

    //try
    private Map1 map1;
    private Map2 map2;
    public int currentMap = 1;

    private KeyHandler keyHandler;
    private Thread gameThread;
    private Camera camera;
    private Collision collision;
    public CharacterLoad character;  // Made public for KeyHandler access
    private SkillManager skillManager;

    private ResourceLoader resourceLoader;

    // Monster system
    private MonsterManager monsterManager;

    // Game State
    private GameState gameState = GameState.PLAYING;
    private PauseMenu pauseMenu;
    private Point mousePosition = new Point(0, 0);

    public GamePanel(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        keyHandler = new KeyHandler(this);
        camera = new Camera(WIDTH, HEIGHT);

        // Initialize character at starting position
        character = new CharacterLoad(100, 100, resourceLoader);

        skillManager = new SkillManager(character, resourceLoader);

        // Initialize monster manager
        monsterManager = new MonsterManager(resourceLoader.getSpriteSize());

        // Initialize pause menu
        pauseMenu = new PauseMenu(WIDTH, HEIGHT, resourceLoader);

        // Initialize map
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        //try
        map1 = new Map1(resourceLoader);
        map2 = new Map2(resourceLoader);

        // Initialize collision checker with rectangle walls
        collision = new Collision();

        // Load monsters for starting map
        monsterManager.loadMap1Monsters();

        gameThread = new Thread(this);
        gameThread.start();

        // Mouse listener for pause menu interactions
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
        if (gameState == GameState.PLAYING) {
            // Check if pause icon was clicked
            if (pauseMenu.isPauseIconClicked(mouseX, mouseY)) {
                pauseGame();
            }
        } else if (gameState == GameState.PAUSED) {
            // Check if continue button was clicked
            if (pauseMenu.isContinueButtonClicked(mouseX, mouseY)) {
                resumeGame();
            }
            // Check if exit button was clicked
            else if (pauseMenu.isExitButtonClicked(mouseX, mouseY)) {
                System.exit(0);
            }
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
            // Wait for fade out to complete before resuming
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
        // Update pause menu fade animation
        pauseMenu.update();

        // Only update game logic if playing
        if (gameState != GameState.PLAYING) {
            return;
        }

        int speed = 4;
        boolean moving = false;
        String direction = "";

        // Store current position
        int oldX = character.getX();
        int oldY = character.getY();
        int nextX = character.getX();
        int nextY = character.getY();

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

        // Check collision and update position only if not attacking
        if (!character.isAttacking() &&
                !collision.checkCollision(nextX + 50, nextY + 35, character.getWidth() - 100, character.getHeight() - 65)) {
            character.setPosition(nextX, nextY);
        }
        // If character is attacking, allow movement through monsters but not walls
        else if (character.isAttacking() &&
                !collision.checkCollision(nextX + 50, nextY + 35, character.getWidth() - 100, character.getHeight() - 65)) {
            character.setPosition(nextX, nextY);
        }
        else if (skillManager.getSkillQ().isActive() || skillManager.getSkillE().isActive() || skillManager.getSkillR().isActive()) {}
        else {
            // Collision detected with wall, stay at old position
            // moving = false;  // Optional: uncomment if you want to stop animation on collision
        }

        character.update(moving, direction);
        camera.followCharacter(character);

        // Update monsters
        monsterManager.update(character, collision);

        skillManager.update();


        // Check if player is attacking and overlapping with any monster
        if (character.isAttacking()) {
            checkPlayerAttackOnMonsters();
        }

        // Check if any monster is attacking player (you can add health system here)
        if (monsterManager.isAnyMonsterAttackingPlayer(character)) {
            // System.out.println("Player is being attacked!");
            // TODO: Implement player damage/health system
        }
    }

    /**
     * Check if player's attack hits any monsters
     */
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

            // Check if hitboxes overlap
            if (playerHitX < monsterHitX + monsterHitW &&
                    playerHitX + playerHitW > monsterHitX &&
                    playerHitY < monsterHitY + monsterHitH &&
                    playerHitY + playerHitH > monsterHitY) {

                // Player is attacking and overlapping monster!
                System.out.println("Hit monster at: (" + monster.getX() + ", " + monster.getY() + ")");
                // TODO: Implement monster damage/death system
                // monsterManager.damageMonster(monster, damageAmount);
            }
        }
    }

    // Try
    public void switchMap() {
        if (currentMap == 1) {
            currentMap = 2;
            character.setPosition(-780, -800); // new starting point for map2
            collision.loadMap2Collisions(); // custom method for map2
            monsterManager.loadMap2Monsters(); // Load map 2 monsters
            System.out.println("Switched to Map 2");
        } else {
            currentMap = 1;
            character.setPosition(100, 200);
            collision.loadMap1Collisions();
            monsterManager.loadMap1Monsters(); // Load map 1 monsters
            System.out.println("Switched to Map 1");
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int cameraX = camera.getX();
        int cameraY = camera.getY();

        // Translate graphics to camera position (world coordinates)
        g2.translate(-cameraX, -cameraY);

        // Draw map at world coordinates
        //Try
        if (currentMap == 1) {
            map1.draw(g, 5000, 5000, 0, 0);
        } else if (currentMap == 2) {
            map2.draw(g, 4000, 4000, 0, 0);
        }

        // Draw collision walls
        collision.drawWalls(g2);

        // Draw monsters at world coordinates
        monsterManager.draw(g, cameraX, cameraY);

        // Draw character at world coordinates
        if (!skillManager.getSkillQ().isActive() && !skillManager.getSkillE().isActive() && !skillManager.getSkillR().isActive()) {
            character.draw(g, 0, 0);
        }

        skillManager.draw(g, 0, 0);



        // Draw character hitbox
        collision.drawCharacterHitbox(g2, character.getX(), character.getY(),
                character.getWidth(), character.getHeight());



        // Restore graphics translation
        g2.translate(cameraX, cameraY);

        // Draw UI elements in screen space

        // Draw pause icon (always visible in top-right corner)
        if (gameState == GameState.PLAYING) {
            pauseMenu.drawPauseIcon(g2);
        }

        // Draw pause menu overlay
        if (gameState == GameState.PAUSED || pauseMenu.getFadeAlpha() > 0) {
            pauseMenu.draw(g2);
        }

        if (gameState == GameState.PLAYING) {
            drawSkillCooldowns(g2);
        }
    }

    private void drawSkillCooldowns(Graphics2D g2) {
        int iconSize = 40;
        int padding = 10;
        int startY = HEIGHT - iconSize - padding;

        // Skill Q cooldown
        drawCooldownIcon(g2, padding, startY, iconSize, "Q",
                skillManager.getSkillQ().isOnCooldown(),
                skillManager.getSkillQ().getCooldownPercent());

        // Skill E cooldown
        drawCooldownIcon(g2, padding + iconSize + padding, startY, iconSize, "E",
                skillManager.getSkillE().isOnCooldown(),
                skillManager.getSkillE().getCooldownPercent());

        // Skill R cooldown
        drawCooldownIcon(g2, padding + (iconSize + padding) * 2, startY, iconSize, "R",
                skillManager.getSkillR().isOnCooldown(),
                skillManager.getSkillR().getCooldownPercent());
    }

    private void drawCooldownIcon(Graphics2D g2, int x, int y, int size, String key,
                                  boolean onCooldown, float cooldownPercent) {
        // Draw background
        if (onCooldown) {
            g2.setColor(new Color(100, 100, 100, 180));
        } else {
            g2.setColor(new Color(50, 150, 255, 180));
        }
        g2.fillRoundRect(x, y, size, size, 10, 10);

        // Draw cooldown overlay
        if (onCooldown) {
            g2.setColor(new Color(0, 0, 0, 150));
            int cooldownHeight = (int)(size * cooldownPercent);
            g2.fillRoundRect(x, y + size - cooldownHeight, size, cooldownHeight, 10, 10);
        }

        // Draw key letter
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        int textX = x + (size - fm.stringWidth(key)) / 2;
        int textY = y + ((size - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(key, textX, textY);

        // Draw border
        g2.setColor(Color.WHITE);
        g2.setStroke(new java.awt.BasicStroke(2));
        g2.drawRoundRect(x, y, size, size, 10, 10);
    }

    public GameState getGameState() {
        return gameState;
    }

    public MonsterManager getMonsterManager() {
        return monsterManager;
    }

    public SkillManager getSkillManager() {
        return skillManager;
    }
}