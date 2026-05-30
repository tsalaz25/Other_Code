/**
 * Author: Tomas Salaz  File: FileGetter.java
 * CS251L   Lab 10: Snake Game Part 2: GameManager
 *
 * FileGetter Class is used to make FileGetter objects that are exclusively used
 * in the Nibbles class. This Class handles all the File IO, Parsing of
 * lines and Storage of important integers used in making a Snake Game
 */
import java.util.*;
import  java.io.*;
public class FileGetter {
    //Member Variables
    private int height;
    private int width;
    public  LinkedList <int[]> wallsIn = new LinkedList<>();
    String S;
    public FileGetter (String S) throws Exception{
        this.S = S;
        try{
            Scanner scanner = new Scanner(new File(S));
            if (scanner.hasNextLine()){
                String[] dimsLine = scanner.nextLine().split(" ");
                if (dimsLine.length == 2){
                    this.width = Integer.parseInt(dimsLine[0]);
                    this.height = Integer.parseInt((dimsLine[1]));
                }
                while (scanner.hasNextLine()){
                    String[] temp = scanner.nextLine().split(" ");
                    if (temp.length == 4){
                        int[] wallVals = new int[4];
                        for (int i = 0; i < 4; i++){
                            wallVals[i] = Integer.parseInt(temp[i]);
                        }
                        wallsIn.add(wallVals);
                    }
                }
                scanner.close();
            }
        }catch (Exception E){
            E.printStackTrace();
        }
    }
    public FileGetter (){
        this.S = null;
        this.width = 15;
        this.height = 15;
        int [] roof = {0,14,0,0};
        int [] floor = {0,14,14,14};
        int [] left = {0,0,1,13};
        int [] right = {14,14,1,13};
        wallsIn.add(roof);
        wallsIn.add(floor);
        wallsIn.add(left);
        wallsIn.add(right);
    }
    //Getters
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public String getFileName (){return S;}
    public LinkedList<int[]> getWallsIn() {return wallsIn;}
    public String getWallCoords (){
        StringBuilder SB = new StringBuilder();
        int numLists = 0;
        for (int[] arr: wallsIn){
            SB.append("Set " + numLists + ": ");
            for (int i = 0; i < arr.length; i++){
                SB.append(arr[i] + " ");
            }
            SB.append("\n");
            numLists++;
        }
        return SB.toString();
    }
}