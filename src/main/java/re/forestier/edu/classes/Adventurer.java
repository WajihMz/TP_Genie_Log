package re.forestier.edu.classes;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.STATS;

import java.util.HashMap;

public class Adventurer extends AbstractPlayer {
    public Adventurer(String playerName, String avatarName, int maxHP, int money) {
        super(playerName, avatarName, maxHP, money);
        this.className = "Adventurer";
        this.classDescription = "un aventurier";
    }

    @Override
    public void resolveEndOTurn() {
        if (isKO()) {
            System.out.println("Le joueur est KO !");
            return;
        }
        
        if (!isLowHealth()) {
            return;
        }
        
        addCurrentHealthPoints(2);
        if (isLowLevel()) {
            removeCurrentHealthPoints(1);
        }
    }
    
    private boolean isLowHealth() {
        return getCurrentHealthPoints() < getMaxHealthPoints() / 2;
    }
    
    private boolean isLowLevel() {
        return level() < 3;
    }

    @Override
    public void initStats() {
        HashMap<STATS, Integer[]> stats = new HashMap<>();
        for (STATS stat : STATS.values()) {
            stats.put(stat, new Integer[MAX_LEVEL]);
        }
        stats.put(STATS.INT, new Integer[]{1, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        stats.put(STATS.DEF, new Integer[]{1, 1, 1, 3, 4, 4, 4, 4, 4, 4});
        stats.put(STATS.ATK, new Integer[]{3, 3, 5, 5, 5, 5, 5, 5, 5, 5});
        stats.put(STATS.CHA, new Integer[]{2, 3, 3, 3, 3, 3, 3, 3, 3, 3});
        stats.put(STATS.ALC, new Integer[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
        stats.put(STATS.VIS, new Integer[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 1});
        this.statsPerLevel = stats;
    }
}

