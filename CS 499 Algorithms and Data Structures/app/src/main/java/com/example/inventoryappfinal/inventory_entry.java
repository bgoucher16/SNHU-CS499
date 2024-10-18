/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

This is where the user adds items to there database.
They input via an integer a number, then a string for the name and then another int for the quantity.

That information is then checked to see if an item with the same number is already in the database,
if it is not then the item will be added into the database and the user is taken back to the main
screen.
 */

package com.example.inventoryappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class inventory_entry extends AppCompatActivity {

    protected EditText itemNum;
    protected EditText itemName;
    protected EditText itemQuantity;
    UserModel _user;
    InventoryDB _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_entry);

        itemNum = findViewById(R.id.ItemNumberInput);
        itemName = findViewById(R.id.ItemNameInput);
        itemQuantity = findViewById(R.id.ItemQuantityInput);

        _user = UserModel.getUserInstance();
        _db = InventoryDB.getInstance(this);
    }

    public void openMain(View view) {
        Intent intent = new Intent(this, main_screen.class);
        startActivity(intent);
        finish();
    }

    public void onAddItem(View view) {
        String iNum = itemNum.getText().toString();
        String iName = itemName.getText().toString();
        String iQuantity = itemQuantity.getText().toString();

        // Protect against an empty form
        if (iNum.isEmpty() || iName.isEmpty() || iQuantity.isEmpty()) {
            Toast.makeText(inventory_entry.this, "Please fill in all of the required categories.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!iNum.isEmpty() & !iName.isEmpty() & !iQuantity.isEmpty()){
            int newItemNumber = Integer.parseInt(iNum);
            int newItemQuantity = Integer.parseInt(iQuantity);
            if (_db.itemExists(newItemNumber)){
                Toast.makeText(inventory_entry.this, "Item Number Already exists...", Toast.LENGTH_SHORT).show();
                return;
            }
            InventoryClass entry = new InventoryClass(newItemNumber, iName, newItemQuantity);
            boolean inventoryAdd = _db.addEntry(entry, _user);

            if (inventoryAdd) {
                Toast.makeText(inventory_entry.this, "Item added successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(inventory_entry.this, "Error adding item.", Toast.LENGTH_SHORT).show();
            }
        }

        // If an item quantity is less than or equal to 0 send an SMS message.
        if (Integer.parseInt(iQuantity) <= 0) {
            if (_user.isTextPermission()) {
                SMSNotification.sendLongSMS(_user.getSMSText());
            }
        }

        // Go back to main
        Intent intent = new Intent(this, main_screen.class);
        startActivity(intent);
        finish();
    }
}

