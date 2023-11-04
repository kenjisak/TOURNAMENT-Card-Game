package cucumber.stepdefinitions;

import game.*;
import io.cucumber.java.en.Given;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class Part3A {
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
    @Given("Round {int} starts as well as distributes each players cards")
    public void roundStarts(int roundNum) {
        //simulates beginning of round, since we cant call play round directly.
        System.out.println("\nRound " + testGame.roundNum + " Starting... Initial Leader of this round is " + testGame.currLeader);
        testGame.distributePlayersHands();
        System.out.println(testGame.displayAllPlayersHandsHP());//display each player's initial hands
    }
    @Given("the Melee {int} starts")
    public void meleeStarts(int meleeNum) {
        System.out.println("\nRound " + testGame.roundNum + ", Melee " + meleeNum + " Starting...");
    }
}
