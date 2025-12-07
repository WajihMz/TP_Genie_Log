package re.forestier.edu.rpg;

import re.forestier.edu.Exceptions.InventoryException;
import re.forestier.edu.Exceptions.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractPlayer {
    private static final int[] LVL_ABSOLUTE_XP_REQ = {10, 27, 57, 111};
    public static final int MAX_LEVEL = 10;
    
    private String playerName;
    private String avatarName;
    private final Money moneyManager;
    private int maxHealthPoints;
    private int currenthealthpoints;
    protected String className;
    protected String classDescription;
    protected HashMap<STATS, Integer[]> statsPerLevel = new HashMap<>();
    private int xp;
    public ArrayList<String> inventory; // Temporairement String pour compatibilité
    private final Integer capacity;

    public AbstractPlayer(String playerName, String avatarName, int maxHealthPoints, int money) {
        this.playerName = playerName;
        this.avatarName = avatarName;
        this.maxHealthPoints = maxHealthPoints;
        this.moneyManager = new Money();
        this.currenthealthpoints = maxHealthPoints;
        this.xp = 0;
        this.addMoney(money);
        this.initStats();
        this.inventory = new ArrayList<>();
        this.capacity = 5000;
    }

    private void addRandomObject() {
        this.inventory.add(ITEM.randomItem().toString());
    }

    public void addItem(String item) {
        // Pour l'instant, on accepte les String. Plus tard, on migrera vers ITEM
        // Vérifier la capacité avant d'ajouter
        int itemWeight = getItemWeightFromString(item);
        if (this.getRemainingCapacity() < itemWeight) {
            throw new InventoryException("Player can't carry this item, not enough capacity");
        }
        this.inventory.add(item);
    }

    /**
     * Helper method pour obtenir le poids d'un item depuis sa représentation String
     * Utilisé temporairement jusqu'à la migration vers ArrayList<ITEM>
     */
    private int getItemWeightFromString(String itemString) {
        // Chercher l'ITEM correspondant dans l'énumération
        for (ITEM item : ITEM.values()) {
            if (item.toString().equals(itemString)) {
                return item.getWeight();
            }
        }
        // Si l'item n'est pas trouvé, retourner 0 (pour compatibilité avec ancien code)
        return 0;
    }

    /**
     * Get the money this player have in his pocket
     * @return The remaining money
     */
    public Integer getMoney() {
        return this.moneyManager.getAmount();
    }

    public void removeMoney(int amount) throws IllegalArgumentException {
        this.moneyManager.removeMoney(amount);
    }

    public void addMoney(int amount) {
        this.moneyManager.addMoney(amount);
    }

    public String getJobName() {
        return this.className;
    }

    public String getDescription() {
        return this.classDescription;
    }

    public int getXp() {
        return this.xp;
    }

    public int level() {
        int i = 0;
        while (i < LVL_ABSOLUTE_XP_REQ.length && LVL_ABSOLUTE_XP_REQ[i] <= xp) {
            i += 1;
        }
        return i + 1;
    }

    public int retrieveLevel() {
        return this.level();
    }

    public void addXp(int xp) {
        int old_level = this.level();
        this.xp += xp;
        if (this.level() != old_level) {
            addRandomObject();
        }
    }

    public int getStatistic(STATS stat) {
        assert stat != null;
        assert level() <= MAX_LEVEL;
        return statsPerLevel.get(stat)[this.level() - 1];
    }

    public int getStatForLevel(STATS stat, int level) {
        assert stat != null;
        assert level <= MAX_LEVEL;
        return statsPerLevel.get(stat)[level - 1];
    }

    public HashMap<STATS, Integer> getStatistics() {
        HashMap<STATS, Integer> map = new HashMap<>();
        for (STATS stat : STATS.values()) {
            map.put(stat, getStatistic(stat));
        }
        return map;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public int getCurrentHealthPoints() {
        return this.currenthealthpoints;
    }

    public void addCurrentHealthPoints(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Trying to add a negative amount of health points");
        }
        this.currenthealthpoints += amount;
        this.currenthealthpoints = Math.min(this.currenthealthpoints, this.maxHealthPoints);
    }

    public void removeCurrentHealthPoints(int amount) {
        assert amount > 0;
        this.currenthealthpoints -= amount;
        this.currenthealthpoints = Math.max(this.currenthealthpoints, 0);
    }

    public void setCurrentHealthPoints(int currentHealthPoints) {
        this.currenthealthpoints = currentHealthPoints;
        this.currenthealthpoints = Math.min(this.currenthealthpoints, this.maxHealthPoints);
        this.currenthealthpoints = Math.max(this.currenthealthpoints, 0);
    }

    public Boolean isKO() {
        return this.currenthealthpoints <= 0;
    }

    public abstract void resolveEndOTurn();

    public abstract void initStats();

    public String displayPlayer() {
        return this.toString();
    }

    @Override
    public String toString() {
        // Estimation de la taille initiale pour éviter les réallocations
        // ~100 caractères pour le header + ~20 par stat + ~30 par item
        int estimatedSize = 100 + (STATS.values().length * 20) + (this.inventory.size() * 30);
        StringBuilder display = new StringBuilder(estimatedSize);
        
        display.append("Joueur ");
        display.append(this.getAvatarName());
        display.append(" joué par ");
        display.append(this.getPlayerName());
        display.append("\nNiveau : ");
        display.append(this.retrieveLevel());
        display.append(" (XP totale : ");
        display.append(this.getXp());
        display.append(")");
        display.append("\n\nCapacités :");
        
        // Optimisation : éviter l'appel double à getStatistic()
        for (STATS stat : STATS.values()) {
            int statValue = this.getStatistic(stat);
            if (statValue != 0) {
                display.append("\n   ").append(stat).append(" : ").append(statValue);
            }
        }
        
        display.append("\n\nInventaire :");
        this.inventory.forEach(item -> display.append("\n   ").append(item));
        
        return display.toString();
    }

    public Integer getLoad() {
        // Calculer le poids total de l'inventaire
        return inventory.stream()
                .mapToInt(this::getItemWeightFromString)
                .sum();
    }

    public String getClassDescription() {
        return classDescription;
    }

    public String getClassName() {
        return className;
    }

    public String toMarkdown() {
        // Estimation de la taille initiale pour éviter les réallocations
        int estimatedSize = 150 + (STATS.values().length * 25) + (this.inventory.size() * 35);
        StringBuilder markdown = new StringBuilder(estimatedSize);
        
        markdown.append("# ");
        markdown.append(this.playerName);
        markdown.append(" *as* ");
        markdown.append(this.avatarName);
        markdown.append("\n\n");
        markdown.append("# About\n\n");
        markdown.append("**Level** : ");
        markdown.append(this.retrieveLevel());
        markdown.append("\n\n## Statistics :\n");
        
        // Optimisation : éviter l'appel double à getStatistic()
        for (STATS stat : STATS.values()) {
            int statValue = this.getStatistic(stat);
            if (statValue != 0) {
                markdown.append("\n * **").append(stat).append("** : ").append(statValue);
            }
        }
        
        markdown.append("\n\n");
        markdown.append("## Inventory :\n");
        this.inventory.forEach(item -> markdown.append("\n * ").append(item));
        markdown.append("\n\n");

        return markdown.toString();
    }

    public Integer getRemainingCapacity() {
        return this.capacity - this.getLoad();
    }
}

