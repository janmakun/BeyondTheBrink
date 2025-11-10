package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {
    private Map<String, BufferedImage> images;
    private Map<String, BufferedImage[]> spriteArrays;
    private volatile boolean finished = false;
    private volatile int totalResources = 0;
    private volatile int loadedResources = 0;

    private int spriteSize = 150;
    private int attackSpriteSize = 150;
    private int skillOneSize = 75;
    private int swordWalkSize = 75;

    public ResourceLoader() {
        images = new HashMap<>();
        spriteArrays = new HashMap<>();
    }

    public void setSpriteSize(int size) {
        this.spriteSize = size;
    }

    public void setAttackSpriteSize(int size) {
        this.attackSpriteSize = size;
    }

    public void loadAllResources() {
        System.out.println("📄 Starting resource loading...");
        System.out.println("   Sprite size: " + spriteSize + "x" + spriteSize);
        System.out.println("   Attack sprite size: " + attackSpriteSize + "x" + attackSpriteSize);

        // Calculate total resources (added NPC, chest, sword selection assets)
        totalResources = 10 + 2 + 5 + 16 + 10; // Added 10 for new features

        System.gc();

        loadCharacterSprites();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();

        loadAttackSprites();
        loadSkillSprites();
        loadMapsOptimized();
        loadPauseMenuAssets();
        loadNPCAndChestAssets(); // New method

        System.out.println("✅ All resources loaded successfully!");
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public float getLoadingProgress() {
        if (totalResources == 0) return 0f;
        return (float) loadedResources / totalResources;
    }

    private void loadCharacterSprites() {
        System.out.println("👤 Loading character sprites...");

        BufferedImage[] downSprites = new BufferedImage[2];
        downSprites[0] = loadImageOptimized("res/Player/frontwalk2.png", spriteSize, spriteSize);
        loadedResources++;
        downSprites[1] = loadImageOptimized("res/Player/frontwalk3.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("down", downSprites);

        BufferedImage[] rightSprites = new BufferedImage[2];
        rightSprites[0] = loadImageOptimized("res/Player/RightWalk2.png", spriteSize, spriteSize);
        loadedResources++;
        rightSprites[1] = loadImageOptimized("res/Player/RightWalk3.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("left", rightSprites);

        BufferedImage[] upSprites = new BufferedImage[2];
        upSprites[0] = loadImageOptimized("res/Player/Back2.png", spriteSize, spriteSize);
        loadedResources++;
        upSprites[1] = loadImageOptimized("res/Player/Back3.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("up", upSprites);

        BufferedImage[] leftSprites = new BufferedImage[2];
        leftSprites[0] = loadImageOptimized("res/Player/LeftWalk2.png", spriteSize, spriteSize);
        loadedResources++;
        leftSprites[1] = loadImageOptimized("res/Player/LeftWalk3.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("right", leftSprites);

        images.put("upStay", loadImageOptimized("res/Player/frontwalk1.png", spriteSize, spriteSize));
        loadedResources++;
        images.put("downStay", loadImageOptimized("res/Player/Back1.png", spriteSize, spriteSize));
        loadedResources++;
        images.put("rightStay", loadImageOptimized("res/Player/LeftWalk1.png", spriteSize, spriteSize));
        loadedResources++;
        images.put("leftStay", loadImageOptimized("res/Player/RightWalk1.png", spriteSize, spriteSize));
        loadedResources++;

        System.out.println("✓ Character sprites loaded! (" + loadedResources + "/" + totalResources + ")");
    }

    private void loadAttackSprites() {
        System.out.println("⚔️ Loading attack sprites...");

        BufferedImage[] attackDownSprites = new BufferedImage[4];
        attackDownSprites[0] = loadImageOptimized("res/BlueSword/BLUEFrontattack1.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackDownSprites[1] = loadImageOptimized("res/BlueSword/BLUEFrontattack2-export.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackDownSprites[2] = loadImageOptimized("res/BlueSword/BLUEFrontattack3-export.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackDownSprites[3] = loadImageOptimized("res/BlueSword/BLUEFrontattack4.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        spriteArrays.put("attackDown", attackDownSprites);

        BufferedImage[] attackRightSprites = new BufferedImage[4];
        attackRightSprites[0] = loadImageOptimized("res/BlueSword/BlueRightAttack1.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackRightSprites[1] = loadImageOptimized("res/BlueSword/BlueRightAttack2.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackRightSprites[2] = loadImageOptimized("res/BlueSword/BlueRightAttack3.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackRightSprites[3] = loadImageOptimized("res/BlueSword/BlueRightAttack4.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        spriteArrays.put("attackRight", attackRightSprites);

        BufferedImage[] attackUpSprites = new BufferedImage[4];
        attackUpSprites[0] = loadImageOptimized("res/BlueSword/BLUEBackAttack1.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackUpSprites[1] = loadImageOptimized("res/BlueSword/BLUEBackAttack2.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackUpSprites[2] = loadImageOptimized("res/BlueSword/BLUEBackAttack3.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackUpSprites[3] = loadImageOptimized("res/BlueSword/BLUEBackAttack4.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        spriteArrays.put("attackUp", attackUpSprites);

        BufferedImage[] attackLeftSprites = new BufferedImage[4];
        attackLeftSprites[0] = loadImageOptimized("res/BlueSword/BLUELeftAttack1.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackLeftSprites[1] = loadImageOptimized("res/BlueSword/BLUELeftAttack2.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackLeftSprites[2] = loadImageOptimized("res/BlueSword/BLUELeftAttack3.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        attackLeftSprites[3] = loadImageOptimized("res/BlueSword/BLUELeftAttack4.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        spriteArrays.put("attackLeft", attackLeftSprites);

        // Blue sword walking sprites (3 frames per direction)
        BufferedImage[] blueSwordDown = new BufferedImage[3];
        blueSwordDown[0] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (57).png", swordWalkSize, swordWalkSize);
        blueSwordDown[1] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (66).png", swordWalkSize, swordWalkSize);
        blueSwordDown[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (68).png", swordWalkSize, swordWalkSize);
        spriteArrays.put("blueSwordWalk_down", blueSwordDown);

        BufferedImage[] blueSwordUp = new BufferedImage[3];
        blueSwordUp[0] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (64).png", swordWalkSize, swordWalkSize);
        blueSwordUp[1] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (65).png", swordWalkSize, swordWalkSize);
        blueSwordUp[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (67).png", swordWalkSize, swordWalkSize);
        spriteArrays.put("blueSwordWalk_up", blueSwordUp);

        BufferedImage[] blueSwordLeft = new BufferedImage[3];
        blueSwordLeft[0] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (63).png", swordWalkSize, swordWalkSize);
        blueSwordLeft[1] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (62).png", swordWalkSize, swordWalkSize);
        blueSwordLeft[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (61).png", swordWalkSize, swordWalkSize);
        spriteArrays.put("blueSwordWalk_right", blueSwordLeft);

        BufferedImage[] blueSwordRight = new BufferedImage[3];
        blueSwordRight[0] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (58).png", swordWalkSize, swordWalkSize);
        blueSwordRight[1] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (59).png", swordWalkSize, swordWalkSize);
        blueSwordRight[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (60).png", swordWalkSize, swordWalkSize);
        spriteArrays.put("blueSwordWalk_left", blueSwordRight);

        System.out.println("✓ Attack sprites loaded! (" + loadedResources + "/" + totalResources + ")");
    }

    private void loadSkillSprites() {
        System.out.println("✨ Loading skill sprites...");

        // Skill Q: Sword Rotation (4 frames each direction)
        BufferedImage[] skillQ_down = new BufferedImage[4];
        skillQ_down[0] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_down[1] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_down[2] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_down[3] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft3-export.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("skillQ_down", skillQ_down);

        BufferedImage[] skillQ_up = new BufferedImage[6];
        skillQ_up[0] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[1] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[2] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[3] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft3-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[4] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[5] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("skillQ_up", skillQ_up);

        BufferedImage[] skillQ_left = new BufferedImage[4];
        skillQ_left[0] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_left[1] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_left[2] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_left[3] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft3-export.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("skillQ_left", skillQ_left);

        BufferedImage[] skillQ_right = new BufferedImage[4];
        skillQ_right[0] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_right[1] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_right[2] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_right[3] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft3-export.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("skillQ_right", skillQ_right);

        // Skill E: Second skill (4 frames each direction)
        BufferedImage[] skillE_down = new BufferedImage[4];
        skillE_down[0] = loadImageOptimized("res/Skills/E/Down1.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_down[1] = loadImageOptimized("res/Skills/E/Down2.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_down[2] = loadImageOptimized("res/Skills/E/Down3.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_down[3] = loadImageOptimized("res/Skills/E/Down4.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("skillE_down", skillE_down);

        BufferedImage[] skillE_up = new BufferedImage[4];
        skillE_up[0] = loadImageOptimized("res/Skills/E/Up1.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_up[1] = loadImageOptimized("res/Skills/E/Up2.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_up[2] = loadImageOptimized("res/Skills/E/Up3.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_up[3] = loadImageOptimized("res/Skills/E/Up4.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("skillE_up", skillE_up);

        BufferedImage[] skillE_left = new BufferedImage[4];
        skillE_left[0] = loadImageOptimized("res/Skills/E/Left1.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_left[1] = loadImageOptimized("res/Skills/E/Left2.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_left[2] = loadImageOptimized("res/Skills/E/Left3.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_left[3] = loadImageOptimized("res/Skills/E/Left4.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("skillE_left", skillE_left);

        BufferedImage[] skillE_right = new BufferedImage[4];
        skillE_right[0] = loadImageOptimized("res/Skills/E/Right1.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_right[1] = loadImageOptimized("res/Skills/E/Right2.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_right[2] = loadImageOptimized("res/Skills/E/Right3.png", spriteSize, spriteSize);
        loadedResources++;
        skillE_right[3] = loadImageOptimized("res/Skills/E/Right4.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("skillE_right", skillE_right);

        // Skill R: Power Up (3 frames each direction)
        BufferedImage[] skillR_down = new BufferedImage[12];
        skillR_down[0] = loadImageOptimized("res/Skills/Red/Last Skill/powerup0 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[1] = loadImageOptimized("res/Skills/Red/Last Skill/powerup0 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[2] = loadImageOptimized("res/Skills/Red/Last Skill/powerup1 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[3] = loadImageOptimized("res/Skills/Red/Last Skill/powerup1 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[4] = loadImageOptimized("res/Skills/Red/Last Skill/powerup2 (2)-export.png", 200, 200);
        loadedResources++;
        skillR_down[5] = loadImageOptimized("res/Skills/Red/Last Skill/powerup2 (2)-export.png", 200, 200);
        loadedResources++;
        skillR_down[6] = loadImageOptimized("res/Skills/Red/Last Skill/powerup3 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[7] = loadImageOptimized("res/Skills/Red/Last Skill/powerup3 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[8] = loadImageOptimized("res/Skills/Red/Last Skill/powerup3 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[9] = loadImageOptimized("res/Skills/Red/Last Skill/powerup2 (2)-export.png", 200, 200);
        loadedResources++;
        skillR_down[10] = loadImageOptimized("res/Skills/Red/Last Skill/powerup1 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[11] = loadImageOptimized("res/Skills/Red/Last Skill/powerup0 (1)-export.png", 200, 200);
        loadedResources++;
        spriteArrays.put("skillR_down", skillR_down);

        BufferedImage[] skillR_up = new BufferedImage[12];
        skillR_up[0] = loadImageOptimized("res/Skills/Red/Last Skill/backpu1 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_up[1] = loadImageOptimized("res/Skills/Red/Last Skill/backpu1 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_up[2] = loadImageOptimized("res/Skills/Red/Last Skill/backpu2 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_up[3] = loadImageOptimized("res/Skills/Red/Last Skill/backpu2 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_up[4] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3-export.png", 200, 200);
        loadedResources++;
        skillR_up[5] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3-export.png", 200, 200);
        loadedResources++;
        skillR_up[6] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3 (2)-export.png", 200, 200);
        loadedResources++;
        skillR_up[7] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3 (2)-export.png", 200, 200);
        loadedResources++;
        skillR_up[8] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3 (2)-export.png", 200, 200);
        loadedResources++;
        skillR_up[9] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3-export.png", 200, 200);
        loadedResources++;
        skillR_up[10] = loadImageOptimized("res/Skills/Red/Last Skill/backpu2 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_up[11] = loadImageOptimized("res/Skills/Red/Last Skill/backpu1 (1)-export.png", 200, 200);
        loadedResources++;

        spriteArrays.put("skillR_up", skillR_up);

        BufferedImage[] skillR_left = new BufferedImage[3];
        skillR_left[0] = loadImageOptimized("res/Skills/R/Left1.png", 200, 200);
        loadedResources++;
        skillR_left[1] = loadImageOptimized("res/Skills/R/Left2.png", 200, 200);
        loadedResources++;
        skillR_left[2] = loadImageOptimized("res/Skills/R/Left3.png", 200, 200);
        loadedResources++;
        spriteArrays.put("skillR_left", skillR_left);

        BufferedImage[] skillR_right = new BufferedImage[6];
        skillR_right[0] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft0 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_right[1] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft1.png", 200, 200);
        loadedResources++;
        skillR_right[2] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft2 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_right[3] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft2 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_right[4] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft1.png", 200, 200);
        loadedResources++;
        skillR_right[5] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft0 (1)-export.png", 200, 200);
        loadedResources++;
        spriteArrays.put("skillR_right", skillR_right);

        System.out.println("✓ Skill sprites loaded! (" + loadedResources + "/" + totalResources + ")");
    }

    private void loadMapsOptimized() {
        System.out.println("🗺️ Loading maps...");
        try {
            ImageIO.setUseCache(false);
            images.put("map1", loadLargeImageOptimized("res/Map/DungeonWithPortalRock (2).png"));
            loadedResources++;
            System.gc();
            Thread.sleep(100);
            images.put("map2", loadLargeImageOptimized("res/Map/forestWithPortalRock.png"));
            loadedResources++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✓ Maps loaded!");
    }

    private void loadPauseMenuAssets() {
        System.out.println("⏸️ Loading pause menu assets...");
        images.put("pauseBackground", loadImageOptimized("res/Pause/GamePausedBgWithFont&PauseBtn4.png", 500, 720));
        loadedResources++;
        images.put("continueButton", loadImageOptimized("res/Pause/continueButton.png", 400, 300));
        loadedResources++;
        images.put("exitButton", loadImageOptimized("res/Pause/ExitButton.png", 400, 300));
        loadedResources++;
        images.put("pauseIcon", loadImageOptimized("res/Pause/PauseButton.png", 60, 60));
        loadedResources++;
        System.out.println("✓ Pause menu assets loaded!");
    }

    private void loadNPCAndChestAssets() {
        System.out.println("🎮 Loading NPC and chest assets...");

        // Load NPC sprite (blue rogue) - use your actual sprite
        images.put("npcBlueRogue",  loadImageOptimized("res/NPC/FinalBossDungeon-export.png", 150, 150));
        loadedResources++;

        // Load chest frames
        BufferedImage[] chestFrames = new BufferedImage[3];
        chestFrames[0] = loadImageOptimized("res/Chest/Chest32k1.png", 100, 100);
        chestFrames[1] = loadImageOptimized("res/Chest/Chest32k2.png", 100, 100);
        chestFrames[2] = loadImageOptimized("res/Chest/Copy of open chest-export.png", 100, 100);
        spriteArrays.put("chest", chestFrames);
        loadedResources += 3;

        // Load sword selection assets - REPLACE THESE WITH YOUR ACTUAL IMAGES
        // Background: 850x700
        images.put("swordSelectionBg", loadImageOptimized("res/ChoosingSword/chooseBGwithdetailsFnal.png", 850, 700));
        loadedResources++;

        // Sword icons (optional - can be null if already on background)
        images.put("blueSwordIcon", null); // Set to null if icons are on background
        loadedResources++;
        images.put("redSwordIcon", null); // Set to null if icons are on background
        loadedResources++;

        // Buttons: 272x102 each (can be null if buttons are on background)
        images.put("blueButton", loadImageOptimized("res/ChoosingSword/ChooseButtonBlue.png", 272, 102)); // Set to null if button is on background
        loadedResources++;
        images.put("redButton", loadImageOptimized("res/ChoosingSword/chooseButtonRed.png", 272, 102)); // Set to null if button is on background
        loadedResources++;

        System.out.println("✓ NPC and chest assets loaded!");
    }

    private BufferedImage createPlaceholderImage(int width, int height, Color color) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(0, 0, width - 1, height - 1);
        g2.dispose();
        return img;
    }

    private BufferedImage createPlaceholderChest(int width, int height, boolean open) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(new Color(139, 69, 19)); // Brown
        g2.fillRect(10, 30, width - 20, height - 30);
        if (open) {
            g2.setColor(new Color(255, 215, 0)); // Gold
            g2.fillOval(width / 2 - 15, height / 2 - 15, 30, 30);
        }
        g2.dispose();
        return img;
    }

    private BufferedImage loadImageOptimized(String path, int targetWidth, int targetHeight) {
        try {
            BufferedImage raw = ImageIO.read(new File(path));
            if (raw == null) return null;

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

    private BufferedImage loadLargeImageOptimized(String path) {
        try {
            File file = new File(path);
            long fileSizeMB = file.length() / (1024 * 1024);
            System.out.println("      File size: " + fileSizeMB + "MB");

            BufferedImage raw = ImageIO.read(file);
            if (raw == null) return null;

            System.out.println("      ✓ Loaded raw image");
            System.out.println("      Original size: " + raw.getWidth() + "x" + raw.getHeight());

            int targetWidth = raw.getWidth() / 2;
            int targetHeight = raw.getHeight() / 2;

            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

            BufferedImage scaled = gc.createCompatibleImage(
                    targetWidth, targetHeight, Transparency.OPAQUE);

            Graphics2D g2 = scaled.createGraphics();
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
        }
    }

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

    public int getSpriteSize() {
        return spriteSize;
    }

    public int getAttackSpriteSize() {
        return attackSpriteSize;
    }

    public int getSkillSpriteSize() {
        return skillOneSize;
    }
    public int getSwordWalkSize(){
        return swordWalkSize;
    }
}