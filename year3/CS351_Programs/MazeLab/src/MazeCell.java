/**
 * Type representing a cell in a maze.
 */
public class MazeCell {

    // copy/adapt from http://nifty.stanford.edu/2021/schwarz-linked-list-labyrinth/

    public String whatsHere = ""; // One of "", "Potion", "Spellbook", and "Wand"

    public MazeCell north = null;
    public MazeCell south = null;
    public MazeCell east  = null;
    public MazeCell west  = null;

}
