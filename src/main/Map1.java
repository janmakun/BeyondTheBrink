package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Map1 {
    private BufferedImage background;


    public Map1(ResourceLoader resourceLoader) {
        try {
            background = ImageIO.read(new File("res/Map/DungeonWithPortalRock (2).png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Map1 image not found!");
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