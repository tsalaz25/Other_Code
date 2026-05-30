/**
 * Tests Card and ComputerScientist classes.
 * <p>
 * NOTE: This code will not compile until you have created and
 * implemented the ComputerScientist and Card classes described in the
 * assignment.
 */
public class ComputerScientistCardTest {

    /**
     * main method to test ComputerScience and Card classes
     *
     * @param args command line arguments are ignored
     */
    public static void main(String[] args) {
        Card[] allTheCards = new Card[30];
        int score = 0;
        int tScore = 0;


        /** Test the constructors to make sure they compile and run.*/

        System.out.println("Attempting constructors:");

        ComputerScientist cs1 = new ComputerScientist("Ada",
                "Lovelace");
        ComputerScientist cs1b = new ComputerScientist("Ada",
                "Lovelace");
        ComputerScientist cs2 = new ComputerScientist("Lydia",
                "Tapia");
        ComputerScientist cs3 = new ComputerScientist("Shuang",
                "Luan", "FACULTY");
        ComputerScientist cs4 = new ComputerScientist("James A.",
                "Gosling", "FAMOUS");
        ComputerScientist cs5 = new ComputerScientist("Dennis",
                "Ritchie", "founder");
        ComputerScientist cs6 = new ComputerScientist("Gruia-Catalin",
                "Roman");
        if (cs5.getType().equals(
                ComputerScientistCardConstants.UNKNOWN_TYPE)) {
            tScore += 3;}
        else {
            tScore += 2;
        }
        score += tScore;
        System.out.println("  - Computer Scientist constructors seem " +
                "functional: " + tScore + "/3");
        tScore = 0;

        allTheCards[0] = new Card();
        allTheCards[1] = new Card();
        allTheCards[2] = new Card("Biomedical Engineering, " +
                "Computational Geometry, Computational Medicine",
                cs3);
        allTheCards[3] = new Card("The Father of Oracle and " +
                "Java Programming Language");
        allTheCards[4] = new Card();
        allTheCards[5] = new Card();
        allTheCards[6] = new Card();
        allTheCards[7] = new Card();
        tScore += 2;
        score += tScore;
        System.out.println("  - Collectors Card constructors seem functional: "
                + tScore + "/2");
        tScore = 0;


        /** Setters/getters Tests */

        System.out.println("\nAttempting simple getters/setters:");

        cs1.setType("FAMOUS");
        cs2.setType("FACULTY");
        cs5.setType("FAMOUS");
        if (cs5.getType().equals("FAMOUS") && cs2.getType().equals("FACULTY")) {
            tScore++;
        }
        System.out.println("  - setType: " + tScore + "/1");
        score += tScore;
        tScore = 0;


        cs1b.setLifespan(1815);
        cs4.setLifespan(1956);
        if (1815 == cs1b.getBirthYear() &&
                cs1b.getDeathYear() ==
                        ComputerScientistCardConstants.UNKNOWN_YEAR) {
            tScore++;
        }
        if (1956 == cs4.getBirthYear() &&
                cs4.getDeathYear() ==
                        ComputerScientistCardConstants.UNKNOWN_YEAR) {
            tScore++;
        }

        cs1.setLifespan(1815, 1852);
        cs5.setLifespan(1941, 2011);
        if (1815 == cs1.getBirthYear() && 1852 == cs1.getDeathYear()) {
            tScore++;
        }
        if (1941 == cs5.getBirthYear() && 2011 == cs5.getDeathYear()) {
            tScore++;
        }
        System.out.println("  - setLifespan: " + tScore + "/4");
        score += tScore;
        tScore = 0;


        cs2.setDegreeYear(2009);
        cs3.setDegreeYear(2004);
        if (2009 == cs2.getDegreeYear() && 2004 == cs3.getDegreeYear()) {
            tScore++;
        }
        System.out.println("  - setDegreeYear: " + tScore + "/1");
        score += tScore;
        tScore = 0;

        //Card setters/getters
        allTheCards[0].setComputerScientist(cs1);
        allTheCards[1].setComputerScientist(cs2);
        allTheCards[3].setComputerScientist(cs4);
        if (allTheCards[0].getComputerScientist() == cs1
                && allTheCards[1].getComputerScientist() == cs2) {
            tScore++;
        }
        System.out.println("  - setComputerScientist: " + tScore + "/1");
        score += tScore;
        tScore = 0;


        String tempContr = "Mathematician who worked with Charles Babbage on " +
                "his proposed Analytical Engine, considered to be the first  " +
                "computer programmer";
        allTheCards[0].setContribution(tempContr);
        allTheCards[1].setContribution("Robotic Motion Planning");
        if (allTheCards[0].getContribution().equals(tempContr) &&
                allTheCards[1].getContribution().equals("Robotic Motion " +
                        "Planning")) {
            tScore++;
        }
        System.out.println("  - setContribution: " + tScore + "/1");
        score += tScore;
        tScore = 0;


        if (allTheCards[2].getYear() == 2004) {
            tScore++;
        }
        if (allTheCards[3].getYear() == 1956) {
            tScore++;
        }
        System.out.println("  - setYear: " + tScore + "/2");
        score += tScore;
        tScore = 0;


        /** BAD setters/getters */
        System.out.println("\nAttempting bad-value setters:");


        cs1.setLifespan(-100);
        cs2.setLifespan(2025);
        if (cs1.getBirthYear() == 1815) {
            tScore++;
        }
        if (cs2.getBirthYear() ==
                ComputerScientistCardConstants.UNKNOWN_YEAR) {
            tScore++;
        }

        cs3.setLifespan(1980, 2023);
        cs5.setLifespan(1941, 1940);
        if (cs3.getBirthYear() ==
                ComputerScientistCardConstants.UNKNOWN_YEAR
                && cs3.getDeathYear() ==
                ComputerScientistCardConstants.UNKNOWN_YEAR) {
            tScore++;
        }
        if (cs5.getBirthYear() == 1941 && cs5.getDeathYear() == 2011) {
            tScore++;
        }
        System.out.println("  - BAD setLifespan: " + tScore + "/4");
        score += tScore;
        tScore = 0;


        cs2.setDegreeYear(-100);
        cs1.setDegreeYear(1980);
        if (cs2.getDegreeYear() == 2009) {
            tScore++;
        }
        if (cs1.getDegreeYear() ==
                ComputerScientistCardConstants.UNKNOWN_YEAR) {
            tScore++;
        }
        System.out.println("  - BAD setDegreeYear: " + tScore + "/2");
        score += tScore;
        tScore = 0;


        cs5.setType("founder");
        if (cs5.getType().equals("FAMOUS")) {
            tScore++;
        }
        System.out.println("  - BAD setType: " + tScore + "/1");
        score += tScore;
        tScore = 0;


        allTheCards[0].setContribution("");
        if (allTheCards[0].getContribution().equals(tempContr)) {
            tScore++;
        }
        System.out.println("  - BAD setContribution: " + tScore + "/1");
        score += tScore;
        tScore = 0;


        if (allTheCards[7].getYear() ==
                ComputerScientistCardConstants.UNKNOWN_YEAR) {
            tScore++;
        }
        System.out.println("  - BAD setYear: " + tScore + "/1");
        score += tScore;
        tScore = 0;


        /** Equality Tests */
        System.out.println("\nAttempting equality tests:");

        if (cs1.isSame(
                new ComputerScientist("Ada", "Lovelace"))) {
            tScore++;
        }
        if (cs2.isSame(
                new ComputerScientist("L", "Tapia"))) {
            tScore++;
        }
        if (cs3.isSame(allTheCards[2].getComputerScientist())) {
            tScore++;
        }
        if (!cs3.isSame(allTheCards[1].getComputerScientist())) {
            tScore++;
        }
        if (!cs1.isSame(
                new ComputerScientist("P", "Lovelace"))) {
            tScore++;
        }
        if (!allTheCards[0].getComputerScientist().isSame(
                allTheCards[2].getComputerScientist())) {
            tScore++;
        }
        System.out.println("  - isSame: " + tScore + "/6");
        score += tScore;
        tScore = 0;


        allTheCards[4].setComputerScientist(cs3);
        if (allTheCards[2].hasSameComputerScientist(allTheCards[4])) {
            tScore++;
        }
        if (!allTheCards[3].hasSameComputerScientist(allTheCards[2])) {
            tScore++;
        }
        System.out.println("  - hasSameComputerScientist: " + tScore + "/2");
        score += tScore;
        tScore = 0;


        allTheCards[4].setContribution("Biomedical Engineering, Computational" +
                " Geometry, Computational Medicine");
        allTheCards[5] = new Card("Computational Biology", cs2);
        allTheCards[6] = new Card("Robotic Motion Planning", cs2);
        if (!allTheCards[2].isSame(new Card("hello", cs3))) {
            tScore++;
        }
        if (allTheCards[2].isSame(allTheCards[4])) {
            tScore++;
        }
        if (!allTheCards[1].isSame(allTheCards[5])) {
            tScore++;
        }
        if (allTheCards[1].isSame(allTheCards[6])) {
            tScore++;
        }
        System.out.println("  - equals: " + tScore + "/4");
        score += tScore;
        tScore = 0;


        /** toString Tests */
        System.out.println("\nAttempting toString tests:");
        if (cs1.toString().equals("Ada Lovelace")) {
            tScore++;
        }
        if (!cs2.toString().equals(cs1.toString())) {
            tScore++;
        }
        System.out.println("  - toString (ComputerScience): " + tScore + "/2");
        score += tScore;
        tScore = 0;

        cs3 = new ComputerScientist("Shuang", "Luan",
                "FACULTY");
        String st1 = "James A. Gosling is a FAMOUS Computer Scientist (born " +
                "1956)";
        String st2 = "Ada Lovelace is a FAMOUS Computer Scientist (1815-1852)";
        String st3 = "Lydia Tapia is a FACULTY Computer Scientist (since " +
                "2009)";
        String st4 = "Shuang Luan is a FACULTY Computer Scientist";
        String st5 = "Dennis Ritchie is a FAMOUS Computer Scientist (born 1941)";
        String st6 = "Gruia-Catalin Roman is a Computer Scientist";
        if (st1.equals(cs4.getLongString())) {
            tScore++;
        }
        if (st2.equals(cs1.getLongString())) {
            tScore++;
        }
        if (st3.equals(cs2.getLongString())) {
            tScore++;
        }
        if (st4.equals(cs3.getLongString())) {
            tScore++;
        }
        if (!st5.equals(cs5.getLongString())) {
            tScore++;
        }
        if (st6.equals(cs6.getLongString())) {
            tScore++;
        }
        System.out.println("  - getLongString: " + tScore + "/6");
        score += tScore;
        tScore = 0;


        System.out.println("\nAttempting Card toString:");
        allTheCards[0].setComputerScientist(cs1);
        allTheCards[1].setComputerScientist(cs2);
        allTheCards[2].setComputerScientist(cs3);
        allTheCards[3].setComputerScientist(cs4);
        allTheCards[7].setComputerScientist(cs5);
        allTheCards[5].setComputerScientist(cs6);
        allTheCards[5].setContribution("Software for Satellite-Swarms");

        if (allTheCards[0].toString().equals("Ada Lovelace is a FAMOUS " +
                "Computer Scientist " +
                "(1815-1852)\n" +
                "Accomplishment: Mathematician who worked with Charles " +
                "Babbage on his proposed Analytical Engine, considered to be " +
                "the first  computer programmer")) {
            tScore++;
        } else System.out.println("1 " + allTheCards[0].toString());
        if (allTheCards[1].toString().equals("Lydia Tapia is a FACULTY " +
                "Computer Scientist (since 2009)\nArea of Study: Robotic " +
                "Motion Planning")) {
            tScore++;
        } else System.out.println("2 " + allTheCards[1].toString());
        if (allTheCards[2].toString().equals("Shuang Luan is a FACULTY " +
                "Computer Scientist\n" +
                "Area of Study: Biomedical Engineering, Computational " +
                "Geometry, Computational Medicine")) {
            tScore++;
        } else System.out.println("3 " + allTheCards[2].toString());
        if (allTheCards[3].toString().equals("James A. Gosling is a FAMOUS " +
                "Computer Scientist (born 1956)\n" + "Accomplishment: The " +
                "Father of Oracle and Java Programming Language")) {
            tScore++;
        } else System.out.println("" + allTheCards[3].toString());
        if (allTheCards[7].toString().equals("Dennis Ritchie is a FAMOUS " +
                "Computer Scientist (1941-2011)")) {
            tScore++;
        } else System.out.println("5 " + allTheCards[7].toString());
        if (allTheCards[5].toString().equals("Gruia-Catalin Roman is a " +
                "Computer Scientist\n" +
                "Contribution: Software for Satellite-Swarms")) {
            tScore++;
        } else System.out.println("6 " + allTheCards[5].toString());

        System.out.println("  - toString (Card): " + tScore + "/6");
        score += tScore;
        System.out.println("\nOverall Score: " + score + "/50");

    }
}
