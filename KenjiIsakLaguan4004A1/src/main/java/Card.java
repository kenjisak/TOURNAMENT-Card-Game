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

        if ((suit.equals("Swords") && (value >= 6 && value <= 9)) || (suit.equals("Arrows") && (value >= 8 && value <= 11)) || (suit.equals("Sorcery") && (value == 5 || value == 6 || value == 10 || value == 11)) || (suit.equals("Deception") && (value == 6 || value == 7 || value == 9 || value == 10)) ){
            this.isPoisoned = true;//checks if value and suit of card is appropriate to be poisoned
            this.injuryPoints = 10;
        } else {//none poisoned card
            this.isPoisoned = false;
            this.injuryPoints = 5;
        }
    }
    public Card(String cardType){

    }
    //getters
    public String getType(){ return this.type; }
    public String getSuit(){ return this.suit; }
    public int getValue(){ return this.value; }
    public Boolean getIsPoisoned(){ return this.isPoisoned; }
    public int getInjuryPoints(){ return this.injuryPoints; }
}
