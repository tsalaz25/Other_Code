/************************************************************
Tomas Salaz
April 7, 2024

Description: convet.c is a program that takes in inputs from
the command line and produces conversions. Conversions and 
inputs are in Binary (base 2), Hexidecimal (base 16), and 
Decimal (base 10) numbers. The program is built on branches 
for error checking and on for loops for conversios and printing

NOTES: One of the solutons on the given script is incorrect,
misses some error cases 
************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*MAXVAL is 2^64*/
#define MAXVAL 18446744073709551615
unsigned long int num;
int numBits = 0;
char *oType;
char *output;

/*errMess mehtod is used to prinout the desription
after an error is Identified*/
void errMess(){
  printf("usage:\nconvert IN OUT SIZE NUMBER\n");
  printf("\tIN:\n\t\t-inB NUMBER in binary.\n");
  printf("\t\t-inD NUMBER is decimal.\n");
  printf("\t\t-inH NUMBER is hexidecimal.\n\n");
  printf("\tOUT:\n\t\t-outB Output will be in binary.");
  printf("\n\t\t-outD Output will bein decimal.");
  printf("\n\t\t-outH Output will be in hexidecimal.\n\n");
  printf("\tSIZE:\n");
  printf("\t\t-8    input is an unsigned 8-bit integer.\n");
  printf("\t\t-16   input is and unsigned 16-bit integer.\n");
  printf("\t\t-32   input is an unsigned 32-bit integer.\n");
  printf("\t\t-64   input is an unsigned 64-bit integer.\n\n");
  printf("\tNUMBER:\n\t\tnumber to be converted.\n\n");
}

/*convert method is used to do conversion and printing. Branching 
directs the program to the correct case, which then uses for
loops to convert the number to teh correct output type and to 
print.
The funtion strcmp is found in the <string.h> library.*/
void convert(){
  int i,j,r;
  output = (char *)malloc(numBits+1 * sizeof(char));
  output[numBits] = '\0';
  /*Branch for -outB, binary output.*/
  if(strcmp(oType, "-outB") == 0){
    for(i = numBits-1; i >= 0; i--){
      r = num%2;
      (r) ? (output[i] = '1') : (output[i] = '0');
      num /= 2;
      r = 0;
    }
    for (j = 0; j <= numBits; j++){
      if (j%4 == 0 && j!=0){
	printf(" ");
      }
      printf("%c",output[j]);
    }
    printf("\n");
  }
  /*Branch for -outH, Hexidecimal output.*/
  else if (strcmp(oType, "-outH") == 0){
    for (i = numBits-1; i >= 0; i--){
      r = num%16;
      if (r == 0){ output[i] = '0';
      }else if (r == 1){ output[i] = '1';
      }else if (r == 2){ output[i] = '2';
      }else if (r == 3){ output[i] = '3';
      }else if (r == 4){ output[i] = '4';
      }else if (r == 5){ output[i] = '5';
      }else if (r == 6){ output[i] = '6';
      }else if (r == 7){ output[i] = '7';
      }else if (r == 8){ output[i] = '8';
      }else if (r == 9){ output[i] = '9';
      }else if (r == 10){ output[i] = 'a';
      }else if (r == 11){ output[i] = 'b';
      }else if (r == 12){ output[i] = 'c';
      }else if (r == 13){ output[i] = 'd';
      }else if (r == 14){ output[i] = 'e';
      }else{ output[i] = 'f';
      }
      num /= 16;
      r = 0;
    }
    for (j = numBits-(numBits/4); j <= numBits; j++){
      if (j%2 == 0 && j!=numBits-(numBits/4)){
	printf(" ");
      }
      printf("%c",output[j]);
    }
    printf("\n");
  }
  /*Branch for -outD, Decimal output.*/
  else if (strcmp(oType, "-outD") == 0){
    for (i = numBits-1; i>= 0; i--){
      r = num%10;
      if (r == 0){ output[i] = '0';
      }else if (r == 1){ output[i] = '1';
      }else if (r == 2){ output[i] = '2';
      }else if (r == 3){ output[i] = '3';
      }else if (r == 4){ output[i] = '4';
      }else if (r == 5){ output[i] = '5';
      }else if (r == 6){ output[i] = '6';
      }else if (r == 7){ output[i] = '7';
      }else if (r == 8){ output[i] = '8';
      }else{ output[i] = '9'; }
      num /= 10;
      r = 0;
    }
    int inNum = 0;
    int x;
    if (numBits == 8){ x = 3;  
    }else if(numBits == 16){ x = 5;
    }else if(numBits == 32){ x = 10;
    }else if(numBits == 64){ x = 20;}
    for (; x > 0; x--){
      if (inNum == 0 && output[numBits-x] == '0'){
	printf(" ");
      }else if (inNum == 0 && output[numBits-x] != '0'){
	inNum = 1;
	printf("%c",output[numBits-x]);
      }else if (inNum == 1 && x%3 == 0){
	printf(",%c", output[numBits-x]);
      }else {
	printf("%c",output[numBits-x]);
      }
    }
    printf("\n");
  }
 
  free(output);
}
/*Main uses command line argument and branching to detect errors
and save values that are used in the convert funtion above.
strtoul funtion is found in the <stdlib.h> library*/
int main (int argc, char *argv[]){
  int i = 0;
  /*Checking Argument amount.*/
  if (argc == 5){
    /*saves valid Output Type or returns error*/
    if (strcmp(argv[2], "-outB") == 0){
      oType = "-outB";
    }else if (strcmp(argv[2], "-outH") == 0){
      oType = "-outH";
    }else if (strcmp(argv[2], "-outD") == 0){
      oType = "-outD";
    }else{
      printf("ERROR: argument 2 must be -outB | -outD | -outH\n");
      errMess();
      return 0;
    }
    /*saves valid Number of Bits in Output or returns error*/
    if (strcmp(argv[3],"-8") == 0){
      numBits = 8;
    }else if (strcmp(argv[3],"-16") == 0){
      numBits = 16;
    }else if (strcmp(argv[3],"-32") == 0){
      numBits = 32;
    }else if (strcmp(argv[3],"-64") == 0){
      numBits = 64;
    }else{
      printf("ERROR: argument 3 must be -8 | -16 | -32 | -64\n");
      return 0;
    }
    /*Checking for valid In Type and checking for valid 4th argument
    if both arguments are valid, saves a unsigned long int to convert*/
    if (strcmp(argv[1],"-inB") == 0){
      while(argv[4][i] != '\0'){
	if(argv[4][i] < '0' && argv[4][i] > '1'){
	  printf("ERROR: argumnet 4 is not a binary integer\n");
	  errMess();
	  return 0;
	}
	i++;
      }
      num = strtoul(argv[4], NULL, 2);
    }else if (strcmp(argv[1],"-inD") == 0){
      while(argv[4][i] != '\0'){ 
        if(argv[4][i] < '0' && argv[4][i] >'9'){
          printf("ERROR: argumnet 4 is not a decimal integer\n");
	  errMess();
          return 0;
        }
        i++;
      }
      num = strtoul(argv[4], NULL, 10);
    }else if (strcmp(argv[1],"-inH") == 0){
      while(argv[4][i] != '\0'){
        if((argv[4][i] < '0' && argv[4][i] > '9') || (argv[4][i] < 'a' && argv[4][i] > 'f')){
          printf("ERROR: argumnet 4 is not a hexidecimal integer\n");
	  errMess();
          return 0;
        }
        i++;
      }
      num = strtoul(argv[4], NULL, 16);
    }else{
      printf("ERROR: argument 1 must be -inB | -inD | -inH\n");
      errMess();
    }
    /*If program hasnt stopped then all iputs are valid*/
    convert();
  }else{
    printf("ERROR: incorrect number of arguments\n");
    errMess();
    return 0;
  }
  return 0;
}
