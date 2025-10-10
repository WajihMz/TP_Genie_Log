package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;

public class UpdatePlayerTests {
    @Test
    @DisplayName("Le constructeur UpdatePlayer doit être testé")
    void testUpdatePlayerConstructor() {
        UpdatePlayer updatePlayer = new UpdatePlayer();
        assertNotNull(updatePlayer);
    }

    @Test
    @DisplayName("addXp doit retourner true quand le joueur monte de niveau")
    void addXp_quandJoueurMonteNiveau_retourneTrue() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        boolean result = UpdatePlayer.addXp(p, 10);
        assertThat(result, is(true));
        assertThat(p.getXp(), is(10));
        assertThat(p.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("addXp doit retourner false quand le joueur ne monte pas de niveau")
    void addXp_quandJoueurNeMontePasNiveau_retourneFalse() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        boolean result = UpdatePlayer.addXp(p, 5);
        assertThat(result, is(false));
        assertThat(p.getXp(), is(5));
        assertThat(p.retrieveLevel(), is(1));
    }

    @Test
    @DisplayName("addXp doit ajouter un objet aléatoire quand le joueur monte de niveau")
    void addXp_quandJoueurMonteNiveau_ajouteObjetAleatoire() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 10);
        assertThat(p.inventory.size(), is(1));
        assertThat(p.inventory.get(0), is(notNullValue()));
    }

    @Test
    @DisplayName("majFinDeTour doit gérer le cas où le joueur est KO")
    void majFinDeTour_quandJoueurKO_afficheMessageKO() {
        player p = new player("T", "A", "ADVENTURER", 20, new ArrayList<>());
        p.currenthealthpoints = 0;
        
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            UpdatePlayer.majFinDeTour(p);
            String printed = out.toString();
            assertThat(printed, containsString("Le joueur est KO !"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("majFinDeTour pour ADVENTURER niveau < 3 doit réduire les HP")
    void majFinDeTour_adventurerNiveauBas_reduitHP() {
        player p = new player("T", "A", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 40;        // Définir HP max
        p.currenthealthpoints = 10; // Définir HP actuels (< 20, donc < moitié)
        
        UpdatePlayer.addXp(p, 5); // niveau 1 (< 3)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(11)); // +2 puis -1 = +1
    }

    @Test
    @DisplayName("majFinDeTour DWARF HP < 50% sans Holy Elixir - bonus simple")
    void majFinDeTour_dwarfSansHolyElixir_bonusSimple() {
        player p = new player("T", "A", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 10; // < 20 (moitié)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(11)); // +1 seulement
    }

    @Test
    @DisplayName("majFinDeTour DWARF HP < 50% avec Holy Elixir - double bonus")
    void majFinDeTour_dwarfAvecHolyElixir_doubleBonus() {
        player p = new player("T", "A", "DWARF", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 10; // < 20 (moitié)
        p.inventory.add("Holy Elixir");
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(12)); // +1 (classe) +1 (objet)
    }

    @Test
    @DisplayName("majFinDeTour ARCHER HP < 50% sans Magic Bow - bonus simple")
    void majFinDeTour_archerSansMagicBow_bonusSimple() {
        player p = new player("T", "A", "ARCHER", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 16; // < 20 (moitié)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(17)); // +1 seulement
    }

    @Test
    @DisplayName("majFinDeTour ARCHER HP < 50% avec Magic Bow - bonus calculé")
    void majFinDeTour_archerAvecMagicBow_bonusCalcule() {
        player p = new player("T", "A", "ARCHER", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 16; // < 20 (moitié)
        p.inventory.add("Magic Bow");
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(18)); // +1 + (16/8-1) = +2
    }

    @Test
    @DisplayName("majFinDeTour ADVENTURER niveau >= 3 - pas de réduction HP")
    void majFinDeTour_adventurerNiveauEleve_pasReductionHP() {
        player p = new player("T", "A", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 10; // < 20 (moitié)
        UpdatePlayer.addXp(p, 27); // niveau 3 (>= 3)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(12)); // +2 seulement
    }

    @Test
    @DisplayName("majFinDeTour HP >= 50% et < max - pas de bonus")
    void majFinDeTour_hpSuperieurMoitie_pasBonus() {
        player p = new player("T", "A", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 25; // >= 20 (moitié) mais < 40 (max)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(25)); // pas de changement
    }

    @Test
    @DisplayName("majFinDeTour HP >= max - plafonné au maximum")
    void majFinDeTour_hpSuperieurMax_plafonneMax() {
        player p = new player("T", "A", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 45; // > 40 (max)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(40)); // plafonné au max
    }

    @Test
    @DisplayName("majFinDeTour HP exactement à la moitié - pas de bonus")
    void majFinDeTour_hpExactementMoitie_pasBonus() {
        player p = new player("T", "A", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 20; // exactement 20 (moitié)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(20)); // pas de changement
    }

    @Test
    @DisplayName("majFinDeTour HP >= 50% sans dépassement - pas de changement")
    void majFinDeTour_hpSuperieurMoitie_pasChangement() {
        player p = new player("T", "A", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 25; // >= 20 (moitié) mais < 40 (max)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(25)); // Pas de changement
    }

    @Test
    @DisplayName("majFinDeTour avec HP qui dépassent après plafonnement")
    void majFinDeTour_testPlafonnementFinal() {
        player p = new player("T", "A", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 20;
        p.currenthealthpoints = 25; // > 20 (max)
        
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, is(20)); // Plafonné au max
    }    
    
}
