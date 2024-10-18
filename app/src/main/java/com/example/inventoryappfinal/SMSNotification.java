/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

This is the file that sends the message to the users phone number. In this case
the message is "Item Quantity is 0!"
 */

package com.example.inventoryappfinal;

import android.telephony.SmsManager;
import java.util.ArrayList;

public class SMSNotification {

    public static void sendLongSMS(String phoneNumber) {
        String message = "Item Quantity is 0!";
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(message);
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
    }

}
