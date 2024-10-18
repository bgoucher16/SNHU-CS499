/*
Brandon Goucher
Last Edited: 10/16/2024
CS 499

All of the main inventory database and queries are located and ran from here.

Creation of the initial table that is used for storing every item that any user adds.

Query for adding items.

Query for removing items.

Query for updating the quantity of items.

Query for searching for items.

Query connecting to the settings page that will delete all of the items associated with a user when
they delete there account.

 */

package com.example.inventoryappfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InventoryDB extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "inventoryApp.db";
    private static InventoryDB _inventoryDB;

    private InventoryDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static InventoryDB getInstance(Context context) {
        if (_inventoryDB == null) {
            _inventoryDB = new InventoryDB(context.getApplicationContext());
        }
        return _inventoryDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE inventories (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT, " +
                "itemNumber INTEGER, " +
                "itemName TEXT, " +
                "itemQuantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists inventories");
    }

    public boolean addEntry(InventoryClass entry, UserModel _user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", _user.getUserName());
        values.put("itemNumber", entry.getItemNumber());
        values.put("itemName", entry.getItemName());
        values.put("itemQuantity", entry.getItemQuantity());

        long id = db.insert("inventories", null, values);
        return id != -1;
    }

    /*
    Updated the removeItem function since any user could delete any
    items for all users, now it is just for that current user
     */
    public void removeItem(int itemNumber, UserModel _user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", _user.getUserName());
        values.put("itemNumber", itemNumber);
        db.delete("inventories", "username = ? AND itemNumber = ?", new String[] { _user.getUserName(), String.valueOf(itemNumber) });
    }

    public boolean updateQuantity(int itemNumber, int itemQuantity, UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", user.getUserName());
        values.put("itemQuantity", itemQuantity);

        long id = db.update("inventories", values, "itemNumber = " + itemNumber, null);
        return id != -1;
    }

    public List<InventoryClass> getAllItems(UserModel user){
        List<InventoryClass> allEntries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM inventories ORDER BY itemNumber", null);

        if(cursor.moveToFirst()){
            do {
                //get the username from the db entry
                String user_id = cursor.getString(1);

                //if the username matches the user logged in then start the loop
                if (user_id.equals(user.getUserName())){
                    Log.e("userGrabbed", "we got the username taken in."); //Not being used anymore but was used to debug from a silly error above...

                    int ID = cursor.getInt(0);
                    int ItemNumber = cursor.getInt(2);
                    String ItemName = cursor.getString(3);
                    int ItemQuantity = cursor.getInt(4);

                    //add the objects to the list
                    InventoryClass newEntry = new InventoryClass(ID, ItemNumber, ItemName, ItemQuantity);
                    allEntries.add(newEntry);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allEntries;
    }

    // Delete all inventory entries for a specific user
    public void deleteUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("inventories", "username = ?", new String[]{user.getUserName()});
    }

    public boolean itemExists(int itemNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM inventories WHERE itemNumber = ?", new String[]{String.valueOf(itemNumber)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Search for an itemNumber under the current user
    public InventoryClass searchItem(int itemNumber, UserModel _user) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL query to search for the itemNumber under the current user
        String query = "SELECT * FROM inventories WHERE username = ? AND itemNumber = ?";
        Cursor cursor = db.rawQuery(query, new String[] {_user.getUserName(), String.valueOf(itemNumber)});

        // Check if the result exists for the current user
        if (cursor.moveToFirst()) {
            // If the item is found, retrieve the details
            int ItemNumber = cursor.getInt(2);
            String ItemName = cursor.getString(3);
            int ItemQuantity = cursor.getInt(4);

            // Create an InventoryClass object with the found data
            InventoryClass foundItem = new InventoryClass(ItemNumber, ItemName, ItemQuantity);

            cursor.close();
            return foundItem;
        } else {
            cursor.close();
            return null;
        }
    }
}
