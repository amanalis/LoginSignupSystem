package com.example.loginsystem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.loginsystem.model.Notes;
import com.example.loginsystem.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    public static final String databaseName = "Signup.db";

    public MyDBHandler(@Nullable Context context) {
        super(context, "Signup.db", null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table allusers(email TEXT primary key, password TEXT)");

        db.execSQL("CREATE TABLE notes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "title TEXT," +
                "content TEXT," +
                "timestamp TEXT," +
                "FOREIGN KEY(email) REFERENCES allusers(email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists allusers");
        db.execSQL("drop Table if exists notes");
        onCreate(db);
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

    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from allusers where email = ?", new String[]{email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from allusers where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean insertNote(String email, String title, String content, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", email);
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("timestamp", timestamp);
        long result = db.insert("notes", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Notes> getNotesByEmail(String email){

        List<Notes> notesList =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes WHERE email = ?",new String[]{email});

        if (cursor.moveToFirst()){
            do{
                Notes notes = new Notes();
                notes.setId(cursor.getInt(0));
                notes.setEmail(cursor.getString(1));
                notes.setTitle(cursor.getString(2));
                notes.setContent(cursor.getString(3));
                notes.setTimestamp(cursor.getString(4));
                notesList.add(notes);
            }while (cursor.moveToNext());
        }
        return notesList;
    }

    public int updateNote(Notes notes){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",notes.getTitle());
        contentValues.put("content",notes.getContent());
        contentValues.put("timestamp",notes.getTimestamp());

        //lets update now
        return database.update("notes", contentValues,"id=?",
                new String[]{String.valueOf(notes.getId())});
    }

    public void deleteNote (Notes notes){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("notes" ,"id=?", new String[]{
                String.valueOf(notes.getId())
        });
        database.close();
    }

}
