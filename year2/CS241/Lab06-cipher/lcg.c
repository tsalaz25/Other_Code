/************************************************************
Tomas Salaz
April 17,2024

Description: lcg.c impements the funtions from lcg.h. lcg.h 
contains the struct used in this program. This program provides
basic funtionality for using Linear Congruential Generators
************************************************************/
#include <stdio.h>
#include "lcg.h"
#define BUG 0
/*
Struct used for holding information about the prime factors 
of m, their product and the count of factors
*/
struct info{
  unsigned long factor[15];
  unsigned long p;
  int count;
};
/*
Function for getting the next pseudo-random number in the LCG
sqeuence. Saves into the LCG.
*/
unsigned long getNextRandomValue(struct LinearCongruentialGenerator *lcg){
  unsigned long n=1;
  n=lcg->x;
  lcg->x=(lcg->a * lcg->x+ lcg->c) % lcg->m;
  return n;
}
/*
Funtion used for generating the primes of a given number (num),
the product of those primes. saves into the a structure info (x)
to be used elsewhere. Implemented with loops and branching. 
*/
unsigned long* prime(struct info *x,unsigned long num){
  unsigned long p1=1;
  unsigned long p2=2;
  int index=0;
  x->count=0;
  if(num<2){
    x->factor[index]=num;
    return x->factor;
  }
  while(num>1){
    if((p2*p2)>num){
      x->factor[index]=num;
      x->count++;
      p1=p1*num;
	    x->p=p1;
      return x->factor;
    }
    else if((p2*p2)==num){
      x->factor[index]=p2;
      x->count++;
      p1=p1*p2;
	    x->p=p1;
      return x->factor;
    }
    else if(num%p2==0){
      x->factor[index]=p2;
      index++;
      x->count++;
      p1=p1*p2;
      x->p=p1;
      while(num%p2==0){
        num=num/p2;
      }
    }
    else {
      p2++;
    }
  }
  return x->factor;
}
/*
Struct used to create a LCG using a given Modulus (m) and Increment (c). 
Calculation is given by
a = 1+2p, if 4 is a factor of m, if not a = 1+p.
p = product of Primes while a < m
If values are invalid then all are set to 0
*/
struct LinearCongruentialGenerator makeLCG(unsigned long m, unsigned long c){
  struct LinearCongruentialGenerator lcg;
  struct info i;
  unsigned long* factor;
  factor=prime(&i,m);
  if(BUG==1){
    int count=0;
    printf("prime factors of %lu\n", m);
    while(count<i.count){
      printf("%d. %lu\n",count+1,factor[count]);
      count++;
    }
    printf("p = %lu\n",i.p);
  }
  if(m>0 && c<m && c>0){
    lcg.m=m;
    lcg.c=c;
    lcg.x=c;
    if(m%4==0){
      lcg.a=1+(2*i.p);
    }else{
      lcg.a=1+i.p;
    }

    if(lcg.a>m || lcg.a<0){
      lcg.m=0;
      lcg.c=0;
      lcg.a=0;
      lcg.x=0;
      return lcg;
    }
  }
  else{
    lcg.m=0;
    lcg.c=0;
    lcg.a=0;
    lcg.x=0;
  }  
  return lcg;
}
