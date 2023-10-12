import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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

        testGame.distributePlayersHands();
        for (int i = 0; i < testPlayerNum ; i++) {//check if each player has 12 cards
            assertEquals(12,testGame.players[i].getDeckInHand().size());
        }
    }
    @Test
    @DisplayName("U-TEST-015: Test if each player's hand and health points is displayed after being given.")
    void testPlayersHandsHPDisplay(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);

        //simulates distributePlayersHands but with only 1 card for a player's hand for ease of testing
        testGame.players[0].addToHand(new Card("Basic","Deception",12));
        testGame.players[1].addToHand(new Card("Basic","Sorcery",6));
        testGame.players[2].addToHand(new Card("Alchemy",3));

        String expectedOutput = """

                Player 1's Hand: [De(12)] Health Points: 50
                Player 2's Hand: [So(6)] Health Points: 50
                Player 3's Hand: [Al(3)] Health Points: 50
                """;

        assertEquals(expectedOutput, testGame.displayAllPlayersHandsHP());
    }
    @Test
    @DisplayName("U-TEST-018: Test if All Players properly receive damage at the end of a round.")
    void testPlayerTakeDmg(){
        List<Card> testMeleeDeck1 = new ArrayList<>();//only add 3 cards ease of testing
        List<Card> testMeleeDeck2 = new ArrayList<>();
        List<Card> testMeleeDeck3 = new ArrayList<>();
        testMeleeDeck1.add(new Card("Merlin"));//25 dmg
        testMeleeDeck2.add(new Card("Basic","Arrows", 8));//10 dmg
        testMeleeDeck3.add(new Card("Alchemy",10));//5 dmg

        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);

        testGame.players[0].addToInjuryDeck(testMeleeDeck1);//50-25 = 25
        testGame.players[1].addToInjuryDeck(testMeleeDeck2);//50-10 = 40
        testGame.players[2].addToInjuryDeck(testMeleeDeck3);//50-5 = 45

        testGame.playersTakeDmg();
        assertEquals(25,testGame.players[0].getHealthPoints());
        assertEquals(40,testGame.players[1].getHealthPoints());
        assertEquals(45,testGame.players[2].getHealthPoints());
    }
    @Test
    @DisplayName("U-TEST-019: Test if each player's updated health points is displayed properly after taking damage at the end of a round.")
    void testPlayersHPDisplay(){
        List<Card> testMeleeDeck1 = new ArrayList<>();//only add 3 cards ease of testing
        List<Card> testMeleeDeck2 = new ArrayList<>();
        List<Card> testMeleeDeck3 = new ArrayList<>();
        testMeleeDeck1.add(new Card("Merlin"));//25 dmg
        testMeleeDeck2.add(new Card("Basic","Arrows", 8));//10 dmg
        testMeleeDeck3.add(new Card("Alchemy",10));//5 dmg

        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);

        testGame.players[0].addToInjuryDeck(testMeleeDeck1);//50-25 = 25
        testGame.players[1].addToInjuryDeck(testMeleeDeck2);//50-10 = 40
        testGame.players[2].addToInjuryDeck(testMeleeDeck3);//50-5 = 45

        testGame.playersTakeDmg();

        String expectedOutput = """

                Player 1 HP: 25
                Player 2 HP: 40
                Player 3 HP: 45
                """;
        assertEquals(expectedOutput, testGame.displayAllPlayersHP());
    }
    @Test
    @DisplayName("U-TEST-020: Test if a Player is 0 or below HP that it ends the game.")
    void testCheckDeadPlayers(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,25);

        assertFalse(testGame.checkDeadPlayers());// no one is dead,still at Init HP

        List<Card> testMeleeDeck3 = new ArrayList<>();
        testMeleeDeck3.add(new Card("Merlin"));//25 dmg
        testGame.players[2].addToInjuryDeck(testMeleeDeck3);//25-25 = 0
        testGame.playersTakeDmg();

        assertTrue(testGame.checkDeadPlayers());
    }
    @Test
    @DisplayName("U-TEST-021: Test if the Game finds the Correct Winners and displays them.")
    void testFindWinners(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,25);

        List<Card> testMeleeDeck3 = new ArrayList<>();
        testMeleeDeck3.add(new Card("Merlin"));//25 dmg
        testGame.players[2].addToInjuryDeck(testMeleeDeck3);//25-25 = 0
        testGame.playersTakeDmg();

        String expectedOutput = """

                Player 1 HP: 25
                Player 2 HP: 25
                Player 3 HP: 0
                The winner(s) of the Tournament is: 1 2""";
        assertEquals(expectedOutput, testGame.endGame());//tests several winners, 1 winner

        testGame.players[1].addToInjuryDeck(testMeleeDeck3);//25-25 = 0
        testGame.playersTakeDmg();
        String expectedOutput2 = """

                Player 1 HP: 25
                Player 2 HP: 0
                Player 3 HP: 0
                The winner(s) of the Tournament is: 1""";
        assertEquals(expectedOutput2, testGame.endGame());

        testGame.players[0].addToInjuryDeck(testMeleeDeck3);//25-25 = 0
        testGame.playersTakeDmg();
        String expectedOutput3 = """

                Player 1 HP: 0
                Player 2 HP: 0
                Player 3 HP: 0
                There are no winners of the Tournament.""";
        assertEquals(expectedOutput3, testGame.endGame());
    }
    @Test
    @DisplayName("U-TEST-022: Test Melee starts with the correct Leader.")
    void testMeleeInitLeader(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);

        for (int i = 0; i < testPlayerNum; i++) {
            assertEquals(i,testGame.findMeleeLeaderIndex());
            testGame.updateRoundLeader();//simulates changing the leader to 1,2,3 since loser of melee isnt implemented yet
        }
    }
    @Test
    @DisplayName("U-TEST-023: Test if Melee can find Player(that's not the leader) if has Any Playable Cards properly.")
    void testAnyPlayableCards(){
        String[] testPlayersNames = {"1","2","3","4"};
        TournamentGame testGame = new TournamentGame(4, testPlayersNames,50);

        testGame.players[1].addToHand(new Card("Merlin"));
        assertTrue(testGame.checkAnyPlayableCards(1,"Arrows"));

        testGame.players[2].addToHand(new Card("Basic","Swords",1));
        assertFalse(testGame.checkAnyPlayableCards(2,"Sorcery"));

        testGame.players[3].addToHand(new Card("Alchemy",1));
        assertTrue(testGame.checkAnyPlayableCards(3,"Deception"));
    }
    @Test
    @DisplayName("U-TEST-024: Test if input is validated correctly when Player is choosing a Card.")
    void testValidateCardInput(){
        String[] testPlayersNames = {"1","2","3"};
        TournamentGame testGame = new TournamentGame(3, testPlayersNames,50);

        testGame.players[2].addToHand(new Card("Basic","Swords",1));

        int testPlayerCardInputValid = 0;//Simulates Input saved into cardIndexSelected
        assertTrue(testGame.checkCardInput(2,testPlayerCardInputValid));

        int testPlayerCardInputInValid = 1;//Out of Bounds simulation, causing while loop restart and ask input again
        assertFalse(testGame.checkCardInput(2,testPlayerCardInputInValid));
    }
    @Test
    @DisplayName("U-TEST-027: Test if a Shamed Player ends or does not end a game properly.")
    void testShamedPlayerEndGame(){
        String[] testPlayersNames = {"1","2","3"};
        TournamentGame testNotEndGame = new TournamentGame(3, testPlayersNames,50);

        Card discardCard = new Card("Basic","Swords",1);
        testNotEndGame.players[0].addToHand(discardCard);

        int testPlayerDiscardInputValid = 0;//Simulates Input saved into cardIndexSelected
        boolean NotEndGame = !testNotEndGame.shamePlayer(0,testPlayerDiscardInputValid);//returns if the player is alive
        assertFalse(NotEndGame);

        TournamentGame testEndGame = new TournamentGame(3, testPlayersNames,5);
        testEndGame.players[0].addToHand(discardCard);

        boolean endGame = !testEndGame.shamePlayer(0,testPlayerDiscardInputValid);//returns if the player is alive
        assertTrue(endGame);
    }
    @Test
    @DisplayName("U-TEST-029: Test if the Leader for the melee, Basic Weapon Card's Suit Gets set to the Current suit.")
    void testMeleeSuitSet(){
        String[] testPlayersNames = {"1","2","3"};
        TournamentGame testGame = new TournamentGame(3, testPlayersNames,50);

        testGame.players[0].addToHand(new Card("Basic","Swords",1));
        Card chosenCard = testGame.players[0].getDeckInHand().get(0);

        testGame.setMeleeSuit(chosenCard);
        assertEquals("Swords",testGame.currSuit);
    }
    @Test
    @DisplayName("U-TEST-030: Test if the Leader for the melee properly checks a valid suit they input for a Merlin/Apprentice card.")
    void testMeleeMeApSuitSet(){
        String[] testPlayersNames = {"1","2","3"};
        TournamentGame testGame = new TournamentGame(3, testPlayersNames,50);

        String validSuitInput = "Swords";
        assertTrue(testGame.checkValidSuitInput(validSuitInput));

        String inValidSuitInput = "Deceptionsss";
        assertFalse(testGame.checkValidSuitInput(inValidSuitInput));
    }
    @Test
    @DisplayName("U-TEST-032: Test if the Leader for the melee properly checks valid and invalid value they input for a Merlin/Apprentice card.")
    void testMeleeMeApValueSet(){
        String[] testPlayersNames = {"1","2","3"};
        TournamentGame testGame = new TournamentGame(3, testPlayersNames,50);

        int validSuitInput = 1;
        assertTrue(testGame.checkValidValue(validSuitInput));

        int inValidSuitInput = 0;
        assertFalse(testGame.checkValidValue(inValidSuitInput));
    }
    @Test
    @DisplayName("U-TEST-033: Test if Melee Leader has Non Alchemy Playable Cards properly when attempting to start with an Alchemy.")
    void testNonAlPlayableCards(){
        String[] testPlayersNames = {"1","2","3"};
        TournamentGame testGame = new TournamentGame(3, testPlayersNames,50);

        testGame.players[0].addToHand(new Card("Alchemy",1));
        assertFalse(testGame.checkNonAlPlayableCards(0));//doesn't have any other Non Alchemy Cards

        testGame.players[0].addToHand(new Card("Merlin"));
        testGame.players[0].addToHand(new Card("Basic","Swords",1));
        assertTrue(testGame.checkNonAlPlayableCards(0));//does have other Non Alchemy Cards
    }
}