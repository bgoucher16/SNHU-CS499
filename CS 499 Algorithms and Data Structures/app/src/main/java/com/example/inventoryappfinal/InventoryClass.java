/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

Receiving and sending out the data that is associated with the database.
 */

package com.example.inventoryappfinal;

public class InventoryClass {

    private int ID;
    private int itemNumber;
    private String itemName;
    private int itemQuantity;

    // Constructor with ID
    public InventoryClass(int ID, int itemNumber, String itemName, int itemQuantity) {
        this.ID = ID;
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    // Constructor without ID
    public InventoryClass(int itemNumber, String itemName, int itemQuantity) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    // Getter and Setter for ID
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    // Getter and Setter for itemNumber
    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    // Getter and Setter for itemName
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Getter and Setter for itemQuantity
    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}

