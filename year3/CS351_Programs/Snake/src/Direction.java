/**
 * Author: Tomas Salaz  File: Direction.java
 * CS251L   Lab 10: Snake Game Part 2: GameManager
 *
 * Enum for storing the Directions the Snake can move in. Has some Helper methods
 * that help with implementing game logic
 */public enum Direction {
    UP(0,-1),
    DOWN(0,1),
    LEFT(-1,0),
    RIGHT(1,0);
    private final int xStep;
    private final int yStep;

    //Constructor that store the offsets for a given direction
    Direction(int xStep, int yStep){
        this.xStep = xStep;
        this.yStep =yStep;
    }
    
    //Helper to make sure we cant turn the snake back on itself
    public Boolean validDirChange (Direction dirChange){
        if (this == dirChange){ return true;}
        else {
            return (this.xStep + dirChange.xStep != 0) &&
            (this.yStep + dirChange.yStep != 0);}
    }
    //Getters
    public int getXStep (){return xStep; }
    public int getYStep (){return yStep;}
}