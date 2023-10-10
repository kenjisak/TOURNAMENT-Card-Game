import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerUnitTest {
    @Test
    @DisplayName("U-TEST-004: Test if Player initializes correctly.")
    void testPlayerCreation(){
        Player testPlayer = new Player("Test",50);

        assertEquals("Test", testPlayer.getName());
        assertEquals(50,testPlayer.getHealthPoints());
        assertEquals(0,testPlayer.getTotalInjuryPoints());
        assertEquals(0,testPlayer.getDeckInHand().size());
        assertEquals(0,testPlayer.getInjuryDeck().size());
    }
}
