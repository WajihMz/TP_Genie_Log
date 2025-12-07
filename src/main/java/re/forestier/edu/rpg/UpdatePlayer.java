package re.forestier.edu.rpg;

import java.util.HashMap;

/**
 * Classe utilitaire temporaire pour compatibilité avec l'ancienne classe player.java
 * 
 * Cette classe sera supprimée en Phase 6 quand player.java sera supprimé.
 * Les nouvelles classes (Adventurer, Archer, Dwarf) utilisent directement
 * les méthodes d'AbstractPlayer (addXp(), resolveEndOTurn()).
 * 
 * @deprecated Cette classe est conservée uniquement pour compatibilité temporaire
 */
@Deprecated
public class UpdatePlayer {

    /**
     * Méthode temporaire pour compatibilité avec player.java
     * Sera supprimée en Phase 6 quand player.java sera supprimé
     * Retourne les stats pour chaque classe et niveau (nécessaire pour les tests)
     */
    @Deprecated
    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {
        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> result = new HashMap<>();
        
        // ADVENTURER
        HashMap<Integer, HashMap<String, Integer>> adventurerMap = new HashMap<>();
        HashMap<String, Integer> adventurerLevel1 = new HashMap<>();
        adventurerLevel1.put("INT", 1);
        adventurerLevel1.put("DEF", 1);
        adventurerLevel1.put("ATK", 3);
        adventurerLevel1.put("CHA", 2);
        adventurerMap.put(1, adventurerLevel1);
        
        HashMap<String, Integer> adventurerLevel2 = new HashMap<>();
        adventurerLevel2.put("INT", 2);
        adventurerLevel2.put("CHA", 3);
        adventurerMap.put(2, adventurerLevel2);
        
        HashMap<String, Integer> adventurerLevel3 = new HashMap<>();
        adventurerLevel3.put("ATK", 5);
        adventurerLevel3.put("ALC", 1);
        adventurerMap.put(3, adventurerLevel3);
        
        HashMap<String, Integer> adventurerLevel4 = new HashMap<>();
        adventurerLevel4.put("DEF", 3);
        adventurerMap.put(4, adventurerLevel4);
        
        HashMap<String, Integer> adventurerLevel5 = new HashMap<>();
        adventurerLevel5.put("VIS", 1);
        adventurerLevel5.put("DEF", 4);
        adventurerMap.put(5, adventurerLevel5);
        
        result.put("ADVENTURER", adventurerMap);
        
        // ARCHER
        HashMap<Integer, HashMap<String, Integer>> archerMap = new HashMap<>();
        HashMap<String, Integer> archerLevel1 = new HashMap<>();
        archerLevel1.put("INT", 1);
        archerLevel1.put("ATK", 3);
        archerLevel1.put("CHA", 1);
        archerLevel1.put("VIS", 3);
        archerMap.put(1, archerLevel1);
        
        HashMap<String, Integer> archerLevel2 = new HashMap<>();
        archerLevel2.put("DEF", 1);
        archerLevel2.put("CHA", 2);
        archerMap.put(2, archerLevel2);
        
        HashMap<String, Integer> archerLevel3 = new HashMap<>();
        archerLevel3.put("ATK", 3);
        archerMap.put(3, archerLevel3);
        
        HashMap<String, Integer> archerLevel4 = new HashMap<>();
        archerLevel4.put("DEF", 2);
        archerMap.put(4, archerLevel4);
        
        HashMap<String, Integer> archerLevel5 = new HashMap<>();
        archerLevel5.put("ATK", 4);
        archerMap.put(5, archerLevel5);
        
        result.put("ARCHER", archerMap);
        
        // DWARF
        HashMap<Integer, HashMap<String, Integer>> dwarfMap = new HashMap<>();
        HashMap<String, Integer> dwarfLevel1 = new HashMap<>();
        dwarfLevel1.put("ALC", 4);
        dwarfLevel1.put("INT", 1);
        dwarfLevel1.put("ATK", 3);
        dwarfMap.put(1, dwarfLevel1);
        
        HashMap<String, Integer> dwarfLevel2 = new HashMap<>();
        dwarfLevel2.put("DEF", 1);
        dwarfLevel2.put("ALC", 5);
        dwarfMap.put(2, dwarfLevel2);
        
        HashMap<String, Integer> dwarfLevel3 = new HashMap<>();
        dwarfLevel3.put("ATK", 4);
        dwarfMap.put(3, dwarfLevel3);
        
        HashMap<String, Integer> dwarfLevel4 = new HashMap<>();
        dwarfLevel4.put("DEF", 2);
        dwarfMap.put(4, dwarfLevel4);
        
        HashMap<String, Integer> dwarfLevel5 = new HashMap<>();
        dwarfLevel5.put("CHA", 1);
        dwarfMap.put(5, dwarfLevel5);
        
        result.put("DWARF", dwarfMap);
        
        return result;
    }

    /**
     * Ajoute de l'XP à un joueur et gère le level-up
     * 
     * @param player Le joueur (ancienne classe player.java)
     * @param xp L'XP à ajouter
     * @return true si le joueur a monté de niveau, false sinon
     * 
     * @deprecated Cette méthode est conservée uniquement pour compatibilité avec player.java
     * Utiliser AbstractPlayer.addXp() pour les nouvelles classes (Adventurer, Archer, Dwarf)
     * Sera supprimée en Phase 6 quand player.java sera supprimé
     */
    @Deprecated
    public static boolean addXp(player player, int xp) {
        int currentLevel = player.retrieveLevel();
        player.xp += xp;
        int newLevel = player.retrieveLevel();

        if (newLevel != currentLevel) {
            // Player leveled-up!
            // Give a random object
            player.inventory.add(ITEM.randomItem().toString());

            // Mise à jour temporaire des capacités pour compatibilité avec player.java
            // Cette logique sera supprimée en Phase 6 quand player.java sera supprimé
            HashMap<String, Integer> newAbilities = abilitiesPerTypeAndLevel().get(player.getAvatarClass()).get(newLevel);
            if (newAbilities != null) {
                newAbilities.forEach((ability, level) -> {
                    player.abilities.put(ability, level);
                });
            }
            return true;
        }
        return false;
    }

    /**
     * Méthode wrapper pour utiliser resolveEndOTurn() avec polymorphisme
     * Utilise resolveEndOTurn() si le joueur est une instance d'AbstractPlayer
     * Sinon, utilise l'ancienne logique pour compatibilité avec player.java (temporaire)
     * 
     * @param player Le joueur (AbstractPlayer ou player.java)
     * 
     * @deprecated Cette méthode est conservée uniquement pour compatibilité avec player.java
     * Utiliser AbstractPlayer.resolveEndOTurn() directement pour les nouvelles classes
     * Sera supprimée en Phase 6 quand player.java sera supprimé
     */
    @Deprecated
    public static void resolveEndOTurn(Object player) {
        if (player instanceof AbstractPlayer) {
            ((AbstractPlayer) player).resolveEndOTurn();
        } else if (player instanceof player) {
            // Compatibilité temporaire avec l'ancienne classe player
            // Cette logique sera supprimée en Phase 6 quand player.java sera supprimé
            player oldPlayer = (player) player;
            if(oldPlayer.currenthealthpoints == 0) {
                System.out.println("Le joueur est KO !");
                return;
            }
            boolean isAdventurer = "ADVENTURER".equals(oldPlayer.getAvatarClass());
            boolean isDwarf = "DWARF".equals(oldPlayer.getAvatarClass());
            boolean isArcher = "ARCHER".equals(oldPlayer.getAvatarClass());

            if (oldPlayer.currenthealthpoints < oldPlayer.healthpoints / 2) {
                if (isAdventurer) {
                    oldPlayer.currenthealthpoints += 2;
                    if (oldPlayer.retrieveLevel() < 3) {
                        oldPlayer.currenthealthpoints -= 1;
                    }
                } else {
                    if (isDwarf) {
                        if (oldPlayer.inventory.contains("Holy Elixir")) {
                            oldPlayer.currenthealthpoints += 1;
                        }
                        oldPlayer.currenthealthpoints += 1;
                    }
                    if (isArcher) {
                        oldPlayer.currenthealthpoints += 1;
                        if (oldPlayer.inventory.contains("Magic Bow")) {
                            oldPlayer.currenthealthpoints += oldPlayer.currenthealthpoints / 8 - 1;
                        }
                    }
                }
            } else {
                if(oldPlayer.currenthealthpoints >= oldPlayer.healthpoints) {
                    oldPlayer.currenthealthpoints = oldPlayer.healthpoints;
                    return;
                }
            }
        }
    }
    
}