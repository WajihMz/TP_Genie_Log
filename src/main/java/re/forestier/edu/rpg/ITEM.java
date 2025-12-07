package re.forestier.edu.rpg;

import java.util.Random;

public enum ITEM {
    LookoutRing("Lookout Ring", "Prevents surprise attacks", 5, 10),
    ScrollOfStupidity("Scroll of Stupidity", "INT-2 when applied to an enemy", 1, 15),
    Draupnir("Draupnir", "Increases XP gained by 100%", 10, 40),
    MagicCharm("Magic Charm", "Magic +10 for 5 rounds", 20, 30),
    RuneStaffOfCurse("Rune Staff of Curse", "May burn your ennemies... Or yourself. Who knows?", 1200, 75),
    CombatEdge("Combat Edge", "Well, that's an edge", 3000, 30),
    HolyElixir("Holy Elixir", "Recover your HP", 700, 100);

    private final String name;
    private final String description;
    private final Integer weight;
    private final Integer value;
    private static final Random PRNG = new Random(42);

    ITEM(final String name, final String description, Integer weight, Integer value) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
    }

    public static ITEM randomItem() {
        ITEM[] items = values();
        return items[PRNG.nextInt(items.length)];
    }

    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        markdown.append("**");
        markdown.append(name);
        markdown.append("**");
        if (description != null) {
            markdown.append(" : ").append(description);
        }
        return markdown.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        if (description != null) {
            stringBuilder.append(" : ").append(description);
        }
        return stringBuilder.toString();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getValue() {
        return value;
    }
}

