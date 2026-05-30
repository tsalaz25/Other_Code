public class CardCollectionTest {
    public static void main(String[] args){
        CardCollection collection = new CardCollection(400);
        int score = 0;
        int tScore = 0;

        System.out.println("Let's find out some information about our collection...");

        if(collection.getTotalNumCopies() == 0) { tScore++; }
        System.out.println(" - getTotalNumCopies works for an empty collection: " + tScore + "/1");
        score += tScore; tScore = 0;

        if(collection.getTotalTradedCards() == 0 ) { tScore++; }
        System.out.println( " - getTotalCheckedOut works for empty collection: " + tScore + "/1" );
        score += tScore; tScore = 0;

        if(collection.getCollectionStatus().equals( "Total unique cards: 0\n" +
                "Total number of copies: 0\n"+
                "Total number of cards traded: 0" ) ) { tScore++; }
        System.out.println( " - getCollectionStatus works for empty collection: " + tScore + "/1\n" );
        score += tScore; tScore = 0;

        Card[] cards = new Card[26];
        ComputerScientist cs1 = new ComputerScientist("Soraya", "Abad-Mota", "FACULTY");
        cs1.setDegreeYear(2006);
        cards[0] = new Card("Semantic Data Modeling", cs1);
        ComputerScientist cs2 = new ComputerScientist( "Amanda", "Bienz","FACULTY");
        cs2.setDegreeYear(2018);
        cards[1] = new Card("High-Performance Computing", cs2);
        ComputerScientist cs3 = new ComputerScientist( "Patrick", "Bridges","FACULTY");
        cs3.setDegreeYear(2002);
        cards[2] = new Card("High-Performance and Large-scale Computing Systems", cs3);
        ComputerScientist cs4 = new ComputerScientist( "Leah", "Buechley","FACULTY");
        cs4.setDegreeYear(2007);
        cards[3] = new Card("Creative Potential of Computation", cs4);
        ComputerScientist cs5 = new ComputerScientist("Brooke", "Chenoweth Creel", "FACULTY");
        cards[4] = new Card("Agent-Based Security Simulations", cs5);
        ComputerScientist cs6 = new ComputerScientist("Trilce", "Estrade", "FACULTY");
        cs6.setDegreeYear(2012);
        cards[5] = new Card("Applying Scalable Machine Learning Techniques to Data-Intensive Scientific " +
                "Problems", cs6);
        ComputerScientist cs7 = new ComputerScientist("Matthew", "Fricke", "FACULTY");
        cs7.setDegreeYear(2017);
        cards[6] = new Card("Computational Biology", cs7);
        ComputerScientist cs8 = new ComputerScientist("Joseph", "Haugh", "FACULTY");
        cs8.setDegreeYear(2020);
        cards[7] = new Card("Type Theory", cs8);
        ComputerScientist cs9 = new ComputerScientist("Bruna", "Jacobson", "FACULTY");
        cs9.setDegreeYear(2012);
        cards[8] = new Card("Computational Biophysics", cs9);
        ComputerScientist cs10 = new ComputerScientist("Deepak", "Kapur", "FACULTY");
        cs10.setDegreeYear(1980);
        cards[9] = new Card("Groebner Basis Computation", cs10);
        ComputerScientist cs11 = new ComputerScientist("Matthew", "Lakin", "FACULTY");
        cs11.setDegreeYear(2010);
        cards[10] = new Card("Molecular Computing", cs11);
        ComputerScientist cs12 = new ComputerScientist("Shuang", "Luan", "FACULTY");
        cs12.setDegreeYear(2004);
        cards[11] = new Card("Biomedical Engineering", cs12);
        ComputerScientist cs13 = new ComputerScientist("Melanie", "Moses", "FACULTY");
        cs13.setDegreeYear(2005);
        cards[12] = new Card("Complex Adaptive Systems", cs13);
        ComputerScientist cs14 = new ComputerScientist("Abdullah", "Mueen", "FACULTY");
        cs14.setDegreeYear(2012);
        cards[13] = new Card("Real-Time Pattern Discovery and Similarity Search", cs14);
        ComputerScientist cs15 = new ComputerScientist("Gruia-Catalin", "Roman", "FACULTY");
        cs15.setDegreeYear(1976);
        cards[14] = new Card("Software for Satellite-Swarms", cs15);
        ComputerScientist cs16 = new ComputerScientist("Jared", "Saia", "FACULTY");
        cs16.setDegreeYear(2002);
        cards[15] = new Card("Distributed Algorithms", cs16);
        ComputerScientist cs17 = new ComputerScientist("Darko", "Stefanovic", "FACULTY");
        cs17.setDegreeYear(1999);
        cards[16] = new Card("Molecular Computing", cs17);
        ComputerScientist cs18 = new ComputerScientist("Lydia", "Tapia", "FACULTY");
        cs18.setDegreeYear(2009);
        cards[17] = new Card("Robotic Motion Planning", cs18);
        ComputerScientist cs19 = new ComputerScientist("Lance", "Williams", "FACULTY");
        cs19.setDegreeYear(1994);
        cards[18] = new Card("Robust Spatial Computation", cs19);

        ComputerScientist cs20 = new ComputerScientist("James A.", "Gosling", "FAMOUS");
        cs20.setLifespan(1956);
        cards[19] = new Card("The Father of Oracle and Java Programming Language", cs20);
        ComputerScientist cs21 = new ComputerScientist("Dennis", "Ritchie", "FAMOUS");
        cs21.setLifespan(1941, 2011);
        cards[20] = new Card("The Father of C Programming Language", cs21);
        ComputerScientist cs22 = new ComputerScientist("Steve", "Wozniak", "FAMOUS");
        cs22.setLifespan(1950);
        cards[21] = new Card("The Co-Founder of Apple", cs22);
        ComputerScientist cs23 = new ComputerScientist("Grace Murray", "Hopper", "FAMOUS");
        cs23.setLifespan(1906, 1992);
        cards[22] = new Card("Female Military Inventor who Developed COBOL", cs23);
        ComputerScientist cs24 = new ComputerScientist("Sophie", "Wilson", "FAMOUS");
        cs24.setLifespan(1957);
        cards[23] = new Card("The Architect of The Modern Computing World", cs24);
        ComputerScientist cs25 = new ComputerScientist("Margaret", "Hamilton", "FAMOUS");
        cs25.setLifespan(1936);
        cards[24] = new Card("Directed development of the on-board flight software for NASA's Apollo program"
                , cs25);
        ComputerScientist cs26 = new ComputerScientist("Satoshi", "Nakamoto", "FAMOUS");
        cs26.setLifespan(1975);
        cards[25] = new Card("Mysterious creator of Bitcoin", cs26);

        collection.addCards(cards);
        System.out.println("Let's add a set of cards, then let's add some more...");

        if(collection.getTotalNumCopies() == 26) { tScore++; }
        System.out.println(" - addCards seems to be working: " + tScore + "/1");
        score += tScore; tScore = 0;

        // Make a duplicate card object to make sure it is added properly
        Card dupCard = new Card("Mysterious creator of Bitcoin", cs26);

        collection.addCard( cards[25] );
        collection.addCard( cards[25] );
        collection.addCard( cards[25] );
        collection.addCard( cards[14] );
        collection.addCard( cards[14] );
        collection.addCard( dupCard );
        collection.addCard( cards[1] );
        collection.addCard( cards[2] );
        collection.addCard( cards[5] );
        collection.addCard( cards[9] );
        collection.addCard( cards[12] );
        collection.addCard( cards[15] );
        collection.addCard( cards[20] );
        collection.addCard( dupCard );

        if( collection.getTotalNumCopies() == 40 ) { tScore++; }
        System.out.println( " - addCard seems to work for duplicates: " + tScore + "/1" );
        score += tScore; tScore = 0;

        ComputerScientist cs27 = new ComputerScientist("Claude", "Shannon", "FAMOUS");
        cs27.setLifespan(1916, 2001);
        Card c1 = new Card("Pioneer of Artificial Intelligence", cs27);
        ComputerScientist cs28 = new ComputerScientist("Guido", "Van Rossum", "FAMOUS");
        cs28.setLifespan(1956);
        Card c2 = new Card("Software for Satellite-Swarms", cs28);
        ComputerScientist cs29 = new ComputerScientist("Ada", "Lovelace", "FAMOUS");
        cs29.setLifespan(1815, 1852);
        Card c3 = new Card("Mathematician who worked with Charles Babbage on his proposed Analytical " +
                "Engine, considered to be the first computer programmer", cs29);

        collection.addCard( c1 );
        collection.addCard( c2 );

        if( collection.getTotalNumCopies() == 42 ) { tScore++; }
        System.out.println( " - addCard seems to work for new cards: " + tScore + "/1" );
        score += tScore; tScore = 0;

        Card[] moreCards = new Card[3];
        moreCards[0] = new Card("Nanotechnology", cs17);
        moreCards[1] = new Card("Computational Biology", cs18);
        moreCards[2] = new Card("Artificial Life", cs19);

        collection.addCards(moreCards);
        if(collection.getTotalNumCopies() == 45) { tScore++; }
        System.out.println( " - addCards works with non-empty collections: " + tScore + "/1" );
        score += tScore; tScore = 0;

        if( collection.getCollectionStatus().equals( "Total unique cards: 31\n"+
                "Total number of copies: 45\n"+
                "Total number of cards traded: 0" ) ) { tScore++; }
        System.out.println( " - getCollectionStatus works after adding cards: " + tScore + "/1\n" );
        score += tScore; tScore = 0;

        System.out.println("Let's try trading some cards...");

        if(collection.tradeCards(c3, dupCard).equals("Traded a copy for a new card!")) { tScore++; }
        if(collection.tradeCards(c2, cards[1]).equals("Traded a copy for a new card!")) { tScore++; }
        System.out.println( " - tradeCards works when a card can be traded: " + tScore + "/2" );
        score += tScore; tScore = 0;
        if(collection.tradeCards(moreCards[0], c3).equals("Not enough copies to trade.")) { tScore++; }
        if(collection.tradeCards(dupCard, cards[1]).equals("Not enough copies to trade.")) { tScore++; }
        System.out.println( " - tradeCards works when there aren't enough copies of a card: " + tScore + "/2" );
        score += tScore; tScore = 0;
        ComputerScientist cs30 = new ComputerScientist("Niklaus E.", "Wirth", "FAMOUS");
        Card c4 = new Card("Father of Pascal Programming Language", cs30);
        if(collection.tradeCards(moreCards[1], c4).equals("Requested card isn't in the collection.")) { tScore++; }
        Card c4b = new Card("Computer Science Education", cs5);
        if(collection.tradeCards(moreCards[1], c4b).equals("Requested card isn't in the collection.")) { tScore++; }
        System.out.println( " - tradeCards works when a card is not in the collection: " + tScore + "/2" );
        score += tScore; tScore = 0;
        if( collection.getCollectionStatus().equals( "Total unique cards: 32\n"+
                "Total number of copies: 45\n"+
                "Total number of cards traded: 2" ) ) { tScore+=2; }
        System.out.println( " - getCollectionStatus works after trading cards: " + tScore + "/2\n" );
        score += tScore; tScore = 0;

        System.out.println("Let's try printing out the whole collection...");
        if(collection.toString().equals("0. Soraya Abad-Mota is a FACULTY Computer Scientist " +
                "(since 2006)\n" +
                "Area of Study: Semantic Data Modeling\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "1. Amanda Bienz is a FACULTY Computer Scientist " +
                "(since 2018)\n" +
                "Area of Study: High-Performance Computing\n" +
                "\tCopies: 1\n" +
                "\tTraded: 1\n" +
                "2. Patrick Bridges is a FACULTY Computer Scientist " +
                "(since 2002)\n" +
                "Area of Study: High-Performance and Large-scale Computing Systems\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "3. Leah Buechley is a FACULTY Computer Scientist " +
                "(since 2007)\n" +
                "Area of Study: Creative Potential of Computation\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "4. Brooke Chenoweth Creel is a FACULTY Computer Scientist\n" +
                "Area of Study: Agent-Based Security Simulations\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "5. Trilce Estrade is a FACULTY Computer Scientist " +
                "(since 2012)\n" +
                "Area of Study: Applying Scalable Machine Learning Techniques to Data-Intensive Scientific Problems\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "6. Matthew Fricke is a FACULTY Computer Scientist " +
                "(since 2017)\n" +
                "Area of Study: Computational Biology\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "7. Joseph Haugh is a FACULTY Computer Scientist " +
                "(since 2020)\n" +
                "Area of Study: Type Theory\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "8. Bruna Jacobson is a FACULTY Computer Scientist " +
                "(since 2012)\n" +
                "Area of Study: Computational Biophysics\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "9. Deepak Kapur is a FACULTY Computer Scientist " +
                "(since 1980)\n" +
                "Area of Study: Groebner Basis Computation\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "10. Matthew Lakin is a FACULTY Computer Scientist " +
                "(since 2010)\n" +
                "Area of Study: Molecular Computing\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "11. Shuang Luan is a FACULTY Computer Scientist " +
                "(since 2004)\n" +
                "Area of Study: Biomedical Engineering\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "12. Melanie Moses is a FACULTY Computer Scientist " +
                "(since 2005)\n" +
                "Area of Study: Complex Adaptive Systems\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "13. Abdullah Mueen is a FACULTY Computer Scientist " +
                "(since 2012)\n" +
                "Area of Study: Real-Time Pattern Discovery and Similarity Search\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "14. Gruia-Catalin Roman is a FACULTY Computer Scientist " +
                "(since 1976)\n" +
                "Area of Study: Software for Satellite-Swarms\n" +
                "\tCopies: 3\n" +
                "\tTraded: 0\n" +
                "15. Jared Saia is a FACULTY Computer Scientist " +
                "(since 2002)\n" +
                "Area of Study: Distributed Algorithms\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "16. Darko Stefanovic is a FACULTY Computer Scientist " +
                "(since 1999)\n" +
                "Area of Study: Molecular Computing\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "17. Lydia Tapia is a FACULTY Computer Scientist " +
                "(since 2009)\n" +
                "Area of Study: Robotic Motion Planning\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "18. Lance Williams is a FACULTY Computer Scientist " +
                "(since 1994)\n" +
                "Area of Study: Robust Spatial Computation\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "19. James A. Gosling is a FAMOUS Computer Scientist " +
                "(born 1956)\n" +
                "Accomplishment: The Father of Oracle and Java Programming Language\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "20. Dennis Ritchie is a FAMOUS Computer Scientist " +
                "(1941-2011)\n" +
                "Accomplishment: The Father of C Programming Language\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "21. Steve Wozniak is a FAMOUS Computer Scientist " +
                "(born 1950)\n" +
                "Accomplishment: The Co-Founder of Apple\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "22. Grace Murray Hopper is a FAMOUS Computer Scientist " +
                "(1906-1992)\n" +
                "Accomplishment: Female Military Inventor who Developed COBOL\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "23. Sophie Wilson is a FAMOUS Computer Scientist " +
                "(born 1957)\n" +
                "Accomplishment: The Architect of The Modern Computing World\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "24. Margaret Hamilton is a FAMOUS Computer Scientist " +
                "(born 1936)\n" +
                "Accomplishment: Directed development of the on-board flight software for NASA's Apollo program\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "25. Satoshi Nakamoto is a FAMOUS Computer Scientist " +
                "(born 1975)\n" +
                "Accomplishment: Mysterious creator of Bitcoin\n" +
                "\tCopies: 5\n" +
                "\tTraded: 1\n" +
                "26. Claude Shannon is a FAMOUS Computer Scientist " +
                "(1916-2001)\n" +
                "Accomplishment: Pioneer of Artificial Intelligence\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "27. Guido Van Rossum is a FAMOUS Computer Scientist " +
                "(born 1956)\n" +
                "Accomplishment: Software for Satellite-Swarms\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "28. Darko Stefanovic is a FACULTY Computer Scientist " +
                "(since 1999)\n" +
                "Area of Study: Nanotechnology\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "29. Lydia Tapia is a FACULTY Computer Scientist " +
                "(since 2009)\n" +
                "Area of Study: Computational Biology\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "30. Lance Williams is a FACULTY Computer Scientist " +
                "(since 1994)\n" +
                "Area of Study: Artificial Life\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "31. Ada Lovelace is a FAMOUS Computer Scientist " +
                "(1815-1852)\n" +
                "Accomplishment: Mathematician who worked with Charles Babbage on his proposed Analytical Engine, considered to be the first computer programmer\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "Total unique cards: 32\n" +
                "Total number of copies: 45\n" +
                "Total number of cards traded: 2")) { tScore+=2; }

        System.out.println( " - toString works: " + tScore + "/2\n" );
        score += tScore; tScore = 0;

        System.out.println("Let's get some information about our collection...");


        ComputerScientist cs31 = new ComputerScientist("Lydia", "Tapia", "FACULTY");
        Card c5 = new Card("Computational Biology", cs31);
        collection.addCard(c5);
        if(collection.numCardsForCS(cs26) == 1) { tScore++; }
        if(collection.numCardsForCS(cs31) == 2) { tScore++; }
        if(collection.numCardsForCS(new ComputerScientist("Clarence", "Ellis")) == 0) { tScore++; }
        if(collection.numCardsForCS(cs22) == 1) { tScore++; }
        System.out.println( " - numCardsForCS works: " + tScore + "/4" );
        score += tScore; tScore = 0;



        Card c6 = new Card("Computational Geometry", cs12);
        Card c7 = new Card("Computational Medicine", cs12);
        Card c8 = new Card("Human and Computer Vision", cs19);
        Card c9 = new Card("Programming Language Implementation", cs17);
        Card c10 = new Card("Machine Learning", cs18);
        collection.addCards(new Card[]{c6, c7, c8, c9, c10});
        if(collection.listCardsForCS(cs12).equals("Shuang Luan is a FACULTY Computer Scientist " +
                "(since 2004)\n" +
                "Area of Study: Biomedical Engineering\n" +
                "Shuang Luan is a FACULTY Computer Scientist " +
                "(since 2004)\n" +
                "Area of Study: Computational Geometry\n" +
                "Shuang Luan is a FACULTY Computer Scientist " +
                "(since 2004)\n" +
                "Area of Study: Computational Medicine\n")) { tScore++;}
        if(collection.listCardsForCS(cs19).equals("Lance Williams is a FACULTY Computer Scientist " +
                "(since 1994)\n" +
                "Area of Study: Robust Spatial Computation\n" +
                "Lance Williams is a FACULTY Computer Scientist " +
                "(since 1994)\n" +
                "Area of Study: Artificial Life\n" +
                "Lance Williams is a FACULTY Computer Scientist " +
                "(since 1994)\n" +
                "Area of Study: Human and Computer Vision\n")) { tScore++; }
        if(collection.listCardsForCS(cs17).equals("Darko Stefanovic is a FACULTY Computer Scientist " +
                "(since 1999)\n" +
                "Area of Study: Molecular Computing\n" +
                "Darko Stefanovic is a FACULTY Computer Scientist " +
                "(since 1999)\n" +
                "Area of Study: Nanotechnology\n" +
                "Darko Stefanovic is a FACULTY Computer Scientist " +
                "(since 1999)\n" +
                "Area of Study: Programming Language Implementation\n")) { tScore++;}
        if(collection.listCardsForCS(new ComputerScientist
                ("Katherine", "Johnson", "FAMOUS")).equals
                ("No cards for Katherine Johnson in the collection.")) { tScore++;}
        System.out.println( " - listCardsForCS works: " + tScore + "/4" );
        score += tScore; tScore = 0;


        if(collection.listCardsByContribution("father").equals("James A. Gosling is a FAMOUS Computer Scientist " +
                "(born 1956)\n" +
                "Accomplishment: The Father of Oracle and Java Programming Language\n" +
                "Dennis Ritchie is a FAMOUS Computer Scientist " +
                "(1941-2011)\n" +
                "Accomplishment: The Father of C Programming Language\n")) { tScore++;}
        if(collection.listCardsByContribution("computational").equals
                ("Matthew Fricke is a FACULTY Computer Scientist " +
                "(since 2017)\n" +
                "Area of Study: Computational Biology\n" +
                "Bruna Jacobson is a FACULTY Computer Scientist " +
                "(since 2012)\n" +
                "Area of Study: Computational Biophysics\n" +
                "Lydia Tapia is a FACULTY Computer Scientist " +
                "(since 2009)\n" +
                "Area of Study: Computational Biology\n" +
                "Shuang Luan is a FACULTY Computer Scientist " +
                "(since 2004)\n" +
                "Area of Study: Computational Geometry\n" +
                "Shuang Luan is a FACULTY Computer Scientist " +
                "(since 2004)\n" +
                "Area of Study: Computational Medicine\n")) { tScore++;}
        if(collection.listCardsByContribution("biology").equals("Matthew Fricke is a FACULTY Computer Scientist " +
                "(since 2017)\n" +
                "Area of Study: Computational Biology\n" +
                "Lydia Tapia is a FACULTY Computer Scientist " +
                "(since 2009)\n" +
                "Area of Study: Computational Biology\n")) { tScore++;}
        if(collection.listCardsByContribution("teen").equals("No cards with \"teen\" as a contribution!")) { tScore++;}
        System.out.println( " - listCardsByContribution works: " + tScore + "/4" );
        score += tScore; tScore = 0;

        System.out.println();
        System.out.println("Bonus! Let's delete a card from our collection...");
        if(collection.deleteCard(c6).equals("Card has been removed!")) { tScore++;}
        if(collection.deleteCard(new Card("First African American to receive a PHD in CS", new ComputerScientist
                ("Katherine", "Johnson", "FAMOUS"))).equals
                ("Card does not exist in this collection.")) { tScore++;}
        if(collection.getCollectionStatus().equals("Total unique cards: 36\n" +
                "Total number of copies: 50\n" +
                "Total number of cards traded: 2")) { tScore++;}
        if(collection.toString().equals("0. Soraya Abad-Mota is a FACULTY Computer Scientist " +
                "(since 2006)\n" +
                "Area of Study: Semantic Data Modeling\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "1. Amanda Bienz is a FACULTY Computer Scientist " +
                "(since 2018)\n" +
                "Area of Study: High-Performance Computing\n" +
                "\tCopies: 1\n" +
                "\tTraded: 1\n" +
                "2. Patrick Bridges is a FACULTY Computer Scientist " +
                "(since 2002)\n" +
                "Area of Study: High-Performance and Large-scale Computing Systems\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "3. Leah Buechley is a FACULTY Computer Scientist " +
                "(since 2007)\n" +
                "Area of Study: Creative Potential of Computation\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "4. Brooke Chenoweth Creel is a FACULTY Computer Scientist\n" +
                "Area of Study: Agent-Based Security Simulations\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "5. Trilce Estrade is a FACULTY Computer Scientist " +
                "(since 2012)\n" +
                "Area of Study: Applying Scalable Machine Learning Techniques to Data-Intensive Scientific Problems\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "6. Matthew Fricke is a FACULTY Computer Scientist " +
                "(since 2017)\n" +
                "Area of Study: Computational Biology\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "7. Joseph Haugh is a FACULTY Computer Scientist " +
                "(since 2020)\n" +
                "Area of Study: Type Theory\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "8. Bruna Jacobson is a FACULTY Computer Scientist " +
                "(since 2012)\n" +
                "Area of Study: Computational Biophysics\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "9. Deepak Kapur is a FACULTY Computer Scientist " +
                "(since 1980)\n" +
                "Area of Study: Groebner Basis Computation\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "10. Matthew Lakin is a FACULTY Computer Scientist " +
                "(since 2010)\n" +
                "Area of Study: Molecular Computing\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "11. Shuang Luan is a FACULTY Computer Scientist " +
                "(since 2004)\n" +
                "Area of Study: Biomedical Engineering\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "12. Melanie Moses is a FACULTY Computer Scientist " +
                "(since 2005)\n" +
                "Area of Study: Complex Adaptive Systems\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "13. Abdullah Mueen is a FACULTY Computer Scientist " +
                "(since 2012)\n" +
                "Area of Study: Real-Time Pattern Discovery and Similarity Search\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "14. Gruia-Catalin Roman is a FACULTY Computer Scientist " +
                "(since 1976)\n" +
                "Area of Study: Software for Satellite-Swarms\n" +
                "\tCopies: 3\n" +
                "\tTraded: 0\n" +
                "15. Jared Saia is a FACULTY Computer Scientist " +
                "(since 2002)\n" +
                "Area of Study: Distributed Algorithms\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "16. Darko Stefanovic is a FACULTY Computer Scientist " +
                "(since 1999)\n" +
                "Area of Study: Molecular Computing\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "17. Lydia Tapia is a FACULTY Computer Scientist " +
                "(since 2009)\n" +
                "Area of Study: Robotic Motion Planning\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "18. Lance Williams is a FACULTY Computer Scientist " +
                "(since 1994)\n" +
                "Area of Study: Robust Spatial Computation\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "19. James A. Gosling is a FAMOUS Computer Scientist " +
                "(born 1956)\n" +
                "Accomplishment: The Father of Oracle and Java Programming Language\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "20. Dennis Ritchie is a FAMOUS Computer Scientist " +
                "(1941-2011)\n" +
                "Accomplishment: The Father of C Programming Language\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "21. Steve Wozniak is a FAMOUS Computer Scientist " +
                "(born 1950)\n" +
                "Accomplishment: The Co-Founder of Apple\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "22. Grace Murray Hopper is a FAMOUS Computer Scientist " +
                "(1906-1992)\n" +
                "Accomplishment: Female Military Inventor who Developed COBOL\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "23. Sophie Wilson is a FAMOUS Computer Scientist " +
                "(born 1957)\n" +
                "Accomplishment: The Architect of The Modern Computing World\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "24. Margaret Hamilton is a FAMOUS Computer Scientist " +
                "(born 1936)\n" +
                "Accomplishment: Directed development of the on-board flight software for NASA's Apollo program\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "25. Satoshi Nakamoto is a FAMOUS Computer Scientist " +
                "(born 1975)\n" +
                "Accomplishment: Mysterious creator of Bitcoin\n" +
                "\tCopies: 5\n" +
                "\tTraded: 1\n" +
                "26. Claude Shannon is a FAMOUS Computer Scientist " +
                "(1916-2001)\n" +
                "Accomplishment: Pioneer of Artificial Intelligence\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "27. Guido Van Rossum is a FAMOUS Computer Scientist " +
                "(born 1956)\n" +
                "Accomplishment: Software for Satellite-Swarms\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "28. Darko Stefanovic is a FACULTY Computer Scientist " +
                "(since 1999)\n" +
                "Area of Study: Nanotechnology\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "29. Lydia Tapia is a FACULTY Computer Scientist " +
                "(since 2009)\n" +
                "Area of Study: Computational Biology\n" +
                "\tCopies: 2\n" +
                "\tTraded: 0\n" +
                "30. Lance Williams is a FACULTY Computer Scientist " +
                "(since 1994)\n" +
                "Area of Study: Artificial Life\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "31. Ada Lovelace is a FAMOUS Computer Scientist " +
                "(1815-1852)\n" +
                "Accomplishment: Mathematician who worked with Charles Babbage on his proposed Analytical Engine, considered to be the first computer programmer\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "32. Shuang Luan is a FACULTY Computer Scientist " +
                "(since 2004)\n" +
                "Area of Study: Computational Medicine\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "33. Lance Williams is a FACULTY Computer Scientist " +
                "(since 1994)\n" +
                "Area of Study: Human and Computer Vision\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "34. Darko Stefanovic is a FACULTY Computer Scientist " +
                "(since 1999)\n" +
                "Area of Study: Programming Language Implementation\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "35. Lydia Tapia is a FACULTY Computer Scientist " +
                "(since 2009)\n" +
                "Area of Study: Machine Learning\n" +
                "\tCopies: 1\n" +
                "\tTraded: 0\n" +
                "Total unique cards: 36\n" +
                "Total number of copies: 50\n" +
                "Total number of cards traded: 2")) { tScore+=2;}
        System.out.println( " - card deletion: " + tScore + "/5\n" );
        score += tScore; tScore = 0;
        //TOTAL SCORE IS 30 (35 WITH BONUS)
        System.out.println("Total score: " + score + "/30  \n   (can be up to 35 with bonus)");
    }

}
