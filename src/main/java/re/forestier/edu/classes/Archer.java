package re.forestier.edu.classes;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.STATS;

import java.util.HashMap;

public class Archer extends AbstractPlayer {
    public Archer(String playerName, String avatarName, int maxHP, int money) {
        super(playerName, avatarName, maxHP, money);
        this.className = "Archer";
        this.classDescription = "un archer, ca tire des flèches.";
    }

    @Override
    public void resolveEndOTurn() {
        if (isKO()) {
            System.out.println("Le joueur est KO !");
            return;
        }
        if (getCurrentHealthPoints() < getMaxHealthPoints() / 2) {
            addCurrentHealthPoints(1);
            // Vérifier si l'inventaire contient "Magic Bow"
            if (inventory.contains("Magic Bow")) {
                addCurrentHealthPoints(getCurrentHealthPoints() / 8 - 1);
            }
        }
    }

    @Override
    public void initStats() {
        HashMap<STATS, Integer[]> stats = new HashMap<>();
        for (STATS stat : STATS.values()) {
            stats.put(stat, new Integer[MAX_LEVEL]);
        }
        stats.put(STATS.ALC, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        stats.put(STATS.INT, new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        stats.put(STATS.ATK, new Integer[]{3, 3, 3, 3, 4, 4, 4, 4, 4, 4});
        stats.put(STATS.DEF, new Integer[]{0, 1, 1, 2, 2, 2, 2, 2, 2, 2});
        stats.put(STATS.CHA, new Integer[]{1, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        stats.put(STATS.VIS, new Integer[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3});
        this.statsPerLevel = stats;
    }
}

