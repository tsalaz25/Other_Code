/**
 * Author: Tomas Salaz File: WordSearch.java used with Trie.java
 * CS251L Lab 7: Word Search Solver
 *
 * Word Search takes in 3 .txt files as arguments; a Dictionary, a Puzzle and a
 * Direction. This file uses a Trie.java to store the Dictionary into a Prefix
 * Trie, the Puzzle is converted into a 2D character Array, and the Direction is
 * used to Search in the given direction(s).
 *
 * */
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class WordSearch extends Trie{
    /**
     * @param args
     * @throws IOException
     * Main Method contains a Comparator for sorting the results. Most of the
     * work is done in other methods.
     */
    public static void main(String[] args) throws IOException{
        Path dictionary = Paths.get(args[0]);
        Path puzzlePath = Paths.get(args[1]);
        String searchDir = (args[2]).toUpperCase();
        char [][] puzzle = makePuzzle(puzzlePath);
        Trie dict = dictTrie(dictionary);
        ArrayList<String> result = search(puzzle, dict, searchDir);
        Collections.sort(result, new Comparator<String>() {
            @Override
            public int compare (String s1, String s2){
                String[] result1 = s1.split(" ");
                String[] result2 = s2.split(" ");
                int stringComp = result1[0].compareTo(result2[0]);
                if (stringComp != 0){
                    return stringComp;
                }
                int r1 = Integer.parseInt(result1[1]);
                int r2 = Integer.parseInt(result2[1]);
                if (r1 != r2){
                    return Integer.compare(r1,r2);
                }
                int c1 = Integer.parseInt(result1[2]);
                int c2 = Integer.parseInt(result1[2]);
                return Integer.compare(c1,c2);
            }
        });

        for (String s: result){
            System.out.println(s);
        }
    }

    /**
     * @param puzzle is the 2D array assigned in Main
     * @param dict is the Prefix Trie assigned in Main
     * @param dir is the String used to access given direction
     * @returns the results in an ArrayList that is sorted in main
     */
    public static ArrayList<String> search(char[][] puzzle, Trie dict, String dir){
        ArrayList<String> result = new ArrayList<>();
        for(int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle.length; col++) {
                StringBuilder word = new StringBuilder();
                word.append(puzzle[row][col]);
                char c = puzzle[row][col];
                if (dir.equals("FORWARD")) {
                    for (int i = 0; i < Dir.FORWARD.length; i++) {
                        int rStep = row + Dir.FORWARD[i].rowOffset;
                        int cStep = col + Dir.FORWARD[i].colOffset;
                        while ((rStep >= 0 && rStep < puzzle.length) &&
                            (cStep >= 0 && cStep < puzzle[0].length)) {
                            word.append(puzzle[rStep][cStep]);
                            if (dict.isPrefix(word.toString())){
                                if(dict.isWord(word.toString())){
                                    StringBuilder found = new StringBuilder();
                                    found.append(word + " " + row + " " +
                                        col + " " + Dir.FORWARD[i] + " ");
                                    result.add(found.toString());
                                }
                                rStep = rStep + Dir.FORWARD[i].rowOffset;
                                cStep = cStep + Dir.FORWARD[i].colOffset;
                            } else {
                                word.delete(0,word.length());
                                word.append(c);
                                break;
                            }
                        }
                    }
                }
                if (dir.equals("ROW_COL_ONLY")) {
                    for (int i = 0; i < Dir.ROW_COL_ONLY.length; i++) {
                        int rStep = row + Dir.ROW_COL_ONLY[i].rowOffset;
                        int cStep = col + Dir.ROW_COL_ONLY[i].colOffset;
                        while ((rStep >= 0 && rStep < puzzle.length) &&
                            (cStep >= 0 && cStep < puzzle[0].length)) {
                            word.append(puzzle[rStep][cStep]);
                            if (dict.isPrefix(word.toString())){
                                if(dict.isWord(word.toString())){
                                    StringBuilder found = new StringBuilder();
                                    found.append(word + " " + row + " " +
                                        col + " " + Dir.ROW_COL_ONLY[i] + " ");
                                    result.add(found.toString());
                                }
                                rStep = rStep + Dir.ROW_COL_ONLY[i].rowOffset;
                                cStep = cStep + Dir.ROW_COL_ONLY[i].colOffset;
                            } else {
                                word.delete(0,word.length());
                                word.append(c);
                                break;
                            }
                        }
                    }
                }
                if (dir.equals("DIAGONAL_ONLY")) {
                    for (int i = 0; i < Dir.DIAGONAL_ONLY.length; i++) {
                        int rStep = row + Dir.DIAGONAL_ONLY[i].rowOffset;
                        int cStep = col + Dir.DIAGONAL_ONLY[i].colOffset;
                        while ((rStep >= 0 && rStep < puzzle.length) &&
                            (cStep >= 0 && cStep < puzzle[0].length)) {
                            word.append(puzzle[rStep][cStep]);
                            if (dict.isPrefix(word.toString())){
                                if(dict.isWord(word.toString())){
                                    StringBuilder found = new StringBuilder();
                                    found.append(word + " " + row + " " +
                                        col + " " + Dir.DIAGONAL_ONLY[i] + " ");
                                    result.add(found.toString());
                                }
                                rStep = rStep + Dir.DIAGONAL_ONLY[i].rowOffset;
                                cStep = cStep + Dir.DIAGONAL_ONLY[i].colOffset;
                            } else {
                                word.delete(0,word.length());
                                word.append(c);
                                break;
                            }
                        }
                    }
                }
                if (dir.equals("ALL")) {
                    for (int i = 0; i < Dir.ALL.length; i++) {
                        int rStep = row + Dir.ALL[i].rowOffset;
                        int cStep = col + Dir.ALL[i].colOffset;
                        while ((rStep >= 0 && rStep < puzzle.length) &&
                            (cStep >= 0 && cStep < puzzle[0].length)) {
                            word.append(puzzle[rStep][cStep]);
                            if (dict.isPrefix(word.toString())){
                                if(dict.isWord(word.toString())){
                                    StringBuilder found = new StringBuilder();
                                    found.append(word + " " + row + " " +
                                        col + " " + Dir.ALL[i] + " ");
                                    result.add(found.toString());
                                }
                                rStep = rStep + Dir.ALL[i].rowOffset;
                                cStep = cStep + Dir.ALL[i].colOffset;
                            } else {
                                word.delete(0,word.length());
                                word.append(c);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param p is the Path for the text file holding the Dictionary
     * @returns the Trie holding the Dictionary
     * Uses the Methods defined in Trie.Java and is static because the Dictionary
     * is not going to change.
     */
    public static Trie dictTrie(Path p){
        Trie dict = new Trie();
        try{
            File file = new File(String.valueOf(p));
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                dict.insert(scanner.nextLine());
            }
            return dict;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param p is the file path holding the puzzle.
     * returns a 2D char array, with the puzzle using a scanner and loops. Is
     * static because the puzzle will not change once its created.
     */
    public static char[][] makePuzzle (Path p){
        char[][] puzzle;
        try {
            File file = new File(String.valueOf(p));
            Scanner scanner = new Scanner(file);
            String dimsLine = scanner.nextLine();
            String [] dims = dimsLine.split(" ");
            int row = Integer.parseInt(dims[0]);
            int col = Integer.parseInt(dims[1]);
            puzzle = new char[row][col];
            for (int x = 0; x < row; x++){
                String line = scanner.nextLine();
                for (int y = 0; y < col; y++){
                    puzzle[x][y] = line.charAt(y);
                }
            }
            scanner.close();
            return puzzle;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Enums for stepping in a Direction, uses cardinal directions, has arrays
     * for specific searching directions
     */
    public enum Dir {
        E(0,1),
        SE(1,1),
        S(1,0),
        SW(1,-1),
        W(0,-1),
        NW(-1,-1),
        N(-1,0),
        NE(-1,1);
        public final int rowOffset;
        public final int colOffset;
        private Dir(int rowStep,int colStep){
            this.rowOffset = rowStep;
            this.colOffset = colStep;
        }
        public static final Dir[] ALL = Dir.values();
        public static final Dir[] FORWARD = {NE, E, SE,S};
        public static final Dir[] ROW_COL_ONLY = {N, E, S, W};
        public static final Dir[] DIAGONAL_ONLY = {NE,SE,SW,NW};
    }
}