package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.UpdatePlayer;

public class UpdatePlayerTests {
    @Test
    @DisplayName("Le constructeur UpdatePlayer doit être testé")
    void testUpdatePlayerConstructor() {
        UpdatePlayer updatePlayer = new UpdatePlayer();
        assertNotNull(updatePlayer);
    }
}
