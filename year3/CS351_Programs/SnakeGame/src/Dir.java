/**
 * Author: Tomas Salaz  File: Dir.java
 * CS251L   Lab 9: Snake Game Part 1: GameManager
 *
 * Enum for Directions. Has methods to check is Direction Change is Valid and
 * to get the offset to apply to the coordinates when stepping.
 * */

public enum Dir {
    UP(0,-1),
    DOWN(0,1),
    LEFT(-1,0),
    RIGHT(1,0);

    private final int xStep;
    private final int yStep;

    Dir(int xStep, int yStep){
        this.xStep = xStep;
        this.yStep =yStep;
    }

    public Boolean validDirChange (Dir dirChange){
        if ((this.xStep + dirChange.xStep == 0)&&
            (this.yStep + dirChange.yStep == 0)){
            return true;
        } else {
            return false;
        }
    }

    public int[] getStep(){
        int[] temp = new int[2];
        temp[0] = xStep;
        temp[1] = yStep;
        return temp;
    }
}
