/************************************************************
Tomas Salaz
May 5, 2024

linkedlist.c is the code that implemetns the funtions
desrcibed in the given header file linkedlist.h and uses the
struct described in the header file.
************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include "linkedlist.h"
/*
struct ListNode
{
  char data;
  struct ListNode* next;
};
*/
/* Alloc a new node with given data. */
struct ListNode* createNode(char data){
  struct ListNode *newNode = (struct ListNode*)malloc(sizeof(struct ListNode));
  if (newNode != NULL){
    newNode ->data = data;
    newNode ->next = NULL;
  }
  return newNode;
}
/* Insert data at appropriate place in a sorted list, return new list head. */
struct ListNode* insertSorted(struct ListNode* head, char data){
  struct ListNode *newNode = createNode(data);
  if (newNode == NULL){
    return head;
  }
  if(head == NULL || data < head ->data){
    newNode ->next = head;
    return newNode;
  }
  struct ListNode *current = head;
  while (current ->next != NULL && current->next->data < data){
    current = current->next;
  }
  newNode ->next= current ->next;
  current ->next= newNode;
  return head;
}
/* Remove data from list pointed to by headRef, changing head if necessary.
 * Make no assumptions as to whether the list is sorted.
 * Memory for removed node should be freed.
 * Return 1 if data was present, 0 if not found. */
int removeItem(struct ListNode** headRef, char data){
  struct ListNode *current = *headRef;
  struct ListNode *prev = NULL;
  while (current != NULL && current ->data !=data){
    prev = current;
    current = current ->next;
  }
  if (current == NULL){return 0;}
  if (prev != NULL){
    prev ->next = current ->next;
  }else{
    *headRef = current ->next;
  }
  free(current);
  return 1;
}
/* Treat list as a stack. (LIFO - last in, first out)
 * Insert data at head of list, return new list head. */
struct ListNode* pushStack(struct ListNode* head, char data){
  struct ListNode *newNode = createNode(data);
  if (newNode == NULL){return head;}
  newNode ->next = head;
  return newNode;
}
/* Treat list as a stack. (LIFO - last in, first out)
 * Remove and return data from head of non-empty list, changing head.
 * Memory for removed node should be freed. */
char popStack(struct ListNode** headRef){
  if (*headRef == NULL){
    return '\0';
  }
  struct ListNode *temp = *headRef;
  char data = temp ->data;
  *headRef = temp ->next;
  free(temp);
  return data;
}
/* Return length of the list. */
int listLength(struct ListNode* head){
  int l = 0;
  while (head != NULL){
    l++;
    head = head ->next;
  }
  return l;
}
/* Print list data on single line, separating values with a comma and
 * a space and ending with newline. */
void printList(struct ListNode* head){
  if(head == NULL){
    printf("\n");
    return;
  }
  printf("%c",head ->data);
  head = head ->next;
  while(head != NULL){
    printf(", %c",head ->data);
    head = head ->next;
  }
  printf("\n");
}
/* Free memory used by the list. */
void freeList(struct ListNode* head){
  struct ListNode *current = head;
  while (current != NULL){
    struct ListNode *temp = current;
    current = current ->next;
    free(temp);
  }
}
/* Reverse order of elements in the list */
void reverseList(struct ListNode** headRef){
  struct ListNode *prev = NULL;
  struct ListNode *current = *headRef;
  struct ListNode *next = NULL;
  while (current != NULL){
    next = current ->next;
    current ->next = prev;
    prev = current;
    current = next;
  }
  *headRef = prev;
}
