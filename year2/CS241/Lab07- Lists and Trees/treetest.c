#include <stdio.h>
#include <stdlib.h>
#include "binarytree.h"

/* Makes a simple tree for testing isBalanced */
struct TreeNode* makeTestTree(int n, int k)
{
  struct TreeNode* root = NULL;
  if(n > 0)
  {
    root = createNode(n + 'A');
    root->left = makeTestTree(n-1,k);
    root->right = makeTestTree(n-1-k,k);
  }
  return root;
}

/* Makes an almost BST that will break naive isBST test */
struct TreeNode* makeNotBST()
{
  struct TreeNode* tree = createNode('E');

  /* left child is a BST */
  tree->left = createNode('C');
  tree->left->left = createNode('A');
  tree->left->right = createNode('H');

  /* right child also a BST */
  tree->right = createNode('G');
  tree->right->left = createNode('D');
  tree->right->right = createNode('J');

  /* Overall tree is not a BST */
  return tree;
}

int main(int argc, char** argv)
{
  int i, n;
  char c;
  struct TreeNode* bst = NULL;
  struct TreeNode* tree = makeTestTree(5,1);

  printf("test tree: ");
  printTree(tree);
  printf("test tree verbose: ");
  printTreeVerbose(tree);
  printf("tree leaves: ");
  printLeaves(tree);
  printf("tree depth = %d\n", maxDepth(tree));
  printf("tree balanced = %d\n", isBalanced(tree));
  printf("tree isBST = %d\n", isBST(tree));

  freeTree(tree);
  tree = NULL;

  tree = makeTestTree(6,2);

  printf("another test tree: ");
  printTree(tree);
  printf("another test tree verbose: ");
  printTreeVerbose(tree);
  printf("tree leaves: ");
  printLeaves(tree);
  printf("tree depth = %d\n", maxDepth(tree));
  printf("tree balanced = %d\n", isBalanced(tree));
  printf("tree isBST = %d\n", isBST(tree));

  freeTree(tree);
  tree = NULL;

  tree = makeNotBST();

  printf("notBST: ");
  printTree(tree);
  printf("notBST verbose: ");
  printTreeVerbose(tree);
  printf("notBST leaves: ");
  printLeaves(tree);
  printf("notBST depth = %d\n", maxDepth(tree));
  printf("notBST balanced = %d\n", isBalanced(tree));
  printf("notBST isBST = %d\n", isBST(tree));

  printf("empty tree: ");
  printTree(bst);
  printf("empty tree verbose: ");
  printTreeVerbose(bst);

  for(i = 0; i < 23; ++i)
  {
    c = (i*17+11) % 23 + 'A';
    bst = insertBST(bst, c);
  }

  printf("filled BST: ");
  printTree(bst);
  printf("filled BST verbose: ");
  printTreeVerbose(bst);
  printf("BST leaves: ");
  printLeaves(bst);
  printf("BST depth = %d\n", maxDepth(bst));
  printf("BST minimum value = %c\n", minValueBST(bst));
  printf("BST balanced = %d\n", isBalanced(bst));
  printf("BST isBST = %d\n", isBST(bst));

  for(i = -4; i < 25; i+=4)
  {
    c = i + 'A';
    n = removeBST(&bst, c);
    if(!n) printf("remove did not find %c\n", c);
  }

  printf("BST after removes: ");
  printTree(bst);
  printf("BST after removes verbose: ");
  printTreeVerbose(bst);
  printf("BST leaves: ");
  printLeaves(bst);
  printf("BST depth = %d\n", maxDepth(bst));
  printf("BST minimum value = %c\n", minValueBST(bst));
  printf("BST balanced = %d\n", isBalanced(bst));
  printf("BST isBST = %d\n", isBST(bst));

  freeTree(bst);
  bst = NULL;

  freeTree(tree);
  tree = NULL;

  return 0;
}
