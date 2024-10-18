/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

Page that is connected with the "activity_search_screen.xml" which displays the item that a
user searches for. In this case the user types in an Item Number and it searchs for it in the
database and then that item if it exists is displayed here showing the Item Number, Item Name,
and item Quantity.

The user can update as well as delete the item on this page.
*/

package com.example.inventoryappfinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class search_screen extends AppCompatActivity {

    InventoryDB _inventory;
    UserModel _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        // Retrieve the data from the Intent
        Intent intent = getIntent();
        int itemNumber = intent.getIntExtra("itemNumber", -1);
        String itemName = intent.getStringExtra("itemName");
        int itemQuantity = intent.getIntExtra("itemQuantity", -1);

        // Now you can use these values to update your UI
        TextView itemNumberView = findViewById(R.id.itemNumberView);
        TextView itemNameView = findViewById(R.id.itemNameView);
        TextView itemQuantityView = findViewById(R.id.itemQuantityView);

        itemNumberView.setText(String.valueOf(itemNumber));
        itemNameView.setText(itemName);
        itemQuantityView.setText(String.valueOf(itemQuantity));
    }

    public void openMain(View view) {
        Intent intent = new Intent(this, main_screen.class);
        startActivity(intent);
        finish();
    }


    public void openSearchUpdateQuantity(View view) {
        /*
        Used to update the current item that is being displayed on the activity_search_screen.xml file

        Asks for the user to enter in the new amount of the items quantity and then updates it
         */
        _user = UserModel.getUserInstance();
        _inventory = InventoryDB.getInstance(this);
        int itemNumber = getIntent().getIntExtra("itemNumber", -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText inputItemQuantity = new EditText(this);
        inputItemQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        TextView itemQuantityLabel = new TextView(this);
        itemQuantityLabel.setText("Enter Item Quantity:");

        layout.addView(itemQuantityLabel);
        layout.addView(inputItemQuantity);

        builder.setView(layout)
                .setTitle("Update Item Quantity")
                .setPositiveButton("Save", (dialog, which) -> {
                    String itemQuantityStr = inputItemQuantity.getText().toString();

                    if (!itemQuantityStr.isEmpty()) {
                        int newQuantity = Integer.parseInt(itemQuantityStr);

                        // Check if item number exists in the database
                        if (_inventory.itemExists(itemNumber)) {
                            _inventory.updateQuantity(itemNumber, newQuantity, _user);
                            Toast.makeText(search_screen.this, "Item updated successfully!", Toast.LENGTH_SHORT).show();

                            // Optionally refresh the activity or return to the previous screen
                            finish();
                            startActivity(getIntent());
                        } else {
                            Toast.makeText(search_screen.this, "Item number does not exist!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(search_screen.this, "Please enter a quantity.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void openSearchDeleteItem(View view) {
        /*
        Used to delete the current item that is being displayed on the activity_search_screen.xml file

        Asks the user if they are sure they want to delete the item, either click confirm on cancel
         */
        _user = UserModel.getUserInstance();
        _inventory = InventoryDB.getInstance(this);
        int itemNumber = getIntent().getIntExtra("itemNumber", -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create a LinearLayout to hold the input fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);


        // Create TextView for the label
        TextView itemRemoveLabel = new TextView(this);
        itemRemoveLabel.setText("Are you sure you want to delete this item?");

        // Add views to the layout
        layout.addView(itemRemoveLabel);

        // Set the layout to the dialog
        builder.setView(layout)
                .setTitle("Delete Item")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Check if item number exists in the database
                    if (_inventory.itemExists(itemNumber)) {
                        _inventory.removeItem(itemNumber, _user);
                        Toast.makeText(search_screen.this, "Item deleted successfully!", Toast.LENGTH_SHORT).show();

                        // Optionally refresh the activity or return to the previous screen
                        finish();
                        Intent intent = new Intent(this, main_screen.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
                }



}
