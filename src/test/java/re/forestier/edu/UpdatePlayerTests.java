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
}
