/**
 * ComputerScientist.java By Tomas Salaz

 * This Java File works in unison with the Card.java file to create a
 * collectable card. This file contains methods used to Create a Computer
 * Scientist and collect and store their information.

 * Each Computer Scientist has a Given Name, a Surname, a Type, and a
 * contribution. Based on the type, you can assign different attributes. For
 * the "FAMOUS" type, you can assign a birth year, a death year, or leave
 * them blank. You can also assign a Contribution. For the "FACULTY" type,
 * you can assign a degree year or leave it blank as well as assigning an
 * Area of Study or Contribution. If you wish to leave any field blank, you
 * can except for Given Name and Surname.
*/


public class ComputerScientist {

    //Global Variables
    String givenName;
    String surname;
    private String type;
    private int birth;
    private int death;
    private int year;


    /**
     * 1st Constructor Method: Creates a card with a Given Name and a Surname,
     * The Type is unknown.
    */
    public ComputerScientist (String givenName, String surname){
        this.givenName = givenName;
        this.surname = surname;
        this.type = ComputerScientistCardConstants.UNKNOWN_TYPE;
    }

    /**
     * 2nd Constructor Method: Creates a card with a Given Name a Surname and
     * a Type. If the Type is an invalid type, It is assigned to Unknown
    */
    public ComputerScientist (String givenName, String surname, String type){
        this.givenName = givenName;
        this.surname = surname;
        if (type.equals("FAMOUS") || type.equals("FACULTY")){
            this.type = type;
        } else {
            this.type = ComputerScientistCardConstants.UNKNOWN_TYPE;
            System.out.println("Invalid Type");
        }
    }

    //toString Method returns a String of the Given Name and the Surname
    public String toString (){
        return givenName + " " + surname;
    }

    /**
     * getLongString Method returns a descriptive String of the Card
     * depending on what information is known. Includes Name, Type, Birth
     * Year, Death Year or Degree Year. Only returns information that is known.

     * Uses a series of If Statements to determine the type, then based on
     * the type there is another series of If Statements to determine the
     * information that is known. The String that is returned is based off
     * these If Statements
    */
    public String getLongString(){
        if (getType().equals("FAMOUS")){
            if (getBirthYear() == ComputerScientistCardConstants.UNKNOWN_YEAR &&
            getDeathYear() == ComputerScientistCardConstants.UNKNOWN_YEAR){
                return toString() + " is a " + type + " Computer Scientist";
            }
            if (getBirthYear() != ComputerScientistCardConstants
            .UNKNOWN_YEAR && getDeathYear() == ComputerScientistCardConstants
            .UNKNOWN_YEAR){
                return toString() + " is a " + type + " Computer Scientist " +
                "(born " + getBirthYear() + ")";
            }
            if (getBirthYear() != ComputerScientistCardConstants.UNKNOWN_YEAR
            && getDeathYear() != ComputerScientistCardConstants.UNKNOWN_YEAR){
                return toString() + " is a " + type + " Computer Scientist (" +
                getBirthYear() + "-" + getDeathYear() + ")";
            }
        }

        if (getType().equals("FACULTY")){
            if (getDegreeYear() == ComputerScientistCardConstants.UNKNOWN_YEAR
            || getDegreeYear() == 0){
                return toString() + " is a " + type + " Computer Scientist";
            } else {
                return toString() + " is a " + type + " Computer Scientist " +
                "(since " + getDegreeYear() + ")" ;
            }
        }

        return toString() + " is a Computer Scientist";
    }

    //getType Method returns the Type of the Card
    public String getType() {
        return type;
    }

    /**
     * setType Method sets the type for the cards. The Error statement shows
     * also elaborates on the problem with the input and reminds user of the
     * valid types
    */
    public void setType(String type){
        if (type.equals("FAMOUS") || type.equals("FACULTY")){
            this.type = type;
        }else{
            System.out.println("Invalid type: Must be either 'FAMOUS' or " +
            "'FACULTY'\nWill " + "be " + "ignored, and is " + "still " +
            "Unknown");
        }
    }

    /**
     * 1st setLifespan Method only uses the Birth Year as the parameter. If
     * the year is valid, the birth year is set. If the Birth Year is
     * invalid the error message lets the user know it is invalid, as well as
     * informing them about the status of the Lifespan.
    */
    public void setLifespan (int birth){
        if (birth > 0 && birth < 2024){
            this.birth = birth;
            this.death = ComputerScientistCardConstants.UNKNOWN_YEAR;
        } else {
            if (getDeathYear() > getBirthYear()){
                System.out.println("Invalid Year Entered, Old years kept");
            }
            if (getDeathYear() == 0){
                this.birth = ComputerScientistCardConstants.UNKNOWN_YEAR;
                this.death = ComputerScientistCardConstants.UNKNOWN_YEAR;
                System.out.println("Invalid Year Entered, Set to Unknown");
            }
        }
    }

    /**
     * 2nd setLifespan Method uses both Birth Year and Death Year as
     * parameters. Death Years is only assigned if the type is 'FAMOUS' and
     * there are error messages based on what the error of the input is
    */

    public void setLifespan (int birth, int death){
        if (birth > 0 && birth < death && death < 2024 && getType().equals
        ("FAMOUS")){
            this.birth = birth;
            this.death = death;
        } else {
            if (getType().equals("FACULTY")){
                System.out.println("Death years can only be applied to " +
                "'FAMOUS' type, Years Ignored");
                this.birth = ComputerScientistCardConstants.UNKNOWN_YEAR;
                this.death = ComputerScientistCardConstants.UNKNOWN_YEAR;
            }
            if (death < birth){
                System.out.println("Invalid Inputs, Death cannot be before " +
                "Birth, inputs ignored");
                this.birth = getBirthYear();
                this.death = getDeathYear();
            }
        }
    }

    //getBirthYear Method returns the birth year
    public int getBirthYear(){
        return birth;
    }

    //getDeathYear Method return the death year
    public int getDeathYear(){
        return death;
    }

    /**
     * setDegreeYear Method uses the Year as a parameter. Since Degree Years
     * can only be applied to 'FACULTY' type, it will only be assigned to
     * that type. The Error messages will also tell the user the issue with
     * the input.
    */
    public void setDegreeYear (int year){
        if (getType().equals("FACULTY") && year > 0 && year < 2024){
            this.year = year;
        } else {
            if (!getType().equals("FACULTY")){
                System.out.println("Type is not 'FACULTY', Degree Year " +
                "Unknown");
                this.year = ComputerScientistCardConstants.UNKNOWN_YEAR;
            }
            if (year < 0 || year > 2024){
                System.out.println("Invalid Year Entered, Will be ignored, " +
                "Old Degree Year Kept");
            }
        }
    }

    //getDegreeYear Method returns the degree year
    public int getDegreeYear(){
        return year;
    }

    /**
     * isSame Method users another Computer Scientists, title 'other' as a
     * parameter. Returns a Boolean Value based on if the Card is the same.
     *
     * Since J Smith and Jason Smith can be considered the same
     * person, I evaluated the surnames 1st. If they are the same, we look
     * 3 different scenarios. First, if the Given Name is the Same, method
     * returns True. Second, if the Character at index 0 is the same in both
     * cases, the method returns true. Third, if neither of the previous
     * scenarios are true, the method returns false.
    */
    public boolean isSame (ComputerScientist other){
        while (surname.equals(other.surname)){
            if (givenName.equals(other.givenName)){
                return true;
            } else if (givenName.charAt(0) == other.givenName.charAt(0)){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}