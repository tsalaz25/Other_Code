/*Author:Tomas Salaz    CS351L  Domino's Project 2      Package Console
Method for implementing the game. Is Most of the Logic for the CLI version of the
Game. Contains 2 Players, a Boneyard and an Arena that all interact to play the
game.
* */
package console;
public class Manager {
    /*Fields for a Manager*/
    private Player human;
    private Player comp;
    private Boneyard boneyard;
    private Arena arena;
    public boolean HumanTurn;
    public boolean isGameOver;
    /*Constructor to Initialize the Fields */
    public Manager(int n) {
        human = new Player('h');
        comp = new Player('c');
        boneyard = new Boneyard(n);
        arena = new Arena();
        /*Generate a Number for Initial Hand Size*/
        int m = n+1;
        int totalDominos = 0;
        while (m > 0){
            totalDominos = totalDominos + m;
            m--;
        }
        initGame(totalDominos);
    }

    /**
     * @param n iks the Max number on the Domino
     *
     * Uses a loop to give the players Dominos from the randomized Boneyard, also
     * set boolean values for the start of the game
     */
    public void initGame(int n){
        for (int i = 0; i < n/4; i++){
            human.addToHand(boneyard.getDom());
            comp.addToHand(boneyard.getDom());
        }
        HumanTurn = true;
        isGameOver = false;
        System.out.println("DOMINOS!");
    }

    /**
     * @param o,i are the input that the user gives in the Main class. The method
     * processes the chars and makes the necessary changes to the fields to play
     * the game. Uses Branching
     */
    public void humanMove(char o, int i){
        switch (o){
            case 'p' -> {
                if (arena.getArena().size() == 0) {
                    Domino D = human.getHand().remove(i);
                    arena.addRight(D);
                    arena.setAnchorDom(D);
                } else {
                    if (arena.getOpenRight() == 0 ) {
                        arena.addRight(human.getHand().remove(i));
                    } else if (arena.getOpenLeft() == 0){
                        arena.addLeft(human.getHand().remove(i));
                    } else if (arena.getOpenRight() == human.getHand().get(i).getLeft()){
                        arena.addRight(human.getHand().remove(i));
                    }else if(arena.getOpenRight() == human.getHand().get(i).getRight()){
                        Domino temp = human.getHand().remove(i);
                        temp.flip();
                        arena.addRight(temp);
                    }else if(arena.getOpenLeft() == human.getHand().get(i).getRight()){
                        arena.addLeft(human.getHand().remove(i));
                    }else if(arena.getOpenLeft() == human.getHand().get(i).getLeft()){
                        Domino temp = human.getHand().remove(i);
                        temp.flip();
                        arena.addLeft(temp);
                    } else if (human.getHand().get(i).getLeft() == 0){
                        arena.addRight(human.getHand().remove(i));
                    } else if(human.getHand().get(i).getRight() == 0){
                        arena.addLeft(human.getHand().remove(i));
                    }
                }
                HumanTurn = false;
            }
            case 'd' -> {human.addToHand(boneyard.getDom());}
            case 'q' -> {isGameOver = true;}
        }
        if (human.getHand().size() == 0){isGameOver = true;}
    }
    /**
     * Method for making a computer move, plays the 1st available move on the
     * right side. Uses branching to make the move
     */
    public void makeCompMove(){
        if (!isCompMoveAvail()){
            comp.addToHand(boneyard.getDom());
            makeCompMove();
        }
        int i = 0;
        while (i < comp.getHand().size() && !HumanTurn){
            if (arena.getOpenLeft() == 0){
                arena.addLeft(comp.getHand().remove(i));
                HumanTurn = true;
            } else if (arena.getOpenRight() == 0) {
                arena.addRight(comp.getHand().remove(i));
                HumanTurn = true;
            } else if (arena.getOpenRight() == comp.getHand().get(i).getLeft()){
                arena.addRight(comp.getHand().remove(i));
                HumanTurn = true;
            }else if (arena.getOpenRight() == comp.getHand().get(i).getRight()){
                Domino temp = comp.getHand().remove(i);
                temp.flip();
                arena.addRight(temp);
                HumanTurn = true;
            }else if (arena.getOpenLeft() == comp.getHand().get(i).getRight()){
                arena.addLeft(comp.getHand().remove(i));
                HumanTurn = true;
            }else if (arena.getOpenLeft() == comp.getHand().get(i).getLeft()){
                Domino temp = comp.getHand().remove(i);
                temp.flip();
                arena.addLeft(temp);
                HumanTurn = true;
            }else if (comp.getHand().get(i).getLeft() == 0){
                arena.addRight(comp.getHand().remove(i));
                HumanTurn = true;
            } else if(comp.getHand().get(i).getRight() == 0){
                arena.addLeft(comp.getHand().remove(i));
                HumanTurn = true;
            }
            i++;
        }
        if (comp.getHand().size() == 0){isGameOver = true;}
    }
    /**
     * Next 2 methods are the same structure and return booleans to verify if
     * the player has a valid move to make.
     */
    public boolean isHumanMoveAvail () {
        if (arena.getArena().size() == 0) {
            return true;
        }
        if (arena.getOpenLeft() == 0 || arena.getOpenRight() == 0) {
            return true;
        }
        for (Domino D : human.getHand()) {
            if (D.getRight() == 0 || D.getLeft() == 0) {
                return true;
            }
            if ((D.getRight() == arena.getOpenLeft() || D.getRight() == arena.getOpenRight() ||
                D.getLeft() == arena.getOpenLeft() || D.getLeft() == arena.getOpenRight())) {
                return true;
            }
        }
        return false;
    }
    public boolean isCompMoveAvail(){
        if (arena.getOpenLeft() == 0 || arena.getOpenRight() ==0 ) {return true;}
        for (Domino D: comp.getHand()){
            if (D.getRight() == 0 || D.getLeft() == 0){return true;}
            if ((D.getRight()==arena.getOpenLeft() || D.getRight()==arena.getOpenRight() ||
                D.getLeft()==arena.getOpenLeft() || D.getLeft()==arena.getOpenRight())){
                return true;
            }
        }
        return false;
    }
    /**
     * The Rest of the methods are used to print out information as the game
     * goes on, Uses method for accessing info from the 4 fields
     */
    public void printGameInfo(){
        System.out.printf("Computer has %d Dominos\nBoneyard has %d Dominos\n",
        comp.getHand().size(), boneyard.getBoneyard().size() );
        System.out.println("CURRENT ARENA\n" + arena.getArenaString() + "\n");
    }
    public void printHumanInfo(){
        System.out.println("HUMANS TURN");
        System.out.println(human.handToString());
    }
    public void printCompInfo(){
        System.out.println("COMPUTER TURN");
        System.out.println(comp.handToString());
    }
    public int numH(){return human.getHand().size();}
    public void gameOverMessage(){
        System.out.println("GAME OVER");

        System.out.println("Remaining Dominos\n" + boneyard.getBoneyard().toString());
        if (human.getHand().size() == 0 && comp.getHand().size() != 0){
            System.out.println("HUMAN WINS");
        } else if(human.getHand().size() != 0 && comp.getHand().size() == 0){
            System.out.println("COMPUTER WINS");
        }else if (human.getHandSum() < comp.getHandSum()){
            System.out.println("HUMAN WINS");
        }else if (human.getHandSum() > comp.getHandSum()) {
            System.out.println("COMPUTER WINS");
        }
    }
}