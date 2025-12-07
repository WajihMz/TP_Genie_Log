package re.forestier.edu.classes;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.STATS;

import java.util.HashMap;

/**
 * Classe représentant un Archer, un type de joueur dans le jeu RPG
 */
public class Archer extends AbstractPlayer {
    /**
     * Constructeur pour créer un Archer
     * @param playerName Le nom du joueur
     * @param avatarName Le nom de l'avatar
     * @param maxHP Les points de vie maximum
     * @param money L'argent initial
     */
    public Archer(String playerName, String avatarName, int maxHP, int money) {
        super(playerName, avatarName, maxHP, money);
        this.className = "Archer";
        this.classDescription = "un archer, ca tire des flèches.";
    }

    /**
     * Résout les effets de fin de tour pour l'Archer
     * Si les HP sont en dessous de 50%, ajoute 1 HP. Si l'Archer a un Magic Bow, ajoute un bonus calculé.
     */
    @Override
    public void resolveEndOTurn() {
        if (isKO()) {
            System.out.println("Le joueur est KO !");
            return;
        }
        
        if (!isLowHealth()) {
            return;
        }
        
        addCurrentHealthPoints(1);
        if (hasMagicBow()) {
            addCurrentHealthPoints(calculateMagicBowBonus());
        }
    }
    
    private boolean isLowHealth() {
        return getCurrentHealthPoints() < getMaxHealthPoints() / 2;
    }
    
    private boolean hasMagicBow() {
        return inventory.contains("Magic Bow");
    }
    
    private int calculateMagicBowBonus() {
        return getCurrentHealthPoints() / 8 - 1;
    }

    /**
     * Initialise les statistiques de l'Archer pour tous les niveaux
     */
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

