package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LoadingScreen extends JPanel {

    private final int WIDTH = 960;
    private final int HEIGHT = 720;
    private final int totalFrames = 25;
    private final int LAST_FRAME_INDEX = totalFrames - 1;

    private BufferedImage[] animationFrames;
    private int currentFrame = 0;
    private int frameDelay = 2;
    private int frameCounter = 0;
    private int completedCycles = 0;
    private final int MIN_CYCLES = 3; // Increased from 2 to 3 for longer display

    private JFrame parentFrame;
    private Timer animationTimer;

    private ResourceLoader resourceLoader;

    private long startTime;
    private final int MIN_DISPLAY_TIME = 2000; // Increased to 2 seconds
    private final int MAX_DISPLAY_TIME = 30000; // 30 seconds failsafe
    private boolean resourcesLoaded = false;
    private boolean gameObjectsReady = false; // NEW: Track if game objects initialized
    private boolean hasTransitioned = false;
    private boolean framesLoaded = false;

    public LoadingScreen(JFrame frame, ResourceLoader loader) {
        this.parentFrame = frame;
        this.resourceLoader = loader;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        loadAnimationFramesAsync();
        startAnimation();
    }

    private void loadAnimationFramesAsync() {
        new Thread(() -> {
            animationFrames = new BufferedImage[totalFrames];

            try {
                int frameIndex = 0;
                for (int i = 1; i <= 26; i++) {
                    if (i == 13) {
                        System.out.println("⚠️ Skipping frame 13 (not available)");
                        continue;
                    }

                    String filename = String.format("res/LoadingScreen/LS%d.png", i);
                    File file = new File(filename);
                    if (!file.exists()) {
                        filename = String.format("resources/loading/frame_%02d.png", i);
                        file = new File(filename);
                    }

                    BufferedImage raw = ImageIO.read(file);
                    GraphicsConfiguration gc = GraphicsEnvironment
                            .getLocalGraphicsEnvironment()
                            .getDefaultScreenDevice()
                            .getDefaultConfiguration();
                    BufferedImage scaled = gc.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);

                    Graphics2D g2 = scaled.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.drawImage(raw, 0, 0, WIDTH, HEIGHT, null);
                    g2.dispose();
                    animationFrames[frameIndex] = scaled;
                    raw.flush();

                    System.out.println("✓ Loaded frame " + i + " -> index " + frameIndex + "/" + totalFrames);
                    frameIndex++;
                }

                framesLoaded = true;
                System.out.println("✅ All animation frames loaded!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "AnimationLoader").start();
    }

    private void startAnimation() {
        startTime = System.currentTimeMillis();

        animationTimer = new Timer(16, e -> {
            if (!hasTransitioned) {
                updateAnimation();
                repaint();
            }
        });
        animationTimer.start();

        // Monitor resource loading AND game initialization
        new Thread(() -> {
            // Step 1: Wait for resources to finish loading
            while (!resourceLoader.isFinished()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            resourcesLoaded = true;
            System.out.println("✅ Resources loaded!");

            // Step 2: Give extra time for game objects to initialize
            // This allows the GamePanel's run() method to complete initializeGameObjects()
            try {
                Thread.sleep(800); // Extra buffer time for game object initialization
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            gameObjectsReady = true;
            System.out.println("✅ Game objects ready!");

        }, "ResourceMonitor").start();
    }

    private void updateAnimation() {
        if (framesLoaded) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrame++;

                if (currentFrame > LAST_FRAME_INDEX) {
                    currentFrame = 0;
                    completedCycles++;
                    System.out.println("✓ Completed animation cycle " + completedCycles);
                }
            }

            long elapsed = System.currentTimeMillis() - startTime;

            // Only transition if ALL conditions are met:
            // 1. Resources are loaded
            // 2. Game objects are ready
            // 3. Minimum time has passed
            // 4. At least 3 complete cycles done
            // 5. Currently on last frame (for smooth transition)
            if (resourcesLoaded &&
                    gameObjectsReady &&
                    elapsed >= MIN_DISPLAY_TIME &&
                    completedCycles >= MIN_CYCLES &&
                    currentFrame == LAST_FRAME_INDEX &&
                    !hasTransitioned) {

                hasTransitioned = true;
                System.out.println("🎮 Transitioning to game after " + completedCycles + " cycles");

                new Thread(() -> {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {}
                    SwingUtilities.invokeLater(this::transitionToGame);
                }, "GameTransition").start();
            }

            // Failsafe max time
            if (!hasTransitioned && elapsed >= MAX_DISPLAY_TIME) {
                hasTransitioned = true;
                System.out.println("⚠️ Max time reached, forcing transition");
                SwingUtilities.invokeLater(this::transitionToGame);
            }
        }
    }

    public void transitionToGame() {
        // Clean up animation frames
        if (animationFrames != null) {
            for (BufferedImage img : animationFrames) {
                if (img != null) img.flush();
            }
            animationFrames = null;
        }

        animationTimer.stop();

        // Force garbage collection before game starts
        System.gc();

        parentFrame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(resourceLoader);
        parentFrame.add(gamePanel);
        parentFrame.revalidate();
        parentFrame.repaint();
        gamePanel.requestFocusInWindow();

        System.out.println("🎮 Game started successfully!");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (framesLoaded && animationFrames != null && animationFrames[currentFrame] != null) {
            g2.drawImage(animationFrames[currentFrame], 0, 0, null);
        } else {
            drawInitialLoadingScreen(g2);
        }
    }

    private void drawInitialLoadingScreen(Graphics2D g2) {
        int margin = 30;
        int spinnerSize = 40;
        int spinnerX = margin + spinnerSize / 2;
        int spinnerY = HEIGHT - margin - spinnerSize / 2;

        long time = System.currentTimeMillis();
        float rotation = (time % 1000) / 1000.0f * 360.0f;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2.setStroke(new BasicStroke(4.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g2.setColor(new Color(80, 80, 80, 150));
        g2.drawOval(spinnerX - spinnerSize / 2, spinnerY - spinnerSize / 2, spinnerSize, spinnerSize);

        g2.setColor(Color.WHITE);

        Graphics2D g2d = (Graphics2D) g2.create();
        g2d.translate(spinnerX, spinnerY);
        g2d.rotate(Math.toRadians(rotation));
        g2d.translate(-spinnerX, -spinnerY);

        g2d.drawArc(spinnerX - spinnerSize / 2, spinnerY - spinnerSize / 2,
                spinnerSize, spinnerSize, 0, 270);
        g2d.dispose();

        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        int dotCount = (int) ((time / 500) % 4);
        String dots = ".".repeat(dotCount);
        String loadingText = "Loading" + dots;
        g2.drawString(loadingText, spinnerX + spinnerSize / 2 + 15, spinnerY + 7);
    }
}