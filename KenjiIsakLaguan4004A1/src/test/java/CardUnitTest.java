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
    @Test
    @DisplayName("U-TEST-003: Test if an Alchemy Card initializes correctly.")
    void testCardCreationAl(){
        Card testAl = new Card("Alchemy", 1);
        assertEquals("Alchemy", testAl.getType());
        assertEquals("", testAl.getSuit());
        assertEquals(1,testAl.getValue());
        assertEquals(false,testAl.getIsPoisoned());
        assertEquals(5,testAl.getInjuryPoints());
    }
    @Test
    @DisplayName("U-TEST-013: Test if a Card Displays Properly.")
    void testCardDisplay(){
        Card testCardBasic = new Card("Basic","Swords", 1);
        assertEquals("Sw(1)", testCardBasic.displayCard());

        Card testCardMeAp = new Card("Merlin");
        assertEquals("Me(0)", testCardMeAp.displayCard());

        Card testCardAl = new Card("Alchemy",1);
        assertEquals("Al(1)", testCardAl.displayCard());
    }
    @Test
    @DisplayName("U-TEST-031: Test if a Merlin/Apprentice Card set its suit properly.")
    void testMeApSuitSet(){
        Card testCardMeAp = new Card("Merlin");
        testCardMeAp.setSuit("Swords");
        assertEquals("Swords", testCardMeAp.getSuit());
    }
}
