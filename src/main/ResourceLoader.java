package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ResourceLoader - Preloads game resources with memory optimization
 */
public class ResourceLoader {

    private Map<String, BufferedImage> images;
    private Map<String, BufferedImage[]> spriteArrays;
    public boolean finished;

    public ResourceLoader() {
        images = new HashMap<>();
        spriteArrays = new HashMap<>();
    }

    /**
     * Load all game resources with progress tracking
     */
    public void loadAllResources() {
        System.out.println("Starting resource loading...");

        // Force garbage collection before loading
        System.gc();

        loadCharacterSprites();

        // Small delay and GC between major loads
        System.gc();

        loadMapsOptimized();

        System.out.println("All resources loaded successfully!");
        finished = true;
    }
    public boolean isFinished() {
        return finished;
    }

    private void loadCharacterSprites() {
        System.out.println("Loading character sprites...");

        // Down/Front sprites
        BufferedImage[] downSprites = new BufferedImage[2];
        downSprites[0] = loadImage("res/Player/Fronwalk1.png");
        downSprites[1] = loadImage("res/Player/Frontwalk2.png");
        spriteArrays.put("down", downSprites);

        // Right sprites
        BufferedImage[] rightSprites = new BufferedImage[2];
        rightSprites[0] = loadImage("res/Player/RightWalk1.png");
        rightSprites[1] = loadImage("res/Player/RightWalk2.png");
        spriteArrays.put("right", rightSprites);

        // Up/Back sprites
        BufferedImage[] upSprites = new BufferedImage[2];
        upSprites[0] = loadImage("res/Player/BackWalk1.png");
        upSprites[1] = loadImage("res/Player/Backwalk2.png");
        spriteArrays.put("up", upSprites);

        // Left sprites
        BufferedImage[] leftSprites = new BufferedImage[2];
        leftSprites[0] = loadImage("res/Player/LeftWalk1.png");
        leftSprites[1] = loadImage("res/Player/LefWalk2.png");
        spriteArrays.put("left", leftSprites);

        // Standing sprites
        images.put("upStay", loadImage("res/Player/Back.png"));
        images.put("downStay", loadImage("res/Player/Front.png"));
        images.put("leftStay", loadImage("res/Player/LeftSide.png"));
        images.put("rightStay", loadImage("res/Player/SideRight.png"));

        System.out.println("Character sprites loaded!");
    }

    private void loadMapsOptimized() {
        System.out.println("Loading maps (this may take a moment)...");

        // Load maps with ImageIO hints for better memory management
        try {
            // Disable ImageIO cache to save memory
            ImageIO.setUseCache(false);

            // Load map 1
            System.out.println("  Loading Map 1...");
            images.put("map1", loadImageOptimized("res/Map/5000kMap.png"));

            // Force GC between large image loads
            System.gc();
            Thread.sleep(100);

            // Load map 2
            System.out.println("  Loading Map 2...");
            images.put("map2", loadImageOptimized("res/Map/forest5k.png"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Maps loaded!");
    }

    private BufferedImage loadImage(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            System.out.println("  ✓ Loaded: " + path);
            return img;
        } catch (IOException e) {
            System.err.println("  ✗ Failed to load: " + path);
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage loadImageOptimized(String path) {
        try {
            File file = new File(path);
            long fileSizeMB = file.length() / (1024 * 1024);
            System.out.println("    File size: " + fileSizeMB + "MB");

            BufferedImage raw = ImageIO.read(file);
            if (raw != null) {
                System.out.println("    ✓ Loaded: " + path);
                System.out.println("    Original size: " + raw.getWidth() + "x" + raw.getHeight());

                // ✅ Scale down large maps to save memory (adjust scale as needed)
                int targetWidth = raw.getWidth() / 2;  // or /3 if your map is still too big
                int targetHeight = raw.getHeight() / 2;

                BufferedImage scaled = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = scaled.createGraphics();
                g2.drawImage(raw, 0, 0, targetWidth, targetHeight, null);
                g2.dispose();
                raw.flush(); // free original memory

                System.out.println("    Scaled size: " + scaled.getWidth() + "x" + scaled.getHeight());
                return scaled;
            }
            return null;
        } catch (IOException e) {
            System.err.println("    ✗ Failed to load: " + path);
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            System.err.println("    ✗ OUT OF MEMORY loading: " + path);
            System.err.println("    Try increasing Java heap size with: -Xmx2048m");
            throw e;
        }
    }

    // Getters for preloaded resources
    public BufferedImage getImage(String key) {
        return images.get(key);
    }

    public BufferedImage[] getSpriteArray(String key) {
        return spriteArrays.get(key);
    }

    public BufferedImage getSprite(String direction, int index) {
        BufferedImage[] sprites = spriteArrays.get(direction);
        if (sprites != null && index >= 0 && index < sprites.length) {
            return sprites[index];
        }
        return null;
    }
}