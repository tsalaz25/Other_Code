/************************************************************
Tomas Salaz
April 17,2024

Description: cipher.c uses basic braching and looping to 
either encrypt or decrypt files. Main is usedre read in the 
characters and important information. Depinding on the info 
passes in,the encData or decData funtions encrypt or decrypt
character by character. 
The header file lcg.h and its methods are also used to
implement LCG funtions.  
The Expected Input is as follows
'action''m', 'c', 'data' 
************************************************************/
#include <stdio.h>
#include "lcg.h"

#define CONVM 0  
#define CONVC 1
#define DATA 2  
#define ENC 1  
#define DEC 2  

int encData(unsigned long m,unsigned long c,unsigned long x,int ch);
int decData(unsigned long m, unsigned long c,unsigned long x, int ch);

/*
As desribed above, main loops through the input, char by char, and 
uses a series of if and if else statements to convert the char to 
the desired output, or prints "Error"
*/
int main(){
  int cIn,currPos,act,lnNum,err;
  unsigned long m,c,rand;
  currPos=act=err=0;
  m=c=rand=0; 
  lnNum=1;
  while((cIn=getchar())!=EOF){
    /*Saving Action*/
    if(currPos==CONVM && cIn =='e')act=ENC;
    else if(currPos==CONVM && cIn=='d')act=DEC;
    /*Branching Depending on CharInput*/
    if(cIn!=',' && cIn!='\n'){
      /*convertig to nums*/
      if(currPos==CONVM && cIn>='0' && cIn<='9'){
        m=10*m+(cIn-'0');
      }else if(currPos==CONVC && cIn>='0' && cIn<='9'){
        c=10*c+(cIn-'0');
      }else if(currPos==DATA){
	/*Encryption Branch*/
        if(act == ENC){
          struct LinearCongruentialGenerator lcg;
          lcg=makeLCG(m,c);
          printf("%5d: ",lnNum);
          lnNum++;
          if(lcg.m==0){
            printf("Error");
            while((cIn=getchar())!='\n');
          }else{
            rand=getNextRandomValue(&lcg);
            while(cIn!='\n' && err==0){
              err= encData(m,c,rand,cIn);
              cIn=getchar();
              rand=getNextRandomValue(&lcg);
            }
          }
          printf("\n");
        }
	/*Decryption Branch*/
        else if(act == DEC){
          struct LinearCongruentialGenerator lcg;
          lcg=makeLCG(m,c);
          printf("%5d: ",lnNum);
          lnNum++;
            if(lcg.m==0){
              printf("Error");
              while((cIn=getchar())!='\n');
            }else{
              rand=getNextRandomValue(&lcg);
              while(cIn!='\n' && err==0){
                err=decData(m,c,rand,cIn);
                cIn=getchar();
                rand=getNextRandomValue(&lcg); 
              }
            }
            printf("\n");
          }
	  /*Error is Incorrect Action*/
          else {
            printf("%5d: ",lnNum);
            lnNum++;
            printf("Error\n");
          }
	  /*resets after every char*/
	  m=c=0;
          currPos=CONVM;
          act=err=0;
        }
      }
      /*2nd Branch for Character Input*/
      if(cIn == ',') {
        if(currPos==CONVM)currPos=CONVC;
        else if(currPos==CONVC)currPos=DATA;
      }
      /*3rd Branch for Character Input*/
      if(cIn=='\n' && currPos!=CONVM){
        printf("%5d: ",lnNum);
        lnNum++;
        printf("Error\n");
        m=c=0;
        currPos=CONVM;
        act=0;
      }
    }
  return 0;
}
/*
Function for encrypting Character (ch) based on the LCG m and c and 
a random x value. If m or c is passed in as 0, program prints and 
"Error" and ends.
*/
int encData(unsigned long m, unsigned long c,unsigned long x, int ch){
  int output=ch^(x%128);
  if(m ==0 || c== 0){
    printf("Error");
    return 1;
  }

  if(output<32){
    printf("*%c",('?' + output));
  }else if(output == 127){
    printf("*$");
  }else if(output == '*'){
    printf("**");
  }else{
    printf("%c",output);
  }
  return 0;
}
/*
Funtion for decrpting Character (ch) based on the LCG m and c and
a random value x. Same Error cathing as previous funtion. 
*/
int decData(unsigned long m, unsigned long c,unsigned long x, int ch){
  int output=ch^(x%128);
  if(m==0 || c==0){
    printf("Error");
    return 1;
  }
  if(ch == '*'){
    ch=getchar();
    if(ch == '*'){
      output=ch^(x%128);
      printf("%c",output);
    }
    else if(ch == '$'){
      output=127^(x%128);
      printf("%c",output);
    }
    else if(ch>32 && ch<127){
      output=(ch-'?')^(x%128);
      printf("%c",output);
    }
    else if(ch==32){
      printf("Error");
      return 1;
    }
  }else{
    printf("%c",output);
  }
  return 0;
}
