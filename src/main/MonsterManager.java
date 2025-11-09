package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterManager {
    private List<Monster> monsters;
    private int spriteSize = 150;

    public MonsterManager(int spriteSize) {
        this.spriteSize = spriteSize;
        monsters = new ArrayList<>();
    }

    /**
     * Add a monster at specific world coordinates
     */
    public void addMonster(int x, int y) {
        Monster monster = new Monster(x, y, spriteSize);
        monsters.add(monster);
        System.out.println("Monster added at: (" + x + ", " + y + ")");
    }

    /**
     * Add a monster with custom attack timing
     * @param x X position
     * @param y Y position
     * @param prepareSeconds Seconds to wait before attacking (e.g., 2.0f for 2 seconds)
     * @param cooldownSeconds Seconds to wait between attacks (e.g., 2.0f for 2 seconds)
     */
    public void addMonster(int x, int y, float prepareSeconds, float cooldownSeconds) {
        Monster monster = new Monster(x, y, spriteSize);
        monster.setAttackTiming(prepareSeconds, cooldownSeconds);
        monsters.add(monster);
        System.out.println("Monster added at: (" + x + ", " + y + ") with " +
                prepareSeconds + "s prepare, " + cooldownSeconds + "s cooldown");
    }

    /**
     * Initialize monsters for Map 1
     */
    public void loadMap1Monsters() {
        monsters.clear();

        // Add monsters at strategic positions on your map
        // Default: 2 seconds prepare, 2 seconds cooldown
        addMonster(500, 200, 2.0f, 2.0f);      // Near starting area
        addMonster(800, -300, 2.0f, 2.0f);     // Upper right section
        addMonster(-400, -500, 2.0f, 2.0f);    // Upper left section
        addMonster(1200, -800, 2.0f, 2.0f);    // Mid-right area
        addMonster(-800, -1500, 2.0f, 2.0f);   // Left corridor
        addMonster(1500, -1200, 2.0f, 2.0f);   // Right corridor

        // Example: Boss monster with longer prepare time and shorter cooldown
        // addMonster(1800, -1400, 3.0f, 1.0f);

        System.out.println("Map 1 monsters loaded: " + monsters.size() + " monsters");
    }

    /**
     * Initialize monsters for Map 2
     */
    public void loadMap2Monsters() {
        monsters.clear();

        // Add monsters for map 2
        addMonster(-500, -600, 2.0f, 2.0f);
        addMonster(200, -900, 2.0f, 2.0f);

        System.out.println("Map 2 monsters loaded: " + monsters.size() + " monsters");
    }

    /**
     * Update all monsters
     */
    public void update(CharacterLoad player, Collision collision) {
        for (Monster monster : monsters) {
            monster.update(player, collision);
        }
    }

    /**
     * Draw all monsters
     */
    public void draw(Graphics g, int cameraX, int cameraY) {
        for (Monster monster : monsters) {
            monster.draw(g, cameraX, cameraY);
        }
    }

    /**
     * Check if any monster is attacking the player
     */
    public boolean isAnyMonsterAttackingPlayer(CharacterLoad player) {
        for (Monster monster : monsters) {
            if (monster.isAttackingPlayer(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all monsters (for custom logic if needed)
     */
    public List<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Remove all monsters
     */
    public void clearMonsters() {
        monsters.clear();
    }

    /**
     * Get monster count
     */
    public int getMonsterCount() {
        return monsters.size();
    }
}