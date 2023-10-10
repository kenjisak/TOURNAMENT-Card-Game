import java.util.*;

public class TournamentGame {
    public Player[] players;//keeps track of turns as well by creating players in order of input
    public String currLeader;
    public List<Card> gameDeck;
    public Map<Player, Card> currMeleeCardsPlayed;
    public int roundNum;

    public TournamentGame(int numPlayers, String[] namesOfPlayers, int initHealthPoints){
        players = new Player[numPlayers];

        if (initHealthPoints <= 0){ initHealthPoints = 50; }//default value if given non-positive HP
        for(int i = 0; i < numPlayers; i++){
            players[i] = new Player(namesOfPlayers[i], initHealthPoints);
        }
        currLeader = namesOfPlayers[0];//init to player 1
        gameDeck = new ArrayList<>();
        currMeleeCardsPlayed = new HashMap<>();//empty list init
        roundNum = 1;
        recreateDeck();
    }
    public void recreateDeck(){//when shuffling, the card is edited and not reverted back for Merlin and Apprentice
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
    public void shuffleDeck(){
        System.out.println("Shuffling Deck...");
        recreateDeck();
        Collections.shuffle(gameDeck);
    }
    public static void main(String[] args) {
        System.out.println("Welcome to the TOURNAMENT Game.");
        System.out.print("\nPlease enter the number of players (3-5 is acceptable): ");

        Scanner scanner = new Scanner(System.in);
        int numPlayers;

        while (true){
            numPlayers = scanner.nextInt();
            if(!checkInputNumPlayers(numPlayers)){//if not valid
                System.out.print("Please enter an ACCEPTABLE number of players (3-5 is acceptable): ");
            } else {
                break;
            }
        }
        scanner.nextLine();//clear input buffer
    }
    public static boolean checkInputNumPlayers(int input){
        return input >= 3 && input <= 5;//if true then valid, else not
    }
}
