package main;

import java.awt.*;

/**
 * Manages all character skills
 */
public class SkillManager {
    private Skill skillQ; // Sword rotation (4 frames)
    private ProjectileSkill skillE; // Projectile skill (12 frames)
    private Skill skillR; // Power up (3 frames)

    private CharacterLoad character;

    private SwordType currentSwordType = SwordType.NONE;
    private ResourceLoader resourceLoader;


    public SkillManager(CharacterLoad character, ResourceLoader resourceLoader) {
        this.character = character;
        this.resourceLoader = resourceLoader;

        // Skill Q: Sword Rotation - 6 frames, 8 frame delay, 180 frame cooldown (3 seconds at 60fps)
        skillQ = new Skill(150, 150, 8, 6, 180);
        skillQ.setFrames(
                resourceLoader.getSpriteArray("skillQ_up"),
                resourceLoader.getSpriteArray("skillQ_down"),
                resourceLoader.getSpriteArray("skillQ_left"),
                resourceLoader.getSpriteArray("skillQ_right")
        );

        // Skill E: Ranged skill - 11 frames, 8 frame delay, 240 frame cooldown (4 seconds)
        // Size: 500x500 (matches character size), Max range: 200 pixels
        skillE = new ProjectileSkill(600, 600, 8, 12, 240, 200);
        skillE.setFrames(
                resourceLoader.getSpriteArray("skillE_up"),
                resourceLoader.getSpriteArray("skillE_down"),
                resourceLoader.getSpriteArray("skillE_left"),
                resourceLoader.getSpriteArray("skillE_right")
        );

        // Skill R: Power Up - 12 frames, 10 frame delay, 60 frame cooldown (1 second)
        skillR = new Skill(150, 150, 6, 12, 60);
        skillR.setFrames(
                resourceLoader.getSpriteArray("skillR_up"),
                resourceLoader.getSpriteArray("skillR_down"),
                resourceLoader.getSpriteArray("skillR_left"),
                resourceLoader.getSpriteArray("skillR_right")
        );
    }

    public void setSwordType(SwordType swordType) {
        this.currentSwordType = swordType;

        // Update all skills with appropriate sprites
        if (swordType == SwordType.RED_SWORD) {
            skillQ.setFrames(
                    resourceLoader.getSpriteArray("redSkillQ_up"),
                    resourceLoader.getSpriteArray("redSkillQ_down"),
                    resourceLoader.getSpriteArray("redSkillQ_left"),
                    resourceLoader.getSpriteArray("redSkillQ_right")
            );

            skillE.setFrames(
                    resourceLoader.getSpriteArray("redSkillE_up"),
                    resourceLoader.getSpriteArray("redSkillE_down"),
                    resourceLoader.getSpriteArray("redSkillE_left"),
                    resourceLoader.getSpriteArray("redSkillE_right")
            );

            skillR.setFrames(
                    resourceLoader.getSpriteArray("redSkillR_up"),
                    resourceLoader.getSpriteArray("redSkillR_down"),
                    resourceLoader.getSpriteArray("redSkillR_left"),
                    resourceLoader.getSpriteArray("redSkillR_right")
            );
        } else if (swordType == SwordType.BLUE_SWORD) {
            // Keep blue sword skills (already loaded in constructor)
            skillQ.setFrames(
                    resourceLoader.getSpriteArray("skillQ_up"),
                    resourceLoader.getSpriteArray("skillQ_down"),
                    resourceLoader.getSpriteArray("skillQ_left"),
                    resourceLoader.getSpriteArray("skillQ_right")
            );

            skillE.setFrames(
                    resourceLoader.getSpriteArray("skillE_up"),
                    resourceLoader.getSpriteArray("skillE_down"),
                    resourceLoader.getSpriteArray("skillE_left"),
                    resourceLoader.getSpriteArray("skillE_right")
            );

            skillR.setFrames(
                    resourceLoader.getSpriteArray("skillR_up"),
                    resourceLoader.getSpriteArray("skillR_down"),
                    resourceLoader.getSpriteArray("skillR_left"),
                    resourceLoader.getSpriteArray("skillR_right")
            );
        }

        System.out.println("Skills updated for sword type: " + swordType);
    }

    public void activateSkillQ(String direction) {
        if (!skillQ.isOnCooldown() && !skillQ.isActive()) {
            skillQ.activate( character.getX(), character.getY(), direction);
            character.addSkillHitbox("skillQ", direction);

            System.out.println("Skill Q activated with direction: " + direction);
        }
    }

    public void activateSkillE(String direction) {
        if (!skillE.isOnCooldown() && !skillE.isActive()) {
            skillE.activate( character.getX(), character.getY(), direction);

            // ADD THIS LINE - Create damage hitbox
            character.addSkillHitbox("skillE", direction);

            System.out.println("Skill E activated with direction: " + direction);
        }
    }

    public void activateSkillR(String direction) {
        if (!skillR.isOnCooldown() && !skillR.isActive()) {
            skillR.activate( character.getX(), character.getY(), direction);

            // ADD THIS LINE - Create damage hitbox
            character.addSkillHitbox("skillR", direction);

            System.out.println("Skill R activated with direction: " + direction);
        }
    }

    public void update() {
        // All skills follow character position
        skillQ.update(character.getX(), character.getY());
        skillE.update(character.getX(), character.getY());
        skillR.update(character.getX(), character.getY());

    }

    public void draw(Graphics g, int cameraX, int cameraY) {
        skillQ.draw(g, cameraX, cameraY);
        skillE.draw(g, cameraX + 230, cameraY + 220);
        skillR.draw(g, cameraX + 5, cameraY + 25);
    }

    public boolean isAnySkillActive() {
        return skillQ.isActive() || skillE.isActive() || skillR.isActive();
    }

    public Skill getSkillQ() { return skillQ; }
    public ProjectileSkill getSkillE() { return (ProjectileSkill) skillE; }
    public Skill getSkillR() { return skillR; }
}