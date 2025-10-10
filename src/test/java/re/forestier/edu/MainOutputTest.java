package re.forestier.edu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
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
    @DisplayName("Main doit tester l'effet de addMoney sur l'affichage")
    void main_testAddMoneyEffect() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            Main.main(new String[]{});
            String printed = out.toString();
            
            assertThat(printed, containsString("Niveau : 2"));
            assertThat(printed, containsString("Niveau : 3"));
            assertThat(printed, containsString("XP totale : 15"));
            assertThat(printed, containsString("XP totale : 35"));
        } finally {
            System.setOut(originalOut);
        }
    }
    
}
