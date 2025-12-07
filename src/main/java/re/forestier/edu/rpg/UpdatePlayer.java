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
     * Retourne les stats du niveau 1 pour chaque classe
     */
    @Deprecated
    public static HashMap<String, HashMap<Integer, HashMap<String, Integer>>> abilitiesPerTypeAndLevel() {
        HashMap<String, HashMap<Integer, HashMap<String, Integer>>> result = new HashMap<>();
        
        // ADVENTURER niveau 1
        HashMap<Integer, HashMap<String, Integer>> adventurerMap = new HashMap<>();
        HashMap<String, Integer> adventurerLevel1 = new HashMap<>();
        adventurerLevel1.put("INT", 1);
        adventurerLevel1.put("DEF", 1);
        adventurerLevel1.put("ATK", 3);
        adventurerLevel1.put("CHA", 2);
        adventurerMap.put(1, adventurerLevel1);
        result.put("ADVENTURER", adventurerMap);
        
        // ARCHER niveau 1
        HashMap<Integer, HashMap<String, Integer>> archerMap = new HashMap<>();
        HashMap<String, Integer> archerLevel1 = new HashMap<>();
        archerLevel1.put("INT", 1);
        archerLevel1.put("ATK", 3);
        archerLevel1.put("CHA", 1);
        archerLevel1.put("VIS", 3);
        archerMap.put(1, archerLevel1);
        result.put("ARCHER", archerMap);
        
        // DWARF niveau 1
        HashMap<Integer, HashMap<String, Integer>> dwarfMap = new HashMap<>();
        HashMap<String, Integer> dwarfLevel1 = new HashMap<>();
        dwarfLevel1.put("ALC", 4);
        dwarfLevel1.put("INT", 1);
        dwarfLevel1.put("ATK", 3);
        dwarfMap.put(1, dwarfLevel1);
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

            // Les capacités sont maintenant gérées dynamiquement par les classes concrètes
            // via getStatistic() dans AbstractPlayer. Plus besoin de mise à jour manuelle.
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