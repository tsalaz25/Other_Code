/**
 * Author: Tomas Salaz  File: GameManagerTester.java
 * CS251L   Lab 9: Snake Game Part 1: GameManager
 *
 * The GameManagerTester uses other classes like GameManager.java, FileGetter.java
 * and the methods in GameManager.java to show functionality.
 *
 * Note: I used the Maze-Cross as Arg#1 and Simple as Arg#2
 * */
public class GameManagerTester {
    public static void main (String[] args) throws Exception {
        GameManager GAME1 = new GameManager(new FileGetter(args[0]));
        GameManager GAMEnoFile = new GameManager(new FileGetter());

        System.out.println(GAMEnoFile.toString());
        System.out.println(GAME1.toString());

        for (int i =0; i < 4; i++){
            GAME1.step(Direction.DOWN);
        }
        System.out.println(GAME1.toString());

        for (int i =0; i < 5; i++){
            GAME1.step(Direction.LEFT);
        }
        System.out.println(GAME1.toString());

        for (int i =0; i < 5; i++){
            GAME1.step(Direction.UP);
        }
        System.out.println(GAME1.toString());

    }
}