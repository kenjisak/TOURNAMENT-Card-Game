import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    @Test
    @DisplayName("U-TEST-014: Test if a Players Hand Displays Properly.")
    void testCardDisplay(){
        Player testPlayer = new Player("Test", 50);

        Card testCardSw2 = new Card("Basic","Swords",2);
        Card testCardAp0 = new Card("Apprentice");
        Card testCardAl1 = new Card("Alchemy",1);

        testPlayer.addToHand(testCardSw2);
        testPlayer.addToHand(testCardAp0);
        testPlayer.addToHand(testCardAl1);

        assertEquals("Player Test's Hand: [Sw(2),Ap(0),Al(1)]", testPlayer.displayHand());
    }
    @Test
    @DisplayName("U-TEST-016: Test if a Player properly adds the melee deck to their injury deck and returns the right number of damage received for that melee.")
    void testInjuryDeckReceive(){
        //5 dmg
        Card testCardBasic = new Card("Basic","Swords", 1);
        //25 dmg
        Card testCardMeAp = new Card("Merlin");//dont need to set a value since all MeAp Cards are 25 dmg pts
        //5 dmg
        Card testCardAl = new Card("Alchemy",1);
        //= 35 dmg pts

        List<Card> testMeleeDeck = new ArrayList<>();
        testMeleeDeck.add(testCardBasic);
        testMeleeDeck.add(testCardMeAp);
        testMeleeDeck.add(testCardAl);

        Player testPlayer = new Player("Test", 100);
        int dmgForCurrMelee = testPlayer.addToInjuryDeck(testMeleeDeck);

        assertEquals(testPlayer.getInjuryDeck(),testMeleeDeck);
        assertEquals(35,dmgForCurrMelee);
    }
}