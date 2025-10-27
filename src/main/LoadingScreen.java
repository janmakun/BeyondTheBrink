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
    private final int totalFrames = 26; // number of frames
    private final int LAST_FRAME_INDEX = totalFrames - 1;

    private BufferedImage[] animationFrames;
    private int currentFrame = 0;
    private int frameDelay = 2; // adjust speed
    private int frameCounter = 0;

    private JFrame parentFrame;
    private Timer animationTimer;

    private ResourceLoader resourceLoader;

    private long startTime;
    private final int MIN_DISPLAY_TIME = 7000; // 7 seconds
    private final int MAX_DISPLAY_TIME = 10000; // optional max
    private boolean resourcesLoaded = false;
    private boolean readyToTransition = false;
    private boolean hasTransitioned = false;
    private boolean isLoading = true;

    public LoadingScreen(JFrame frame) {
        this.parentFrame = frame;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        loadAnimationFrames();
        startAnimation();
    }

    private void loadAnimationFrames() {
        animationFrames = new BufferedImage[totalFrames];

        try {
            for (int i = 0; i < totalFrames; i++) {
                if (i + 1 == 13) { // skip frame 13 if missing
                    animationFrames[i] = animationFrames[i - 1];
                    System.out.println("⚠️ Skipping frame 13 (not available)");
                    continue;
                }

                String filename = String.format("res/LoadingScreen/LS%d.png", i + 1);
                File file = new File(filename);
                if (!file.exists()) {
                    filename = String.format("resources/loading/frame_%02d.png", i + 1);
                    file = new File(filename);
                }

                BufferedImage raw = ImageIO.read(file);
                BufferedImage scaled = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = scaled.createGraphics();
                g2.drawImage(raw, 0, 0, WIDTH, HEIGHT, null);
                g2.dispose();
                animationFrames[i] = scaled;
                raw.flush();

                System.out.println("✓ Loaded frame " + (i + 1) + "/" + totalFrames);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        startResourceLoading();
    }

    private void updateAnimation() {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            currentFrame++;
            if (currentFrame >= totalFrames) currentFrame = 0; // loop animation
        }

        long elapsed = System.currentTimeMillis() - startTime;

        // Only transition if minimum time passed and resources loaded
        if (resourcesLoaded && elapsed >= MIN_DISPLAY_TIME && currentFrame == LAST_FRAME_INDEX && !hasTransitioned) {
            hasTransitioned = true;
            new Thread(() -> {
                try { Thread.sleep(500); } catch (InterruptedException ex) {}
                SwingUtilities.invokeLater(this::transitionToGame);
            }).start();
        }

        // Failsafe max time
        if (!hasTransitioned && elapsed >= MAX_DISPLAY_TIME) {
            hasTransitioned = true;
            SwingUtilities.invokeLater(this::transitionToGame);
        }
    }

    private void startResourceLoading() {
        new Thread(() -> {
            resourceLoader = new ResourceLoader();
            resourceLoader.loadAllResources();
            resourcesLoaded = true;
            System.out.println("Resources loaded!");
        }).start();
    }

    public void transitionToGame() {
        if (animationFrames != null) {
            for (BufferedImage img : animationFrames) if (img != null) img.flush();
            animationFrames = null;
        }

        animationTimer.stop();
        isLoading = false;

        parentFrame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(resourceLoader);
        parentFrame.add(gamePanel);
        parentFrame.revalidate();
        parentFrame.repaint();
        gamePanel.requestFocusInWindow();

        System.out.println("Game started!");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (animationFrames != null && animationFrames[currentFrame] != null) {
            g2.drawImage(animationFrames[currentFrame], 0, 0, WIDTH, HEIGHT, null);
        } else {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            String text = "Loading... Frame " + (currentFrame + 1) + "/" + totalFrames;
            FontMetrics fm = g2.getFontMetrics();
            int x = (WIDTH - fm.stringWidth(text)) / 2;
            int y = HEIGHT / 2;
            g2.drawString(text, x, y);
        }
    }
}
