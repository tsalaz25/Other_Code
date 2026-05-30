import java.util.Arrays;

/**
 * Class to test the World methods separately.
 */
public class WordleTester {
    private static int correctTests = 0;
    private static int totalTests = 0;

    /** Clear test count variables */
    private static void clearCounts() {
        correctTests = 0;
        totalTests = 0;
    }

    /** 
     * Update test count variables depending on if test passed.
     * @param correct True if test counts as correct.
     */
    private static void countTest(boolean correct) {
        if(correct) {
            correctTests++;
        } else {
            // Uncomment next line of code to see which test(s) are failing,
            // but don't do it unless you are only failing a few
            //new Exception().printStackTrace(System.out);
        }
        totalTests++;
    }

    /** 
     * Print the testing results.
     * @param methodName The name of the method tested.
     */
    private static void printResults(String methodName) {
        String msg = "";
        if(correctTests < totalTests) {
            // I want failed tests to really jump out at you.
            msg = " INCORRECT!";
        }
        System.out.println("testing " + methodName + ": passes " +
                           correctTests + " of " + totalTests + " tests" + msg);
    }

    private static void testSelectRandomWord() {
        // Make a tiny dictionary
        String[] dictionary = { "foo", "bar", "baz", "qux", "quux" };
        // Sort so I'll be able to use binarySearch
        Arrays.sort(dictionary);
        String[] dictionaryCopy = Arrays.copyOf(dictionary, dictionary.length);

        int[] counts = new int[dictionary.length];
        int n = 10000;
        // Many times, choose a random word from this dictionary
        for (int i = 0; i < n; i++) {
            String word = Wordle.selectRandomWord(dictionary);
            // dictionary should not have changed
            countTest(Arrays.equals(dictionary, dictionaryCopy));
            // Word selected better be in the dictionary
            int index = word == null ? -1 : Arrays.binarySearch(dictionary, word);
            countTest(index >= 0);
            // Update a counter for each word when it appears
            if(index >= 0) { counts[index]++; }
        }
        // All words in the dictionary should appear with similar frequency        
        double expected = (double)n / dictionary.length;
        for (int i = 0; i < counts.length; i++) {
            countTest(counts[i] > expected * 0.9);
            countTest(counts[i] < expected * 1.1);
        }
    }

    private static void testIsValidWord() {
        String[] dictionary = { "foo", "bar", "baz", "qux" };
        testValidWords(dictionary, true, dictionary);
        testValidWords(dictionary, false, new String[]{"boo", "bat", "qui"});

        testValidWords(WordleDictionary.FIVE_LETTER_WORDS, true,
                       WordleDictionary.FIVE_LETTER_WORDS);
        testValidWords(WordleDictionary.FIVE_LETTER_WORDS, false,
                       new String[] {"qwert", "abcde", "asdfg", "12345"});

        testValidWords(WordleDictionary.TWO_LETTER_WORDS, true,
                       WordleDictionary.TWO_LETTER_WORDS);
        testValidWords(WordleDictionary.TWO_LETTER_WORDS, false,
                       new String[]{"ab", "ek", "zz", "12", "hx", "oz"});
    }

    private static void testValidWords(String[] dictionary,
                                       boolean expectedResult,
                                       String[] testWords) {
        String[] dictionaryCopy = Arrays.copyOf(dictionary, dictionary.length);

        for(String word : testWords) {
            countTest(Wordle.isValidWord(word, dictionary) == expectedResult);
            // dictionary should not have changed
            countTest(Arrays.equals(dictionary, dictionaryCopy));
        }
    }

    private static void testGetGuessResult() {
        countTest(Arrays.equals("oo.X.".toCharArray(), Wordle.getGuessResult("sassy", "glass")));
        countTest(Arrays.equals("...XX".toCharArray(), Wordle.getGuessResult("geese", "those")));
        countTest(Arrays.equals("oo.oX".toCharArray(), Wordle.getGuessResult("added", "dread")));
        countTest(Arrays.equals("ooooo".toCharArray(), Wordle.getGuessResult("rated", "trade")));
        countTest(Arrays.equals("...X..".toCharArray(), Wordle.getGuessResult("banana", "potato")));
        countTest(Arrays.equals("oo.".toCharArray(), Wordle.getGuessResult("arm", "car")));
        countTest(Arrays.equals("oooo".toCharArray(), Wordle.getGuessResult("live", "evil")));
        countTest(Arrays.equals("....".toCharArray(), Wordle.getGuessResult("doom", "evil")));

        countTest(Arrays.equals("X.".toCharArray(), Wordle.getGuessResult("aa", "ah")));
        countTest(Arrays.equals("oo".toCharArray(), Wordle.getGuessResult("ha", "ah")));
        countTest(Arrays.equals("..".toCharArray(), Wordle.getGuessResult("ox", "ah")));
    }

    private static void testIsWinningResult() {
        countTest(!Wordle.isWinningResult(".....".toCharArray()));
        countTest(!Wordle.isWinningResult(".........".toCharArray()));
        countTest(!Wordle.isWinningResult("XXXXX....".toCharArray()));
        countTest(!Wordle.isWinningResult("XoX.XoX.X".toCharArray()));
        countTest(!Wordle.isWinningResult("ooooo".toCharArray()));
        countTest(!Wordle.isWinningResult("oXoXoX".toCharArray()));
        countTest(!Wordle.isWinningResult("XXXoX".toCharArray()));
        countTest(!Wordle.isWinningResult("XXXX.".toCharArray()));
        countTest(!Wordle.isWinningResult(".XXXX".toCharArray()));
        countTest(Wordle.isWinningResult("XXXXX".toCharArray()));
        countTest(Wordle.isWinningResult("XXXXXXXXXXX".toCharArray()));
        countTest(Wordle.isWinningResult("XX".toCharArray()));
    }

    public static void main(String[] args) {
        clearCounts();
        testSelectRandomWord();
        printResults("selectRandomWord");

        clearCounts();
        testIsValidWord();
        printResults("isValidWord");

        clearCounts();
        testGetGuessResult();
        printResults("getGuessResult");

        clearCounts();
        testIsWinningResult();
        printResults("isWinningResult");
    }

}
