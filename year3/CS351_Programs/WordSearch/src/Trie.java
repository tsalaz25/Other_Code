/**
 * Author: Tomas Salaz File: Trie.java used with WordSearch.java
 * CS251L Lab 7: Word Search Solver
 *
 * Creates a prefix trie used to seatch through 2D puzzle in WordSearch.Java
 */
public class Trie {
    TrieNode root;
    public Trie(){
        root = new TrieNode();
    }
    /**
     * @param s is the Sting we are inserting into the Trie, is broken into
     * chars. Marks the End od a Branch
     */
    public void insert (String s){
        TrieNode node = root;
        for (char c: s.toCharArray()){
            int letterID = c - 'a';
            if (node.children[letterID] == null){
                node.children[letterID] = new TrieNode();
            }
            node = node.children[letterID];
        }
        node.isWords = true;
    }
    /**
     * @param s is the String we are searching for in the Trie. Returns if the
     * given string is a prefix to any of the branches in the Trie.
     */
    public boolean isPrefix (String s){
        TrieNode node = root;
        for (char c: s.toCharArray()){
            int letterID = c - 'a';
            if (node.children[letterID] == null){
                return false;
            }
            node = node.children[letterID];
        }
        return true;
    }

    /**
     * @param s is the String we are searching for in the Trie
     */
    public boolean isWord (String s){
        TrieNode node = root;
        for (char c: s.toCharArray()){
            int letterID = c - 'a';
            if (node.children[letterID] == null){
                return false;
            }
            node = node.children[letterID];
        }
        return node.isWords;
    }
}
/**
 * Class for making different branches (children) for the trie.
 */
class TrieNode{
    TrieNode [] children;
    boolean isWords;

    public TrieNode(){
        children = new TrieNode[26];
        isWords = false;
    }
}