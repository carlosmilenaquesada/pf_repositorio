package com.example.tfg_carlosmilenaquesada.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tpv.db";
    private static final String TABLE_USERS = "users";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                "id TEXT PRIMARY KEY," +
                "password TEXT NOT NULL " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean isValidUser(String idUser, String passwordUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {idUser, passwordUser};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " where id = ? AND password = ?", selectionArgs);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Cursor getUsersIds() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  id FROM " + TABLE_USERS, null);
        return cursor;
    }

    public long insertUser(String idUser, String passwordUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", idUser);
        contentValues.put("password", passwordUser);
        return db.insert(TABLE_USERS, null, contentValues);

    }

    public void wipeTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_USERS, null, null);
    }

}