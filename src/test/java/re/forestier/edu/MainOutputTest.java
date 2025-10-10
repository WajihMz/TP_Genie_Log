package re.forestier.edu;

import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.player;

import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainOutputTest {
    @Test
    @DisplayName("Le constructeur Main doit être testé")
    void testMainConstructor() {
        Main main = new Main();
        assertNotNull(main);
    }

    @Test
    @DisplayName("La méthode main doit afficher les informations du joueur DWARF")
    void main_afficheInformationsJoueurDwarf() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            Main.main(new String[]{});
            String printed = out.toString();
            
            assertThat(printed, containsString("Joueur"));
            assertThat(printed, containsString("Niveau :"));
            assertThat(printed, containsString("Capacités :"));
            assertThat(printed, containsString("Inventaire :"));
            assertThat(printed, containsString("------------------"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Main doit tester l'effet de addMoney sur l'argent du joueur")
    void main_testAddMoneyEffect() {
        player testPlayer = new player("Florian", "Ruzberg de Rivehaute", "DWARF", 200, new ArrayList<>());
        assertThat(testPlayer.money, is(200));
        testPlayer.addMoney(400);
        assertThat(testPlayer.money, is(600));
    }

    @Test
    @DisplayName("Main doit tester le premier affichage du joueur")
    void main_testFirstPlayerDisplay() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            Main.main(new String[]{});
            String printed = out.toString();
            
            // Vérifier que le premier affichage contient des éléments spécifiques
            String[] lines = printed.split("\n");
            assertThat(lines[0], containsString("Joueur"));
            assertThat(lines[0], containsString("Ruzberg de Rivehaute"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Main doit tester le deuxième affichage du joueur après addXp")
    void main_testSecondPlayerDisplay() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            Main.main(new String[]{});
            String printed = out.toString();
            
            // Vérifier qu'il y a deux affichages séparés par "------------------"
            String[] sections = printed.split("------------------");
            assertThat(sections.length, is(2));
            assertThat(sections[0], containsString("Joueur"));
            assertThat(sections[1], containsString("Joueur"));

            // Vérifier que le deuxième affichage est différent du premier (plus d'XP)
            assertThat(sections[0], containsString("XP totale : 15"));
            assertThat(sections[1], containsString("XP totale : 35"));
        } finally {
            System.setOut(originalOut);
        }
    }
    
}
