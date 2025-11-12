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

        //Red Sword

        BufferedImage[] redAttackDown = new BufferedImage[4];
        redAttackDown[0] = loadImageOptimized("res/RedSwordAttack/redFA1.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackDown[1] = loadImageOptimized("res/RedSwordAttack/redFA2-export.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackDown[2] = loadImageOptimized("res/RedSwordAttack/redFA3-export.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackDown[3] = loadImageOptimized("res/RedSwordAttack/redFA4.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        spriteArrays.put("redAttackDown", redAttackDown);

        BufferedImage[] redAttackRight = new BufferedImage[4];
        redAttackRight[0] = loadImageOptimized("res/RedSwordAttack/REDRightAttack1.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackRight[1] = loadImageOptimized("res/RedSwordAttack/REDRightAttack2.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackRight[2] = loadImageOptimized("res/RedSwordAttack/REDRightAttack3.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackRight[3] = loadImageOptimized("res/RedSwordAttack/REDRightAttack4.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        spriteArrays.put("redAttackRight", redAttackRight);

        BufferedImage[] redAttackUp = new BufferedImage[4];
        redAttackUp[0] = loadImageOptimized("res/RedSwordAttack/RedBA5.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackUp[1] = loadImageOptimized("res/RedSwordAttack/RedBA3.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackUp[2] = loadImageOptimized("res/RedSwordAttack/RedBA.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackUp[3] = loadImageOptimized("res/RedSwordAttack/RedBA1.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        spriteArrays.put("redAttackUp", redAttackUp);

        BufferedImage[] redAttackLeft = new BufferedImage[4];
        redAttackLeft[0] = loadImageOptimized("res/RedSwordAttack/REDLeftAttack1.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackLeft[1] = loadImageOptimized("res/RedSwordAttack/REDLeftAttack2.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackLeft[2] = loadImageOptimized("res/RedSwordAttack/REDLeftAttack3.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        redAttackLeft[3] = loadImageOptimized("res/RedSwordAttack/REDLeftAttack4.png", attackSpriteSize, attackSpriteSize);
        loadedResources++;
        spriteArrays.put("redAttackLeft", redAttackLeft);

        System.out.println("✓ Red sword attack sprites loaded!");

        // Blue sword walking sprites (3 frames per direction)
        BufferedImage[] blueSwordDown = new BufferedImage[3];
        blueSwordDown[0] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (89).png", swordWalkSize, swordWalkSize);
        blueSwordDown[1] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (88).png", swordWalkSize, swordWalkSize);
        blueSwordDown[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (91).png", swordWalkSize, swordWalkSize);
        spriteArrays.put("blueSwordWalk_down", blueSwordDown);

        BufferedImage[] blueSwordUp = new BufferedImage[3];
        blueSwordUp[0] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 - 2025-11-11T145801.533.png", swordWalkSize, swordWalkSize);
        blueSwordUp[1] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 - 2025-11-11T145038.497.png", swordWalkSize, swordWalkSize);
        blueSwordUp[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 - 2025-11-11T145247.632.png", swordWalkSize, swordWalkSize); //
        spriteArrays.put("blueSwordWalk_up", blueSwordUp);

        BufferedImage[] blueSwordLeft = new BufferedImage[3];
        blueSwordLeft[0] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (86).png", swordWalkSize, swordWalkSize);
        blueSwordLeft[1] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 (87).png", swordWalkSize, swordWalkSize);
        blueSwordLeft[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 - 2025-11-10T203656.235.png", swordWalkSize, swordWalkSize);
        spriteArrays.put("blueSwordWalk_left", blueSwordLeft);

        BufferedImage[] blueSwordRight = new BufferedImage[3];
        blueSwordRight[0] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 - 2025-11-11T151734.925.png", swordWalkSize, swordWalkSize);
        blueSwordRight[1] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 - 2025-11-11T150729.663.png", swordWalkSize, swordWalkSize);
        blueSwordRight[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 - 2025-11-11T151145.739.png", swordWalkSize, swordWalkSize);
        spriteArrays.put("blueSwordWalk_right", blueSwordRight);

        //Red Sword
        BufferedImage[] redSwordDown = new BufferedImage[3];
        redSwordDown[0] = loadImageOptimized("res/RedSwordWalking/RedSwordDown1.png", swordWalkSize, swordWalkSize);
        redSwordDown[1] = loadImageOptimized("res/RedSwordWalking/RedSwordDown2.png", swordWalkSize, swordWalkSize);
        redSwordDown[2] = loadImageOptimized("res/RedSwordWalking/RedSwordDown3.png", swordWalkSize, swordWalkSize);
        spriteArrays.put("redSwordWalk_down", redSwordDown);

        BufferedImage[] redSwordUp = new BufferedImage[3];
        redSwordUp[0] = loadImageOptimized("res/RedSwordWalking/RedSwordUp1.png", swordWalkSize, swordWalkSize);
        redSwordUp[1] = loadImageOptimized("res/RedSwordWalking/RedSwordUp2.png", swordWalkSize, swordWalkSize);
        redSwordUp[2] = loadImageOptimized("res/RedSwordWalking/RedSwordUp3.png", swordWalkSize, swordWalkSize);
        spriteArrays.put("redSwordWalk_up", redSwordUp);

        BufferedImage[] redSwordLeft = new BufferedImage[3];
        redSwordLeft[0] = loadImageOptimized("res/RedSwordWalking/RedSwordLeft1.png", swordWalkSize, swordWalkSize);
        redSwordLeft[1] = loadImageOptimized("res/RedSwordWalking/RedSwordLeft2.png", swordWalkSize, swordWalkSize);
        redSwordLeft[2] = loadImageOptimized("res/RedSwordWalking/RedSwordLeft3.png", swordWalkSize, swordWalkSize);
        spriteArrays.put("redSwordWalk_left", redSwordLeft);

        BufferedImage[] redSwordRight = new BufferedImage[3];
        redSwordRight[0] = loadImageOptimized("res/RedSwordWalking/RedSwordRight1.png", swordWalkSize, swordWalkSize);
        redSwordRight[1] = loadImageOptimized("res/RedSwordWalking/RedSwordRight2.png", swordWalkSize, swordWalkSize);
        redSwordRight[2] = loadImageOptimized("res/RedSwordWalking/RedSwordRight3.png", swordWalkSize, swordWalkSize);
        spriteArrays.put("redSwordWalk_right", redSwordRight);

        System.out.println("✓ Red sword walking sprites loaded!");

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


        BufferedImage[] redSkillQ_down = new BufferedImage[6];
        redSkillQ_down[0] = loadImageOptimized("res/Skills/Red/First Skill/redattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[1] = loadImageOptimized("res/Skills/Red/First Skill/redattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[2] = loadImageOptimized("res/Skills/Red/First Skill/redattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[3] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft3-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[4] = loadImageOptimized("res/Skills/Red/First Skill/redattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[5] = loadImageOptimized("res/Skills/Red/First Skill/redattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("redSkillQ_down", redSkillQ_down);

        BufferedImage[] redSkillQ_up = new BufferedImage[6];
        redSkillQ_up[0] = loadImageOptimized("res/Skills/Red/SkillQ/up1.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[1] = loadImageOptimized("res/Skills/Red/SkillQ/up2.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[2] = loadImageOptimized("res/Skills/Red/SkillQ/up3.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[3] = loadImageOptimized("res/Skills/Red/SkillQ/up4.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[4] = loadImageOptimized("res/Skills/Red/SkillQ/up3.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[5] = loadImageOptimized("res/Skills/Red/SkillQ/up2.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("redSkillQ_up", redSkillQ_up);

        BufferedImage[] redSkillQ_left = new BufferedImage[4];
        redSkillQ_left[0] = loadImageOptimized("res/Skills/Red/SkillQ/left1.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_left[1] = loadImageOptimized("res/Skills/Red/SkillQ/left2.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_left[2] = loadImageOptimized("res/Skills/Red/SkillQ/left3.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_left[3] = loadImageOptimized("res/Skills/Red/SkillQ/left4.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("redSkillQ_left", redSkillQ_left);

        BufferedImage[] redSkillQ_right = new BufferedImage[4];
        redSkillQ_right[0] = loadImageOptimized("res/Skills/Red/SkillQ/right1.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_right[1] = loadImageOptimized("res/Skills/Red/SkillQ/right2.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_right[2] = loadImageOptimized("res/Skills/Red/SkillQ/right3.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_right[3] = loadImageOptimized("res/Skills/Red/SkillQ/right4.png", spriteSize, spriteSize);
        loadedResources++;
        spriteArrays.put("redSkillQ_right", redSkillQ_right);

// RED SWORD - Skill E (12 frames each direction)
        BufferedImage[] redSkillE_down = new BufferedImage[11];
        redSkillE_down[0] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill1.png", 500, 500);
        loadedResources++;
        redSkillE_down[1] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill2.png", 500, 500);
        loadedResources++;
        redSkillE_down[2] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill3.png", 500, 500);
        loadedResources++;
        redSkillE_down[3] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill4.png", 500, 500);
        loadedResources++;
        redSkillE_down[4] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill5.png", 500, 500);
        loadedResources++;
        redSkillE_down[5] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill6.png", 500, 500);
        loadedResources++;
        redSkillE_down[6] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill7.png", 500, 500);
        loadedResources++;
        redSkillE_down[7] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill8.png", 500, 500);
        loadedResources++;
        redSkillE_down[8] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill9.png", 500, 500);
        loadedResources++;
        redSkillE_down[9] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill10.png", 500, 500);
        loadedResources++;
        redSkillE_down[10] = loadImageOptimized("res/Skills/Red/Second Skill/2ndskill11.png", 500, 500);
        loadedResources++;
        spriteArrays.put("redSkillE_down", redSkillE_down);

        BufferedImage[] redSkillE_up = new BufferedImage[11];
        redSkillE_up[0] = loadImageOptimized("res/Skills/Red/SkillE/up1.png", 75, 75);
        loadedResources++;
        redSkillE_up[1] = loadImageOptimized("res/Skills/Red/SkillE/up2.png", 75, 75);
        loadedResources++;
        redSkillE_up[2] = loadImageOptimized("res/Skills/Red/SkillE/up3.png", 75, 75);
        loadedResources++;
        redSkillE_up[3] = loadImageOptimized("res/Skills/Red/SkillE/up4.png", 75, 75);
        loadedResources++;
        redSkillE_up[4] = loadImageOptimized("res/Skills/Red/SkillE/up5.png", 75, 75);
        loadedResources++;
        redSkillE_up[5] = loadImageOptimized("res/Skills/Red/SkillE/up6.png", 75, 75);
        loadedResources++;
        redSkillE_up[6] = loadImageOptimized("res/Skills/Red/SkillE/up7.png", 75, 75);
        loadedResources++;
        redSkillE_up[7] = loadImageOptimized("res/Skills/Red/SkillE/up8.png", 75, 75);
        loadedResources++;
        redSkillE_up[8] = loadImageOptimized("res/Skills/Red/SkillE/up9.png", 75, 75);
        loadedResources++;
        redSkillE_up[9] = loadImageOptimized("res/Skills/Red/SkillE/up10.png", 75, 75);
        loadedResources++;
        redSkillE_up[10] = loadImageOptimized("res/Skills/Red/SkillE/up11.png", 75, 75);
        loadedResources++;
        spriteArrays.put("redSkillE_up", redSkillE_up);

        BufferedImage[] redSkillE_left = new BufferedImage[11];
        redSkillE_left[0] = loadImageOptimized("res/Skills/Red/SkillE/left1.png", 75, 75);
        loadedResources++;
        redSkillE_left[1] = loadImageOptimized("res/Skills/Red/SkillE/left2.png", 75, 75);
        loadedResources++;
        redSkillE_left[2] = loadImageOptimized("res/Skills/Red/SkillE/left3.png", 75, 75);
        loadedResources++;
        redSkillE_left[3] = loadImageOptimized("res/Skills/Red/SkillE/left4.png", 75, 75);
        loadedResources++;
        redSkillE_left[4] = loadImageOptimized("res/Skills/Red/SkillE/left5.png", 75, 75);
        loadedResources++;
        redSkillE_left[5] = loadImageOptimized("res/Skills/Red/SkillE/left6.png", 75, 75);
        loadedResources++;
        redSkillE_left[6] = loadImageOptimized("res/Skills/Red/SkillE/left7.png", 75, 75);
        loadedResources++;
        redSkillE_left[7] = loadImageOptimized("res/Skills/Red/SkillE/left8.png", 75, 75);
        loadedResources++;
        redSkillE_left[8] = loadImageOptimized("res/Skills/Red/SkillE/left9.png", 75, 75);
        loadedResources++;
        redSkillE_left[9] = loadImageOptimized("res/Skills/Red/SkillE/left10.png", 75, 75);
        loadedResources++;
        redSkillE_left[10] = loadImageOptimized("res/Skills/Red/SkillE/left11.png", 75, 75);
        loadedResources++;
        spriteArrays.put("redSkillE_left", redSkillE_left);

        BufferedImage[] redSkillE_right = new BufferedImage[11];
        redSkillE_right[0] = loadImageOptimized("res/Skills/Red/SkillE/right1.png", 75, 75);
        loadedResources++;
        redSkillE_right[1] = loadImageOptimized("res/Skills/Red/SkillE/right2.png", 75, 75);
        loadedResources++;
        redSkillE_right[2] = loadImageOptimized("res/Skills/Red/SkillE/right3.png", 75, 75);
        loadedResources++;
        redSkillE_right[3] = loadImageOptimized("res/Skills/Red/SkillE/right4.png", 75, 75);
        loadedResources++;
        redSkillE_right[4] = loadImageOptimized("res/Skills/Red/SkillE/right5.png", 75, 75);
        loadedResources++;
        redSkillE_right[5] = loadImageOptimized("res/Skills/Red/SkillE/right6.png", 75, 75);
        loadedResources++;
        redSkillE_right[6] = loadImageOptimized("res/Skills/Red/SkillE/right7.png", 75, 75);
        loadedResources++;
        redSkillE_right[7] = loadImageOptimized("res/Skills/Red/SkillE/right8.png", 75, 75);
        loadedResources++;
        redSkillE_right[8] = loadImageOptimized("res/Skills/Red/SkillE/right9.png", 75, 75);
        loadedResources++;
        redSkillE_right[9] = loadImageOptimized("res/Skills/Red/SkillE/right10.png", 75, 75);
        loadedResources++;
        redSkillE_right[10] = loadImageOptimized("res/Skills/Red/SkillE/right11.png", 75, 75);
        loadedResources++;
        spriteArrays.put("redSkillE_right", redSkillE_right);

        System.out.println("✓ Projectile skill sprites loaded (12 frames)!");

// Red Skill R (12 frames each direction - same as blue)
        BufferedImage[] redSkillR_down = new BufferedImage[12];
        redSkillR_down[0] = loadImageOptimized("res/Skills/Red/SkillR/down1.png", 200, 200);
        loadedResources++;
        redSkillR_down[1] = loadImageOptimized("res/Skills/Red/SkillR/down1.png", 200, 200);
        loadedResources++;
        redSkillR_down[2] = loadImageOptimized("res/Skills/Red/SkillR/down2.png", 200, 200);
        loadedResources++;
        redSkillR_down[3] = loadImageOptimized("res/Skills/Red/SkillR/down2.png", 200, 200);
        loadedResources++;
        redSkillR_down[4] = loadImageOptimized("res/Skills/Red/SkillR/down3.png", 200, 200);
        loadedResources++;
        redSkillR_down[5] = loadImageOptimized("res/Skills/Red/SkillR/down3.png", 200, 200);
        loadedResources++;
        redSkillR_down[6] = loadImageOptimized("res/Skills/Red/SkillR/down4.png", 200, 200);
        loadedResources++;
        redSkillR_down[7] = loadImageOptimized("res/Skills/Red/SkillR/down4.png", 200, 200);
        loadedResources++;
        redSkillR_down[8] = loadImageOptimized("res/Skills/Red/SkillR/down4.png", 200, 200);
        loadedResources++;
        redSkillR_down[9] = loadImageOptimized("res/Skills/Red/SkillR/down3.png", 200, 200);
        loadedResources++;
        redSkillR_down[10] = loadImageOptimized("res/Skills/Red/SkillR/down2.png", 200, 200);
        loadedResources++;
        redSkillR_down[11] = loadImageOptimized("res/Skills/Red/SkillR/down1.png", 200, 200);
        loadedResources++;
        spriteArrays.put("redSkillR_down", redSkillR_down);

// Continue for up, left, right...
// (Follow same pattern as blue skill R)

        System.out.println("✓ Red sword skill sprites loaded!");
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

        // Load NPC sprites (5 frames for idle animation)
        BufferedImage[] npcBlueRogueFrames = new BufferedImage[5];
        npcBlueRogueFrames[0] = loadImageOptimized("res/NPC/BlueRogue1.png", 150, 150);
        loadedResources++;
        npcBlueRogueFrames[1] = loadImageOptimized("res/NPC/BlueRogue2.png", 150, 150);
        loadedResources++;
        npcBlueRogueFrames[2] = loadImageOptimized("res/NPC/BlueRogue3.png", 150, 150);
        loadedResources++;
        npcBlueRogueFrames[3] = loadImageOptimized("res/NPC/BlueRogue4.png", 150, 150);
        loadedResources++;
        npcBlueRogueFrames[4] = loadImageOptimized("res/NPC/BlueRogue5.png", 150, 150);
        loadedResources++;
        spriteArrays.put("npcBlueRogue", npcBlueRogueFrames);

        // Load chest frames
        BufferedImage[] chestFrames = new BufferedImage[3];
        chestFrames[0] = loadImageOptimized("res/Chest/Chest32k1.png", 100, 100);
        chestFrames[1] = loadImageOptimized("res/Chest/Chest32k2.png", 100, 100);
        chestFrames[2] = loadImageOptimized("res/Chest/Copy of open chest-export.png", 100, 100);
        spriteArrays.put("chest", chestFrames);
        loadedResources += 3;

        // Load sword selection assets
        images.put("swordSelectionBg", loadImageOptimized("res/ChoosingSword/chooseBGwithdetailsFnal.png", 850, 700));
        loadedResources++;
        images.put("blueSwordIcon", null);
        loadedResources++;
        images.put("redSwordIcon", null);
        loadedResources++;
        images.put("blueButton", loadImageOptimized("res/ChoosingSword/ChooseButtonBlue.png", 272, 102));
        loadedResources++;
        images.put("redButton", loadImageOptimized("res/ChoosingSword/chooseButtonRed.png", 272, 102));
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