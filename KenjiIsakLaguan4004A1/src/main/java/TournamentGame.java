import java.util.*;

public class TournamentGame {
    public Player[] players;//keeps track of turns as well by creating players in order of input
    public String currLeader;
    public List<Card> gameDeck;
    public Map<Player, Card> currMeleeCardsPlayed;
    public int roundNum;
    public String currSuit;

    public TournamentGame(int numPlayers, String[] namesOfPlayers, int initHealthPoints){
        players = new Player[numPlayers];

        if (initHealthPoints <= 0){ initHealthPoints = 50; }//default value if given non-positive HP
        for(int i = 0; i < numPlayers; i++){
            players[i] = new Player(namesOfPlayers[i], initHealthPoints);
        }
        currLeader = namesOfPlayers[0];//init to player 1
        currMeleeCardsPlayed = new HashMap<>();//empty list init
        roundNum = 1;
        currSuit = "";//if we want to set to no suit then well set it to "No Suit"
        recreateDeck();
    }
    public void recreateDeck(){//when shuffling, the card is edited and not reverted back for Merlin and Apprentice
        gameDeck = new ArrayList<>();
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
    public void distributePlayersHands(){
        shuffleDeck();
        //iterate and give each player 12 cards
        int cardIndex = 0;//cant restart from 0 after each player, so players don't get duplicate hands
        for (Player currPlayer: players){
            for (int i = 0; i < 12; i++) {
                currPlayer.addToHand(gameDeck.get(cardIndex++));//never empty, don't need to remove from deck, just reshuffle each round
            }
        }
    }
    public void updateRoundLeader(){
        currLeader = players[roundNum % players.length].getName();//don't need separate round and melee leader. this calcs based on round Num not currLeader
        roundNum++;
    }
    public String displayAllPlayersHandsHP(){
        String returnThis = "";
        for (Player currPlayer: players){
            returnThis += "\n" + currPlayer.displayHand() + " Health Points: " + currPlayer.getHealthPoints();
        }
        return returnThis + "\n";
    }
    public void playersTakeDmg(){
        for(Player currPlayer: players){
            currPlayer.takeDmg();//subtracts player's hp from all of their injury deck
        }
    }
    public String displayAllPlayersHP(){
        String returnthis = "";
        for (Player currPlayer: players){//print round over with players hp
            returnthis += "\nPlayer " + currPlayer.getName() + " HP: " + currPlayer.getHealthPoints();
        }
        return returnthis + "\n";
    }
    public boolean checkDeadPlayers(){
        for(Player currPlayer: players){//if any players are dead after dmg taken, end the game
            if (!currPlayer.isAlive()){
                return true;
            }
        }
        return false;
    }
    public String endGame(){
        //print out hp
        String returnthis = "";
        for (Player currPlayer: players){
            returnthis += "\nPlayer " + currPlayer.getName() + " HP: " + currPlayer.getHealthPoints();
        }
        String winners = findWinners();
        if(Objects.equals(winners, "")){
            return returnthis + "\nThere are no winners of the Tournament.";
        }else{
            return returnthis + "\nThe winner(s) of the Tournament is:" + findWinners();
        }
    }
    public String findWinners(){
        List<Player> winners = new ArrayList<>();
        //find highest hp player(s) and display as winner
        int maxHP;
        String winnersName = "";
        maxHP = 0;

        List<Player> numDead = new ArrayList<>();
        for (Player currPlayer: players){//checks if ALL players are dead
            if(!currPlayer.isAlive()){
                numDead.add(currPlayer);
            }
        }
        if(numDead.size() == players.length){
            return "";
        }

        for (Player currPlayer: players) {
            if (currPlayer.getHealthPoints() > maxHP){
                winners.clear();
                winners.add(currPlayer);
                maxHP = currPlayer.getHealthPoints();
            }else if(currPlayer.getHealthPoints() == maxHP){
                winners.add(currPlayer);
            }
        }
        for(Player winner: winners){
            winnersName += " " + winner.getName();
        }
        return winnersName;
    }
    public void playRound(){
        System.out.println("\nRound " + roundNum + " Starting... Initial Leader of this round is " + currLeader);
        distributePlayersHands();
        //display each player's initial hands
        System.out.println(displayAllPlayersHandsHP());

        //playMelee in 12 time loop
//        for (int i = 1; i <= 12; i++) {
//        System.out.println("\nRound " + roundNum + ", Melee " + i + " Starting...");
        playMelee();
//        System.out.println("Round " + roundNum + ", Melee " + i + " over...");
//        }

        playersTakeDmg();
        System.out.println(displayAllPlayersHP());

        System.out.println("Round " + roundNum + " over...");
        if(checkDeadPlayers()){//if a players dead then end the game
            System.out.println(endGame());
            System.exit(0);
        }
        updateRoundLeader();//else update leader and continue round
    }
    public int findMeleeLeaderIndex(){
        int meleeLeaderIndex = 0;
        for (int i = 0; i < players.length; i++) {
            if (Objects.equals(players[i].getName(), currLeader)) {
                meleeLeaderIndex = i;//sets the index of leader of this melee
            }
        }
        return meleeLeaderIndex;
    }
    public boolean checkAnyPlayableCards(int currPlyrIndex, String currSuit){
        //check if player has any cards that match the suit
        boolean anyPlayableCardsFound = false;
        for (Card checkCard: players[currPlyrIndex].getDeckInHand()){
            if(Objects.equals(currSuit, "No Suit")){
                anyPlayableCardsFound = true;
                //skip the check
            }else{
                if ((Objects.equals(checkCard.getType(), "Basic") && Objects.equals(checkCard.getSuit(), currSuit)) || Objects.equals(checkCard.getType(), "Merlin") || Objects.equals(checkCard.getType(), "Apprentice") || Objects.equals(checkCard.getType(), "Alchemy")) {
                    //loop through all cards and check if theres Basic cards that match the suit or, a Merlin or Apprentice, Alchemy
                    anyPlayableCardsFound = true;
                    break;//cut for loop short
                }
            }
        }
        return anyPlayableCardsFound;
    }
    public boolean checkCardInput(int currPlyrIndex, int cardIndexSelected){
        return cardIndexSelected >= 0 && cardIndexSelected < players[currPlyrIndex].getDeckInHand().size();//stop asking for input, game hasn't ended
    }
    public boolean shamePlayer(int currPlyrIndex, int cardIndexSelected){
        return players[currPlyrIndex].shamed(cardIndexSelected);//remove card from players hand
    }
    public void setMeleeSuit(Card chosenCard){
        currSuit = chosenCard.getSuit();
    }
    public boolean checkValidSuitInput(String currSuit){
        return Objects.equals(currSuit, "Swords") || Objects.equals(currSuit, "Arrows") || Objects.equals(currSuit, "Sorcery") || Objects.equals(currSuit, "Deception");
    }
    public boolean checkValidValue(int valueChosen){
        return valueChosen > 0 && valueChosen <= 15;
    }
    public boolean checkNonAlPlayableCards(int currPlyrIndex){
        boolean nonAlFound = false;
        for (Card checkCard: players[currPlyrIndex].getDeckInHand()){
            if (!Objects.equals(checkCard.getType(), "Alchemy")) {
                //loop through all cards and check if theres any non Al
                nonAlFound = true;
                break;
            }
        }

        if (!nonAlFound){//else looped through and couldn't find any
            currSuit = "No Suit";
            System.out.println("There is No Suit set for this Melee.");
        }
        return nonAlFound;
    }
    public boolean checkNoSuitorSuitMatches(Card chosenCard){
        return Objects.equals(currSuit, "No Suit") || Objects.equals(chosenCard.getSuit(), currSuit);
    }
    public boolean checkSuitPlayableCards(int currPlyrIndex){
        return false;
    }
    public void playMelee() {
        System.out.println("Leader " + currLeader + " starts this Melee...");
        int meleeLeaderIndex = findMeleeLeaderIndex();

        currSuit = "";//resets each melee
        Scanner cardInput = new Scanner(System.in);
        Scanner suitInput = new Scanner(System.in);
        Scanner valueInput = new Scanner(System.in);
        currMeleeCardsPlayed = new HashMap<>();//reset melee deck

        for (int i = 0; i < players.length; i++) {//goes through each player's turns
            int currPlyrIndex = (meleeLeaderIndex + i) % players.length;
            int cardIndexSelected;
            boolean anyPlayableCardsFound = false;

            if (i > 0){//not possible for leader to be SHAMED
                anyPlayableCardsFound = checkAnyPlayableCards(currPlyrIndex,currSuit);
                if (!anyPlayableCardsFound){//if no Playable Cards then force player to discard
                    System.out.println("\nPlayer " + players[currPlyrIndex].getName() + " has No Playable Cards this Melee, and is SHAMED.");
                    System.out.println("\n" + players[currPlyrIndex].displayHand());
                    while(true){
                        System.out.print("Choose a card to Discard: ");
                        cardIndexSelected = cardInput.nextInt();
                        if (checkCardInput(currPlyrIndex,cardIndexSelected)){//if var = true/within index then break and stop asking input
                            boolean shamedPlayerisAlive = shamePlayer(currPlyrIndex,cardIndexSelected);
                            if(!shamedPlayerisAlive){//if the player died then end the game
                                System.out.println(endGame());
                                System.exit(0);
                            }
                            break;
                        }
                    }
                    continue;//skip while loop below, not gonna ask player to play a card
                }
            }
            Card chosenCard;
            while (true){
                //display players hand
                System.out.println("\n" + players[currPlyrIndex].displayHand());
                System.out.print("Player " + players[currPlyrIndex].getName() + " Select a VALID Card Index: ");
                cardIndexSelected = cardInput.nextInt();

                if (checkCardInput(currPlyrIndex,cardIndexSelected)){
                    chosenCard = players[currPlyrIndex].getDeckInHand().get(cardIndexSelected);

                    if(i == 0){//then Leader can set the suit,
                        //if non Alchemy, check if Basic or Merlin or Apprentice,
                        if (Objects.equals(chosenCard.getType(), "Basic")) {
                            setMeleeSuit(chosenCard);
                            System.out.println("The Suit Set for this Melee is: " + currSuit);
                            break;
                        }
                        if (Objects.equals(chosenCard.getType(), "Merlin") || Objects.equals(chosenCard.getType(), "Apprentice")) {
                            //ask input again to choose the suit
                            while (true){
                                System.out.print("Please choose a VALID Suit(Swords, Arrows, Sorcery, Deception): ");
                                currSuit = suitInput.nextLine();
                                if (checkValidSuitInput(currSuit)){
                                    chosenCard.setSuit(currSuit);
                                    break;//valid suit
                                }
                            }
                            //ask input again to choose the value
                            while (true){
                                System.out.print("Please choose a value (1-15) for your " + chosenCard.getType() + " card: ");
                                int valueChosen = valueInput.nextInt();
                                if(checkValidValue(valueChosen)){
                                    chosenCard.setValue(valueChosen);
                                    break;
                                }
                            }
                            System.out.println("The Suit Set for this Melee is: " + currSuit);
                            break;
                        }
                        if(Objects.equals(chosenCard.getType(), "Alchemy")){
                            boolean nonAlFound = checkNonAlPlayableCards(currPlyrIndex);
                            if (!nonAlFound){//no NON Alchemy found, is forced to play it
                                break;
                            }
                            System.out.println("You cannot start with an Alchemy card, with other type of cards left in hand.");
                        }

                    }else{//else check for the suit if card matches
                        //if no suit or suit matches ,dont bother checking the type/playable cards
                        if (checkNoSuitorSuitMatches(chosenCard)){ break; }
                        if(Objects.equals(chosenCard.getType(), "Merlin") || Objects.equals(chosenCard.getType(), "Apprentice")){
                            chosenCard.setSuit(currSuit);
                            //ask for a valid Value
                            while (true){
                                System.out.print("Please choose a value (1-15) for your " + chosenCard.getType() + " card: ");
                                int valueChosen = valueInput.nextInt();
                                if(checkValidValue(valueChosen)){
                                    chosenCard.setValue(valueChosen);
                                    break;
                                }
                            }
                            break;
                        }

                    }
                }

            }

        }
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

        String[] playersNames = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++){
            System.out.print("Enter (" + (i + 1) + ") Player's name: ");
            playersNames[i] = scanner.nextLine();
        }

        TournamentGame tournamentGame = new TournamentGame(numPlayers, playersNames, 100);

//        while (true) {//disable loop to play 1 round at a time
        tournamentGame.playRound();
//        }
    }
    public static boolean checkInputNumPlayers(int input){
        return input >= 3 && input <= 5;//if true then valid, else not
    }
}
