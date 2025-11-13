package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonsterManager {
    private List<Monster> monsters;
    private List<Monster2> monster2s;
    private List<Monster3> monster3s;
    private int spriteSize = 150;

    private List<DamageSystem.DamageNumber> damageNumbers;

    public MonsterManager(int spriteSize) {
        this.spriteSize = spriteSize;
        monsters = new ArrayList<>();
        monster2s = new ArrayList<>();
        monster3s = new ArrayList<>();
        damageNumbers = new ArrayList<>();
    }

    public void addMonster(int x, int y) {
        Monster monster = new Monster(x, y, spriteSize);
        monsters.add(monster);
        System.out.println("Monster added at: (" + x + ", " + y + ")");
    }

    public void addMonster(int x, int y, float prepareSeconds, float cooldownSeconds) {
        Monster monster = new Monster(x, y, spriteSize);
        monster.setAttackTiming(prepareSeconds, cooldownSeconds);
        monsters.add(monster);
        System.out.println("Monster added at: (" + x + ", " + y + ") with " +
                prepareSeconds + "s prepare, " + cooldownSeconds + "s cooldown");
    }

    public void addMonster2(int x, int y) {
        Monster2 monster = new Monster2(x, y, spriteSize);
        monster2s.add(monster);
        System.out.println("Monster2 added at: (" + x + ", " + y + ")");
    }

    public void addMonster2(int x, int y, float prepareSeconds, float cooldownSeconds) {
        Monster2 monster = new Monster2(x, y, spriteSize);
        monster.setAttackTiming(prepareSeconds, cooldownSeconds);
        monster2s.add(monster);
        System.out.println("Monster2 added at: (" + x + ", " + y + ") with " +
                prepareSeconds + "s prepare, " + cooldownSeconds + "s cooldown");
    }

    public void addMonster3(int x, int y) {
        Monster3 monster = new Monster3(x, y, spriteSize);
        monster3s.add(monster);
        System.out.println("Monster3 added at: (" + x + ", " + y + ")");
    }

    public void addMonster3(int x, int y, float prepareSeconds, float cooldownSeconds) {
        Monster3 monster = new Monster3(x, y, spriteSize);
        monster.setAttackTiming(prepareSeconds, cooldownSeconds);
        monster3s.add(monster);
        System.out.println("Monster3 added at: (" + x + ", " + y + ") with " +
                prepareSeconds + "s prepare, " + cooldownSeconds + "s cooldown");
    }

    public void loadMap1Monsters() {
        monsters.clear();
        monster2s.clear();
        monster3s.clear();

        addMonster(1200, -480, 2.0f, 2.0f);
        addMonster(950, -1050, 2.0f, 2.0f);
        addMonster(1500, -1400, 2.0f, 2.0f);
        addMonster(900, -1400, 2.0f, 2.0f);
        addMonster(-85, -1870, 2.0f, 2.0f);
        addMonster(-330, -2384, 2.0f, 2.0f);
        addMonster(1180, -2324, 2.0f, 2.0f);
        addMonster(-982, -2784, 2.0f, 2.0f);
        addMonster(-1980, -2300, 2.0f, 2.0f);
        addMonster(-1170, -4100, 2.0f, 2.0f);
        addMonster(-1580, -270, 2.0f, 2.0f);

        addMonster2(1380, -2324, 1.5f, 2.5f);
        addMonster2(1600, -130, 1.5f, 2.5f);
        addMonster2(1630, -1200, 1.5f, 2.5f);
        addMonster2(-360, -1320, 1.5f, 2.5f);
        addMonster2(-1282, -2784, 1.5f, 2.5f);
        addMonster2(-982, -1200, 1.5f, 2.5f);
        addMonster2(-1980, -3200, 1.5f, 2.5f);
        addMonster2(-1490, -1450, 1.5f, 2.5f);
        addMonster2(200, -900, 1.5f, 2.5f);

        addMonster3(1890, -880, 1.0f, 1.5f);
        addMonster3(1900, -1880, 1.0f, 1.5f);
        addMonster3(500, -2824, 1.0f, 1.5f);
        addMonster3(-982, -1900, 1.0f, 1.5f);
        addMonster3(-1980, -3695, 1.0f, 1.5f);
        addMonster3(-1980, -700, 1.0f, 1.5f);
        addMonster3(-1580, -700, 1.0f, 1.5f);
        addMonster3(-1070, -4100, 1.0f, 1.5f);
        addMonster3(-980, -3695, 1.0f, 1.5f);

        int totalMonsters = monsters.size() + monster2s.size() + monster3s.size();
        System.out.println("Map 1 monsters loaded: " + monsters.size() + " Monster, " +
                monster2s.size() + " Monster2, " + monster3s.size() + " Monster3 (" +
                totalMonsters + " total)");
    }

    public void loadMap2Monsters() {
        monsters.clear();
        monster2s.clear();
        monster3s.clear();

        int totalMonsters = monsters.size() + monster2s.size() + monster3s.size();
        System.out.println("Map 2 monsters loaded: " + monsters.size() + " Monster, " +
                monster2s.size() + " Monster2, " + monster3s.size() + " Monster3 (" +
                totalMonsters + " total)");
    }

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
        monsters.removeIf(monster -> monster.isDead());
        monster2s.removeIf(monster -> monster.isDead());
        monster3s.removeIf(monster -> monster.isDead());
        damageNumbers.removeIf(n -> !n.isActive());
    }

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
        Graphics2D g2 = (Graphics2D) g;
        for (DamageSystem.DamageNumber damageNum : damageNumbers) {
            damageNum.draw(g2);
        }
    }

    public boolean isAnyMonsterAttackingPlayer(CharacterLoad player) {
        for (Monster monster : monsters) {
            if (monster.isAttackingPlayer(player)) {
                return true;
            }
        }
        for (Monster2 monster : monster2s) {
            if (monster.isAttackingPlayer(player)) {
                return true;
            }
        }
        for (Monster3 monster : monster3s) {
            if (monster.isAttackingPlayer(player)) {
                return true;
            }
        }
        return false;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Monster2> getMonster2s() {
        return monster2s;
    }

    public List<Monster3> getMonster3s() {
        return monster3s;
    }

    public List<Object> getAllMonsters() {
        List<Object> allMonsters = new ArrayList<>();
        allMonsters.addAll(monsters);
        allMonsters.addAll(monster2s);
        allMonsters.addAll(monster3s);
        return allMonsters;
    }

    public void clearMonsters() {
        monsters.clear();
        monster2s.clear();
        monster3s.clear();
    }

    public int getMonsterCount() {
        return monsters.size() + monster2s.size() + monster3s.size();
    }

    public int getMonster1Count() {
        return monsters.size();
    }

    public int getMonster2Count() {
        return monster2s.size();
    }

    public int getMonster3Count() {
        return monster3s.size();
    }

    public void checkAttackCollisions(List<DamageSystem.AttackHitbox> attackHitboxes, int cameraX, int cameraY) {
        for (DamageSystem.AttackHitbox attack : attackHitboxes) {
            if (!attack.isActive()) continue;

            for (Monster monster : monsters) {
                if (!monster.isDead() && attack.collidesWith(monster.getHitbox())) {
                    if (monster.takeDamage(attack.getDamage())) {
                        int screenX = monster.getX() - cameraX;
                        int screenY = monster.getY() - cameraY;
                        damageNumbers.add(new DamageSystem.DamageNumber(
                                attack.getDamage(), screenX, screenY - 30
                        ));
                    }
                }
            }

            for (Monster2 monster : monster2s) {
                if (!monster.isDead() && attack.collidesWith(monster.getHitbox())) {
                    if (monster.takeDamage(attack.getDamage())) {
                        int screenX = monster.getX() - cameraX;
                        int screenY = monster.getY() - cameraY;
                        damageNumbers.add(new DamageSystem.DamageNumber(
                                attack.getDamage(), screenX, screenY - 30
                        ));
                    }
                }
            }

            for (Monster3 monster : monster3s) {
                if (!monster.isDead() && attack.collidesWith(monster.getHitbox())) {
                    if (monster.takeDamage(attack.getDamage())) {
                        int screenX = monster.getX() - cameraX;
                        int screenY = monster.getY() - cameraY;
                        damageNumbers.add(new DamageSystem.DamageNumber(
                                attack.getDamage(), screenX, screenY - 30
                        ));
                    }
                }
            }
        }
    }
}