package cucumber.stepdefinitions;

import game.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Part3 {
    public TournamentGame testGame;
    public StringWriter output;
    public String plyrsInput;
    @Given("Game initializes with {int} hp for {int} players")
    public void scenarioAsetup(int HP, int numofPlyrs){
        testGame = null;
        output = new StringWriter();
        plyrsInput = "";

        String numPlyrs = numofPlyrs + "\n";//user input for num of players

        String plyrNames = "";
        for (int i = 0; i < numofPlyrs; i++) {//user input for names of players
            plyrNames += "P" + (i + 1) + "\n";
        }

        String initHP = HP + "\n";//user input for initHP of players

        //initializes the game with simulated user input
        TournamentGame.getInitInfo(new Scanner(numPlyrs + plyrNames + initHP), new PrintWriter(output));
        testGame = TournamentGame.tournamentGame;
        System.out.println();
    }
    @Given("Round {int} starts, as well as distributes each players cards")
    public void roundStarts(int roundNum) {
        //simulates beginning of round, since we cant call play round directly.
        System.out.println("\nRound " + testGame.roundNum + " Starting... Initial Leader of this round is " + testGame.currLeader);
        testGame.distributePlayersHands();
        System.out.println(testGame.displayAllPlayersHandsHP());//display each player's initial hands
    }
    @When("the Melee {int} starts")
    public void meleeStarts(int meleeNum) {
        System.out.println("\nRound " + testGame.roundNum + ", Melee " + meleeNum + " Starting...");
    }

    @Then("{string} is the current leader")
    public void isTheCurrentLeader(String leaderName) {
        assertEquals(leaderName,testGame.currLeader);
    }

    @Given("player {int} is rigged with {string} cards")
    public void playerIsRiggedWithCards(int playerNum, String hand) {
        List<Card> riggedHand = new ArrayList<>();
        String[] strPlyrcard = hand.split(",");

        for(String currCard: strPlyrcard){
            riggedHand.add(createCard(currCard));
        }

        int playersIndex = playerNum - 1;

        testGame.players[playersIndex].rigHand(riggedHand);
        System.out.println("RIGGED: " + testGame.players[playersIndex].displayHand());
    }

    @When("player {int} plays their {string} card")
    public void playerPlaysTheirCard(int plyrNum, String plyrCard) {
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

    @Then("there is No loser for this melee")
    public void thereIsNoLoserForThisMelee() {
        testGame.playMelee(new Scanner(plyrsInput),new PrintWriter(output));
        plyrsInput = "";//reset input for next melee

        assertNull(testGame.loser);
    }

    @Then("{string} will be the loser for this melee")
    public void willBeTheLoserForThisMelee(String plyrName) {
        testGame.playMelee(new Scanner(plyrsInput),new PrintWriter(output));
        plyrsInput = "";//reset input for next melee

        assertEquals(plyrName, testGame.loser.getName());
    }

    @When("Round {int} is over")
    public void roundIsOver(int roundNum) {
        System.out.println("\nRound " + roundNum + " over...");
        testGame.playersTakeDmg();
        System.out.println(testGame.displayAllPlayersHP());

        testGame.updateRoundLeader();//else update leader and continue round
    }

    @Then("the Game is over with no winners")
    public void theGameIsOverWithNoWinners() {
        String endOutput = "";

        if(testGame.checkDeadPlayers()){//if a players dead then end the game
            endOutput = testGame.endGame();
            System.out.println(endOutput);
        }

        assertTrue(endOutput.contains("no winners"));
    }

    /////////////HELPER FUNCTIONS/////////////
    public Card createCard(String givenCard){
        if (givenCard.contains("Alchemy")){
            int cardValue = Integer.parseInt(givenCard.split("_")[1]);
            return new Card("Alchemy",cardValue);
        } else if (givenCard.contains("Merlin") || givenCard.contains("Apprentice")) {
            String meApType = givenCard.split("_")[0];
            return new Card(meApType);
        }else{//is a basic weapon card
            String cardSuit = givenCard.split("_")[0];
            int cardValue = Integer.parseInt(givenCard.split("_")[1]);
            return new Card("Basic",cardSuit,cardValue);
        }
    }

    /////////////SCENARIO B/////////////

    @And("player {int} is shamed and discards their {string} card")
    public void playerIsShamedAndDiscardsTheirCard(int plyrNum, String card) {
        plyrsInput += "0\n";
    }

    @Then("the Game ends from player {int} being shamed")
    public void theGameEndsFromPlayerBeingShamed(int plyrNum) {
        testGame.playMelee(new Scanner(plyrsInput),new PrintWriter(output));

        String endOutput = "";
        if(testGame.endGame){//this is how we check to end the game in playRound
            endOutput = testGame.endGame();
            System.out.println(endOutput);
        }
        assertTrue(endOutput.contains("The winner(s) of the Tournament is: P1 P3"));
    }
    /////////////SCENARIO B/////////////

    /////////////SCENARIO C/////////////
    @Then("the Game ends with {string} as the winner\\(s) and not {string}")
    public void theGameEndsWithAsTheWinnerS(String winnersNames, String losersNames) {
        String endOutput = "";

        if(testGame.checkDeadPlayers()){//if a players dead then end the game
            endOutput = testGame.endGame();
            System.out.println(endOutput);
        }

        String winnerOutput = endOutput.split("The")[1];
        String[] winners = winnersNames.split(",");
        for (String winner: winners) {
            assertTrue(winnerOutput.contains(winner));// checks each winner name is in the end game winner declaration
        }

        String[] losers = losersNames.split(",");
        for (String loser: losers) {
            assertFalse(winnerOutput.contains(loser));// checks each loser name is NOT the end game winner declaration
        }
    }
    /////////////SCENARIO C/////////////


}
