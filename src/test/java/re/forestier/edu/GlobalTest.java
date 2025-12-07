package re.forestier.edu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import re.forestier.edu.classes.Adventurer;
import re.forestier.edu.classes.Archer;
import re.forestier.edu.classes.Dwarf;
import re.forestier.edu.rpg.AbstractPlayer;

import static org.approvaltests.Approvals.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class GlobalTest {

    @Test
    void testAffichageBase() {
        AbstractPlayer player = new Adventurer("Florian", "Gnognak le Barbare", 200, 0);
        // Ajouter de l'XP (ajoute un objet aléatoire au level-up)
        player.addXp(20);
        // Vider l'inventaire pour correspondre au test original
        player.inventory.clear();

        verify(player.toString());
    }

    @Test
    @DisplayName("Test affichage joueur DWARF avec XP et inventaire")
    void testAffichageDwarfWithXpAndInventory() {
        AbstractPlayer player = new Dwarf("Florian", "Gnognak le Barbare", 200, 0);
        player.addXp(20);
        player.inventory.clear();
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
        AbstractPlayer player = new Archer("Test", "Archer", 100, 0);
        player.addXp(27);
        player.inventory.clear();
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

    @Test
    @DisplayName("Test affichage avec inventaire non vide pour tuer la mutation PIT")
    void testAffichageWithInventory() {
        AbstractPlayer p = new Dwarf("Test", "Test", 200, 0);
        p.inventory.add("Épée");
        String result = p.toString();
        assertThat(result, containsString("Épée"));
    }
    
}
