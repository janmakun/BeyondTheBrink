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
    private CharacterLoad character;

    private ResourceLoader resourceLoader;

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

        // Check collision before updating position
        if (!collision.checkCollision(nextX + 10, nextY, character.getWidth() -20 , character.getHeight())) {
            character.setPosition(nextX, nextY);
        } else {
            // Collision detected, stay at old position
//            moving = false;
        }

        character.update(moving, direction);
        camera.followCharacter(character);
    }

    // Try
    public void switchMap() {
        if (currentMap == 1) {
            currentMap = 2;
            character.setPosition(-780, -800); // new starting point for map2
            collision.loadMap2Collisions(); // custom method for map2
            System.out.println("Switched to Map 2");
        } else {
            currentMap = 1;
            character.setPosition(100, 200);
            collision.loadMap1Collisions();
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

        // Draw character at world coordinates
        character.draw(g, 0, 0);

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
    }

    public GameState getGameState() {
        return gameState;
    }
}