import java.io.PrintWriter;
import java.util.*;

public class TournamentGame {
    public Player[] players;//keeps track of turns as well by creating players in order of input
    public String currLeader;
    public List<Card> gameDeck;
    public Map<Player, Card> currMeleeCardsPlayed;
    public int roundNum;
    public String currSuit;
    public Player loser;
    public Card chosenCard;

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
        chosenCard = null;
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
    public boolean shamePlayer(int currPlyrIndex, int cardIndexSelected){
        return players[currPlyrIndex].shamed(cardIndexSelected);//remove card from players hand
    }
    public void addChosenCard(int currPlyrIndex, int cardIndexSelected, Card chosenCard){
        currMeleeCardsPlayed.put(players[currPlyrIndex],chosenCard);//addChosenCard to meleeDeck
        players[currPlyrIndex].playCard(cardIndexSelected);//remove chose card from players hand
    }
    public String printMeleeDeck(Map<Player, Card> deckGiven){
        String returnthis = "";
        for (Map.Entry<Player, Card> entry : deckGiven.entrySet()) {
            Player player = entry.getKey();
            Card card = entry.getValue();
            returnthis += "\nPlayer: " + player.getName() + ", Card: " + card.displayCard();
        }
        return returnthis;
    }
    public int processDiscardInput(Scanner cardInput, PrintWriter output,int currPlyrIndex){
        System.out.print("Choose a card to Discard: ");
        int cardIndexSelected = cardInput.nextInt();
        if (cardIndexSelected >= 0 && cardIndexSelected < players[currPlyrIndex].getDeckInHand().size()){//stop asking for input, game hasn't ended){//if var = true/within index then break and stop asking input
            boolean shamedPlayerisAlive = shamePlayer(currPlyrIndex,cardIndexSelected);
            if(!shamedPlayerisAlive){//if the player died then end the game
                System.out.println(endGame());
                System.exit(0);
            }
            return cardIndexSelected;
        }
        output.println("Invalid card Index Selected.");
        return -1;
    }
    public Card processCardInput(Scanner cardInput, PrintWriter output,int currPlyrIndex,int turnIndex){
        //display players hand
        System.out.println("\n" + players[currPlyrIndex].displayHand());
        System.out.print("Player " + players[currPlyrIndex].getName() + " Select a VALID Card Index: ");
        int cardIndexSelected = cardInput.nextInt();
        if (cardIndexSelected >= 0 && cardIndexSelected < players[currPlyrIndex].getDeckInHand().size()){
            chosenCard = players[currPlyrIndex].getDeckInHand().get(cardIndexSelected);

            if(turnIndex == 0){//then Leader can set the suit,
                //if non Alchemy, check if Basic or Merlin or Apprentice,
                if (Objects.equals(chosenCard.getType(), "Basic")) {
                    currSuit = chosenCard.getSuit();
                    System.out.println("The Suit Set for this Melee is: " + currSuit);
                    return chosenCard;
                }
            }
        }
        output.println("Invalid card Index Selected.");
        return null;
    }
    public void playMelee() {
        System.out.println("Leader " + currLeader + " starts this Melee...");
        int meleeLeaderIndex = findMeleeLeaderIndex();

        currSuit = "";//if we want to set to no suit then well set it to "No Suit"
        Scanner cardInput = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);

        for (int i = 0; i < players.length; i++) {//goes through each player's turns
            int currPlyrIndex = (meleeLeaderIndex + i) % players.length;
            int cardIndexSelected = -1;
            boolean anyPlayableCardsFound = false;

            if (i > 0){//not possible for leader to be SHAMED
                anyPlayableCardsFound = checkAnyPlayableCards(currPlyrIndex,currSuit);
                if (!anyPlayableCardsFound){//if no Playable Cards then force player to discard
                    System.out.println("\nPlayer " + players[currPlyrIndex].getName() + " has No Playable Cards this Melee, and is SHAMED.");
                    System.out.println("\n" + players[currPlyrIndex].displayHand());
                    while(cardIndexSelected == -1){//while chosen card is invalid
                        cardIndexSelected = processDiscardInput(cardInput,output,currPlyrIndex);
                        output.flush();
                    }
                    continue;//skip while loop below, not gonna ask player to play a card
                }
            }

            chosenCard = null;
            while (chosenCard == null){
                chosenCard = processCardInput(cardInput,output,currPlyrIndex,i);
                output.flush();
            }
            addChosenCard(currPlyrIndex,cardIndexSelected,chosenCard);
        }
        System.out.println("\nCurrent Melee Cards Played: ");//display melee deck
        System.out.println(printMeleeDeck(currMeleeCardsPlayed));
        //determine loser
        loser = findLoser();
        meleeLoserUpdate();//if theres a loser then update as leader, remove their hp and display their name
    }

    public Map<Player,Card> feintStep(){//return a Map List of Unique Played Cards, after feint step
        Map<Player, Card> uniquePlayerCard = new HashMap<>();//copy and dont use currMeleeDeck to be able to add to players deck after
        Map<Integer, Integer> cardValueCounter = new HashMap<>();
        //feint step, remove all cards in meleeDeck that have matching values
        for (Map.Entry<Player, Card> entry : currMeleeCardsPlayed.entrySet()) {//Creates a counter for checking singletons in the meleeDeck
            Player player = entry.getKey();
            Card card = entry.getValue();
            int cardValue = card.getValue();

            if (cardValueCounter.containsKey(cardValue)) {// Check if the card value is already in the counter map
                int count = cardValueCounter.get(cardValue);
                cardValueCounter.put(cardValue, count + 1);// Increment the counter for this card value
            } else {// Initialize the counter for this card value
                cardValueCounter.put(cardValue, 1);
            }
            uniquePlayerCard.put(player, card);// Add the card to uniquePlayerCard
        }

        // Remove from map if card values aren't singletons
        Iterator<Map.Entry<Player, Card>> iterator = uniquePlayerCard.entrySet().iterator();
        while (iterator.hasNext()) {//Iterate through map
            Map.Entry<Player, Card> entry = iterator.next();
            int cardValue = entry.getValue().getValue();
            int count = cardValueCounter.get(cardValue);

            if (count > 1) {//If more than 1 instance, remove
                iterator.remove();
            }
        }
        System.out.println("Current Melee Deck After Feint Step: ");
        System.out.println(printMeleeDeck(uniquePlayerCard));
        return uniquePlayerCard;
    }
    public Player findLowestCard(Map<Player, Card> uniquePlayerCard){
        if(!uniquePlayerCard.isEmpty()){//find lowest card of all of them
            Card lowestCard = null;
            Player playerWithLowestCard = null;

            for (Map.Entry<Player, Card> entry : uniquePlayerCard.entrySet()) {
                Player player = entry.getKey();
                Card card = entry.getValue();

                if (lowestCard == null || card.getValue() < lowestCard.getValue()) {
                    lowestCard = card;
                    playerWithLowestCard = player;
                }
            }
            return playerWithLowestCard;
        }
        return null;//if empty then no losers, keep currLeader.
    }
    public Player findLoser(){
        Map<Player, Card> uniquePlayerCard = feintStep();
        Player loser = findLowestCard(uniquePlayerCard);

        return loser;
    }
    public void meleeLoserUpdate(){
        if(loser != null){
            currLeader = loser.getName();//set loser to currLeader
            //adds all meleeCards losers injury deck, doesnt include discarded as its not added to meleeDeck
            List<Card> meleeDeck = new ArrayList<>(currMeleeCardsPlayed.values());
            int dmgThisMelee = loser.addToInjuryDeck(meleeDeck);
            System.out.println(loser.getName() + " is the loser for this melee and accumulated " + dmgThisMelee + " injury points this melee.");
        }else{
            System.out.println("There are no losers for this melee.");
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
