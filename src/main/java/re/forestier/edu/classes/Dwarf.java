package re.forestier.edu.classes;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.ITEM;
import re.forestier.edu.rpg.STATS;

import java.util.HashMap;

/**
 * Classe représentant un Nain, un type de joueur dans le jeu RPG
 */
public class Dwarf extends AbstractPlayer {
    /**
     * Constructeur pour créer un Nain
     * @param playerName Le nom du joueur
     * @param avatarName Le nom de l'avatar
     * @param maxHP Les points de vie maximum
     * @param money L'argent initial
     */
    public Dwarf(String playerName, String avatarName, int maxHP, int money) {
        super(playerName, avatarName, maxHP, money);
        this.className = "Dwarf";
        this.classDescription = "un nain commun, qu'on trouve à la pelle";
    }

    /**
     * Résout les effets de fin de tour pour le Nain
     * Si les HP sont en dessous de 50%, ajoute 2 HP si le Nain a un Holy Elixir, sinon 1 HP.
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
        
        int healingAmount = hasHolyElixir() ? 2 : 1;
        addCurrentHealthPoints(healingAmount);
    }
    
    private boolean isLowHealth() {
        return getCurrentHealthPoints() < getMaxHealthPoints() / 2;
    }
    
    private boolean hasHolyElixir() {
        return inventory.contains(ITEM.HolyElixir.toString());
    }

    /**
     * Initialise les statistiques du Nain pour tous les niveaux
     */
    @Override
    public void initStats() {
        HashMap<STATS, Integer[]> stats = new HashMap<>();
        for (STATS stat : STATS.values()) {
            stats.put(stat, new Integer[MAX_LEVEL]);
        }
        stats.put(STATS.INT, new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        stats.put(STATS.DEF, new Integer[]{0, 1, 1, 2, 2, 2, 2, 2, 2, 2});
        stats.put(STATS.ATK, new Integer[]{3, 3, 4, 4, 4, 4, 4, 4, 4, 4});
        stats.put(STATS.ALC, new Integer[]{4, 5, 5, 5, 5, 5, 5, 5, 5, 5});
        stats.put(STATS.CHA, new Integer[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 1});
        stats.put(STATS.VIS, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        this.statsPerLevel = stats;
    }
}

