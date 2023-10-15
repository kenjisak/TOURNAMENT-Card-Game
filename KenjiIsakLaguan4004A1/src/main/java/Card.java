import java.util.Objects;

public class Card {
    private String type;//Basic, Me,Ap,Al
    private String suit;//Sw,Ar,So,De, or empty if not Basic
    private int value;
    private boolean isPoisoned;
    private int injuryPoints;

    public Card(String cardType, String suit, int value) {
        //cardType == Basic
        this.type = cardType;
        this.suit = suit;
        this.value = value;

        if ((suit.equals("Swords") && (value >= 6 && value <= 9)) || (suit.equals("Arrows") && (value >= 8 && value <= 11)) || (suit.equals("Sorcery") && (value == 5 || value == 6 || value == 11 || value == 12)) || (suit.equals("Deception") && (value == 6 || value == 7 || value == 9 || value == 10)) ){
            this.isPoisoned = true;//checks if value and suit of card is appropriate to be poisoned
            this.injuryPoints = 10;
        } else {//none poisoned card
            this.isPoisoned = false;
            this.injuryPoints = 5;
        }
    }
    public Card(String cardType, int value){
        //cardType == Alchemy
        this.type = cardType;
        this.suit = "";//empty
        this.value = value;
        this.isPoisoned = false;
        this.injuryPoints = 5;
    }
    public Card(String cardType){
        //cardType == Me,Ap only
        this.type = cardType;
        this.suit = "";
        this.value = 0;//values are above 0, this needs to be set by user
        this.isPoisoned = false;
        if (Objects.equals(cardType, "Merlin")){
            this.injuryPoints = 25;
        } else if (Objects.equals(cardType, "Apprentice")) {
            this.injuryPoints = 5;
        }
    }
    //getters
    public String getType(){ return this.type; }
    public String getSuit(){ return this.suit; }
    public int getValue(){ return this.value; }
    public Boolean getIsPoisoned(){ return this.isPoisoned; }
    public int getInjuryPoints(){ return this.injuryPoints; }
    public String displayCard(){
        if(Objects.equals(type, "Basic")){
            return suit.substring(0,2) + "(" + value + ")";
        }else{
            return type.substring(0,2) + "(" + value + ")";
        }
    }
    public void setSuit(String givenSuit){ this.suit = givenSuit; }
    public void setValue(int givenValue){ this.value = givenValue; }
}
