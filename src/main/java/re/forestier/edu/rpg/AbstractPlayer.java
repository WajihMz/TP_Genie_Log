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
    private int currentHealthPoints;
    protected String className;
    protected String classDescription;
    protected HashMap<STATS, Integer[]> statsPerLevel = new HashMap<>();
    private int xp;
    public ArrayList<String> inventory;
    private final Integer capacity;

    public AbstractPlayer(String playerName, String avatarName, int maxHealthPoints, int money) {
        this.playerName = playerName;
        this.avatarName = avatarName;
        this.maxHealthPoints = maxHealthPoints;
        this.moneyManager = new Money();
        this.currentHealthPoints = maxHealthPoints;
        this.xp = 0;
        this.addMoney(money);
        this.initStats();
        this.inventory = new ArrayList<>();
        this.capacity = 5000;
    }

    private void addRandomObject() {
        this.inventory.add(ITEM.randomItem().toString());
    }

    /**
     * Ajoute un objet à l'inventaire du joueur
     * @param item L'objet à ajouter (représenté par une String)
     * @throws InventoryException si le joueur n'a pas assez de capacité pour porter l'objet
     */
    public void addItem(String item) {
        int itemWeight = getItemWeightFromString(item);
        if (this.getRemainingCapacity() < itemWeight) {
            throw new InventoryException("Player can't carry this item, not enough capacity");
        }
        this.inventory.add(item);
    }

    /**
     * Helper method pour obtenir le poids d'un item depuis sa représentation String
     */
    private int getItemWeightFromString(String itemString) {
        for (ITEM item : ITEM.values()) {
            if (item.toString().equals(itemString)) {
                return item.getWeight();
            }
        }
        return 0;
    }

    /**
     * Get the money this player have in his pocket
     * @return The remaining money
     */
    public int getMoney() {
        return this.moneyManager.getAmount();
    }

    /**
     * Retire de l'argent au joueur
     * @param amount Le montant à retirer
     * @throws IllegalArgumentException si le montant est négatif ou si le joueur n'a pas assez d'argent
     */
    public void removeMoney(int amount) throws IllegalArgumentException {
        this.moneyManager.removeMoney(amount);
    }

    /**
     * Ajoute de l'argent au joueur
     * @param amount Le montant à ajouter
     * @throws IllegalArgumentException si le montant est négatif
     */
    public void addMoney(int amount) {
        this.moneyManager.addMoney(amount);
    }

    /**
     * Retourne le nom de la classe du joueur
     * @return Le nom de la classe (ex: "Adventurer", "Archer", "Dwarf")
     */
    public String getJobName() {
        return this.className;
    }

    /**
     * Retourne la description de la classe du joueur
     * @return La description de la classe
     */
    public String getDescription() {
        return this.classDescription;
    }

    /**
     * Retourne l'expérience totale du joueur
     * @return L'expérience totale accumulée
     */
    public int getXp() {
        return this.xp;
    }

    /**
     * Calcule le niveau actuel du joueur basé sur son XP
     * @return Le niveau actuel (entre 1 et MAX_LEVEL)
     */
    public int level() {
        int i = 0;
        while (i < LVL_ABSOLUTE_XP_REQ.length && LVL_ABSOLUTE_XP_REQ[i] <= xp) {
            i += 1;
        }
        return i + 1;
    }

    /**
     * Retourne le niveau actuel du joueur
     * @return Le niveau actuel (entre 1 et MAX_LEVEL)
     */
    public int retrieveLevel() {
        return this.level();
    }

    /**
     * Ajoute de l'expérience au joueur
     * Si le joueur monte de niveau, un objet aléatoire est ajouté à l'inventaire
     * @param xp L'expérience à ajouter
     */
    /**
     * Ajoute de l'expérience au joueur
     * Si le joueur monte de niveau, un objet aléatoire est ajouté à l'inventaire
     * @param xp L'expérience à ajouter
     */
    public void addXp(int xp) {
        int oldLevel = this.level();
        this.xp += xp;
        if (this.level() != oldLevel) {
            addRandomObject();
        }
    }

    /**
     * Retourne la valeur d'une statistique pour le niveau actuel du joueur
     * @param stat La statistique à récupérer
     * @return La valeur de la statistique au niveau actuel
     */
    public int getStatistic(STATS stat) {
        assert stat != null;
        assert level() <= MAX_LEVEL;
        return statsPerLevel.get(stat)[this.level() - 1];
    }

    /**
     * Retourne la valeur d'une statistique pour un niveau spécifique
     * @param stat La statistique à récupérer
     * @param level Le niveau pour lequel récupérer la statistique
     * @return La valeur de la statistique au niveau spécifié
     */
    public int getStatForLevel(STATS stat, int level) {
        assert stat != null;
        assert level <= MAX_LEVEL;
        return statsPerLevel.get(stat)[level - 1];
    }

    /**
     * Retourne toutes les statistiques du joueur pour son niveau actuel
     * @return Une HashMap contenant toutes les statistiques avec leurs valeurs
     */
    public HashMap<STATS, Integer> getStatistics() {
        HashMap<STATS, Integer> map = new HashMap<>();
        for (STATS stat : STATS.values()) {
            map.put(stat, getStatistic(stat));
        }
        return map;
    }

    /**
     * Retourne le nom du joueur
     * @return Le nom du joueur
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Définit le nom du joueur
     * @param playerName Le nouveau nom du joueur
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Retourne le nom de l'avatar du joueur
     * @return Le nom de l'avatar
     */
    public String getAvatarName() {
        return avatarName;
    }

    /**
     * Définit le nom de l'avatar du joueur
     * @param avatarName Le nouveau nom de l'avatar
     */
    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    /**
     * Retourne les points de vie maximum du joueur
     * @return Les points de vie maximum
     */
    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    /**
     * Définit les points de vie maximum du joueur
     * @param maxHealthPoints Les nouveaux points de vie maximum
     */
    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    /**
     * Retourne les points de vie actuels du joueur
     * @return Les points de vie actuels
     */
    public int getCurrentHealthPoints() {
        return this.currentHealthPoints;
    }

    /**
     * Ajoute des points de vie au joueur
     * @param amount Le montant à ajouter (doit être positif)
     * @throws IllegalArgumentException si amount est négatif
     */
    public void addCurrentHealthPoints(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Trying to add a negative amount of health points");
        }
        this.currentHealthPoints = Math.min(this.currentHealthPoints + amount, this.maxHealthPoints);
    }

    /**
     * Retire des points de vie au joueur
     * @param amount Le montant à retirer (doit être positif)
     * @throws AssertionError si amount est négatif ou nul
     */
    public void removeCurrentHealthPoints(int amount) {
        assert amount > 0 : "Amount must be positive";
        this.currentHealthPoints = Math.max(this.currentHealthPoints - amount, 0);
    }

    /**
     * Définit les points de vie actuels du joueur
     * Les HP sont automatiquement plafonnés entre 0 et maxHealthPoints
     * @param currentHealthPoints Les nouveaux points de vie
     */
    public void setCurrentHealthPoints(int currentHealthPoints) {
        this.currentHealthPoints = Math.max(0, Math.min(currentHealthPoints, this.maxHealthPoints));
    }

    /**
     * Vérifie si le joueur est KO (Knocked Out)
     * @return true si les points de vie sont à 0 ou moins, false sinon
     */
    public Boolean isKO() {
        return this.currentHealthPoints <= 0;
    }

    /**
     * Résout les effets de fin de tour pour le joueur
     * Cette méthode est implémentée différemment selon la classe du joueur
     */
    public abstract void resolveEndOTurn();

    /**
     * Initialise les statistiques du joueur selon sa classe
     * Cette méthode est implémentée différemment selon la classe du joueur
     */
    public abstract void initStats();

    /**
     * Affiche les informations du joueur
     * @return Une représentation textuelle du joueur
     */
    public String displayPlayer() {
        return this.toString();
    }

    /**
     * Retourne une représentation textuelle du joueur
     * @return Une chaîne contenant les informations du joueur (nom, niveau, capacités, inventaire)
     */
    @Override
    public String toString() {
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
        
        STATS[] displayOrder = {STATS.DEF, STATS.ATK, STATS.CHA, STATS.INT, STATS.ALC, STATS.VIS};
        for (STATS stat : displayOrder) {
            int statValue = this.getStatistic(stat);
            if (statValue != 0) {
                display.append("\n   ").append(stat).append(" : ").append(statValue);
            }
        }
        
        display.append("\n\nInventaire :");
        this.inventory.forEach(item -> display.append("\n   ").append(item));
        
        return display.toString();
    }

    /**
     * Calcule le poids total de l'inventaire du joueur
     * @return Le poids total de tous les objets dans l'inventaire
     */
    public Integer getLoad() {
        return inventory.stream()
                .mapToInt(this::getItemWeightFromString)
                .sum();
    }

    /**
     * Retourne la description de la classe du joueur
     * @return La description de la classe
     */
    public String getClassDescription() {
        return classDescription;
    }

    /**
     * Retourne le nom de la classe du joueur
     * @return Le nom de la classe
     */
    public String getClassName() {
        return className;
    }

    /**
     * Génère une représentation Markdown du joueur
     * @return Une chaîne Markdown représentant le joueur
     */
    public String toMarkdown() {
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

    /**
     * Calcule la capacité restante du joueur (poids disponible)
     * @return La capacité restante (capacité totale - poids actuel)
     */
    public Integer getRemainingCapacity() {
        return this.capacity - this.getLoad();
    }

    /**
     * Vendre un objet (retirer de l'inventaire et ajouter de l'argent)
     * @param item L'objet à vendre
     * @throws InventoryException si l'objet n'est pas dans l'inventaire
     */
    public void sell(ITEM item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot sell null item");
        }
        
        String itemString = item.toString();
        if (!this.inventory.contains(itemString)) {
            throw new InventoryException("Item not in inventory: " + item.getName());
        }
        
        this.inventory.remove(itemString);
        this.addMoney(item.getValue());
    }

    /**
     * Vendre un objet à un autre joueur
     * @param item L'objet à vendre
     * @param buyer Le joueur qui achète l'objet
     * @throws InventoryException si l'objet n'est pas dans l'inventaire du vendeur ou si l'acheteur n'a pas assez de capacité
     * @throws NotEnoughMoneyException si l'acheteur n'a pas assez d'argent
     * @throws IllegalArgumentException si l'acheteur est null ou si c'est le même joueur
     */
    public void sell(ITEM item, AbstractPlayer buyer) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot sell null item");
        }
        if (buyer == null) {
            throw new IllegalArgumentException("Buyer cannot be null");
        }
        if (buyer == this) {
            throw new IllegalArgumentException("Cannot sell item to yourself");
        }
        
        String itemString = item.toString();
        
        if (!this.inventory.contains(itemString)) {
            throw new InventoryException("Item not in seller's inventory: " + item.getName());
        }
        
        int itemValue = item.getValue();
        if (buyer.getMoney() < itemValue) {
            throw new NotEnoughMoneyException("Buyer does not have enough money. Required: " + itemValue + ", Available: " + buyer.getMoney());
        }
        
        int itemWeight = item.getWeight();
        if (buyer.getRemainingCapacity() < itemWeight) {
            throw new InventoryException("Buyer does not have enough capacity. Required: " + itemWeight + ", Available: " + buyer.getRemainingCapacity());
        }
        
        this.inventory.remove(itemString);
        buyer.addItem(itemString);
        buyer.removeMoney(itemValue);
        this.addMoney(itemValue);
    }
}

