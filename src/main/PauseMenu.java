package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseMenu extends JPanel {

    private int width, height;
    private ResourceLoader resourceLoader;

    // === BUTTON STATE AND ANIMATION ===
    private Rectangle continueButtonBounds;
    private Rectangle exitButtonBounds;
    private Rectangle pauseIconBounds;

    private String hoveredButton = null;
    private boolean fadingIn = false;
    private boolean fadingOut = false;
    private boolean fadedOut = true;
    private float fadeAlpha = 0f; // 0 = transparent, 1 = fully visible

    public PauseMenu(int width, int height, ResourceLoader resourceLoader) {
        this.width = width;
        this.height = height;
        this.resourceLoader = resourceLoader;

        setPreferredSize(new Dimension(width, height));
        setOpaque(false);

        setupMouseHover();
    }

    // === HANDLE MOUSE HOVER DETECTION ===
    private void setupMouseHover() {
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();

                if (continueButtonBounds != null && continueButtonBounds.contains(p)) {
                    hoveredButton = "continue";
                } else if (exitButtonBounds != null && exitButtonBounds.contains(p)) {
                    hoveredButton = "exit";
                } else {
                    hoveredButton = null;
                }
                repaint();
            }
        });
    }

    // === UPDATE FADE LOGIC ===
    public void update() {
        if (fadingIn) {
            fadeAlpha += 0.05f;
            if (fadeAlpha >= 1f) {
                fadeAlpha = 1f;
                fadingIn = false;
                fadedOut = false;
            }
        } else if (fadingOut) {
            fadeAlpha -= 0.05f;
            if (fadeAlpha <= 0f) {
                fadeAlpha = 0f;
                fadingOut = false;
                fadedOut = true;
            }
        }
    }

    // === DRAW EVERYTHING ===
    public void draw(Graphics2D g2) {
        if (fadeAlpha <= 0f && fadedOut) return;

        // Apply fade transparency
        Composite original = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));

        // === DRAW BACKGROUND ===
        BufferedImage bg = resourceLoader.getImage("pauseBackground");
        if (bg != null) {
            g2.drawImage(bg, 230, -50, 500, 720, null);
        }

        // === BUTTON IMAGE POSITIONS ===
        // You can freely adjust these for layout later
        int continueX = 280;
        int continueY = 200;
        int exitX = 280;
        int exitY = 350;
        int buttonWidth = 400;
        int buttonHeight = 300;

        continueButtonBounds = new Rectangle(320, 305, 320, 80);
        exitButtonBounds = new Rectangle(320, 450, 320, 80);

        // === DRAW CONTINUE BUTTON ===
        BufferedImage continueBtn = resourceLoader.getImage("continueButton");
        if (continueBtn != null) {
            g2.drawImage(continueBtn, continueX, continueY, buttonWidth, buttonHeight, null);
        }

        // === DRAW EXIT BUTTON ===
        BufferedImage exitBtn = resourceLoader.getImage("exitButton");
        if (exitBtn != null) {
            g2.drawImage(exitBtn, exitX, exitY, buttonWidth, buttonHeight, null);
        }

        // === HOVER BRIGHTEN EFFECT ===
        if (hoveredButton != null) {
            g2.setColor(new Color(255, 255, 255, 50));
            if (hoveredButton.equals("continue")) {
                g2.fillRoundRect(continueX, continueY, buttonWidth, buttonHeight, 15, 15);
            } else if (hoveredButton.equals("exit")) {
                g2.fillRoundRect(exitX, exitY, buttonWidth, buttonHeight, 15, 15);
            }
        }

        g2.setComposite(original);
    }

    // === DRAW PAUSE ICON (TOP-RIGHT CORNER) ===
    public void drawPauseIcon(Graphics2D g2) {
        BufferedImage pauseIcon = resourceLoader.getImage("pauseIcon");
        if (pauseIcon != null) {
            int iconSize = 60;
            int x = width - iconSize - 30; // 30px from right edge
            int y = 30; // 30px from top
            g2.drawImage(pauseIcon, x, y, iconSize, iconSize, null);
            pauseIconBounds = new Rectangle(x, y, 50, 50);
        }
    }

    // === BUTTON CLICK DETECTION ===
    public boolean isPauseIconClicked(int mouseX, int mouseY) {
        return pauseIconBounds != null && pauseIconBounds.contains(mouseX, mouseY);
    }

    public boolean isContinueButtonClicked(int mouseX, int mouseY) {
        return continueButtonBounds != null && continueButtonBounds.contains(mouseX, mouseY);
    }

    public boolean isExitButtonClicked(int mouseX, int mouseY) {
        return exitButtonBounds != null && exitButtonBounds.contains(mouseX, mouseY);
    }

    // === FADE CONTROL METHODS ===
    public void startFadeIn() {
        fadingIn = true;
        fadingOut = false;
        fadedOut = false;
    }

    public void startFadeOut() {
        fadingOut = true;
        fadingIn = false;
    }

    public boolean isFading() {
        return fadingIn || fadingOut;
    }

    public boolean isFadedOut() {
        return fadedOut;
    }

    public float getFadeAlpha() {
        return fadeAlpha;
    }
}
