package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SwordSelectionMenu {
    private boolean isActive = false;
    private BufferedImage backgroundImage;
    private BufferedImage blueSwordIcon;
    private BufferedImage redSwordIcon;
    private BufferedImage blueButtonImage;
    private BufferedImage redButtonImage;

    private int screenWidth, screenHeight;
    private Rectangle blueButtonBounds;
    private Rectangle redButtonBounds;
    private Point mousePosition = new Point(0, 0);

    // Background dimensions
    private int bgWidth = 850;
    private int bgHeight = 700;

    private float fadeAlpha = 0f;
    private boolean fadingIn = false;
    private boolean fadingOut = false;

    public SwordSelectionMenu(int screenWidth, int screenHeight, BufferedImage backgroundImage,
                              BufferedImage blueSwordIcon, BufferedImage redSwordIcon,
                              BufferedImage blueButton, BufferedImage redButton,
                              int blueX, int blueY, int blueW, int blueH,
                              int redX, int redY, int redW, int redH) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.backgroundImage = backgroundImage;
        this.blueSwordIcon = blueSwordIcon;
        this.redSwordIcon = redSwordIcon;
        this.blueButtonImage = blueButton;
        this.redButtonImage = redButton;

        // Calculate background position (centered)
        int bgX = (screenWidth - bgWidth) / 2;
        int bgY = (screenHeight - bgHeight) / 2;

        // Button positions are relative to background position
        blueButtonBounds = new Rectangle(
                529,
                617,
                blueW,
                blueH
        );

        redButtonBounds = new Rectangle(
                180,
                606,
                redW,
                redH
        );
    }

    public void show() {
        isActive = true;
        fadingIn = true;
        fadeAlpha = 0f;
    }

    public void hide() {
        fadingOut = true;
    }

    public void update() {
        if (fadingIn) {
            fadeAlpha += 0.05f;
            if (fadeAlpha >= 1f) {
                fadeAlpha = 1f;
                fadingIn = false;
            }
        } else if (fadingOut) {
            fadeAlpha -= 0.05f;
            if (fadeAlpha <= 0f) {
                fadeAlpha = 0f;
                fadingOut = false;
                isActive = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (!isActive && fadeAlpha <= 0) return;

        // Semi-transparent overlay
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha * 0.7f));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));

        // Draw background centered (850x700)
        if (backgroundImage != null) {
            int bgX = (screenWidth - bgWidth) / 2;
            int bgY = (screenHeight - bgHeight) / 2;
            g2.drawImage(backgroundImage, bgX, bgY, bgWidth, bgHeight, null);
        } else {
            // Fallback if no background image
            int bgX = (screenWidth - bgWidth) / 2;
            int bgY = (screenHeight - bgHeight) / 2;
            g2.setColor(new Color(20, 20, 40));
            g2.fillRoundRect(bgX, bgY, bgWidth, bgHeight, 20, 20);
        }

        // Draw buttons at exact positions (if custom images not provided, draw rectangles)
        if (redButtonImage != null) {
            g2.drawImage(redButtonImage, redButtonBounds.x, redButtonBounds.y,
                    redButtonBounds.width, redButtonBounds.height, null);
        } else {
            drawButton(g2, redButtonBounds, "HELLFIRE EDGE", new Color(255, 50, 50));
        }

        if (blueButtonImage != null) {
            g2.drawImage(blueButtonImage, blueButtonBounds.x, blueButtonBounds.y,
                    blueButtonBounds.width, blueButtonBounds.height, null);
        } else {
            drawButton(g2, blueButtonBounds, "FROST BANE", new Color(0, 150, 255));
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawButton(Graphics2D g2, Rectangle bounds, String text, Color color) {
        // Button background
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20);

        // Button border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20);

        // Button text
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        int textX = bounds.x + (bounds.width - fm.stringWidth(text)) / 2;
        int textY = bounds.y + (bounds.height + fm.getAscent()) / 2 - 5;
        g2.drawString(text, textX, textY);
    }

    public boolean isBlueButtonClicked(int mouseX, int mouseY) {
        return isActive && fadeAlpha >= 1f && blueButtonBounds.contains(mouseX, mouseY);
    }

    public boolean isRedButtonClicked(int mouseX, int mouseY) {
        return isActive && fadeAlpha >= 1f && redButtonBounds.contains(mouseX, mouseY);
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isFading() {
        return fadingIn || fadingOut;
    }
}