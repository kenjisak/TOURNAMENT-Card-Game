import java.util.*;

public class TournamentGame {
    public Player[] players;//keeps track of turns as well by creating players in order of input
    public String currLeader;
    public List<Card> gameDeck;
    public Map<Player, Card> currMeleeCardsPlayed;
    public int roundNum;

    public TournamentGame(int numPlayers, String[] namesOfPlayers, int initHealthPoints){
        players = new Player[numPlayers];

        for(int i = 0; i < numPlayers; i++){
            players[i] = new Player(namesOfPlayers[i], initHealthPoints);
        }
        currLeader = namesOfPlayers[0];//init to player 1
        gameDeck = new ArrayList<>();
        currMeleeCardsPlayed = new HashMap<>();//empty list init
        roundNum = 1;
        //basic weapon cards
        for(int i = 1; i <= 15; i++){//swords
            gameDeck.add(new Card("Basic","Swords",i));
            gameDeck.add(new Card("Basic","Arrows",i));
            gameDeck.add(new Card("Basic","Sorcery",i));
            gameDeck.add(new Card("Basic","Deception",i));
        }

        for(int i = 1; i <= 3; i++){
            gameDeck.add(new Card("Merlin"));
        }

        for(int i = 1; i <= 2; i++){
            gameDeck.add(new Card("Apprentice"));
        }

        for(int i = 1; i <= 15; i++){
            gameDeck.add(new Card("Alchemy",i));
        }
    }
    public static void main(String[] args) {

    }
    public static boolean checkInputNumPlayers(int input){
        return false;
    }
}
