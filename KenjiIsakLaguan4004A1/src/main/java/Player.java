import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int healthPoints;
    private int totalInjuryPoints;
    private List<Card> deckInHand;
    private List<Card> injuryDeck;

    public Player(String inputName,int inputHealthPoints){
        name = inputName;
        healthPoints = inputHealthPoints;
        totalInjuryPoints = 0;
        deckInHand = new ArrayList<>();
        injuryDeck = new ArrayList<>();
    }
    public String getName(){ return this.name; }
    public int getHealthPoints(){ return this.healthPoints; }
    public int getTotalInjuryPoints(){ return totalInjuryPoints; }
    public List<Card> getDeckInHand(){ return this.deckInHand; }
    public List<Card> getInjuryDeck(){ return this.injuryDeck; }
    public void addToHand(Card givenCard){
        deckInHand.add(givenCard);
    }
    public String displayHand(){
        String playersHand = "[";
        for (Card currCard: deckInHand){
            playersHand += currCard.displayCard() + ",";
        }
        if(playersHand.length() > 1){ playersHand = playersHand.substring(0,playersHand.length() - 1);}
        playersHand += "]";
        return "Player " + name + "'s Hand: " + playersHand;
    }
    public int addToInjuryDeck(List<Card> meleeDeck){
        injuryDeck.addAll(meleeDeck);
        int dmgPtsThisMelee = 0;
        for (Card currCard: meleeDeck){
            dmgPtsThisMelee += currCard.getInjuryPoints();
        }
        return dmgPtsThisMelee;
    }
    public void takeDmg(){
        for (Card dmgCard: injuryDeck){
            healthPoints -= dmgCard.getInjuryPoints();
        }
        injuryDeck = new ArrayList<>();//resets the injury deck for next melee
    }
    public boolean isAlive(){ return false; }
}