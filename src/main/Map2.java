package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Map2 {
    private BufferedImage background;

    public Map2(ResourceLoader resourceLoader) {
        try {
            // Fixed: Use File path like Map1 does
            background = ImageIO.read(new File("res/Map/forestWithPortalRock.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Map2 image not found!");
        }
    }

    public void draw(Graphics g, int width, int height, int cameraX, int cameraY) {
        if (background != null) {
            // When using translate, draw at actual world position
            g.drawImage(background, -2430, -4600, width, height, null);
        }
    }

    public BufferedImage getBackground() {
        return background;
    }
}