/**
 * Card.java by Tomas Salaz

 * This Java file works with in unison with ComputerScientist.java to create
 * Collectable Cards. This file contains methods used to create the card and
 * access information stored within the card.

 * There are 3 variations of a Card. The First Variation is a blank card
 * without any information. The Second Variation only contains a Contribution
 * and the Third Variation contains both a Contribution and a Computer
 * Scientist. There are also method to Compare Cards and Output the
 * information of the Card.
*/

public class Card {

    //Global Variables
    public ComputerScientist computerScientist;
    public String contribution;

    /**
     * 1st Constructor Method: Creates a Blank Card with no information
    */
    public Card() {
        this.computerScientist =
        ComputerScientistCardConstants.UNKNOWN_COMPUTER_SCIENTIST;
        this.contribution = ComputerScientistCardConstants.UNKNOWN_CONTRIBUTION;
    }

    /**
     * 2nd Constructor Method: Creates a Card that contains a contribution.
     * The Computer Scientist field is assigned to Unknown
    */
    public Card(String contribution) {
        this.contribution = contribution;
        this.computerScientist =
        ComputerScientistCardConstants.UNKNOWN_COMPUTER_SCIENTIST;
    }

    /**
     * 3rd Constructor Method: Creates a Card with both a Contribution and an
     * assigned Computer Scientist.
    */
    public Card(String contribution, ComputerScientist ComputerScientist) {
        this.contribution = contribution;
        this.computerScientist = ComputerScientist;
    }

    /**
     * setContribution Method sets the Contribution for a card. The
     * Contribution must be unknown. If the Parameter is too short, then the
     * method will not work and ask the user to elaborate further and will
     * keep the old status of the contribution
    */
    public void setContribution(String Contribution) {
        if (ComputerScientistCardConstants.UNKNOWN_CONTRIBUTION
        .equals(contribution) || Contribution.length() > 10){
            this.contribution = Contribution;

        } else {
            System.out.println("Contribution too short, please elaborate or " +
            "Contribution is already set. Previous Status kept");
        }
    }

    //setContribution Method returns the Contribution of the Card
    public String getContribution() {
        return contribution;
    }

    //setComputerScientist Method set the Cards Computer Scientist
    public void setComputerScientist(ComputerScientist computerScientist) {
        this.computerScientist = computerScientist;
    }

    //getComputerScientist Method returns the Computer Scientist on the Card
    public ComputerScientist getComputerScientist() {
        return computerScientist;
    }

    /**
     * getYear Method returns either the Birth Year or the Degree Year of the
     * Computer Scientist on the Card, depends on the type of Computer
     * Scientist, if type is Unknown, then the that is what is returned.
    */
    public int getYear() {
        if (computerScientist.getType().equals("FAMOUS")) {
            return computerScientist.getBirthYear();

        } else if (computerScientist.getType().equals("FACULTY")) {
            return computerScientist.getDegreeYear();

        } else {
            return ComputerScientistCardConstants.UNKNOWN_YEAR;

        }
    }

    /**
     * hasSameComputerScientist Method uses isSame Method from this file to
     * see is the card has the same Computer Scientist
    */
    public boolean hasSameComputerScientist(Card other) {
        if (computerScientist.isSame(other.getComputerScientist())) {
            return true;
        } else {
            return false;
        }
    }

    //isSame Method checks if the cars is the same by comparing both the Name
    // of the Computer Scientist and the Contribution of the card.
    public boolean isSame(Card other) {
        if (computerScientist.toString().equals(other.computerScientist
        .toString()) && (getContribution().equals(other.getContribution()))){
            return true;
        } else {
            return false;
        }
    }

    /**
     * toString Method returns a Sting containing all the information on the
     * card. The String that is returned is dependent on the Available
     * information, similar to the 'getLongString' Method in the
     * ComputerScientist.java file. Along with that information there is also
     * information about the Contribution, the Area of Study, of the
     * Accomplishment of the Computer Scientist.
    */
    public String toString() {
        if (computerScientist.getType().equals("FAMOUS")) {
            if (getContribution() != ComputerScientistCardConstants
            .UNKNOWN_CONTRIBUTION){
                return computerScientist.getLongString() + "\nAccomplishment: "+
                getContribution();
            } else {
                return computerScientist.getLongString();
            }

        } else if (computerScientist.getType().equals("FACULTY")) {
            return computerScientist.getLongString() + "\nArea of Study: " +
            getContribution();

        } else { //No Type
            return computerScientist.getLongString() + "\nContribution: " +
            getContribution();
        }
    }
}