public class Card {
    private String type;//Basic, Me,Ap,Al
    private String suit;//Sw,Ar,So,De, or empty if not Basic
    private int value;
    private boolean isPoisoned;
    private int injuryPoints;

    public Card(String cardType, String suit, int value) {

    }
    //getters
    public String getType(){ return null; }
    public String getSuit(){ return null; }
    public int getValue(){ return -1; }
    public Boolean getIsPoisoned(){ return null; }
    public int getInjuryPoints(){ return -1; }
}
