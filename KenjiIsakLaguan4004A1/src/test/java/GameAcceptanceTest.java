import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import java.io.PrintWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }
    @Test
    @DisplayName("A-TEST-002: Scenario 2 Round shuffles and deals to the Players and displays their initial hand and health.")
    void ATEST_002(){
        String[] testPlayersNames = {"1","2","3"};
        TournamentGame testGame = new TournamentGame(3, testPlayersNames,50);

        testGame.distributePlayersHands();

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
}