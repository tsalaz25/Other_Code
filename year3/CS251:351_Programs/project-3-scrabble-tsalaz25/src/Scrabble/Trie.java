/* Trie.java  Project 3: Scrabble
Author: Tomas Salaz
Description: Is used to implement the Dictionary object for the Scrabble Game
*/
package Scrabble;

public class Trie {
    private static final int LETTERS = 26;
    /**
     * INNER CLASS: Nodes
     * Used for making the nodes in the Trie
     */
    public class Node{
        /*Global Variables*/
        Node[] children = new Node[LETTERS];
        boolean isWord;
        /**
         * Node Constructor
         */
        Node(){
            isWord = false;
            for (int i = 0; i < LETTERS; i++){
                children[i] = null;
            }
        }
        /*Setters and Getters for the Nodes*/
        public void setChildren(Node n, int i){children[i] = n;}
        public void setWord(boolean b){isWord = b;}
        public boolean isWord(){return isWord;}
        public Node getChild(int i){return children[i];}
        /**
         * @param c characters that is being looked for in the trie
         * @return
         */
        public boolean contains(char c){
            int index = c - 'a';
            return children[index] != null;
        }
    }

    /*Global Variables for the Trie*/
    public Node root;
    private int size;
    /**
     * Trie Constructor
     */
    public Trie(){
        root = new Node();
        size = 0;
    }
    /**
     * param word is the word that is being inserted into the Trie
     */
    public void insert(String word){
        int index;
        Node parent = root;
        for (int i = 0; i < word.length(); i++){
            index = word.charAt(i) - 'a';
            if (parent.getChild(index) == null){
                parent.setChildren(new Node(), index);
            }
            parent = parent.getChild(index);
        }
        parent.setWord(true);
    }
    /**
     * @param word is the word we are checking for in the dictionary
     * @return boolean value if the word exists
     */
    public boolean isWord (String word){
        int index;
        Node parent = root;
        for (int i = 0; i < word.length(); i++){
            index = word.toLowerCase().charAt(i) - 'a';
            if (parent.getChild(index) == null){
                return false;
            }
            parent = parent.getChild(index);
        }
        return parent.isWord();
    }
    /**
     * @return the root of the trie
     */
    public Node getRoot(){return root;}
}