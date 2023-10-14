import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}