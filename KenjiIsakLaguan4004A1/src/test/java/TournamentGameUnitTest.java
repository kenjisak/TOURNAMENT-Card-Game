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
    @Test
    @DisplayName("U-TEST-009: Test Game shuffles the cards properly.")
    void testCardShuffle(){
        TournamentGame game = new TournamentGame(3, new String[]{"1", "2", "3"},50);

        String beforeShuffle = game.gameDeck.toString();
        game.shuffleDeck();
        String afterShuffle = game.gameDeck.toString();

        assertEquals(80,game.gameDeck.size());
        assertNotEquals(beforeShuffle,afterShuffle);
    }
    @Test
    @DisplayName("U-TEST-010: Test Game Stores Players inputted names properly.")
    void testPlayersNameInput(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};

        if(TournamentGame.checkInputNumPlayers(testPlayerNum)){//if valid Number of Players
            TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);

            for (int i = 0; i < testPlayerNum; i++) {//test if names were stored correctly
                assertEquals(testPlayersNames[i],testGame.players[i].getName());
            }
        }else{//should return false to ask input for Valid Player Num Again
            assertFalse(TournamentGame.checkInputNumPlayers(testPlayerNum));
        }
    }
    @Test
    @DisplayName("U-TEST-011: Test Rounds start with an Initial Leader based on Order of Players Inputted Names.")
    void testRoundInitLeader(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);

        for (int i = 0; i < testPlayerNum * 3; i++) {//tests for 9 rounds passing, for when Round Leader should loop back to the 1st Player
            assertEquals(testPlayersNames[i % 3],testGame.currLeader);
            testGame.updateRoundLeader();
        }
    }
    @Test
    @DisplayName("U-TEST-012: Test if each player receives 12 cards after the game deck is shuffled.")
    void testPlayerGetsStartingHand(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);

        testGame.playRound();
        for (int i = 0; i < testPlayerNum ; i++) {//check if each player has 12 cards
            assertEquals(12,testGame.players[i].getDeckInHand().size());
        }
    }
}