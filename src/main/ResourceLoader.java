package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ResourceLoader - Preloads game resources with memory optimization and progress tracking
 */
public class ResourceLoader {

    private Map<String, BufferedImage> images;
    private Map<String, BufferedImage[]> spriteArrays;
    private volatile boolean finished = false;
    private volatile int totalResources = 0;
    private volatile int loadedResources = 0;

    public ResourceLoader() {
        images = new HashMap<>();
        spriteArrays = new HashMap<>();
    }

    /**
     * Load all game resources with progress tracking
     */
    public void loadAllResources() {
        System.out.println("🔄 Starting resource loading...");

        // Calculate total resources to load
        totalResources = 10 + 2; // 10 character sprites + 2 maps

        // Force garbage collection before loading
        System.gc();

        loadCharacterSprites();

        // Small delay and GC between major loads
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();

        loadMapsOptimized();

        System.out.println("✅ All resources loaded successfully!");
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    /**
     * Get loading progress as a percentage (0.0 to 1.0)
     */
    public float getLoadingProgress() {
        if (totalResources == 0) return 0f;
        return (float) loadedResources / totalResources;
    }

    private void loadCharacterSprites() {
        System.out.println("👤 Loading character sprites...");

        // Down/Front sprites
        BufferedImage[] downSprites = new BufferedImage[2];
        downSprites[0] = loadImageOptimized("res/Player/Fronwalk1.png", 64, 64);
        loadedResources++;
        downSprites[1] = loadImageOptimized("res/Player/Frontwalk2.png", 64, 64);
        loadedResources++;
        spriteArrays.put("down", downSprites);

        // Right sprites
        BufferedImage[] rightSprites = new BufferedImage[2];
        rightSprites[0] = loadImageOptimized("res/Player/RightWalk1.png", 64, 64);
        loadedResources++;
        rightSprites[1] = loadImageOptimized("res/Player/RightWalk2.png", 64, 64);
        loadedResources++;
        spriteArrays.put("right", rightSprites);

        // Up/Back sprites
        BufferedImage[] upSprites = new BufferedImage[2];
        upSprites[0] = loadImageOptimized("res/Player/BackWalk1.png", 64, 64);
        loadedResources++;
        upSprites[1] = loadImageOptimized("res/Player/Backwalk2.png", 64, 64);
        loadedResources++;
        spriteArrays.put("up", upSprites);

        // Left sprites
        BufferedImage[] leftSprites = new BufferedImage[2];
        leftSprites[0] = loadImageOptimized("res/Player/LeftWalk1.png", 64, 64);
        loadedResources++;
        leftSprites[1] = loadImageOptimized("res/Player/LefWalk2.png", 64, 64);
        loadedResources++;
        spriteArrays.put("left", leftSprites);

        // Standing sprites
        images.put("upStay", loadImageOptimized("res/Player/Back.png", 64, 64));
        loadedResources++;
        images.put("downStay", loadImageOptimized("res/Player/Front.png", 64, 64));
        loadedResources++;
        images.put("leftStay", loadImageOptimized("res/Player/LeftSide.png", 64, 64));
        loadedResources++;
        images.put("rightStay", loadImageOptimized("res/Player/SideRight.png", 64, 64));
        loadedResources++;

        System.out.println("✓ Character sprites loaded! (" + loadedResources + "/" + totalResources + ")");
    }

    private void loadMapsOptimized() {
        System.out.println("🗺️ Loading maps (this may take a moment)...");

        // Load maps with ImageIO hints for better memory management
        try {
            // Disable ImageIO cache to save memory
            ImageIO.setUseCache(false);

            // Load map 1
            System.out.println("  📍 Loading Map 1...");
            images.put("map1", loadLargeImageOptimized("res/Map/5000kMap.png"));
            loadedResources++;
            System.out.println("  ✓ Map 1 loaded! (" + loadedResources + "/" + totalResources + ")");

            // Force GC between large image loads
            System.gc();
            Thread.sleep(100);

            // Load map 2
            System.out.println("  📍 Loading Map 2...");
            images.put("map2", loadLargeImageOptimized("res/Map/forest5k.png"));
            loadedResources++;
            System.out.println("  ✓ Map 2 loaded! (" + loadedResources + "/" + totalResources + ")");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("✓ Maps loaded!");
    }

    /**
     * Load and optimize small images (sprites)
     */
    private BufferedImage loadImageOptimized(String path, int targetWidth, int targetHeight) {
        try {
            BufferedImage raw = ImageIO.read(new File(path));
            if (raw == null) return null;

            // Create hardware-accelerated compatible image
            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

            BufferedImage optimized = gc.createCompatibleImage(
                    targetWidth, targetHeight, Transparency.TRANSLUCENT);

            Graphics2D g2 = optimized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(raw, 0, 0, targetWidth, targetHeight, null);
            g2.dispose();
            raw.flush();

            System.out.println("    ✓ Loaded: " + path);
            return optimized;
        } catch (IOException e) {
            System.err.println("    ✗ Failed to load: " + path);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Load and optimize large images (maps)
     */
    private BufferedImage loadLargeImageOptimized(String path) {
        try {
            File file = new File(path);
            long fileSizeMB = file.length() / (1024 * 1024);
            System.out.println("      File size: " + fileSizeMB + "MB");

            BufferedImage raw = ImageIO.read(file);
            if (raw == null) return null;

            System.out.println("      ✓ Loaded raw image");
            System.out.println("      Original size: " + raw.getWidth() + "x" + raw.getHeight());

            // Scale down large maps to save memory
            int targetWidth = raw.getWidth() / 2;
            int targetHeight = raw.getHeight() / 2;

            // Create hardware-accelerated compatible image
            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

            BufferedImage scaled = gc.createCompatibleImage(
                    targetWidth, targetHeight, Transparency.OPAQUE);

            Graphics2D g2 = scaled.createGraphics();
            // Use faster rendering for large images
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_SPEED);

            g2.drawImage(raw, 0, 0, targetWidth, targetHeight, null);
            g2.dispose();
            raw.flush();

            System.out.println("      Scaled size: " + scaled.getWidth() + "x" + scaled.getHeight());
            return scaled;

        } catch (IOException e) {
            System.err.println("      ✗ Failed to load: " + path);
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e) {
            System.err.println("      ✗ OUT OF MEMORY loading: " + path);
            System.err.println("      Try increasing Java heap size with: -Xmx2048m or -Xmx4096m");
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