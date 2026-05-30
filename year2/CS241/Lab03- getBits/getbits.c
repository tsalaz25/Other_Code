/**************************************************************
Tomas Salaz       
Februrary 26, 2024

Description: getbits.c uses the getbits method (from 
textbok section 2.9) along with main to print out 
a the record for a valid input and the output of 
get bits in the desired format. Main uses functions like
fgets to store strings and sscanf to pass in data from stdin.       
**************************************************************/
#include <stdio.h>
#define UNSIGNEDLIMIT 4294967295
/*getbits: get n bits from position p*/
unsigned getbits (unsigned x, int p, int n){
  return (x >> (p+1-n)) & ~(~0 << n);
}
int main (){
  /*Global Varibles*/
  char lineIn[50];
  unsigned tempX;
  unsigned long intOverflow;
  int tempPos, tempNum, value;
  tempX = 0;
  tempNum = tempPos = value = 0;
  while(fgets(lineIn,sizeof(lineIn),stdin)){
    /*Error handling done with conditionals*/
    if (sscanf(lineIn,"%lu:%d:%d\n", &intOverflow,&tempPos,&tempNum) == 3){
      if (intOverflow > UNSIGNEDLIMIT){
	printf("Error: value out of range\n");
      }
      else if((tempPos+1) < tempNum){
	printf("Error: too many bits requested from position\n");
      }
      else if(tempPos > 31){
	printf("Error: position out of range\n");
      }
      else if(tempNum > 31){
	printf("Error: number of bits out of range\n");
      }
      else{
	tempX = (unsigned)intOverflow;
	value = getbits(tempX, tempPos, tempNum);
	printf("getbits(x=%u, p=%d, n=%d) = %u\n",
        tempX, tempPos, tempNum, value);
      }
      
    }
    else{printf("Error: Record Formula is wrong");}
  }
  return 0;
}
