/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

A fairly basic binary search tree that takes in all of the items that are concurrently in the database,
and adds them into this tree when a user searches for an item to be able to check to make sure that
item does exist. If the item does exist in the database then the item is then send to the InventoryDB
file where it gets searched for if it exists for the current user then it gets displayed on the search
screen.

Currently this can either be O(log n) or O(n) depending on how items are put into the database.

For it to be O(log n) the numbers from the database will need to be random to avoid the tree being
unbalanced. Assuming that many users are using this, the numbers are more than likely going to be random,
therefore making this work.

On the off chance that doesn't happen and the users make it in perfect sequential order than the time
complexity will be O(n)
 */

package com.example.inventoryappfinal;

public class BinarySearchTree {

    class Node {
        int value;
        Node left, right;

        public Node(int item) {
            value = item;
            left = right = null;
        }
    }

    Node root;

    BinarySearchTree() {
        root = null;
    }

    void insert(int value) {
        root = insertRec(root, value);
    }

    Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    boolean search(int value) {
        return searchRec(root, value);
    }

    boolean searchRec(Node root, int value) {
        if (root == null) {
            return false;
        }
        if (root.value == value) {
            return true;
        }
        if (value < root.value) {
            return searchRec(root.left, value);
        }
        return searchRec(root.right, value);
    }

}
