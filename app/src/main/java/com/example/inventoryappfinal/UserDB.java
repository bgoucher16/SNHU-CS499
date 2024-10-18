/*
Brandon Goucher
Last Edited: 9/27/2024
CS 499

Main creation and structure of the entire User database. All connections are suited here.

Everything from creating, deleting, and username/password checks are done here.
 */

package com.example.inventoryappfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "usersApp.db";
    private static UserDB userDB;

    private UserDB(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, VERSION);
    }

    public static synchronized UserDB getInstance(Context context) {
        if (userDB == null) {
            userDB = new UserDB(context);
        }
        return userDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
    }

    public Boolean insertUser(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("username", userName);
        values.put("password", password);
        long writeResult = db.insert("users", null, values);

        //check if the write result is successful
        return writeResult != -1;
    }

    public Boolean checkUserName(String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ?",
                new String[]{userName});
        return cursor.getCount() > 0;
    }

    public Boolean checkUserPassword(String userName, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ? and password =?",
                new String[]{userName, password});
        return (cursor.getCount() > 0) ? true : false;
    }

    public void deleteUser(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "username = ?", new String[]{user.getUserName()});
    }
}
