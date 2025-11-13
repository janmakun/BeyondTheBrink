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
    private final int totalResources = 263;;
    private volatile int loadedResources = 0;

    private int spriteSize = 150;
    private int attackSpriteSize = 150;
    private int skillOneSize = 75;
    private int swordWalkSize = 75;
    private int UISize = 120;

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

        // Calculate total resources (added UI icons)

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
        loadNPCAndChestAssets();
        loadUIIcons(); // New method for UI icons

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
        blueSwordUp[2] = loadImageOptimized("res/BlueSwordWalking/pixil-frame-0 - 2025-11-11T145247.632.png", swordWalkSize, swordWalkSize);
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
        BufferedImage[] skillQ_down = new BufferedImage[6];
        skillQ_down[0] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_down[1] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_down[2] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_down[3] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft3-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_down[4] = skillQ_down[2];
        skillQ_down[5] = skillQ_down[1];
        spriteArrays.put("skillQ_down", skillQ_down);

        BufferedImage[] skillQ_up = new BufferedImage[6];
        skillQ_up[0] = loadImageOptimized("res/Skills/Blue/First Skill/blueatt0-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[1] = loadImageOptimized("res/Skills/Blue/First Skill/pixil-frame-0 - 2025-11-11T235413.889.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[2] = loadImageOptimized("res/Skills/Blue/First Skill/blueatt2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[3] = loadImageOptimized("res/Skills/Blue/First Skill/pixil-frame-0 - 2025-11-11T235442.509.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_up[4] = skillQ_up[2];
        skillQ_up[5] = skillQ_up[1];
        spriteArrays.put("skillQ_up", skillQ_up);

        BufferedImage[] skillQ_left = new BufferedImage[6];
        skillQ_left[0] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_left[1] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_left[2] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_left[3] = loadImageOptimized("res/Skills/Blue/First Skill/blueattleft3-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_left[4] = skillQ_left[2];
        skillQ_left[5] = skillQ_left[1];
        spriteArrays.put("skillQ_left", skillQ_left);

        BufferedImage[] skillQ_right = new BufferedImage[6];
        skillQ_right[0] = loadImageOptimized("res/Skills/Blue/First Skill/blueatt0-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_right[1] = loadImageOptimized("res/Skills/Blue/First Skill/pixil-frame-0 - 2025-11-11T235413.889.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_right[2] = loadImageOptimized("res/Skills/Blue/First Skill/blueatt2-export.png", spriteSize, spriteSize);
        loadedResources++;
        skillQ_right[3] = loadImageOptimized("res/Skills/Blue/First Skill/pixil-frame-0 - 2025-11-11T235442.509.png", spriteSize, spriteSize);
        skillQ_right[4] = skillQ_right[2];
        skillQ_right[5] = skillQ_right[1];
        spriteArrays.put("skillQ_right", skillQ_right);

//         Skill E: Second skill (4 frames each direction)
        BufferedImage[] skillE_down = new BufferedImage[12];
        skillE_down[0] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB1.png", 600, 600);
        loadedResources++;
        skillE_down[1] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB2.png", 600, 600);
        loadedResources++;
        skillE_down[2] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB3.png", 600, 600);
        loadedResources++;
        skillE_down[3] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB4.png", 600, 600);
        loadedResources++;
        skillE_down[4] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB5.png", 600, 600);
        loadedResources++;
        skillE_down[5] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB6.png", 600, 600);
        loadedResources++;
        skillE_down[6] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB7.png", 600, 600);
        loadedResources++;
        skillE_down[7] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB8.png", 600, 600);
        loadedResources++;
        skillE_down[8] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB9.png", 600, 600);
        loadedResources++;
        skillE_down[9] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB10.png", 600, 600);
        loadedResources++;
        skillE_down[10] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB11.png", 600, 600);
        loadedResources++;
        skillE_down[11] = skillE_down[10];
        spriteArrays.put("skillE_down", skillE_down);

        BufferedImage[] skillE_up = new BufferedImage[12];
        skillE_up[0] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB1.png", 600, 600);
        loadedResources++;
        skillE_up[1] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB2.png", 600, 600);
        loadedResources++;
        skillE_up[2] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB3.png", 600, 600);
        loadedResources++;
        skillE_up[3] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB4.png", 600, 600);
        loadedResources++;
        skillE_up[4] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB5.png", 600, 600);
        loadedResources++;
        skillE_up[5] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB6.png", 600, 600);
        loadedResources++;
        skillE_up[6] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB7.png", 600, 600);
        loadedResources++;
        skillE_up[7] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB8.png", 600, 600);
        loadedResources++;
        skillE_up[8] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB9.png", 600, 600);
        loadedResources++;
        skillE_up[9] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB10.png", 600, 600);
        loadedResources++;
        skillE_up[10] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB11.png", 600, 600);
        loadedResources++;
        skillE_up[11] = skillE_up[10];
        spriteArrays.put("skillE_up", skillE_up);

        BufferedImage[] skillE_left = new BufferedImage[12];
        skillE_left[0] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB1.png", 600, 600);
        loadedResources++;
        skillE_left[1] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB2.png", 600, 600);
        loadedResources++;
        skillE_left[2] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB3.png", 600, 600);
        loadedResources++;
        skillE_left[3] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB4.png", 600, 600);
        loadedResources++;
        skillE_left[4] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB5.png", 600, 600);
        loadedResources++;
        skillE_left[5] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB6.png", 600, 600);
        loadedResources++;
        skillE_left[6] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB7.png", 600, 600);
        loadedResources++;
        skillE_left[7] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB8.png", 600, 600);
        loadedResources++;
        skillE_left[8] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB9.png", 600, 600);
        loadedResources++;
        skillE_left[9] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB10.png", 600, 600);
        loadedResources++;
        skillE_left[10] = loadImageOptimized("res/Skills/Blue/Second Skill/Left2ndSkillB11.png", 600, 600);
        loadedResources++;
        skillE_left[11] = skillE_left[10];
        spriteArrays.put("skillE_left", skillE_left);

        BufferedImage[] skillE_right = new BufferedImage[12];
        skillE_right[0] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB1.png", 600, 600);
        loadedResources++;
        skillE_right[1] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB2.png", 600, 600);
        loadedResources++;
        skillE_right[2] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB3.png", 600, 600);
        loadedResources++;
        skillE_right[3] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB4.png", 600, 600);
        loadedResources++;
        skillE_right[4] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB5.png", 600, 600);
        loadedResources++;
        skillE_right[5] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB6.png", 600, 600);
        loadedResources++;
        skillE_right[6] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB7.png", 600, 600);
        loadedResources++;
        skillE_right[7] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB8.png", 600, 600);
        loadedResources++;
        skillE_right[8] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB9.png", 600, 600);
        loadedResources++;
        skillE_right[9] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB10.png", 600, 600);
        loadedResources++;
        skillE_right[10] = loadImageOptimized("res/Skills/Blue/Second Skill/Right2ndSkillB11.png", 600, 600);
        loadedResources++;
        skillE_right[11] = skillE_right[10];
        spriteArrays.put("skillE_right", skillE_right);

        // Skill R: Power Up (3 frames each direction)
        BufferedImage[] skillR_down = new BufferedImage[12];
        skillR_down[0] = loadImageOptimized("res/Skills/Red/Last Skill/powerup0 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[1] = skillR_down[0];
        skillR_down[2] = loadImageOptimized("res/Skills/Red/Last Skill/powerup1 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[3] = skillR_down[2];
        skillR_down[4] = loadImageOptimized("res/Skills/Red/Last Skill/powerup2 (2)-export.png", 200, 200);
        loadedResources++;
        skillR_down[5] = skillR_down[4];
        skillR_down[6] = loadImageOptimized("res/Skills/Red/Last Skill/powerup3 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_down[7] = skillR_down[6];
        skillR_down[8] = skillR_down[6];
        skillR_down[9] = skillR_down[4];
        skillR_down[10] = skillR_down[2];
        skillR_down[11] = skillR_down[0];
        spriteArrays.put("skillR_down", skillR_down);

        BufferedImage[] skillR_up = new BufferedImage[12];
        skillR_up[0] = loadImageOptimized("res/Skills/Red/Last Skill/backpu1 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_up[1] = skillR_up[0];
        skillR_up[2] = loadImageOptimized("res/Skills/Red/Last Skill/backpu2 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_up[3] =skillR_up[2];
        skillR_up[4] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3-export (1).png", 200, 200);
        loadedResources++;
        skillR_up[5] = skillR_up[4];
        skillR_up[6] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3 (2)-export.png", 200, 200);
        loadedResources++;
        skillR_up[7] = skillR_up[6];
        skillR_up[8] = skillR_up[6];
        skillR_up[9] = skillR_up[4];
        skillR_up[10] = skillR_up[2];
        skillR_up[11] = skillR_up[0];

        spriteArrays.put("skillR_up", skillR_up);

        BufferedImage[] skillR_left = new BufferedImage[12];
        skillR_left[0] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft0 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_left[1] = skillR_left[0];
        skillR_left[2] = skillR_left[0];
        skillR_left[3] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft1.png", 200, 200);
        loadedResources++;
        skillR_left[4] = skillR_left[3];
        skillR_left[5] = skillR_left[3];
        skillR_left[6] = loadImageOptimized("res/Skills/Blue/Last Skill/blueleft2 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_left[7] = skillR_left[6];
        skillR_left[8] = skillR_left[6];
        skillR_left[9] = skillR_left[6];
        skillR_left[10] = skillR_left[3];
        skillR_left[11] = skillR_left[0];
        spriteArrays.put("skillR_left", skillR_left);

        BufferedImage[] skillR_right = new BufferedImage[12];
        skillR_right[0] = loadImageOptimized("res/Skills/Blue/Last Skill/blueright0 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_right[1] = skillR_right[0];
        skillR_right[2] = skillR_right[0];
        skillR_right[3] = loadImageOptimized("res/Skills/Blue/Last Skill/blueright2 (1)-export.png", 200, 200);
        loadedResources++;
        skillR_right[4] = skillR_right[3];
        skillR_right[5] = skillR_right[3];
        skillR_right[6] = loadImageOptimized("res/Skills/Blue/Last Skill/blueright11-export.png", 200, 200);
        loadedResources++;
        skillR_right[7] = skillR_right[6];
        skillR_right[8] = skillR_right[6];
        skillR_right[9] = skillR_right[6];
        skillR_right[10] = skillR_right[3];
        skillR_right[11] = skillR_right[0];
        spriteArrays.put("skillR_right", skillR_right);

        System.out.println("✓ Skill sprites loaded! (" + loadedResources + "/" + totalResources + ")");


        BufferedImage[] redSkillQ_down = new BufferedImage[6];
        redSkillQ_down[0] = loadImageOptimized("res/Skills/Red/First Skill/redattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[1] = loadImageOptimized("res/Skills/Red/First Skill/redattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[2] = loadImageOptimized("res/Skills/Red/First Skill/redattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[3] = loadImageOptimized("res/Skills/Red/First Skill/pixil-frame-0 - 2025-11-12T002023.074.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_down[4] = redSkillQ_down[2];
        redSkillQ_down[5] = redSkillQ_down[1];
        spriteArrays.put("redSkillQ_down", redSkillQ_down);

        BufferedImage[] redSkillQ_up = new BufferedImage[6];
        redSkillQ_up[0] = loadImageOptimized("res/Skills/Red/First Skill/redatt0-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[1] = loadImageOptimized("res/Skills/Red/First Skill/pixil-frame-0 - 2025-11-12T002120.716.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[2] = loadImageOptimized("res/Skills/Red/First Skill/pixil-frame-0 - 2025-11-12T002205.798.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[3] = loadImageOptimized("res/Skills/Red/First Skill/pixil-frame-0 - 2025-11-12T002340.325.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_up[4] = redSkillQ_up[2];
        redSkillQ_up[5] = redSkillQ_up[1];
        spriteArrays.put("redSkillQ_up", redSkillQ_up);

        BufferedImage[] redSkillQ_left = new BufferedImage[6];
        redSkillQ_left[0] = loadImageOptimized("res/Skills/Red/First Skill/redattleft0-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_left[1] = loadImageOptimized("res/Skills/Red/First Skill/redattleft1-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_left[2] = loadImageOptimized("res/Skills/Red/First Skill/redattleft2-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_left[3] = loadImageOptimized("res/Skills/Red/First Skill/pixil-frame-0 - 2025-11-12T002023.074.png", spriteSize, spriteSize);
        redSkillQ_left[4] = redSkillQ_left[2];
        redSkillQ_left[5] = redSkillQ_left[1];
        spriteArrays.put("redSkillQ_left", redSkillQ_left);

        BufferedImage[] redSkillQ_right = new BufferedImage[6];
        redSkillQ_right[0] = loadImageOptimized("res/Skills/Red/First Skill/redatt0-export.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_right[1] = loadImageOptimized("res/Skills/Red/First Skill/pixil-frame-0 - 2025-11-12T002120.716.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_right[2] = loadImageOptimized("res/Skills/Red/First Skill/pixil-frame-0 - 2025-11-12T002205.798.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_right[3] = loadImageOptimized("res/Skills/Red/First Skill/pixil-frame-0 - 2025-11-12T002340.325.png", spriteSize, spriteSize);
        loadedResources++;
        redSkillQ_right[4] = redSkillQ_right[2];
        redSkillQ_right[5] = redSkillQ_right[1];
        spriteArrays.put("redSkillQ_right", redSkillQ_right);

// RED SWORD - Skill E (12 frames each direction)
        BufferedImage[] redSkillE_down = new BufferedImage[12];
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
        redSkillE_down[11] = redSkillE_down[10];
        spriteArrays.put("redSkillE_down", redSkillE_down);

        BufferedImage[] redSkillE_up = new BufferedImage[12];
        redSkillE_up[0] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill1.png", 500, 500);
        loadedResources++;
        redSkillE_up[1] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill2.png", 500, 500);
        loadedResources++;
        redSkillE_up[2] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill3.png", 500, 500);
        loadedResources++;
        redSkillE_up[3] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill4.png", 500, 500);
        loadedResources++;
        redSkillE_up[4] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill5.png", 500, 500);
        loadedResources++;
        redSkillE_up[5] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill6.png", 500, 500);
        loadedResources++;
        redSkillE_up[6] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill7.png", 500, 500);
        loadedResources++;
        redSkillE_up[7] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill8.png", 500, 500);
        loadedResources++;
        redSkillE_up[8] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill9.png", 500, 500);
        loadedResources++;
        redSkillE_up[9] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill10.png", 500, 500);
        loadedResources++;
        redSkillE_up[10] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill11.png", 500, 500);
        loadedResources++;
        redSkillE_up[11] = loadImageOptimized("res/Skills/Red/Second Skill/Back2ndSkill12.png", 500, 500);
        loadedResources++;
        spriteArrays.put("redSkillE_up", redSkillE_up);

        BufferedImage[] redSkillE_left = new BufferedImage[12];
        redSkillE_left[0] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill1.png", 600, 600);
        loadedResources++;
        redSkillE_left[1] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill2.png", 600, 600);
        loadedResources++;
        redSkillE_left[2] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill3.png", 600, 600);
        loadedResources++;
        redSkillE_left[3] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill4.png", 600, 600);
        loadedResources++;
        redSkillE_left[4] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill5.png", 600, 600);
        loadedResources++;
        redSkillE_left[5] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill6.png", 600, 600);
        loadedResources++;
        redSkillE_left[6] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill7.png", 600, 600);
        loadedResources++;
        redSkillE_left[7] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill8.png", 600, 600);
        loadedResources++;
        redSkillE_left[8] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill9.png", 600, 600);
        loadedResources++;
        redSkillE_left[9] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill10.png", 600, 600);
        loadedResources++;
        redSkillE_left[10] = loadImageOptimized("res/Skills/Red/Second Skill/Left2ndSkill11.png", 600, 600);
        loadedResources++;
        redSkillE_left[11] = redSkillE_left[10];
        spriteArrays.put("redSkillE_left", redSkillE_left);

        BufferedImage[] redSkillE_right = new BufferedImage[12];
        redSkillE_right[0] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill1.png", 600, 600);
        loadedResources++;
        redSkillE_right[1] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill2.png", 600, 600);
        loadedResources++;
        redSkillE_right[2] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill3.png", 600, 600);
        loadedResources++;
        redSkillE_right[3] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill4.png", 600, 600);
        loadedResources++;
        redSkillE_right[4] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill5.png", 600, 600);
        loadedResources++;
        redSkillE_right[5] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill6.png", 600, 600);
        loadedResources++;
        redSkillE_right[6] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill7.png", 600, 600);
        loadedResources++;
        redSkillE_right[7] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill8.png", 600, 600);
        loadedResources++;
        redSkillE_right[8] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill9.png", 600, 600);
        loadedResources++;
        redSkillE_right[9] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill10.png", 600, 600);
        loadedResources++;
        redSkillE_right[10] = loadImageOptimized("res/Skills/Red/Second Skill/Right2ndSkill11.png", 600, 600);
        loadedResources++;
        redSkillE_right[11] = redSkillE_right[10];
        spriteArrays.put("redSkillE_right", redSkillE_right);

        System.out.println("✓ Projectile skill sprites loaded (12 frames)!");

// Red Skill R (12 frames each direction - same as blue)
        BufferedImage[] redSkillR_down = new BufferedImage[12];
        redSkillR_down[0] = loadImageOptimized("res/Skills/Red/Last Skill/powerup0 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_down[1] = redSkillR_down[0];
        redSkillR_down[2] = loadImageOptimized("res/Skills/Red/Last Skill/powerup1 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_down[3] = redSkillR_down[2];
        redSkillR_down[4] = loadImageOptimized("res/Skills/Red/Last Skill/powerup2 (2)-export.png", 200, 200);
        loadedResources++;
        redSkillR_down[5] = redSkillR_down[4];
        redSkillR_down[6] = loadImageOptimized("res/Skills/Red/Last Skill/powerup3 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_down[7] = redSkillR_down[6];
        redSkillR_down[8] = redSkillR_down[6];
        redSkillR_down[9] = redSkillR_down[4];
        redSkillR_down[10] = redSkillR_down[2];
        redSkillR_down[11] = redSkillR_down[0];
        spriteArrays.put("redSkillR_down", redSkillR_down);

        BufferedImage[] redSkillR_up = new BufferedImage[12];
        redSkillR_up[0] = loadImageOptimized("res/Skills/Red/Last Skill/backpu1 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_up[1] = redSkillR_up[0];
        redSkillR_up[2] = loadImageOptimized("res/Skills/Red/Last Skill/backpu2 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_up[3] = redSkillR_up[2];
        redSkillR_up[4] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3-export (1).png", 200, 200);
        loadedResources++;
        redSkillR_up[5] = redSkillR_up[4];
        redSkillR_up[6] = loadImageOptimized("res/Skills/Red/Last Skill/backpu3 (2)-export.png", 200, 200);
        loadedResources++;
        redSkillR_up[7] = redSkillR_up[6];
        redSkillR_up[8] = redSkillR_up[6];
        redSkillR_up[9] = redSkillR_up[4];
        redSkillR_up[10] = redSkillR_up[2];
        redSkillR_up[11] = redSkillR_up[0];

        spriteArrays.put("redSkillR_up", redSkillR_up);

        BufferedImage[] redSkillR_left = new BufferedImage[12];
        redSkillR_left[0] = loadImageOptimized("res/Skills/Red/Last Skill/leftpu0 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_left[1] = redSkillR_left[0];
        redSkillR_left[2] = redSkillR_left[0];
        redSkillR_left[3] = loadImageOptimized("res/Skills/Red/Last Skill/leftpu1.png", 200, 200);
        loadedResources++;
        redSkillR_left[4] = redSkillR_left[3];
        redSkillR_left[5] = redSkillR_left[3];
        redSkillR_left[6] = loadImageOptimized("res/Skills/Red/Last Skill/leftpu2 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_left[7] = redSkillR_left[6];
        redSkillR_left[8] = redSkillR_left[6];
        redSkillR_left[9] = redSkillR_left[6];
        redSkillR_left[10] = redSkillR_left[3];
        redSkillR_left[11] = redSkillR_left[0];

        spriteArrays.put("redSkillR_left", redSkillR_left);

        BufferedImage[] redSkillR_right = new BufferedImage[12];
        redSkillR_right[0] = loadImageOptimized("res/Skills/Red/Last Skill/rightpu0 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_right[1] = redSkillR_right[0];
        redSkillR_right[2] = redSkillR_right[0];
        redSkillR_right[3] = loadImageOptimized("res/Skills/Red/Last Skill/rightpu1export.png", 200, 200);
        loadedResources++;
        redSkillR_right[4] = redSkillR_right[3];
        redSkillR_right[5] = redSkillR_right[3];
        redSkillR_right[6] = loadImageOptimized("res/Skills/Red/Last Skill/rightpu2 (1)-export.png", 200, 200);
        loadedResources++;
        redSkillR_right[7] = redSkillR_right[6];
        redSkillR_right[8] = redSkillR_right[6];
        redSkillR_right[9] = redSkillR_right[6];
        redSkillR_right[10] = redSkillR_right[3];
        redSkillR_right[11] = redSkillR_right[0];

        spriteArrays.put("redSkillR_right", redSkillR_right);

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
        System.out.println("⸏ Loading pause menu assets...");
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

        BufferedImage[] chestFrames = new BufferedImage[3];
        chestFrames[0] = loadImageOptimized("res/Chest/Chest32k1.png", 100, 100);
        chestFrames[1] = loadImageOptimized("res/Chest/Chest32k2.png", 100, 100);
        chestFrames[2] = loadImageOptimized("res/Chest/Copy of open chest-export.png", 100, 100);
        spriteArrays.put("chest", chestFrames);
        loadedResources += 3;

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

    private void loadUIIcons() {
        System.out.println("🎨 Loading UI icons...");

        // Blue sword UI icons (60x60 for consistency)
        images.put("blueSkillQIcon", loadImageOptimized("res/UIIcons/skills2Icon.png", UISize, UISize));
        loadedResources++;
        images.put("blueSkillEIcon", loadImageOptimized("res/UIIcons/eruptionSkillIconBLUE.png", UISize, UISize));
        loadedResources++;
        images.put("blueSkillRIcon", loadImageOptimized("res/UIIcons/BlueBuffSkillIcon.png", UISize, UISize));
        loadedResources++;
        images.put("blueSwordToggleIcon", loadImageOptimized("res/UIIcons/swordBlueIcon.png", UISize, UISize));
        loadedResources++;

        // Red sword UI icons
        images.put("redSkillQIcon", loadImageOptimized("res/UIIcons/redSkillIcon2.png", UISize, UISize));
        loadedResources++;
        images.put("redSkillEIcon", loadImageOptimized("res/UIIcons/eruptionskillIconRED.png", UISize, UISize));
        loadedResources++;
        images.put("redSkillRIcon", loadImageOptimized("res/UIIcons/buffSkillRed.png", UISize, UISize));
        loadedResources++;
        images.put("redSwordToggleIcon", loadImageOptimized("res/UIIcons/redSwordIcon.png", UISize, UISize));
        loadedResources++;

        System.out.println("✓ UI icons loaded!");
    }

    private BufferedImage loadImageOptimized(String path, int targetWidth, int targetHeight) {
        try {
            BufferedImage raw = ImageIO.read(new File(path));
            if (raw == null) {
                System.err.println("    ✗ Image is null: " + path);
                return createPlaceholderImage(targetWidth, targetHeight, Color.GRAY);
            }

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
            return createPlaceholderImage(targetWidth, targetHeight, Color.GRAY);
        }
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