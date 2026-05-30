/************************************************************
Tomas Salaz
March 20, 2024

Description: sudoku.c takes in chars from from the command
prompt, and uses various method that to find solutions to the
given sudoku puzzle. The output is alway going to echo the
puzzle input, on the proceeding line, either the solution or 
"No Solution" or "Error" will appear followed by new line.
Do note that most of the chars are listed as their ascii value.

NOTE: The program takes a few minutes to solve multiple
puzzles at a time but the solutions are correct 
************************************************************/
#include <stdio.h>
/*CheckRow is a helper method that iterates over a given row
and returns whether the char is in the row*/
int checkRow(char sudoku[9][9], int row, char n){
  int i;
  for (i = 0; i < 9; i++){
    if (sudoku[row][i] == n){
      return 1;
    }
  }
  return 0;
}
/*CheckCol is a helper method that iterates over the given 
column and returns whether the char is in the col*/
int checkCol(char sudoku[9][9], int col, char n){
  int i;
  for (i = 0; i < 9; i++){
    if (sudoku[i][col] == n){
      return 1;
    }
  }
  return 0;
}
/*checkBox is a helper method that iterates over a subgrid
in on the board. Returns whether the char is in the subgrid*/
int checkBox(char sudoku[9][9], int row, int col, char n){
  int i,j;
  int boxRow = row - row % 3;
  int boxCol = col - col % 3;
  for (i = boxRow; i < boxRow + 3; i++){
    for (j = boxCol; j < boxCol + 3; j++){
      if (sudoku[i][j] == n){
        return 1;
      }
    }
  }
  return 0;
}
/*validCell is a helper method that calls check methods to 
check the validity of a number in a box*/
int validCell(char sudoku[9][9], int row, int col, char n){
  return (!checkRow(sudoku,row,n) &&
	  !checkCol(sudoku,col,n) &&
	  !checkBox(sudoku,row,col,n));
}
/*boardSolved is a helper method that is used to print out
the board (in a single line) after it has been solved.*/
void boardSolved (char solved[9][9]){
  int row, col;
  for (row = 0 ; row < 9; row++){
    for (col = 0; col < 9; col++){
      printf("%c", solved[row][col]);
    }
  }
  printf("\n\n");
}
/*solve uses a backtracking algorithm that iterates over an
entire sudoku board to find an empty cell. If the cell is 
empty, a loop of paaible values (in asii) is used to find 
a valid number. Once the entire board is solved recursivly,
the method returns a 1 (true) or 0 (false) value.*/
int solve (char sudoku[9][9]){
  int row,col,n;
  for (row = 0; row < 9; row++){
    for (col = 0; col < 9; col++){
      if (sudoku[row][col] == 46){
	for (n = 49; n <= 57; n++){
	  if(validCell(sudoku, row, col, n)){
	    sudoku[row][col] = n;
	    if (solve(sudoku)){
	      return 1;
	    } else {
	      sudoku[row][col] = 46;
	    }
	  }
	}
	return 0;
      }
    }
  }
  return 1;
}
/*Main uses getchar() to get input from the commmand 
line. Main also handle error printing, and keeps track of 
board.
Error are handled by counting valid chars that are recieved
from the commmand line. If there is an invalid char, the 
output is "Error".
Since the solve method returns a 'boolean' value, A ternary
operation is used to handle either case.
Once a conclusion has been reached about hte solvability of
the given puzzle, loops reset the info and the getchar() 
while loop continues.*/
int main (){
  char lineIn[100]; /*assumse a line is <100 chars*/
  char board[9][9];
  char c;
  int numchars ,i, j, k, row, col, x, y;
  numchars = i = j = k = 0;
  while ((c = getchar()) != EOF){
    printf("%c",c);
    lineIn[i] = c;
    i++;
    if ((c >= '1' && c <= '9') || c == '.'){
      numchars++;
    } else if (c == '\n'){
      if (numchars == 81){
        for(row = 0; row < 9; row++){
	  for (col = 0; col < 9; col++){
	    board[row][col] = lineIn[j];
	    j++;
	  }
	}
	(solve(board)) ? (boardSolved(board)) : (printf("No solution\n\n"));
      } else {
	printf("Error\n\n");
      }for (x = 0; x < 9; x++){
        for (y = 0; y < 9; y++){
          board[x][y] = 0;
        }
      }
      while (k < sizeof(lineIn)){
        lineIn[k] = 0;
        k++;
      }
      numchars = i = j = k = 0;
    }
  }
  return 0;
}