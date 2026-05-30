/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package Console
Entry point fot the Console Program. Contains the MainGameLoop and contains the
a Manager and an Input object for playing the game
* */
package console;
public class Main {
    public static void main (String args[]) throws Exception {
        /*Quick IO for running the game in the CLI, No arguments makes the max=6 */
        Manager manager;
        if (args.length == 0){
            manager = new Manager(6); //No Args Case
        } else if (args.length == 1 && Integer.parseInt(args[0]) < 10){
            manager = new Manager(Integer.parseInt(args[0])); //CLI Arg
        } else {
            throw new Exception("Invalid Argument, Only 1 argument and must be <10\n");
        }
        Input input = new Input();

        /*Main Game Loop*/
        while (!manager.isGameOver){
            manager.printGameInfo();
            /*Must be Either of these Cases*/
            if (manager.HumanTurn){
                manager.printHumanInfo();
                if (manager.isHumanMoveAvail()){
                    manager.humanMove(input.getHumanPlay(),
                    input.getHumanDomIndex(manager.numH()));
                    if (manager.isGameOver){
                        manager.gameOverMessage();}
                } else {
                    System.out.println("No Moves Available, Pulling From Boneyard");
                    manager.humanMove('d', 0);
                }
            }
            else if (!manager.HumanTurn){
                manager.printCompInfo();
                manager.makeCompMove();
                if (manager.isGameOver){manager.gameOverMessage();}
            }
        }
        System.exit(0);
    }
}