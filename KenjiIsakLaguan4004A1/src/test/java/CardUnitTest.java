import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardUnitTest {

    @Test
    @DisplayName("U-TEST-001: Test if a Basic Weapon Card initializes correctly, poison and none poisoned.")
    void testCardCreation(){
        Card testBasicNonPoisoned = new Card("Basic", "Swords", 1);
        assertEquals("Basic", testBasicNonPoisoned.getType());
        assertEquals("Swords", testBasicNonPoisoned.getSuit());
        assertEquals(1,testBasicNonPoisoned.getValue());
        assertEquals(false,testBasicNonPoisoned.getIsPoisoned());
        assertEquals(5,testBasicNonPoisoned.getInjuryPoints());

        Card testBasicPoisoned = new Card("Basic", "Arrows", 8);
        assertEquals("Basic", testBasicPoisoned.getType());
        assertEquals("Arrows", testBasicPoisoned.getSuit());
        assertEquals(8,testBasicPoisoned.getValue());
        assertEquals(true,testBasicPoisoned.getIsPoisoned());
        assertEquals(10,testBasicPoisoned.getInjuryPoints());
    }
    @Test
    @DisplayName("U-TEST-002: Test if a Merlin or Apprentice Card initializes correctly.")
    void testCardCreationMeAp(){
        Card testMeAp = new Card("Merlin");
        assertEquals("Merlin", testMeAp.getType());
        assertEquals("", testMeAp.getSuit());
        assertEquals(0,testMeAp.getValue());
        assertEquals(false,testMeAp.getIsPoisoned());
        assertEquals(25,testMeAp.getInjuryPoints());
    }
}
