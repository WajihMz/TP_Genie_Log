package re.forestier.edu;

import org.junit.jupiter.api.*;

import re.forestier.edu.classes.Adventurer;
import re.forestier.edu.rpg.AbstractPlayer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class UnitTests {

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        AbstractPlayer player = new Adventurer("Florian", "Grognak le barbare", 100, 0);
        assertThat(player.getPlayerName(), is("Florian"));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        AbstractPlayer p = new Adventurer("Florian", "Grognak le barbare", 100, 0);

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
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(9);
        assertThat(p.retrieveLevel(), is(1));
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=10 -> niveau 2")
    void testLevelTwoAtTenXp() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(10);
        assertThat(p.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=27 -> niveau 3")
    void testLevelThreeAtTwentySevenXp() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(27);
        assertThat(p.retrieveLevel(), is(3));
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=57 -> niveau 4")
    void testLevelFourAtFiftySevenXp() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(57);
        assertThat(p.retrieveLevel(), is(4));
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=111 -> niveau 5")
    void testLevelFiveAtOneHundredElevenXp() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(111);
        assertThat(p.retrieveLevel(), is(5));
    }

    @Test
    @DisplayName("removeMoney avec montant valide - succès")
    void removeMoney_montantValide_succes() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addMoney(50); // avoir 50 d'argent
        p.removeMoney(30); // retirer 30
        assertThat(p.getMoney(), is(20)); // reste 20
    }

    @Test
    @DisplayName("removeMoney avec montant égal à l'argent - cas limite pour tuer mutation PIT")
    void removeMoney_montantEgalArgent_casLimite() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 100);
        p.removeMoney(100); // retirer exactement l'argent disponible
        assertThat(p.getMoney(), is(0)); // doit rester 0
    }

    @Test
    @DisplayName("addMoney avec montant null - gestion null")
    void addMoney_montantNull_gestionNull() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 100);
        int initialMoney = p.getMoney();
        p.addMoney(0); // cas limite
        assertThat(p.getMoney(), is(initialMoney)); // pas de changement
    }

    @Test
    @DisplayName("Test niveau initial - XP=0 -> niveau 1")
    void testNiveauInitial_zeroXp_niveau1() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        assertThat(p.retrieveLevel(), is(1));
    }

    @Test
    @DisplayName("Test niveau intermédiaire - XP=26 -> niveau 2")
    void testNiveauIntermediaire_vingtSixXp_niveau2() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(26);
        assertThat(p.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("Test niveau intermédiaire - XP=56 -> niveau 3")
    void testNiveauIntermediaire_cinquanteSixXp_niveau3() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(56);
        assertThat(p.retrieveLevel(), is(3));
    }

    @Test
    @DisplayName("Test niveau intermédiaire - XP=110 -> niveau 4")
    void testNiveauIntermediaire_centDixXp_niveau4() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(110);
        assertThat(p.retrieveLevel(), is(4));
    }

    @Test
    @DisplayName("addMoney avec Integer.valueOf null - gestion cas limite")
    void addMoney_integerValueOfNull_gestionCasLimite() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);

        p.addMoney(0);
        assertThat(p.getMoney(), is(0)); // Pas de changement
    }

    // Le test avec classe invalide n'est plus applicable car on utilise maintenant
    // des classes concrètes (Adventurer, Archer, Dwarf) au lieu d'une String

}
