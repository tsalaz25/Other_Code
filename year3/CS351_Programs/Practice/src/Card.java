public class Card {
    /**
     * Global Variables
     */
    Rank rank;
    Suit suit;
    Color color;
    Status status;

    /**
     * Constructor Method for making Cards
     * @param rank is the rank of the card
     * @param suit is the suit of the card
     *             the Status is Unknown, in the sense that we dont know if
     *             it is up or down
     */
    public Card (Rank rank ,Suit suit){
        this.rank = rank;
        this.suit = suit;
        this.status = Status.Unknown;


        if (suit.equals(Suit.DIAMONDS) || suit.equals(Suit.HEARTS)){
            this.color = Color.RED;
        } else {
            this.color = Color.BLACK;
        }
    }

    /**
     * Constructor Method for making cards
     * @param rank is the rank of the card
     * @param suit is the suit of the card
     * @param status is the status of the card, idUP or isDOWN
     */
    public Card (Rank rank ,Suit suit , Status status){
        this.rank = rank;
        this.suit = suit;
        this.status = status;

        if (suit.equals(Suit.DIAMONDS) || suit.equals(Suit.HEARTS)){
            this.color = Color.RED;
        } else {
            this.color = Color.BLACK;
        }
    }


    /**
     * Enums for all the Parts of the Card
     */
    enum Rank{
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING,
        ACE
    }
    enum Suit{
        CLUBS, DIAMONDS, HEARTS, SPADES
    }
    enum Color{
        RED, BLACK
    }
    enum Status{
        isUP, isDOWN, Unknown
    }

    /**
     * Getters for the different Variable of the Card
      * @return
     */
    public Rank getRank(){
        return rank;
    }

    public Suit getSuit(){
        return suit;
    }

    public Color getColor(){
        return color;
    }

    public Status getStatus(){
        return status;
    }

    /**
     * Attempted to make a toString method, dont work
     */
    public String toString (Card c){
        return "You have the Card, " + getRank() + " of " + getSuit() + "\n" +
        "the card is " + getColor() + " and the status is " + getStatus();
    }

    /**
     * Main Method for Testing
     * @param args
     */
    public static void main (String [] args){
        Card c1 = new Card(Rank.ACE, Suit.SPADES, Status.isDOWN);
        Card c2 = new Card(Rank.NINE, Suit.DIAMONDS);

        System.out.println(c1.getColor());
        System.out.println(c1.getStatus());
        System.out.println(c1.getRank() + "\n");

        System.out.println(c2.getStatus());
        System.out.println(c2.getSuit());
    }
}
