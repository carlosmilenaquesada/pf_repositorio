package com.example.tfg_carlosmilenaquesada.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tfg_carlosmilenaquesada.models.User;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tpv.db";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_ARTICLES = "articles";



    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        wipeTable(TABLE_USERS);
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                "id TEXT PRIMARY KEY," +
                "password TEXT NOT NULL, " +
                "privileges TEXT NOT NULL" +
                ")");

        wipeTable(TABLE_ARTICLES);
        db.execSQL("CREATE TABLE " + TABLE_ARTICLES + "(" +
                "id TEXT PRIMARY KEY," +
                "base_price NUMBER NOT NULL, " +
                "tax_percent NUMBER NOT NULL, " +
                "offer_start_date DATE, " +
                "offer_end_date DATE, " +
                "base_offer_price NUMBER" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    public long insertUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", user.getId());
        contentValues.put("password", user.getPassword());
        contentValues.put("privileges", user.getPrivileges());
        return db.insert(TABLE_USERS, null, contentValues);
    }


    public User getValidUser(String idUser, String passwordUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {idUser, passwordUser};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " where id = ? AND password = ?", selectionArgs);
        User user = cursor.moveToNext() ? new User(cursor.getString(0), cursor.getString(1), cursor.getString(2)) : null;
        cursor.close();
        return user;
    }

    private void wipeTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(tableName, null, null);
    }

}
