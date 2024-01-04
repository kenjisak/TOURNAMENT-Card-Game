package cucumber.stepdefinitions;

import game.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class Part1 {
    public TournamentGame testGame;
    public StringWriter output;
    public String plyrsInput;

    @Given("there are {int} players in a game with {int} hp each and players hands were handed out")
    public void thereAreFourPlayersInAGame(int numofPlyrs,int HP) {
        String numPlyrs = numofPlyrs + "\n";//user input for num of players

        String plyrNames = "";
        for (int i = 0; i < numofPlyrs; i++) {//user input for names of players
            plyrNames += (i + 1) + "\n";
        }

        String initHP = HP + "\n";//user input for initHP of players

        //initializes the game with simulated user input
        output = new StringWriter();
        TournamentGame.getInitInfo(new Scanner(numPlyrs + plyrNames + initHP), new PrintWriter(output));
        testGame = TournamentGame.tournamentGame;
        System.out.println();

        testGame.distributePlayersHands();//Simulates start of "round/1 melee" by handing their cards out
        plyrsInput = "";
    }

    @And("player {int} hand is rigged with just {string}")
    public void playerHandRigged(int plyrNum, String plyrCard) {
        Card card;
        if (plyrCard.contains("Merlin") || plyrCard.contains("Apprentice")) {
            String[] strPlyrcard = plyrCard.split("_");
            card = new Card(strPlyrcard[0]);
        } else if (plyrCard.contains("Alchemy")) {
            String[] strPlyrcard = plyrCard.split("_");
            card = new Card(strPlyrcard[0], Integer.parseInt(strPlyrcard[1]));
        } else {//is a basic card
            String[] strPlyrcard = plyrCard.split("_");
            card = new Card("Basic", strPlyrcard[0], Integer.parseInt(strPlyrcard[1]));
        }

        testGame.players[plyrNum - 1].rigHand(Collections.singletonList(card));
    }

    @When("player {int} plays {string} card")
    public void playerPlaysACard(int plyrNum, String plyrCard) {
        if (plyrCard.contains("Merlin") || plyrCard.contains("Apprentice")) {
            String[] strPlyrcard = plyrCard.split("_");
            if (Objects.equals(testGame.currLeader, testGame.players[plyrNum - 1].getName())) {//if player is the Leader and playing MeAp
                plyrsInput += "0\n" + strPlyrcard[1] + "\n" + strPlyrcard[2] + "\n";//Suit of MeAp card, then value
            } else {
                plyrsInput += "0\n" + strPlyrcard[1] + "\n";//value of MeAp card
            }
        } else {//basic and alchemy cards, no input needed just index
            plyrsInput += "0\n";
        }
    }

    @Then("the loser will be {string}")
    public void loserWillBeCorrectAndReceivesInjuryPoints(String loserName) {
        testGame.playMelee(new Scanner(plyrsInput), new PrintWriter(output));

        if (Objects.equals(loserName, "null")) {//loser is set to null if no losers in a melee
            assertNull(testGame.loser);
        } else {//there is a loser for this case from the table
            assertEquals(loserName, testGame.loser.getName());
        }
    }

    @Then("{string} receives {int} injury points")
    public void loserReceivesInjuryPointsInjuryPoints(String loserName, int injPts) {
        if (Objects.equals(loserName, "null")) {//loser is set to null if no losers in a melee
            for (int i = 0; i < testGame.players.length; i++) {//checks that nobody received any injury points
                assertEquals(injPts, testGame.players[i].getTotalInjuryPoints());
            }
        } else {//there is a loser for this case from the table
            assertEquals(injPts, testGame.loser.getTotalInjuryPoints());
        }
    }
}
