/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

Main screen where most of the app function is located.

Users can add, remove, and update items here. As well as access the settings page.

This screen contains a search bar that allows a user to search for items that will be connected
to the search_screen.java file.

Last but not least, it contains a ScrollView where all of the items that user is keeping track
of are held.
 */

package com.example.inventoryappfinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.util.List;

public class main_screen extends AppCompatActivity {

    InventoryDB _inventory;
    UserModel _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        try {
            onInit(main_screen.this);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SearchView numberInput = findViewById(R.id.numberInput);
        Button searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            String input = numberInput.getQuery().toString(); // Use getQuery() for SearchView input
            if (!input.isEmpty()) {
                int searchValue = Integer.parseInt(input);

                // Get all user items and populate the binary search tree
                List<InventoryClass> userItems = _inventory.getAllItems(_user);
                BinarySearchTree tree = new BinarySearchTree();
                for (InventoryClass item : userItems) {
                    tree.insert(item.getItemNumber());
                }

                // Perform binary search
                boolean found = tree.search(searchValue);
                if (found) {
                    // Check if the item belongs to the current user in the database
                    InventoryClass foundItem = _inventory.searchItem(searchValue, _user);
                    if (foundItem != null) {
                        //Toast.makeText(main_screen.this, "we reached here", Toast.LENGTH_SHORT).show(); Used as a debugging tool
                        // Create an Intent to start the search_screen activity
                        Intent intent = new Intent(main_screen.this, search_screen.class);

                        intent.putExtra("itemNumber", foundItem.getItemNumber());
                        intent.putExtra("itemName", foundItem.getItemName());
                        intent.putExtra("itemQuantity", foundItem.getItemQuantity());
                        intent.putExtra("itemNumber", searchValue);

                        startActivity(intent);
                    } else {
                        Toast.makeText(main_screen.this, "Item not found for current user!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(main_screen.this, "Number not found!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(main_screen.this, "Please enter a number!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openSettings(View view){
        Intent intent = new Intent(this, UserSettings.class);
        startActivity(intent);
    }



    public void openAddItem(View view) {
        Intent intent = new Intent(this, inventory_entry.class);
        startActivity(intent);
    }

    public void openUpdateQuantity(View view) {
        _user = UserModel.getUserInstance();
        _inventory = InventoryDB.getInstance(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create a LinearLayout to hold the input fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        // Create the EditText fields and labels
        final EditText inputItemNumber = new EditText(this);
        final EditText inputItemQuantity = new EditText(this);

        // Set input types
        inputItemNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputItemQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Create TextViews for labels
        TextView itemNumberLabel = new TextView(this);
        itemNumberLabel.setText("Enter Item Number:");
        TextView itemQuantityLabel = new TextView(this);
        itemQuantityLabel.setText("Enter Item Quantity:");

        // Add views to the layout
        layout.addView(itemNumberLabel);
        layout.addView(inputItemNumber);
        layout.addView(itemQuantityLabel);
        layout.addView(inputItemQuantity);

        // Set the layout to the dialog
        builder.setView(layout)
                .setTitle("Update Item Quantity")
                .setPositiveButton("Save", (dialog, which) -> {
                    String itemNumberStr = inputItemNumber.getText().toString();
                    String itemQuantityStr = inputItemQuantity.getText().toString();

                    if (!itemNumberStr.isEmpty() && !itemQuantityStr.isEmpty()) {
                        int itemNumber = Integer.parseInt(itemNumberStr);
                        int newQuantity = Integer.parseInt(itemQuantityStr);

                        // Check if item number exists in database
                        if (_inventory.itemExists(itemNumber)) {
                            _inventory.updateQuantity(itemNumber, newQuantity, _user);
                            Toast.makeText(main_screen.this, "Item updated successfully!", Toast.LENGTH_SHORT).show();

                            // Restart activity to refresh the data
                            finish();
                            startActivity(getIntent());
                        } else {
                            Toast.makeText(main_screen.this, "Item number does not exist!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(main_screen.this, "Please enter both item number and quantity.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void onRemoveItem(View view){
        _user = UserModel.getUserInstance();
        _inventory = InventoryDB.getInstance(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create a LinearLayout to hold the input fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        // Create the EditText fields and labels
        final EditText inputItemNumber = new EditText(this);

        // Set input types
        inputItemNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Create TextViews for labels
        TextView itemNumberLabel = new TextView(this);
        itemNumberLabel.setText("Enter Item Number:");

        // Add views to the layout
        layout.addView(itemNumberLabel);
        layout.addView(inputItemNumber);

        // Set the layout to the dialog
        builder.setView(layout)
                .setTitle("Enter the Item number of the item you want to remove.")
                .setPositiveButton("Save", (dialog, which) -> {
                    String userInput = inputItemNumber.getText().toString();
                    if (!userInput.isEmpty()) {
                        int itemNumber = Integer.parseInt(userInput);
                        _inventory.removeItem(itemNumber, _user);

                        finish();
                        startActivity(getIntent());
                    } else {
                        Toast.makeText(main_screen.this, "Please enter an item number.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void onInit(Context context) throws ParseException {

        _inventory = InventoryDB.getInstance(this);
        _user = UserModel.getUserInstance();

        List<InventoryClass> allEntries;
        allEntries = _inventory.getAllItems(_user);

        //Start table layout
        TableLayout _table = findViewById(R.id.inventory_table);

        TableRow _header = getTableRow(context);

        _table.addView(_header);

        for (int i = 0; i < allEntries.size(); i++) {
            TableRow _row = new TableRow(this);

            TextView _textC1 = new TextView(this);
            int tableItemNumber = allEntries.get(i).getItemNumber();
            String stringTableItemNumber = String.valueOf(tableItemNumber);

            _textC1.setText(stringTableItemNumber);
            _textC1.setTextSize(16);
            _textC1.setTextColor(ContextCompat.getColor(context, R.color.black));
            _textC1.setBackgroundResource(R.color.Table_and_buttons);
            _textC1.setGravity(Gravity.CENTER_HORIZONTAL);
            _textC1.setPadding(10, 10, 10, 10);
            _row.addView(_textC1);

            TextView _textC2 = new TextView(this);
            _textC2.setText(allEntries.get(i).getItemName());
            _textC2.setTextSize(16);
            _textC2.setTextColor(ContextCompat.getColor(context, R.color.black));
            _textC2.setBackgroundResource(R.color.Table_and_buttons);
            _textC2.setGravity(Gravity.CENTER_HORIZONTAL);
            _textC2.setPadding(10, 10, 10, 10);
            _row.addView(_textC2);

            TextView _textC3 = new TextView(this);
            int tableItemQuantity = allEntries.get(i).getItemQuantity();
            String stringTableItemQuantity = String.valueOf(tableItemQuantity);

            _textC3.setText(stringTableItemQuantity);
            _textC3.setTextSize(16);
            _textC3.setTextColor(ContextCompat.getColor(context, R.color.black));
            _textC3.setBackgroundResource(R.color.Table_and_buttons);
            _textC3.setGravity(Gravity.CENTER_HORIZONTAL);
            _textC3.setPadding(10, 10, 10, 10);
            _row.addView(_textC3);

            _table.addView(_row);
        }
    }

    @NonNull
    private TableRow getTableRow(Context context) {
        TableRow _header = new TableRow(main_screen.this);

        TextView _headerC1 = new TextView(this);
        _headerC1.setText("Item Number");
        _headerC1.setTextColor(ContextCompat.getColor(context, R.color.black));
        _headerC1.setBackgroundResource(R.color.white);
        _headerC1.setGravity(Gravity.CENTER);
        _headerC1.setPadding(10, 10, 10, 10);
        _header.addView(_headerC1);

        TextView _headerC2 = new TextView(this);
        _headerC2.setText("Item Name");
        _headerC2.setTextColor(ContextCompat.getColor(context, R.color.black));
        _headerC2.setBackgroundResource(R.color.white);
        _headerC2.setGravity(Gravity.CENTER);
        _headerC2.setPadding(10, 10, 10, 10);
        _header.addView(_headerC2);

        TextView _headerC3 = new TextView(this);
        _headerC3.setText("Item Quantity");
        _headerC3.setTextColor(ContextCompat.getColor(context, R.color.black));
        _headerC3.setBackgroundResource(R.color.white);
        _headerC3.setGravity(Gravity.CENTER);
        _headerC3.setPadding(10, 10, 10, 10);
        _header.addView(_headerC3);
        return _header;
    }
}