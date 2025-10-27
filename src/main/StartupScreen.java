package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class StartupScreen extends JPanel {
    private final int WIDTH = 960;
    private final int HEIGHT = 720;

    private BufferedImage backgroundImage;
    private BufferedImage playButtonImage;
    private BufferedImage quitButtonImage;

    private Rectangle playButton;
    private Rectangle quitButton;

    private JFrame parentFrame;
    private GamePanel gamePanel;

    private boolean playHovered = false;
    private boolean quitHovered = false;

    public StartupScreen(JFrame frame) {
        this.parentFrame = frame;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        loadImages();
        initializeButtons();
        setupMouseListener();
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("res/StartUp/StartUpScreen.png"));
            playButtonImage = ImageIO.read(new File("res/StartUp/play buton.png"));
            quitButtonImage = ImageIO.read(new File("res/StartUp/quitButton.png"));

        } catch (IOException e) {
            System.err.println("Error loading startup screen images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeButtons() {
        playButton = new Rectangle(87, 370, 415, 81);
        quitButton = new Rectangle(87, 483, 250, 81);
    }

    private void setupMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point click = e.getPoint();

                if (playButton.contains(click)) {
                    startGame();
                } else if (quitButton.contains(click)) {
                    System.exit(0);
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point mouse = e.getPoint();
                playHovered = playButton.contains(mouse);
                quitHovered = quitButton.contains(mouse);
                repaint();
            }
        });
    }

    private void startGame() {
        parentFrame.getContentPane().removeAll();

        LoadingScreen loadingScreen = new LoadingScreen(parentFrame);
        parentFrame.add(loadingScreen);
        parentFrame.revalidate();
        parentFrame.repaint();

        // Run resource loading in a background thread
        new Thread(() -> {
            ResourceLoader loader = new ResourceLoader();
            loader.loadAllResources();  // heavy resource loading here

            // When finished, transition to the game
            SwingUtilities.invokeLater(() -> loadingScreen.transitionToGame());
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smoother graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw background
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);
        }

        // Draw Play button
        drawButton(g2, playButton, playHovered);

        // Draw Quit button
        drawButton(g2, quitButton, quitHovered);
    }

    private void drawButton(Graphics2D g2, Rectangle button, boolean hovered) {
        // Determine which button image to use
        BufferedImage btnImage = null;
        if (button == playButton) {
            btnImage = playButtonImage;
        } else if (button == quitButton) {
            btnImage = quitButtonImage;
        }

        // Draw button image
        if (btnImage == playButtonImage) {
            g2.drawImage(btnImage, 87, 370, 415, 81, null);
        }
        else if (btnImage == quitButtonImage) {
            g2.drawImage(btnImage, 87, 483, 250, 81, null);
        }
        else {
        }

        // Apply hover effect (semi-transparent white overlay)
        if (hovered) {
            g2.setColor(new Color(255, 255, 255, 50));
            if(button == playButton) {
                g2.fillRoundRect(87, 370, 415, 81, 15, 15);
            }
            else if(button == quitButton) {
                g2.fillRoundRect(87, 483, 250, 81, 15, 15);
            }
        }
    }
}