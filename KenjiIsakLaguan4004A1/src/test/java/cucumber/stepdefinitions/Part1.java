package cucumber.stepdefinitions;
import game.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import java.io.StringWriter;

public class Part1 {
    public TournamentGame testGame = new TournamentGame(4, new String[]{"1", "2", "3", "4"},50);;
    public StringWriter output = new StringWriter();
    @Given("testing1 {string}")
    public void testing1(String plyrCard){
        System.out.println("testing step definition");
        String[] strPlyrcard = plyrCard.split("_");

        Card card;
        if(strPlyrcard.length == 3){
            card = new Card(strPlyrcard[0],strPlyrcard[1], Integer.parseInt(strPlyrcard[2]));

        }else if(strPlyrcard.length == 2){
            card = new Card(strPlyrcard[0], Integer.parseInt(strPlyrcard[1]));
        }else{
            card = new Card(strPlyrcard[0]);
        }
        testGame.players[0].addToHand(card);
    }
    @And("testing2 {string}")
    public void testing2(String plyrCard){
        System.out.println("testing step definition");
        String[] strPlyrcard = plyrCard.split("_");

        Card card;
        if(strPlyrcard.length == 3){
            card = new Card(strPlyrcard[0],strPlyrcard[1], Integer.parseInt(strPlyrcard[2]));

        }else if(strPlyrcard.length == 2){
            card = new Card(strPlyrcard[0], Integer.parseInt(strPlyrcard[1]));
        }else{
            card = new Card(strPlyrcard[0]);
        }
        testGame.players[0].addToHand(card);
    }
    @And("testing3 {string}")
    public void testing3(String plyrCard){
        System.out.println("testing step definition");
        String[] strPlyrcard = plyrCard.split("_");

        Card card;
        if(strPlyrcard.length == 3){
            card = new Card(strPlyrcard[0],strPlyrcard[1], Integer.parseInt(strPlyrcard[2]));

        }else if(strPlyrcard.length == 2){
            card = new Card(strPlyrcard[0], Integer.parseInt(strPlyrcard[1]));
        }else{
            card = new Card(strPlyrcard[0]);
        }
        testGame.players[0].addToHand(card);
    }
    @And("testing4 {string}")
    public void testing4(String plyrCard){
        System.out.println("testing step definition");
        String[] strPlyrcard = plyrCard.split("_");

        Card card;
        if(strPlyrcard.length == 3){
            card = new Card(strPlyrcard[0],strPlyrcard[1], Integer.parseInt(strPlyrcard[2]));

        }else if(strPlyrcard.length == 2){
            card = new Card(strPlyrcard[0], Integer.parseInt(strPlyrcard[1]));
        }else{
            card = new Card(strPlyrcard[0]);
        }
        testGame.players[0].addToHand(card);
    }

    @Then("loser {string}")
    public void loser(String loser) {
        assertEquals("2",loser);
    }
}
