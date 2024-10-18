/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

Java file connected to the "activity_user_settings.xml" file. Allows the user to
delete there account, or update a phone number that is used for SMS notifications for items that
have a quantity equal to 0.
 */

package com.example.inventoryappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class UserSettings extends AppCompatActivity {

    private Button deleteAccount;
    private UserDB _userDB;
    private InventoryDB _inventory;
    private UserModel _user;
    private CompoundButton _switch;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        _user = UserModel.getUserInstance();
        phone = findViewById(R.id.editTextPhone);
        phone.setText(_user.getSMSText());

        deleteAccount = findViewById(R.id.deleteAccountBtn);
        _switch = findViewById(R.id.NotificationSwitch);
        _switch.setChecked(_user.isTextPermission());
        _switch.setOnCheckedChangeListener((buttonView, isChecked) -> _user.setTextPermission(isChecked));
    }

    public void deleteUserAccounts(View view) {
        // Call the singletons
        _userDB = UserDB.getInstance(this);
        _inventory = InventoryDB.getInstance(this);
        _user = UserModel.getUserInstance();

        _inventory.deleteUser(_user);
        _userDB.deleteUser(_user);

        // Force the user back out
        this.finishAffinity();
        System.exit(0);
    }

    public void openMain(View view) {
        phone = findViewById(R.id.editTextPhone);
        String sPhone = phone.getText().toString();

        if (!sPhone.isEmpty()) {
            _user.setSMSText(sPhone);
            Toast.makeText(UserSettings.this,"Account Notification have been activated.",Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, main_screen.class);
        startActivity(intent);
    }
}
