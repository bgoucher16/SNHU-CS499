/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

This file is used for the authentication of each user.

Allows the user to be able to create (register) an account.
That account is connected with a Inventory database by the id that the user is given after
registering.

Also checks to make sure that the password is more than 4 characters checking with every
single character that gets input.
*/

package com.example.inventoryappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class sign_in extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signInBtn;
    TextView passwordMessage;
    UserDB _db;
    UserModel validUser;
    InventoryDB inventoryDB;

    private void loginSuccess(View view, String _user){

        //start the inventory Database
        inventoryDB = InventoryDB.getInstance(this);

        //instantiate the UserModel
        validUser = UserModel.getUserInstance();
        validUser.setUserName(_user);

        Intent intent = new Intent(this, main_screen.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);
        signInBtn = findViewById(R.id.SignInBtn);
        passwordMessage = findViewById(R.id.passwordMessage);

        //call the singleton
        _db = UserDB.getInstance(this);

        //admin and admin
        signInBtn.setOnClickListener(v -> {

            String _username = username.getText().toString();
            String _password = password.getText().toString();

            boolean validUser = _db.checkUserName(_username);
            boolean validPass = _db.checkUserPassword(_username, _password);

            //first test if a valid user/pass combo --> registered user
            if(validUser){
                if(validPass){
                    Toast.makeText(sign_in.this,"Login Success",Toast.LENGTH_SHORT).show();
                    loginSuccess(v, _username);
                }
                else{
                    Toast.makeText(sign_in.this,"Incorrect Password!!!",Toast.LENGTH_SHORT).show();
                }
            }

            else if(!validUser) {
                AlertDialog.Builder builder = new AlertDialog.Builder(sign_in.this);
                builder
                        .setTitle("Account Not Recognized")
                        .setMessage("Would you like to register?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            //Yes button clicked, do something
                            //get the userDB
                            //call insertuser
                            Boolean userAdded = _db.insertUser(_username, _password);

                            if(userAdded){
                                Toast.makeText(sign_in.this,"Account added",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(sign_in.this,"Account failed",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null) //Do nothing on no
                        .show();
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            //monitor the password entry
            //if the passwords meets the minimum (>5 char)
            //enable the button
            //hide the message
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 5) {
                    passwordMessage.setVisibility(View.VISIBLE);
                } else {
                    passwordMessage.setVisibility(View.INVISIBLE);
                    signInBtn.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}