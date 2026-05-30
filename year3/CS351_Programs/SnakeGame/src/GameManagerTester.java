/**
 * Author: Tomas Salaz
 * File: GameManagerTester.java
 * CS251L Lab 9: Snake Game Part 1: GameManager
 *
 * Game Manager Tester uses the Game Manager class to test the Game and make
 * sure things are functioning in the way that they are supposed to.
 */
import java.io.*;
import java.util.*;

//Not finished yet, But method calls are working, just buggy
public class GameManagerTester {

    public static void main(String[] args) throws FileNotFoundException {
        GameManagerTester GAME1 = new GameManagerTester();
        String File1 = args[0];
        GAME1.file1Test(File1);
    }

    public void file1Test (String S){
        FileGetter F = new FileGetter(new File(S));
       GameManager GAME = new GameManager(F.width,F.height);
       GAME.getSnake().setDir(Dir.RIGHT);
       GAME.step();
       GAME.toString();
       GAME.step();
    }
}