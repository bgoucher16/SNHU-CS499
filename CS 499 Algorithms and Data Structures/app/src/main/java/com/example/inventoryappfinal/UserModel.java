/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

This file is for setting up the UserModel that is used for the SMS notifications.

Gets the username of the current user and relays the phone number associated with them.
 */

package com.example.inventoryappfinal;

import android.content.Context;

//this class represents the valid user
//once logged in the UserModel stores the name for use in other DB RW OI
//The goal is also retrieved to prevent OI later
public class UserModel {

    private String userName;
    private boolean textPermission = false;
    private String SMSText = "0000000000";
    private static UserModel _loggedUser;

    // There can only be one valid user logged in at any time.
    // This should be a singleton to prevent multiple user objects.
    public static synchronized UserModel getUserInstance() {
        if (_loggedUser == null) {
            _loggedUser = new UserModel();
        }
        return _loggedUser;
    }

    // Private constructor to prevent instantiation.
    private UserModel() {}

    // Getter and Setter for userName.
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter and Setter for textPermission.
    public boolean isTextPermission() {
        return textPermission;
    }

    public void setTextPermission(boolean textPermission) {
        this.textPermission = textPermission;
    }

    // Getter and Setter for SMSText.
    public String getSMSText() {
        return SMSText;
    }

    public void setSMSText(String SMSText) {
        this.SMSText = SMSText;
    }
}

