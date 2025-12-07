package re.forestier.edu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.classes.Adventurer;
import re.forestier.edu.classes.Archer;
import re.forestier.edu.classes.Dwarf;
import re.forestier.edu.rpg.AbstractPlayer;

public class UpdatePlayerTests {
    // Le constructeur UpdatePlayer n'est plus testé car la classe est maintenant
    // une classe utilitaire avec uniquement des méthodes statiques

    @Test
    @DisplayName("addXp doit ajouter de l'XP et monter de niveau")
    void addXp_quandJoueurMonteNiveau_retourneTrue() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(10);
        assertThat(p.getXp(), is(10));
        assertThat(p.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("addXp ne fait pas monter de niveau si XP insuffisant")
    void addXp_quandJoueurNeMontePasNiveau_retourneFalse() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(5);
        assertThat(p.getXp(), is(5));
        assertThat(p.retrieveLevel(), is(1));
    }

    @Test
    @DisplayName("addXp doit ajouter un objet aléatoire quand le joueur monte de niveau")
    void addXp_quandJoueurMonteNiveau_ajouteObjetAleatoire() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.addXp(10);
        assertThat(p.inventory.size(), is(1));
        assertThat(p.inventory.get(0), is(notNullValue()));
    }

    @Test
    @DisplayName("resolveEndOTurn doit gérer le cas où le joueur est KO")
    void resolveEndOTurn_quandJoueurKO_afficheMessageKO() {
        AbstractPlayer p = new Adventurer("T", "A", 100, 0);
        p.setCurrentHealthPoints(0);
        
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            p.resolveEndOTurn();
            String printed = out.toString();
            assertThat(printed, containsString("Le joueur est KO !"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("resolveEndOTurn pour ADVENTURER niveau < 3 doit réduire les HP")
    void resolveEndOTurn_adventurerNiveauBas_reduitHP() {
        AbstractPlayer p = new Adventurer("T", "A", 40, 0);
        p.setCurrentHealthPoints(10); // Définir HP actuels (< 20, donc < moitié)
        
        p.addXp(5); // niveau 1 (< 3)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(11)); // +2 puis -1 = +1
    }

    @Test
    @DisplayName("resolveEndOTurn DWARF HP < 50% sans Holy Elixir - bonus simple")
    void resolveEndOTurn_dwarfSansHolyElixir_bonusSimple() {
        AbstractPlayer p = new Dwarf("T", "A", 40, 0);
        p.setCurrentHealthPoints(10); // < 20 (moitié)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(11)); // +1 seulement
    }

    @Test
    @DisplayName("resolveEndOTurn DWARF HP < 50% avec Holy Elixir - double bonus")
    void resolveEndOTurn_dwarfAvecHolyElixir_doubleBonus() {
        AbstractPlayer p = new Dwarf("T", "A", 40, 0);
        p.setCurrentHealthPoints(10); // < 20 (moitié)
        p.inventory.add("Holy Elixir : Recover your HP");
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(12)); // +2 (avec Holy Elixir)
    }

    @Test
    @DisplayName("resolveEndOTurn ARCHER HP < 50% sans Magic Bow - bonus simple")
    void resolveEndOTurn_archerSansMagicBow_bonusSimple() {
        AbstractPlayer p = new Archer("T", "A", 40, 0);
        p.setCurrentHealthPoints(16); // < 20 (moitié)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(17)); // +1 seulement
    }

    @Test
    @DisplayName("resolveEndOTurn ARCHER HP < 50% avec Magic Bow - bonus calculé")
    void resolveEndOTurn_archerAvecMagicBow_bonusCalcule() {
        AbstractPlayer p = new Archer("T", "A", 40, 0);
        p.setCurrentHealthPoints(16); // < 20 (moitié)
        p.inventory.add("Magic Bow");
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(18)); // +1 + (16/8-1) = +2
    }

    @Test
    @DisplayName("resolveEndOTurn ADVENTURER niveau >= 3 - pas de réduction HP")
    void resolveEndOTurn_adventurerNiveauEleve_pasReductionHP() {
        AbstractPlayer p = new Adventurer("T", "A", 40, 0);
        p.setCurrentHealthPoints(10); // < 20 (moitié)
        p.addXp(27); // niveau 3 (>= 3)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(12)); // +2 seulement
    }

    @Test
    @DisplayName("resolveEndOTurn HP >= 50% et < max - pas de bonus")
    void resolveEndOTurn_hpSuperieurMoitie_pasBonus() {
        AbstractPlayer p = new Adventurer("T", "A", 40, 0);
        p.setCurrentHealthPoints(25); // >= 20 (moitié) mais < 40 (max)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(25)); // pas de changement
    }

    @Test
    @DisplayName("resolveEndOTurn HP >= max - plafonné au maximum")
    void resolveEndOTurn_hpSuperieurMax_plafonneMax() {
        AbstractPlayer p = new Adventurer("T", "A", 40, 0);
        p.setCurrentHealthPoints(45); // > 40 (max)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(40)); // plafonné au max
    }

    @Test
    @DisplayName("resolveEndOTurn HP exactement à la moitié - pas de bonus")
    void resolveEndOTurn_hpExactementMoitie_pasBonus() {
        AbstractPlayer p = new Adventurer("T", "A", 40, 0);
        p.setCurrentHealthPoints(20); // exactement 20 (moitié)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(20)); // pas de changement
    }

    @Test
    @DisplayName("resolveEndOTurn HP >= 50% sans dépassement - pas de changement")
    void resolveEndOTurn_hpSuperieurMoitie_pasChangement() {
        AbstractPlayer p = new Adventurer("T", "A", 40, 0);
        p.setCurrentHealthPoints(25); // >= 20 (moitié) mais < 40 (max)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(25)); // Pas de changement
    }

    @Test
    @DisplayName("resolveEndOTurn avec HP qui dépassent après plafonnement")
    void resolveEndOTurn_testPlafonnementFinal() {
        AbstractPlayer p = new Adventurer("T", "A", 20, 0);
        p.setCurrentHealthPoints(25); // > 20 (max)
        
        p.resolveEndOTurn();
        assertThat(p.getCurrentHealthPoints(), is(20)); // Plafonné au max
    }    

    @Test 
    @DisplayName("resolveEndOTurn joueur non KO - pas de message affiché") 
    void resolveEndOTurn_joueurNonKO_pasMessage() {
        AbstractPlayer p = new Archer("T", "A", 30, 0); 
        p.setCurrentHealthPoints(1); 
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        System.setOut(new PrintStream(out)); 
        p.resolveEndOTurn(); 
        System.setOut(System.out); 
        assertThat(out.toString(), is("")); 
    }
    
}
