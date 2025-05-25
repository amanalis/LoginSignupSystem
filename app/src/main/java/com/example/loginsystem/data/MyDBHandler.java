package com.example.loginsystem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.loginsystem.params.Params;

public class MyDBHandler extends SQLiteOpenHelper {

    public static final String databaseName = "Signup.db";

    public MyDBHandler(@Nullable Context context) {
        super(context, "Signup.db", null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table allusers(email TEXT primary key, password TEXT)");

//        String create = "CREATE TABLE " + Params.TABLE_NAME +
//                "(" + Params.KEY_ID + " INTEGER PRIMARY KEY,"
//                + Params.KEY_EMAIL+ " TEXT, "
//                + Params.KEY_PASSWORD + " TEXT" + ")";
//        Log.d("dbaman", "QUERY BEING RUN IS : " + create);
//        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists allusers");
    }

    public Boolean insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = db.insert("allusers", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from allusers where email = ?" , new String[]{email});

        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from allusers where email = ? and password = ?" , new String[]{email, password});

        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }
}
