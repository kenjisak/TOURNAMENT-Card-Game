package cucumber.stepdefinitions;

import game.TournamentGame;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class Part2 {
    public TournamentGame testGame;
    public StringWriter output;
    public String setUpInput;
    @Given("the game starts")
    public void theGameStarts() {
        testGame = null;
        output = new StringWriter();
        setUpInput = "";
    }

    @When("user enters {int} for number of players")
    public void userEntersInvalidNumberOfPlayers(int numPlayers) {
        output = new StringWriter();
        TournamentGame.processNumPlyrInput(new Scanner(numPlayers + "\n"),new PrintWriter(output));
        if(numPlayers >= 3 && numPlayers <=5){
            setUpInput += numPlayers + "\n";
        }
    }

    @When("user enters {string} name for player {int}")
    public void userEntersEmptyNameForPlayer(String plyrName, int plyrNum) {
        output = new StringWriter();
        TournamentGame.processNamePlyrInput(new Scanner(plyrName + "\n"),new PrintWriter(output),plyrNum - 1);
        if(!plyrName.isEmpty()){
            setUpInput += plyrName + "\n";
        }
    }

    @When("user enters {int} for initial health points")
    public void userEntersForInitialHealthPoints(int initHP) {
        output = new StringWriter();
        TournamentGame.processHPInput(new Scanner(initHP + "\n"),new PrintWriter(output));
        if(initHP > 0){
            setUpInput += initHP + "\n";
        }
    }

    @Then("user receives invalid {string} message")
    public void userReceivesInvalidNameMessage(String invalidMessage) {
        assertTrue(output.toString().contains(invalidMessage));
    }

    @Given("the round starts and distributes each players cards")
    public void theRoundStartsAndDistributesEachPlayersCards() {
        TournamentGame.getInitInfo(new Scanner(setUpInput), new PrintWriter(output));
        testGame = TournamentGame.tournamentGame;
        System.out.println();
        testGame.distributePlayersHands();
    }
}
