package main;

import java.awt.*;

/**
 * Manages all character skills
 */
public class SkillManager {
    private Skill skillQ; // Sword rotation (4 frames)
    private Skill skillE; // Second skill (3-4 frames)
    private Skill skillR; // Power up (3 frames)

    private CharacterLoad character;

    public SkillManager(CharacterLoad character, ResourceLoader resourceLoader) {
        this.character = character;

        // Skill Q: Sword Rotation - 4 frames, 8 frame delay, 180 frame cooldown (3 seconds at 60fps)
        skillQ = new Skill(150, 150, 8, 6, 180);
        skillQ.setFrames(
                resourceLoader.getSpriteArray("skillQ_up"),
                resourceLoader.getSpriteArray("skillQ_down"),
                resourceLoader.getSpriteArray("skillQ_left"),
                resourceLoader.getSpriteArray("skillQ_right")
        );

        // Skill E: Second skill - 4 frames, 10 frame delay, 240 frame cooldown (4 seconds)
        skillE = new Skill(150, 150, 10, 4, 240);
        skillE.setFrames(
                resourceLoader.getSpriteArray("skillE_up"),
                resourceLoader.getSpriteArray("skillE_down"),
                resourceLoader.getSpriteArray("skillE_left"),
                resourceLoader.getSpriteArray("skillE_right")
        );

        // Skill R: Power Up - 3 frames, 12 frame delay, 360 frame cooldown (6 seconds)
        skillR = new Skill(150, 150, 15, 12, 60);
        skillR.setFrames(
                resourceLoader.getSpriteArray("skillR_up"),
                resourceLoader.getSpriteArray("skillR_down"),
                resourceLoader.getSpriteArray("skillR_left"),
                resourceLoader.getSpriteArray("skillR_right")
        );
    }

    public void activateSkillQ(String direction) {
        if (!character.isAttacking()) {
            skillQ.activate(character.getX(), character.getY(), direction);
        }
    }

    public void activateSkillE(String direction) {
        if (!character.isAttacking()) {
            skillE.activate(character.getX(), character.getY(), direction);
        }
    }

    public void activateSkillR(String direction) {
        if (!character.isAttacking()) {
            skillR.activate(character.getX(), character.getY(), direction);
        }
    }

    public void update() {
        skillQ.update(character.getX(), character.getY());
        skillE.update(character.getX(), character.getY());
        skillR.update(character.getX() , character.getY());
    }

    public void draw(Graphics g, int cameraX, int cameraY) {
        skillQ.draw(g, cameraX, cameraY);
        skillE.draw(g, cameraX, cameraY);
        skillR.draw(g, cameraX + 5, cameraY + 25);
    }

    public boolean isAnySkillActive() {
        return skillQ.isActive() || skillE.isActive() || skillR.isActive();
    }

    public Skill getSkillQ() { return skillQ; }
    public Skill getSkillE() { return skillE; }
    public Skill getSkillR() { return skillR; }
}