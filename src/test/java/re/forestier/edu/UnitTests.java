package re.forestier.edu;

import org.junit.jupiter.api.*;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

public class UnitTests {

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(player.playerName, is("Florian"));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        try {
            p.removeMoney(200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=9 -> niveau 1")
    void testLevelOneAtNineXp() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 9);
        assertThat(p.retrieveLevel(), is(1));
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=10 -> niveau 2")
    void testLevelTwoAtTenXp() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 10);
        assertThat(p.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=27 -> niveau 3")
    void testLevelThreeAtTwentySevenXp() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 27);
        assertThat(p.retrieveLevel(), is(3));
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=57 -> niveau 4")
    void testLevelFourAtFiftySevenXp() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 57);
        assertThat(p.retrieveLevel(), is(4));
    }

}
