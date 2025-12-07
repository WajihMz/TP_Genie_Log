package re.forestier.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

import java.util.ArrayList;

import static org.approvaltests.Approvals.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.Matchers.containsString;

public class GlobalTest {

    @Test
    void testAffichageBase() {
        player player = new player("Florian", "Gnognak le Barbare", "ADVENTURER", 200, new ArrayList<>());
        UpdatePlayer.addXp(player, 20);
        player.inventory = new ArrayList<>();

        verify(player.toString());
    }

    @Test
    @DisplayName("Test affichage joueur DWARF avec XP et inventaire")
    void testAffichageDwarfWithXpAndInventory() {
        player player = new player("Florian", "Gnognak le Barbare", "DWARF", 200, new ArrayList<>());
        UpdatePlayer.addXp(player, 20);
        player.inventory = new ArrayList<>();
        String result = player.toString();
        
        assertThat(result, containsString("Gnognak le Barbare"));
        assertThat(result, containsString("Florian"));
        assertThat(result, containsString("Niveau : 2"));
        assertThat(result, containsString("XP totale : 20"));
        assertThat(result, containsString("DEF"));
        assertThat(result, containsString("ALC"));
        assertThat(result, containsString("ATK"));
        assertThat(result, containsString("INT"));
    }

    @Test
    @DisplayName("Test affichage joueur ARCHER niveau 3")
    void testAffichageArcherLevelThree() {
        player player = new player("Test", "Archer", "ARCHER", 100, new ArrayList<>());
        UpdatePlayer.addXp(player, 27);
        player.inventory = new ArrayList<>();
        String result = player.toString();
        
        assertThat(result, containsString("Archer"));
        assertThat(result, containsString("Test"));
        assertThat(result, containsString("Niveau : 3"));
        assertThat(result, containsString("XP totale : 27"));
        assertThat(result, containsString("VIS"));
        assertThat(result, containsString("ATK"));
        assertThat(result, containsString("CHA"));
        assertThat(result, containsString("INT"));
    }

    // Le test du constructeur Affichage a été supprimé car la classe Affichage est supprimée
    // L'affichage est maintenant géré par player.toString()

    @Test
    @DisplayName("Test affichage avec inventaire non vide pour tuer la mutation PIT")
    void testAffichageWithInventory() {
        player p = new player("Test", "Test", "DWARF", 200, new ArrayList<>());
        p.inventory.add("Épée");
        String result = p.toString();
        assertThat(result, containsString("Épée"));
    }
    
}
