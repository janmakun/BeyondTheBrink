package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterManager {
    private List<Monster> monsters;
    private List<Monster2> monster2s;
    private List<Monster3> monster3s;
    private int spriteSize = 150;

    public MonsterManager(int spriteSize) {
        this.spriteSize = spriteSize;
        monsters = new ArrayList<>();
        monster2s = new ArrayList<>();
        monster3s = new ArrayList<>();
    }

    /**
     * Add a Monster at specific world coordinates
     */
    public void addMonster(int x, int y) {
        Monster monster = new Monster(x, y, spriteSize);
        monsters.add(monster);
        System.out.println("Monster added at: (" + x + ", " + y + ")");
    }

    /**
     * Add a Monster with custom attack timing
     */
    public void addMonster(int x, int y, float prepareSeconds, float cooldownSeconds) {
        Monster monster = new Monster(x, y, spriteSize);
        monster.setAttackTiming(prepareSeconds, cooldownSeconds);
        monsters.add(monster);
        System.out.println("Monster added at: (" + x + ", " + y + ") with " +
                prepareSeconds + "s prepare, " + cooldownSeconds + "s cooldown");
    }

    /**
     * Add a Monster2 at specific world coordinates
     */
    public void addMonster2(int x, int y) {
        Monster2 monster = new Monster2(x, y, spriteSize);
        monster2s.add(monster);
        System.out.println("Monster2 added at: (" + x + ", " + y + ")");
    }

    /**
     * Add a Monster2 with custom attack timing
     */
    public void addMonster2(int x, int y, float prepareSeconds, float cooldownSeconds) {
        Monster2 monster = new Monster2(x, y, spriteSize);
        monster.setAttackTiming(prepareSeconds, cooldownSeconds);
        monster2s.add(monster);
        System.out.println("Monster2 added at: (" + x + ", " + y + ") with " +
                prepareSeconds + "s prepare, " + cooldownSeconds + "s cooldown");
    }

    /**
     * Add a Monster3 at specific world coordinates
     */
    public void addMonster3(int x, int y) {
        Monster3 monster = new Monster3(x, y, spriteSize);
        monster3s.add(monster);
        System.out.println("Monster3 added at: (" + x + ", " + y + ")");
    }

    /**
     * Add a Monster3 with custom attack timing
     */
    public void addMonster3(int x, int y, float prepareSeconds, float cooldownSeconds) {
        Monster3 monster = new Monster3(x, y, spriteSize);
        monster.setAttackTiming(prepareSeconds, cooldownSeconds);
        monster3s.add(monster);
        System.out.println("Monster3 added at: (" + x + ", " + y + ") with " +
                prepareSeconds + "s prepare, " + cooldownSeconds + "s cooldown");
    }

    /**
     * Initialize monsters for Map 1
     */
    public void loadMap1Monsters() {
        monsters.clear();
        monster2s.clear();
        monster3s.clear();

//         --- Monster (Ground) [10 total] ---
        addMonster(1200, -480, 2.0f, 2.0f); // 34
        addMonster(950, -1050, 2.0f, 2.0f);  // 18
        addMonster(1500, -1400, 2.0f, 2.0f);  // 21
        addMonster(900, -1400, 2.0f, 2.0f);  // 57
        addMonster(-85, -1870, 2.0f, 2.0f);  // 65
        addMonster(-330, -2384, 2.0f, 2.0f);  // 65
        addMonster(1180, -2324, 2.0f, 2.0f);  // 52
        addMonster(-982, -2784, 2.0f, 2.0f);  // y
        addMonster(-1980, -2300, 2.0f, 2.0f);  //ml
        addMonster(-1170, -4100, 2.0f, 2.0f);//mi
        addMonster(-1580, -270, 2.0f, 2.0f);//S

//         --- Monster2 (Flying / Hovering) [10 total] ---
        addMonster2(1380, -2324, 1.5f, 2.5f); //51
        addMonster2(1600, -130, 1.5f, 2.5f); // 36
        addMonster2(1630, -1200, 1.5f, 2.5f); // 22
        addMonster2(-360, -1320, 1.5f, 2.5f); //17
        addMonster2(-1282, -2784, 1.5f, 2.5f);//V
        addMonster2(-982, -1200, 1.5f, 2.5f); //X
        addMonster2(-1980, -3200, 1.5f, 2.5f); //ml
        addMonster2(-1490, -1450, 1.5f, 2.5f); // K11
//        addMonster2(700, -1900, 1.5f, 2.5f);
        addMonster2(200, -900, 1.5f, 2.5f);// 62

//         --- Monster3 (Fast / Aggressive) [9 total] ---
        addMonster3(1890, -880, 1.0f, 1.5f); // 40
        addMonster3(1900, -1880, 1.0f, 1.5f); //48
        addMonster3(500, -2824, 1.0f, 1.5f); //I9
        addMonster3(-982, -1900, 1.0f, 1.5f); //W
        addMonster3(-1980, -3695, 1.0f, 1.5f);//ml
        addMonster3(-1980, -700, 1.0f, 1.5f);//ms
        addMonster3(-1580, -700, 1.0f, 1.5f);//T
        addMonster3(-1070, -4100, 1.0f, 1.5f); //MK
        addMonster3(-980, -3695, 1.0f, 1.5f); //F

        int totalMonsters = monsters.size() + monster2s.size() + monster3s.size();
        System.out.println("Map 1 monsters loaded: " + monsters.size() + " Monster, " +
                monster2s.size() + " Monster2, " + monster3s.size() + " Monster3 (" +
                totalMonsters + " total)");
    }

    /**
     * Initialize monsters for Map 2
     */
    public void loadMap2Monsters() {
        monsters.clear();
        monster2s.clear();
        monster3s.clear();



        int totalMonsters = monsters.size() + monster2s.size() + monster3s.size();
        System.out.println("Map 2 monsters loaded: " + monsters.size() + " Monster, " +
                monster2s.size() + " Monster2, " + monster3s.size() + " Monster3 (" +
                totalMonsters + " total)");
    }

    /**
     * Update all monsters
     */
    public void update(CharacterLoad player, Collision collision) {
        for (Monster monster : monsters) {
            monster.update(player, collision);
        }
        for (Monster2 monster : monster2s) {
            monster.update(player, collision);
        }
        for (Monster3 monster : monster3s) {
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
        for (Monster2 monster : monster2s) {
            monster.draw(g, cameraX, cameraY);
        }
        for (Monster3 monster : monster3s) {
            monster.draw(g, cameraX, cameraY);
        }
    }

    /**
     * Check if any monster is attacking the player
     */
    public boolean isAnyMonsterAttackingPlayer(CharacterLoad player) {
        // Check Monster
        for (Monster monster : monsters) {
            if (monster.isAttackingPlayer(player)) {
                return true;
            }
        }
        // Check Monster2
        for (Monster2 monster : monster2s) {
            if (monster.isAttackingPlayer(player)) {
                return true;
            }
        }
        // Check Monster3
        for (Monster3 monster : monster3s) {
            if (monster.isAttackingPlayer(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all Monster (for custom logic if needed)
     */
    public List<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Get all Monster2s (for custom logic if needed)
     */
    public List<Monster2> getMonster2s() {
        return monster2s;
    }

    /**
     * Get all Monster3s (for custom logic if needed)
     */
    public List<Monster3> getMonster3s() {
        return monster3s;
    }

    /**
     * Get all monsters combined
     */
    public List<Object> getAllMonsters() {
        List<Object> allMonsters = new ArrayList<>();
        allMonsters.addAll(monsters);
        allMonsters.addAll(monster2s);
        allMonsters.addAll(monster3s);
        return allMonsters;
    }

    /**
     * Remove all monsters
     */
    public void clearMonsters() {
        monsters.clear();
        monster2s.clear();
        monster3s.clear();
    }

    /**
     * Get total monster count
     */
    public int getMonsterCount() {
        return monsters.size() + monster2s.size() + monster3s.size();
    }

    /**
     * Get Monster count
     */
    public int getMonster1Count() {
        return monsters.size();
    }

    /**
     * Get Monster2 count
     */
    public int getMonster2Count() {
        return monster2s.size();
    }

    /**
     * Get Monster3 count
     */
    public int getMonster3Count() {
        return monster3s.size();
    }
}