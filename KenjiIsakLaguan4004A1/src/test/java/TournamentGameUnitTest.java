import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TournamentGameUnitTest {
    @Test
    @DisplayName("U-TEST-005: Test if a Tournament Game initializes correctly, except the Game Deck.")
    void testGameCreation(){
        TournamentGame testGame = new TournamentGame(3,new String[]{"1", "2", "3"},50);

        assertEquals(3,testGame.players.length);
        assertEquals("1",testGame.currLeader);
        assertEquals(0,testGame.currMeleeCardsPlayed.size());
        assertEquals(1,testGame.roundNum);

        for (int i = 0; i < 3; i++) {//test if names were correct, dont need to check the rest prev UTESTs covered it
            assertEquals(String.valueOf(i + 1),testGame.players[i].getName());
        }
    }
    @Test
    @DisplayName("U-TEST-006: Test if a Tournament's Game Deck initializes correctly.")
    void testGameCreationGameDeck(){
        TournamentGame testGame = new TournamentGame(3,new String[]{"1", "2", "3"},50);

        int swordsCount = 0, arrowsCount = 0, sorceryCount = 0, deceptionCount = 0;
        int merlinCount = 0, apprenticeCount = 0, alchemyCount = 0;

        for (Card testCard: testGame.gameDeck){
            if(Objects.equals(testCard.getSuit(), "Swords")){ swordsCount++; }
            if(Objects.equals(testCard.getSuit(), "Arrows")){ arrowsCount++; }
            if(Objects.equals(testCard.getSuit(), "Sorcery")){ sorceryCount++; }
            if(Objects.equals(testCard.getSuit(), "Deception")){ deceptionCount++; }

            if(Objects.equals(testCard.getType(), "Merlin")){ merlinCount++; }
            if(Objects.equals(testCard.getType(), "Apprentice")){ apprenticeCount++; }
            if(Objects.equals(testCard.getType(), "Alchemy")){ alchemyCount++; }
        }
        assertEquals(15,swordsCount);
        assertEquals(15,arrowsCount);
        assertEquals(15,sorceryCount);
        assertEquals(15,deceptionCount);
        assertEquals(3,merlinCount);
        assertEquals(2,apprenticeCount);
        assertEquals(15,alchemyCount);
    }
    @Test
    @DisplayName("U-TEST-007: Test user input for number of players is entered and stored properly.")
    void testNumPlayersInput(){
        assertFalse(TournamentGame.checkInputNumPlayers(2));//checks lower than 3
        assertFalse(TournamentGame.checkInputNumPlayers(6));//checks higher than 5

        for (int i = 3; i <= 5 ; i++) {//checks all valid inputs
            assertTrue(TournamentGame.checkInputNumPlayers(i));
        }
    }
    @Test
    @DisplayName("U-TEST-008: Test Game sets Players initial HP to a Non-Negative Value.")
    void testValidHpInit(){
        TournamentGame testGameHPzero = new TournamentGame(3,new String[]{"1", "2", "3"},0);
        for (int i = 0; i < 3; i++) {
            assertEquals(50,testGameHPzero.players[i].getHealthPoints());
        }

        TournamentGame testGameHPneg = new TournamentGame(3,new String[]{"1", "2", "3"},-5);
        for (int i = 0; i < 3; i++) {
            assertEquals(50,testGameHPneg.players[i].getHealthPoints());
        }

        TournamentGame testGameHPpos = new TournamentGame(3,new String[]{"1", "2", "3"},100);
        for (int i = 0; i < 3; i++) {
            assertEquals(100,testGameHPpos.players[i].getHealthPoints());
        }
    }
}