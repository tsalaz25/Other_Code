/************************************************************
Tomas Salaz
May 5, 2024

binarytree.c is the code that implemetns the funtions 
desrcibed in the given header file binarytree.h and uses the 
struct described in the header file.
************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include "binarytree.h"
/*
struct TreeNode
{
  char data;
  struct TreeNode* left;
  struct TreeNode* right;
};
*/
/* Alloc a new node with given data. */
struct TreeNode* createNode(char data){
  struct TreeNode *newNode = (struct TreeNode*)malloc(sizeof(struct TreeNode));
  if(newNode == NULL){printf("ERROR\n");}
    
  newNode ->data = data;
  newNode ->left = NULL;
  newNode ->right = NULL;
  return newNode;
}
/* Insert data at appropriate place in BST, return new tree root. */
struct TreeNode *insertBST(struct TreeNode* root, char data) {
  if(root == NULL){return createNode(data);}

  if(data < root ->data){
    root ->left = insertBST(root ->left,data);
  }else if(data > root ->data){
    root ->right = insertBST(root ->right,data);
  }
  return root;
}
/*Helper for removing a BST*/
int remHelper(struct TreeNode** rootRef) {
  struct TreeNode *root = *rootRef;
  if(root ->left == NULL){
    *rootRef = root ->right;
    free(root);
    return 1;
  }else if(root ->right == NULL){
    *rootRef = root ->left;
    free(root);
    return 1;
  }else{
    char rtMin = minValueBST(root ->right);
    root ->data = rtMin;
    return removeBST(&root ->right,rtMin);
  }
}
/* Remove data from BST pointed to by rootRef, changing root if necessary.
 * For simplicity's sake, always choose node's in-order
 * successor in the two-child case.
 * Memory for removed node should be freed.
 * Return 1 if data was present, 0 if not found. */
int removeBST(struct TreeNode** rootRef, char data) {
  struct TreeNode *root = *rootRef;
  if(root == NULL){return 0;}

  if(data < root ->data){
    return removeBST(&root ->left,data);
  }else if(data > root ->data){
    return removeBST(&root ->right,data);
  }else{
    return remHelper(rootRef);
  }
}
/* Return minimum value in non-empty binary search tree. */
char minValueBST(struct TreeNode* root) {
  struct TreeNode *current = root;
  while(current ->left != NULL){
    current = current ->left;
  }
  return current ->data;
}
/* Return maximum depth of tree. Empty tree has depth 0. */
int maxDepth(struct TreeNode* root) {
  if(root == NULL){
    return 0;
  }else{
    int l = maxDepth(root ->left);
    int r = maxDepth(root ->right);
    return (l > r) ? (l + 1) : (r + 1);
  }
}
/* A tree is balanced if both subtrees are balanced and
 * the difference in height between the subtrees is no more than 1.
 * Return 1 if tree is balanced, 0 if not. */
int isBalanced(struct TreeNode* root) {
  if(root == NULL){return 1;}
    
  int l = maxDepth(root ->left);
  int r = maxDepth(root ->right);
  if (abs(l-r) <= 1 && isBalanced(root ->left) && isBalanced(root ->right)) {
    return 1;
  }
  return 0;
}
/*Helper Funtion for checking BST*/
int isBSThelper(struct TreeNode* root, char min, char max) {
  if(root == NULL){return 1;}
  if(root ->data < min || root ->data > max){return 0;}
  return isBSThelper(root ->left,min,root ->data-1) && isBSThelper(root ->right,root ->data+1,max);
}
/* Return 1 if tree is a binary search tree, 0 if not. */
int isBST(struct TreeNode* root) {
  return isBSThelper(root, CHAR_MIN, CHAR_MAX);
}

void printTreeInorder(struct TreeNode* root) {
  if(root != NULL){
    printTreeInorder(root ->left);
    printf("%c ",root ->data);
    printTreeInorder(root ->right);
  }
}
/* Print data for inorder tree traversal on single line,
 * separated with spaces, ending with newline. */
void printTree(struct TreeNode* root) {
  printTreeInorder(root);
  printf("\n");
}
/* Helper function for printing leaves*/
void leavesHelper(struct TreeNode* root) {
  if (root != NULL){
    if (root ->left == NULL && root ->right == NULL) {
      printf("%c ",root ->data);
    }
    leavesHelper(root ->left);
    leavesHelper(root ->right);
  }
}
/* Print data for leaves on single line,
 * separated with spaces, ending with newline.*/
void printLeaves(struct TreeNode* root) {
  leavesHelper(root);
  printf("\n");
}
/* Helper Function for printing a verbose tree.*/
void verboseHelper(struct TreeNode* root, int d) {
  if(root!= NULL){
    printf("(%c,%d) ",root ->data,d);
    verboseHelper(root->left,d+1);
    verboseHelper(root->right,d+1);
  }
}
/* Print data for a preorder tree traversal on a single line
 * as a sequence of (data, depth) pairs
 * separated with spaces, ending with newline.
 * (The root node has a depth of 1)*/
void printTreeVerbose(struct TreeNode* root) {
  verboseHelper(root, 1);
  printf("\n");
}
/* Free memory used by the tree. */
void freeTree(struct TreeNode* root) {
  if(root!=NULL){
    freeTree(root ->left);
    freeTree(root ->right);
    free(root);
  }
}
