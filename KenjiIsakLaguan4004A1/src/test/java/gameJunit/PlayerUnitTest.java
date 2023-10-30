package gameJunit;

import game.Card;
import game.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerUnitTest {
    @Test
    @DisplayName("U-TEST-004: Test if game.Player initializes correctly.")
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

        assertEquals("game.Player Test's Hand: [Sw(2),Ap(0),Al(1)]", testPlayer.displayHand());
    }
    @Test
    @DisplayName("U-TEST-016: Test if a game.Player properly adds the melee deck to their injury deck and returns the right number of damage accumulated for that melee.")
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
    @Test
    @DisplayName("U-TEST-017: Test if a game.Player properly takes damage.")
    void testPlayerTakeDmg(){
        List<Card> testMeleeDeck = new ArrayList<>();//only add 3 cards ease of testing
        testMeleeDeck.add(new Card("Basic","Swords", 1));//5 dmg
        testMeleeDeck.add(new Card("Apprentice"));//5 dmg
        testMeleeDeck.add(new Card("Alchemy",1));//5 dmg
        //= 15 dmg pts

        Player testPlayer = new Player("Test", 100);
        testPlayer.addToInjuryDeck(testMeleeDeck);
        testPlayer.takeDmg();//100-15 = 85

        assertEquals(85,testPlayer.getHealthPoints());
    }
    @Test
    @DisplayName("U-TEST-025: Test if game.Player's card is discarded from their deck properly when shamed.")
    void testShamePlayerDiscard(){
        Player testPlayer = new Player("Test",50);

        Card discardCard = new Card("Basic","Swords",1);
        Card stayCard = new Card("Merlin");
        testPlayer.addToHand(discardCard);
        testPlayer.addToHand(stayCard);

        int testPlayerDiscardInputValid = 0;//Simulates Input saved into cardIndexSelected
        testPlayer.shamed(testPlayerDiscardInputValid);

        assertFalse(testPlayer.getDeckInHand().contains(discardCard));
        assertTrue(testPlayer.getDeckInHand().contains(stayCard));
    }
    @Test
    @DisplayName("U-TEST-026: Test if game.Player's Health is subtracted properly when shamed.")
    void testShamePlayerHealth(){
        Player testPlayer = new Player("Test",50);

        Card discardCard = new Card("Basic","Swords",1);
        Card stayCard = new Card("Merlin");
        testPlayer.addToHand(discardCard);
        testPlayer.addToHand(stayCard);

        int testPlayerDiscardInputValid = 0;//Simulates Input saved into cardIndexSelected
        testPlayer.shamed(testPlayerDiscardInputValid);

        assertEquals(45,testPlayer.getHealthPoints());
    }
    @Test
    @DisplayName("U-TEST-028: Test if game.Player's Hand is updated correctly when playing a card.")
    void testPlayCard(){
        Player testPlayer = new Player("Test",50);
        testPlayer.addToHand(new Card("Merlin"));
        testPlayer.playCard(0);

        assertFalse(testPlayer.getDeckInHand().contains(new Card("Merlin")));
    }
}