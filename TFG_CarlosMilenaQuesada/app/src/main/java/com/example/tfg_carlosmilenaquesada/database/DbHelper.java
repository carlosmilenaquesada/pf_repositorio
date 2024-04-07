package com.example.tfg_carlosmilenaquesada.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tfg_carlosmilenaquesada.models.Article;
import com.example.tfg_carlosmilenaquesada.models.User;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tpv.db";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ARTICLES = "articles";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //SQLite solo permite tipos INTEGER, REAL, TEXT, BLOB y NULL,
    //sin necesidad de especificar ni longuitud ni precisión.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                "id TEXT PRIMARY KEY NOT NULL," +
                "password TEXT NOT NULL, " +
                "privileges TEXT NOT NULL" +
                ")");


        db.execSQL("CREATE TABLE " + TABLE_ARTICLES + "(" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "internal_code TEXT NOT NULL, " +
                "barcode_id TEXT," +
                "name TEXT  NOT NULL, " +
                "family_id TEXT, " +
                "category_id TEXT, " +
                "base_price REAL NOT NULL," +
                "vat_percent_id TEXT NOT NULL," +
                "stock REAL," +
                "sold REAL," +
                "offer_start_date TEXT," +
                "offer_end_date TEXT," +
                "offer_base_price REAL" +
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", user.getId());
        contentValues.put("password", user.getPassword());
        contentValues.put("privileges", user.getPrivileges());
        getReadableDatabase().insert(TABLE_USERS, null, contentValues);
    }

    public User getValidUser(String idUser, String passwordUser) {
        String[] selectionArgs = {idUser, passwordUser};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_USERS + " where id = ? AND password = ?", selectionArgs);
        User user = cursor.moveToNext() ? new User(cursor.getString(0), cursor.getString(1), cursor.getString(2)) : null;
        cursor.close();
        return user;
    }

    public void insertArticle(Article article) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", article.getId());
        contentValues.put("internal_code", article.getInternalCode());
        contentValues.put("barcode_id", article.getBarcodeId());
        contentValues.put("name", article.getName());
        contentValues.put("family_id", article.getFamilyId());
        contentValues.put("category_id", article.getCategoryId());
        contentValues.put("base_price", article.getBasePrice());
        contentValues.put("vat_percent_id", article.getVatPercentId());
        contentValues.put("stock", article.getStock());
        contentValues.put("sold", article.getSold());
        contentValues.put("offer_start_date", article.getOfferStartDate() == null ? null : article.getOfferStartDate().toString());
        contentValues.put("offer_end_date", article.getOfferEndDate() == null ? null : article.getOfferEndDate().toString());
        contentValues.put("offer_base_price", article.getOfferBasePrice());

        getReadableDatabase().insert(TABLE_ARTICLES, null, contentValues);

    }

    public void wipeTable(String tableName) {
        getReadableDatabase().delete(tableName, null, null);
    }

}
