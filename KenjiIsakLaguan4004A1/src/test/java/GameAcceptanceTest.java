import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameAcceptanceTest {
    /*
     * A-TEST 001:
     * > User enters invalid inputs for num players: 2, 6
     * > then valid for num players: 3
     * > First Player empty input for name : \n
     * > then valid for names : A, B, C
     * > Tournament game is initialized with 3 players named A, B, C
     */
    @Test
    @DisplayName("A-TEST-001: Scenario 1 Initialize the Game with given Number of Players and Player Names.")
    void ATEST_001(){
        TournamentGame.getInitInfo(new Scanner("2\n6\n3\n\nA\nB\nC"),new PrintWriter(System.out));
        assertEquals(3,TournamentGame.tournamentGame.players.length);
        assertEquals("A",TournamentGame.tournamentGame.players[0].getName());
        assertEquals("B",TournamentGame.tournamentGame.players[1].getName());
        assertEquals("C",TournamentGame.tournamentGame.players[2].getName());
        for (int i = 0; i < TournamentGame.tournamentGame.players.length; i++) {
            assertEquals(100,TournamentGame.tournamentGame.players[i].getHealthPoints());
        }
        assertEquals(3,TournamentGame.tournamentGame.players.length);
        assertEquals("A",TournamentGame.tournamentGame.currLeader);
        assertEquals(0,TournamentGame.tournamentGame.currMeleeCardsPlayed.size());
        assertEquals(1,TournamentGame.tournamentGame.roundNum);
        assertEquals(80,TournamentGame.tournamentGame.gameDeck.size());
    }
    @Test
    @DisplayName("A-TEST-002: Scenario 2 Round shuffles and deals to the Players and displays their initial hand and health.")
    void ATEST_002(){
        String[] testPlayersNames = {"1","2","3"};
        TournamentGame testGame = new TournamentGame(3, testPlayersNames,50);

        String beforeShuffle = testGame.gameDeck.toString();
        testGame.distributePlayersHands();
        String afterShuffle = testGame.gameDeck.toString();
        assertEquals(80,testGame.gameDeck.size());
        assertNotEquals(beforeShuffle,afterShuffle);

        assertEquals(80,testGame.gameDeck.size());
        for (Player currPlayer: testGame.players) {//check each player handed 12 cards
            assertEquals(12, currPlayer.getDeckInHand().size());
        }

        for (Player currPlayer: testGame.players) {//clears their hands for ease of testing printing their initial hand
            currPlayer.getDeckInHand().clear();
        }
        testGame.players[0].addToHand(new Card("Basic","Deception",12));
        testGame.players[1].addToHand(new Card("Basic","Sorcery",6));
        testGame.players[2].addToHand(new Card("Alchemy",3));

        String expectedOutput = """

                Player 1's Hand: [De(12)] Health Points: 50
                Player 2's Hand: [So(6)] Health Points: 50
                Player 3's Hand: [Al(3)] Health Points: 50""";

        assertEquals(expectedOutput, testGame.displayAllPlayersHandsHP());
    }
    /*
     * A-TEST 003:
     * > Round 1 Leader = 1st Player
     * > Round 2 Leader = 2nd Player
     * > Round 3 Leader = 3rd Player
     * > Round 4 Leader = 1st Player(Loop back inorder)
     * > ....... so on
     */
    @Test
    @DisplayName("A-TEST-003: Scenario 3 When Round number exceeds Number of Players, the initial leader will be loop back through the players.")
    void ATEST_003(){
        int testPlayerNum3 = 3;
        String[] testPlayersNames3 = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum3, testPlayersNames3,50);

        for (int i = 0; i < testPlayerNum3 * testPlayerNum3; i++) {//tests for 9 rounds passing, for when Round Leader should loop back to the 1st Player
            assertEquals(testPlayersNames3[i % testPlayerNum3],testGame.currLeader);
            testGame.updateRoundLeader();
        }

        int testPlayerNum4 = 4;
        String[] testPlayersNames4 = {"1", "2", "3", "4"};
        testGame = new TournamentGame(testPlayerNum4, testPlayersNames4,50);

        for (int i = 0; i < testPlayerNum4 * testPlayerNum4; i++) {//tests for 16 rounds passing, for when Round Leader should loop back to the 1st Player
            assertEquals(testPlayersNames4[i % testPlayerNum4],testGame.currLeader);
            testGame.updateRoundLeader();
        }
    }
    /*
     * A-TEST 004:
     * > Leader Plays Sword 2
     * > 2nd Plays Merlin and inputs 3
     * > 3rd Plays Matching Sword 1
     * > Loser will be the Player 3 as it's the lowest card
     * > Player 3 will accumulate 35 points from that melee, and be the next Melee Leader
     * > Round is also Over Since 1 melee only
     */
    @Test
    @DisplayName("A-TEST-004: Scenario 4 Leader sets the Suit with a Basic Card, following players, play a Merlin and Basic Card that matches a suit. All valid inputs, No matching values, and loser is determined with just lowest card")
    void ATEST_004(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);
        Card ply1Card = new Card("Basic","Swords",2);
        Card ply2Card = new Card("Merlin");
        Card lowestCard = new Card("Basic","Swords",1);

        testGame.players[0].addToHand(ply1Card);
        testGame.players[1].addToHand(ply2Card);
        testGame.players[2].addToHand(lowestCard);

        testGame.playMelee(new Scanner("0\n0\n3\n0\n"),new PrintWriter(System.out));

        assertEquals("Basic",testGame.currMeleeCardsPlayed.get(testGame.players[0]).getType());//check Leader card is basic
        assertEquals("Swords", testGame.currSuit);//check check currSuit set to Swords

        assertEquals(3,testGame.currMeleeCardsPlayed.get(testGame.players[1]).getValue());//check merlin value set to 3
        assertEquals(testGame.currSuit,testGame.currMeleeCardsPlayed.get(testGame.players[1]).getSuit());//check merlin suit set to swords/currsuit

        assertEquals(testGame.currSuit,testGame.currMeleeCardsPlayed.get(testGame.players[2]).getSuit());//check card matched currsuit

        assertEquals(testGame.players[2],testGame.findLowestCard(testGame.currMeleeCardsPlayed));//check player 3 had the lowest card
        assertEquals(testGame.loser, testGame.players[2]);//check player 3 is the loser
        assertEquals(testGame.currLeader, testGame.players[2].getName());//check player 3 is the next melee leader

        assertEquals(3,testGame.players[2].getInjuryDeck().size());//check player 3 added melee cards to their injury deck
        assertTrue(testGame.players[2].getInjuryDeck().contains(ply1Card));
        assertTrue(testGame.players[2].getInjuryDeck().contains(ply2Card));
        assertTrue(testGame.players[2].getInjuryDeck().contains(lowestCard));

        testGame.playersTakeDmg();
        assertEquals(15,testGame.players[2].getHealthPoints());//1 melee only in 1 round to test, verify damage taken at end of round
        assertEquals(35,testGame.players[2].addToInjuryDeck(new ArrayList<>(testGame.currMeleeCardsPlayed.values())));//check accumulated dmg points was correct
        String expectedOutput = """

                Player 1 HP: 50
                Player 2 HP: 50
                Player 3 HP: 15""";
        assertEquals(expectedOutput, testGame.displayAllPlayersHP());
    }
    /*
     * A-TEST 005:
     * > Leader Plays Merlin 1 and inputs Sorcery 1
     * > 2nd Plays Apprentice and inputs 1
     * > 3rd forced to Play Alchemy 2
     * > Loser will be the Player 3 with feint step
     * > No health removed at end of melee
     */
    @Test
    @DisplayName("A-TEST-005: Scenario 5 Leader sets the Suit with a Merlin, following players, play a Apprentice and Alchemy. Merlin and Apprentice have matching values. Loser is determined with feint step and lowest card.")
    void ATEST_005(){
        int testPlayerNum = 3;
        String[] testPlayersNames = {"1", "2", "3"};
        TournamentGame testGame = new TournamentGame(testPlayerNum, testPlayersNames,50);
        Card ply1Card = new Card("Merlin");
        Card ply2Card = new Card("Apprentice");
        Card feintStepCard = new Card("Alchemy",2);

        testGame.players[0].addToHand(ply1Card);
        testGame.players[1].addToHand(ply2Card);
        testGame.players[2].addToHand(feintStepCard);

        testGame.playMelee(new Scanner("0\nSorcery\n1\n0\n1\n0"),new PrintWriter(System.out));

        assertEquals("Merlin",testGame.currMeleeCardsPlayed.get(testGame.players[0]).getType());//check Leader card is merlin
        assertEquals("Sorcery", testGame.currSuit);//check curr suit is set to input Sorcery
        assertEquals(1,testGame.currMeleeCardsPlayed.get(testGame.players[0]).getValue());//check merlin value set to 1

        assertEquals("Apprentice",testGame.currMeleeCardsPlayed.get(testGame.players[1]).getType());//check nextplyr card is Apprentice
        assertEquals(1,testGame.currMeleeCardsPlayed.get(testGame.players[1]).getValue());//check apprentice value set to 1
        assertEquals(testGame.currSuit,testGame.currMeleeCardsPlayed.get(testGame.players[1]).getSuit());//check apprentice suit set to sorcery/currsuit

        assertEquals("Alchemy",testGame.currMeleeCardsPlayed.get(testGame.players[2]).getType());//check nextplyr card is Alchemy
        assertFalse(testGame.checkSuitPlayableCards(2));//check if no other playable cards and is forced to
        assertEquals(testGame.currSuit,testGame.currMeleeCardsPlayed.get(testGame.players[1]).getSuit());//check alchemy suit set to sorcery/currsuit

        Map<Player,Card> expectedReturn = new HashMap<>();
        expectedReturn.put(testGame.players[2],feintStepCard);
        assertEquals(expectedReturn,testGame.feintStep());//check alchemy is the only one left after feint step

        assertEquals(3,testGame.players[2].getInjuryDeck().size());//check all melee cards is added to player 3 injury deck
        assertTrue(testGame.players[2].getInjuryDeck().contains(ply1Card));
        assertTrue(testGame.players[2].getInjuryDeck().contains(ply2Card));
        assertTrue(testGame.players[2].getInjuryDeck().contains(feintStepCard));

        assertEquals(50,testGame.players[2].getHealthPoints());//check health isnt deducted at end of melee
    }
}