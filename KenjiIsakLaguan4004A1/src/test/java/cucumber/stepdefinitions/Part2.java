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

public class Part2 {
    public TournamentGame testGame;
    public StringWriter output;
    public String setUpInput;
    public String meleeInput;
    @Given("the game starts")
    public void theGameStarts() {
        testGame = null;
        output = new StringWriter();
        setUpInput = "";
        meleeInput = "";
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

    @And("{string} hand is rigged with {string}")
    public void rigPlayersHand(String name, String hand) {
        List<Card> riggedHand = new ArrayList<>();
        String[] strPlyrcard = hand.split(",");

        for(String currCard: strPlyrcard){
            riggedHand.add(createCard(currCard));
        }

        int playersIndex = findPlayerIndex(name);

        testGame.players[playersIndex].rigAddAllHand(riggedHand);
        System.out.println(testGame.players[playersIndex].displayHand());
    }

    @When("{string} {string} plays {string}")
    public void playerPlaysACard(String isLeader,String name, String chosenCard) {
        output = new StringWriter();
        int plyrIndex = findPlayerIndex(name);
        String chosenCardInput = findCardIndex(plyrIndex,chosenCard);

        int turnIndex = 1;//non leader turn index
        if (Objects.equals(isLeader, "Leader")){
            turnIndex = 0;//is a leader turn index
        }

        testGame.processCardInput(new Scanner(chosenCardInput),new PrintWriter(output),findPlayerIndex(name),turnIndex);
        System.out.println(output);

        meleeInput += chosenCardInput;
    }

    /////////////HELPER FUNCTIONS/////////////
    public int findPlayerIndex(String name){//find player index using their name
        for (int i = 0; i < testGame.players.length; i++) {
            if (Objects.equals(testGame.players[i].getName(), name)){
                return i;
            }
        }
        return -1;
    }
    public String findCardIndex(int plyrIndex, String chosenCard){//find chosen card index
        Card findCard = createCard(chosenCard);

        return testGame.players[plyrIndex].getDeckInHand().indexOf(findCard) + "\n";
    }
    public Card createCard(String givenCard){
        if (givenCard.contains("Alchemy")){
            int cardValue = Integer.parseInt(givenCard.split("_")[1]);
            return new Card("Alchemy",cardValue);
        } else if (givenCard.contains("Merlin") || givenCard.contains("Apprentice")) {
            return new Card(givenCard);
        }else{//is a basic weapon card
            String cardSuit = givenCard.split("_")[0];
            int cardValue = Integer.parseInt(givenCard.split("_")[1]);
            return new Card("Basic",cardSuit,cardValue);
        }
    }

    @Then("{string} receives invalid {string} card message")
    public void receivesInvalidCardMessage(String name, String violationMsg) {
        assertTrue(output.toString().contains(violationMsg));
    }

    @Then("{string} is the loser with {int} injury points for this melee, total round injury points is {int}")
    public void isTheLoserWithInjuryPointsForThisMeleeTotalRoundInjuryPointsIs(String loser, int meleeInjPts, int rndInjPts) {
        output = new StringWriter();

        int loserInjPtsB4Melee = testGame.players[1].getTotalInjuryPoints();
        testGame.playMelee(new Scanner(meleeInput), new PrintWriter(output));
        int loserInjPtsAfterMelee = testGame.players[1].getTotalInjuryPoints();

        assertEquals(loser, testGame.loser.getName());
        assertEquals(meleeInjPts, loserInjPtsAfterMelee - loserInjPtsB4Melee);
        assertEquals(rndInjPts, loserInjPtsAfterMelee);
        meleeInput = "";//reset melee input
    }
}
