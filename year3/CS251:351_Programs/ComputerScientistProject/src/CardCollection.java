/**
 * A CardCollection tracks a collection of computer scientist cards.
 */

/** By: Tomas Salaz*/

public class CardCollection {

    /** Unique cards in the collection. */
    private Card[] collection;

    /** Number of unique cards. */
    private int numCards;

    /** Number of copies currently in the collection */
    private int[] copies;

    /** Tracks the number of copies that have been traded away */
    private int[] traded;

    /** Constant used to fill array with empty cards*/
    public static final Card EMPTY_CARD = new Card("Empty" ,
            ComputerScientistCardConstants.UNKNOWN_COMPUTER_SCIENTIST);

    /**
     * Construct a new empty CardCollection.
     * @param collectionSize Number of unique cards collection can manage.
     */
    public CardCollection(int collectionSize){
        collection = new Card[collectionSize];
        copies = new int[collectionSize];
        traded = new int[collectionSize];
        numCards = 0;

        for (int i = 0; i < collectionSize; i++){
            collection[i] = EMPTY_CARD;
        }
    }

    /**
     * Get the number of cards (counting duplicate copies) in the collection.
     * @return Total number of cards in the collection.
     */
    public int getTotalNumCopies(){
        int totalCopies = 0;
        for (int i = 0; i < copies.length; i++){
            totalCopies = totalCopies + copies[i];
        }
        return totalCopies;
    }

    /**
     * Get the number of cards that have been traded away.
     * @return Total number of cards traded away.
     */
    public int getTotalTradedCards(){
        int totalTraded = 0;
        for (int i = 0; i < copies.length; i++){
            totalTraded = totalTraded + traded[i];
        }
        return totalTraded;
    }

    /**
     * Get a String representing the status of this collection.
     * @return Status string.
     */
    public String getCollectionStatus(){

//        System.out.println("Total unique cards: " + numCards + "\n" +
//        "Total number of copies: " + getTotalNumCopies() + "\n" +
//        "Total number of cards traded: " + getTotalTradedCards());

        return "Total unique cards: " + numCards + "\n" +
        "Total number of copies: " + getTotalNumCopies() + "\n" +
        "Total number of cards traded: " + getTotalTradedCards();
    }

    /**
     * Add a single card to this collection.
     *
     * If the card is already present, adds another copy.
     * If the card is new, add it after the existing cards
     * @param c Card to add.
     */
    public void addCard(Card c){
        boolean ownsCard = false;
        int searchingIndex = 0;

        for (int i = 0; i < collection.length; i++){
            if (collection[i].isSame(c)){
                ownsCard = true;
                searchingIndex = i;
                break;
            }
            if (collection[i] == EMPTY_CARD){
                searchingIndex = i;
                break;
            }
        }

        if (ownsCard){
            copies[searchingIndex]++;
        }
        if (!ownsCard){
            collection[searchingIndex] = c;
            copies[searchingIndex] = 1;
            traded[searchingIndex] = 0;
            numCards++;
        }
    }

    /**
     * Add all the cards in the array to the collection.
     * Adds one copy of each card.
     * @param newCards Cards to add.
     */
    public void addCards(Card[] newCards){
        for (int i = 0; i < newCards.length; i++){
            if (newCards[i] != EMPTY_CARD){
                addCard(newCards[i]);
            }
        }
    }


    /**
     * Trade cards with other collectors!
     * You can only trade the requested card 'c' for 'newCard' if:
     * - It exists in the collection
     * - And the collection has more than one copy
     *
     * @param newCard The card from the other collector
     * @param c The card being traded away from this collection
     */
    public String tradeCards(Card newCard, Card c){
        boolean ownsCard = false;
        boolean hasCopies = false;
        int searchingIndex = 0;

        for (int i = 0; i <= numCards; i++){
            if (collection[i].isSame(c)) {
                ownsCard = true;
                searchingIndex = i;
            }
        }

        if (ownsCard && copies[searchingIndex] > 1){
            hasCopies = true;
        }

        if (hasCopies){
            int nextOpenIndex = 0;
            for (int i = 0; i < collection.length; i++){
                if (collection[i].isSame(EMPTY_CARD)){
                    nextOpenIndex = i;
                    break;
                }
            }
            addCard(newCard);
            copies[searchingIndex]--;
            traded[searchingIndex]++;
            traded[nextOpenIndex] = 0;
            //System.out.println("traded");
            return "Traded a copy for a new card!";

        } else if (ownsCard){
            //System.out.println("No Copies, Owns one");
            return "Not enough copies to trade.";

        } else {
            //System.out.println("No Card");
            return "Requested card isn't in the collection.";
        }

    }

    /**
     * Get string representation of entire collection and status.
     * @return String representation of collection
     */
    public String toString(){
        String res = "";

        for (int i = 0; i < collection.length; i++){
            if (collection[i] != EMPTY_CARD){
                res = res + i + ". " + collection[i] + "\n\tCopies: " +
                copies[i] + "\n\tTraded: " + traded[i] + "\n";
            }
        }
        //System.out.println(res + getCollectionStatus());
        return res + getCollectionStatus();
    }

    /**
     * Get number of unique cards that exist for a given ComputerScientist
     * @param cs The ComputerScientist to check
     * @return Number of cards for that ComputerScientist
     */
    public int numCardsForCS(ComputerScientist cs){
        int counter = 0;

        for (int i = 0;  i < numCards; i++){
            if (collection[i].getComputerScientist().isSame(cs)){
                counter++;
            }
        }
        return counter;
    }

    /**
     * Returns a String that lists the unique cards which exist for a given
     * computer scientist. If no cards are found for the computer scientist,
     * returns string that says so.
     *
     * @param cs The computer scientist in question.
     * @return String listing cards for the computer scientist.
     */
    public String listCardsForCS(ComputerScientist cs){
        String res = "";
        for (int i = 0; i < collection.length; i++) {
            if (collection[i].getComputerScientist().isSame(cs)) {
                res = res + collection[i] + "\n";
            }
            if (collection[i] == EMPTY_CARD && res.equals("")){
                res = "No cards for " + cs.givenName + " " + cs.surname + " " +
                "in the collection.";
            }
        }

        return res;
    }

    /**
     * Returns a String that lists the unique cardss which contain the given
     * string within their contribution, without regard for case. If no cards
     * are found, returns string that says so.
     * @param s The string to look for in the contributions.
     * @return String listing cards that contain given string in contributions.
     */
    public String listCardsByContribution(String s){
        String res = "";
        String compStr = s.toLowerCase();

        for (int i = 0; i < collection.length; i++) {
            if (collection[i] != EMPTY_CARD) {
                String csStr = collection[i].contribution.toLowerCase();
                if (csStr.contains(compStr)) {
                    res = res + collection[i].toString() + "\n";
                }
            }
            if (collection[i] == EMPTY_CARD && res.equals("")){
                res = "No cards with \"" + s + "\" as a contribution!";
            }
        }
        System.out.println(res);
        return res;
    }

    /**
     * Deletes a card from the collection entirely,
     * including all copies and trading information.
     * @param c Card to remove.
     * @return String denoting success or failure.
     */
    public String deleteCard(Card c){
        int amountOut = 0;

        for (int i = 0; i < collection.length; i++){
            if (collection[i].isSame(c)){
                amountOut = copies[i];
                copies[i] = 0;
                traded[i] = 0;
                numCards = numCards - amountOut;

                return "Card has been removed!";
            }
            if (collection[i] == EMPTY_CARD){

                return "Card does not exist in this collection.";
            }
        }
        return " ";
    }
}