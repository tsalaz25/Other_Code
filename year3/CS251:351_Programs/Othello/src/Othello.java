/**
 *Lab #2: Othello
 * Author: Tomas Salaz CS251
 * Play a game of Othello
 * */

import cs251.lab2.*;

public class Othello implements OthelloInterface {
    public Othello() {
    }

    /**Main Method to call the GUI*/
    public static void main(String args[]) {
        Othello game = new Othello();
        if (args.length > 0) {
            game.initComputerPlayer(args[0]);
        }
        OthelloGUI.showGUI(game);
    }

    //Member Variables
    private Piece whoseTurn;
    private int size = DEFAULT_SIZE;
    private Piece[][] currBoard;
    private int totalPieces;
    private boolean cpuPlayer = false;

    private final int[] colStep = {-1, -1, 0, 1, 1, 1, 0, -1};
    private final int[] rowStep = {0, 1, 1, 1, 0, -1, -1, -1};

    /**Method that returns the Default Size, is the size of the axes of the
     * grid that the GUI draws*/
    public int getSize() {
        return size;
    }
    /**is used to access the player currently making a move*/
    public Piece getCurrentTurn() {
        return whoseTurn;
    }

    /**Initializes the game, creates 2D array for the board, and places
     * pieces in the proper starting spots, initialized some variables */
    public void initGame() {
        currBoard = new Piece[size][size];
        for (int i = 0; i < size; i++) {
            for (int x = 0; x < size; x++) {
                currBoard[i][x] = Piece.EMPTY;
            }
        }
        currBoard[size / 2][size / 2] = Piece.LIGHT;
        currBoard[size / 2 - 1][size / 2 - 1] = Piece.LIGHT;
        currBoard[size / 2][size / 2 - 1] = Piece.DARK;
        currBoard[size / 2 - 1][size / 2] = Piece.DARK;
        totalPieces = 4;
        whoseTurn = Piece.DARK;

        if (cpuPlayer){cpuPlayersMove();}
    }

    /**Called by the GUI to actually draw the grid, Uses loop and String
     * builder to create the string*/
    public String getBoardString() {
        StringBuilder board = new StringBuilder();
        for (int row = 0; row < size; row++) {
            int col = 0;
            while (col < size) {
                board.append(currBoard[row][col].toChar());
                col++;
                if (col == size) {
                    board.append("\n");
                }
            }
        }
        return board.toString();
    }

    /**Is Legal checks to see if a move is valid. Calls 3 different Helper
     * methods to confirm if a piece can be placed*/
    public boolean isLegal(int row, int col) {
        boolean res = false;
        if (isEmpty(row, col) && hasOppositeAdjacent(row, col) &&
        trapOppoPiece(row, col)) {
            res = true;
        }
        return res;
    }

    /**Called by the GUI to get the location of the cursor. Calls isLeagal
     * method before anything is placed to confirm if there is a valid move.
     * Also calls placePiece Helper once isLeagal returns true. */
    public Result handleClickAt(int row, int col) {
        int totalLegalMoves = 0;
        int lightTotal = 0;
        int darkTotal = 0;

        for (int r = 0; r < size;r++){
            for (int c = 0; c<size;c++){
                if(isLegal(r,c)){totalLegalMoves++;}
            }
        }

        if (totalLegalMoves==0){
            for (int r = 0; r < size;r++) {
                for (int c = 0; c < size; c++) {
                    if (currBoard[r][c]==Piece.LIGHT){lightTotal++;}
                    if (currBoard[r][c]==Piece.DARK){darkTotal++;}
                }
            }
            if (darkTotal>lightTotal){
                return Result.DARK_WINS;
            } else if (lightTotal > darkTotal){
                return Result.LIGHT_WINS;
            } else {
                return Result.DRAW;
            }
        }


        if (isLegal(row, col)) {
            placePiece(row, col);
            flipPieces(row, col);
        }
        return Result.GAME_NOT_OVER;
    }

    /**Takes in the Argument from the compiler to initialize a Computer
     * player is that is the argument. If there is no Computer Player, this
     * method will be skipped*/
    public void initComputerPlayer(String opponent) {
        if (opponent.equals("COMPUTER")){
            cpuPlayer = true;
        } else {
            cpuPlayer = false;
        }
    }

    /**Helper Methods*/
    /**Is called by the handleClickAt method. Used to place a piece and flip
     * piece is the location is a legal move*/
    public void placePiece(int row, int col) {
        if (whoseTurn == Piece.DARK && isLegal(row, col)) {
            currBoard[row][col] = Piece.DARK;
            totalPieces++;
            whoseTurn = Piece.LIGHT;
        }
        if (whoseTurn == Piece.LIGHT && isLegal(row, col)) {
            currBoard[row][col] = Piece.LIGHT;
            totalPieces++;
            whoseTurn = Piece.DARK;
        }
    }

    /**Helper Method to check if a location is Empty*/
    public Boolean isEmpty(int row, int col) {
        boolean res = false;
        if (currBoard[row][col] == Piece.EMPTY) {
            res = true;
        }
        return res;
    }

    /**Loops through all possible directions of a given direction to see if
     * there is an adjacent piece of any color. */
    public Boolean hasOppositeAdjacent(int row, int col) {
        boolean res = false;
        for (int i = 0; i < colStep.length; i++) {
            if (row + rowStep[i] >= 0 && row + rowStep[i] < size &&
                    col + colStep[i] >= 0 && col + colStep[i] < size) {
                if (whoseTurn == Piece.DARK &&
                        currBoard[row + rowStep[i]][col + colStep[i]] == Piece.LIGHT) {
                    res = true;
                }
                if (whoseTurn == Piece.LIGHT &&
                        currBoard[row + rowStep[i]][col + colStep[i]] == Piece.DARK) {
                    res = true;
                }
            }
        }
        return res;
    }

    /**Loops in the same direction to check if the current player traps any
     * of the opposing pieces*/
    public Boolean trapOppoPiece (int row, int col){
        boolean res = false;
        for (int i = 0; i < rowStep.length;i++){
             int nextRow = row + rowStep[i];
             int nextCol = col + colStep[i];
             boolean oppoColor = false;
             while (nextRow >= 0 && nextRow< size && nextCol >= 0 &&
             nextCol < size && currBoard[nextRow][nextCol]!=Piece.EMPTY){
                 if (currBoard[nextRow][nextCol] == whoseTurn){
                     if (oppoColor){
                         res = true;
                     }
                     break;
                 } else {
                     oppoColor = true;
                 }
                 nextRow = nextRow + rowStep[i];
                 nextCol = nextCol + colStep[i];
             }
        }
        return  res;
    }

    /**Called by Place Piece to Flip Pieces when Legal*/
    public void flipPieces (int row, int col){

        for (int i = 0; i < rowStep.length; i++) {
            int nextRow = row + rowStep[i];
            int nextCol = col + colStep[i];
            boolean nextStep = false;

            while (nextRow >= 0 && nextRow < size && nextCol >= 0 &&
                    nextCol < size && currBoard[nextRow][nextCol] != Piece.EMPTY) {
                if (currBoard[nextRow][nextCol] == whoseTurn) {
                    if (nextStep) {
                        nextStep = false;
                    }
                    break;
                } else {
                    currBoard[nextRow][nextCol] = whoseTurn;
                    nextStep = true;
                }

                nextRow = nextRow + rowStep[i];
                nextCol = nextCol + colStep[i];
            }
        }
    }

    public void cpuPlayersMove(){
        boolean turnOver = false;
        for (int r = 0;r<size;r++){
            for(int c = 0;c<size;c++){
                if (isLegal(r,c) && whoseTurn == Piece.LIGHT){
                    currBoard[r][c] = whoseTurn;
                    flipPieces(r,c);
                    turnOver = true;
                }
                if (turnOver){
                    break;
                }
            }
        }
    }
}