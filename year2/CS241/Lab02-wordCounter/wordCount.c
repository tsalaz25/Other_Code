/******************************************************************
*Tomas Salaz  CS241L-005 
*                       
*The Following program takes in assumes an input of a .txt file. 
*With that file the progrm outputs the line number followed by the
*line, then some brackets that return the intformation in the 
*format [words, chars] for the given line. The last 3 lines return
*the lines with the total amount of lines, words and characters as
*well as the line(s) with the most words and characters.
*******************************************************************/
#include<stdio.h>
#define IN 1
#define OUT 0
int main (){
  /*Variables for counting and conditionals*/
  char c;
  int totalChars, totalWords, totalLines, currLineChar, currLineWord, status;
  int lineMaxWords [2] ={0,0};
  int lineMaxChars [2] ={0,0};
  totalLines = 1;
  status = OUT;
  totalChars = totalWords = currLineChar = currLineWord = 0;
  /*loop for looping through .txt file*/
  while ((c = getchar()) != EOF){
    if (currLineChar == 0){
      printf("(%2d) " , totalLines);
    }
    totalChars++;
    currLineChar++;
    if (c != '\n'){
      printf("%c" , c);
    }
    if (c == '\n'){
      totalLines++;
      totalChars--;
      currLineChar--;
      printf("[%d, %d]\n" , currLineWord, currLineChar);
      /*conditionals for updating the Max Line information*/
      if (currLineChar > lineMaxChars[1]){
	lineMaxChars[1] = currLineChar;
	lineMaxChars[0] = totalLines;
      }
      if (currLineWord > lineMaxWords[1]){
	lineMaxWords[1] = currLineWord;
	lineMaxWords[0] = totalLines;
      } 
      currLineChar = currLineWord = 0;
    }
    if (c == ' ' || c == '\n' || c == '\t'){
      status = OUT;
    }else if (status == OUT){
      status = IN;
      totalWords++;
      currLineWord++;
    }     
  }
  printf("\n%d lines, %d words, %d characters\n", totalLines-1, totalWords, totalChars);
  printf("Line %d has the most words with %d\n", lineMaxWords[0]-1, lineMaxWords[1]);
  printf("Line %d has the most characters with %d\n" , lineMaxChars[0]-1 , lineMaxChars[1]);
  return 0;
}