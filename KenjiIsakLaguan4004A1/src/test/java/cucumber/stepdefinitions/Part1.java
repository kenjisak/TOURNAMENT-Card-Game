package cucumber.stepdefinitions;
import game.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class Part1 {
    public TournamentGame testGame;
    public StringWriter output;
    public String plyrsInput;
    @Given("there are {int} players in a game")
    public void thereAreFourPlayersInAGame(int numofPlyrs) {
        String[] plyrNames = new String[numofPlyrs];
        for (int i = 0; i < numofPlyrs ; i++) {
            plyrNames[i] = Integer.toString(i + 1);
        }
        testGame = new TournamentGame(numofPlyrs,plyrNames,50);;
        output = new StringWriter();
        plyrsInput = "";
        testGame.distributePlayersHands();//Simulates start of "round/1 melee" by handing their cards out
    }
    @When("player {int} plays {string} card")
    public void testing1(int plyrNum, String plyrCard) {
        Card card;
        if (plyrCard.contains("Basic")) {
            String[] strPlyrcard = plyrCard.split("_");
            card = new Card(strPlyrcard[0], strPlyrcard[1], Integer.parseInt(strPlyrcard[2]));
            plyrsInput += "0\n";
        }else if (plyrCard.contains("Merlin") || plyrCard.contains("Apprentice")){
            String[] strPlyrcard = plyrCard.split("_");
            card = new Card(strPlyrcard[0]);
            plyrsInput += "0\n" + strPlyrcard[1] + "\n";//value of MeAp card
        }else {
            card = new Card("Merlin");
        }

        testGame.players[plyrNum - 1].rigDelHand();
        testGame.players[plyrNum - 1].addToHand(card);
    }

    @Then("the loser will be {string} and receives {int} injury points")
    public void loser(String loser, int injPts) {
        testGame.playMelee(new Scanner(plyrsInput),new PrintWriter(output));
        assertEquals(loser, testGame.loser.getName());
        assertEquals(injPts, testGame.loser.getTotalInjuryPoints());
    }
}
