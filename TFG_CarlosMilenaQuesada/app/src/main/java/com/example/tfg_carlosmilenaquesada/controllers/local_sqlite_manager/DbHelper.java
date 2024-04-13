package com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tfg_carlosmilenaquesada.models.Article;
import com.example.tfg_carlosmilenaquesada.models.Barcode;
import com.example.tfg_carlosmilenaquesada.models.CustomerType;
import com.example.tfg_carlosmilenaquesada.models.User;

public class DbHelper extends SQLiteOpenHelper {

    public static final String NODE_SERVER = "http://192.168.0.3:3000/sync/";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tpv.db";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ARTICLES = "articles";
    public static final String TABLE_BARCODES = "barcodes";
    public static final String TABLE_CUSTOMERS_TYPES = "customers_types";


    public DbHelper(@Nullable Application application) {
        super(application, DATABASE_NAME, null, DATABASE_VERSION);
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
                "internal_code TEXT PRIMARY KEY NOT NULL, " +
                "name TEXT  NOT NULL, " +
                "sale_base_price REAL NOT NULL," +
                "vat_fraction TEXT NOT NULL," +
                "offer_start_date TEXT," +
                "offer_end_date TEXT," +
                "offer_sale_base_price REAL" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_BARCODES + "(" +
                "barcode TEXT PRIMARY KEY NOT NULL," +
                "internal_code TEXT NOT NULL " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_CUSTOMERS_TYPES + "(" +
                "id TEXT PRIMARY KEY NOT NULL," +
                "description TEXT NOT NULL " +
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
        contentValues.put("internal_code", article.getInternalCode());
        contentValues.put("name", article.getName());
        contentValues.put("sale_base_price", article.getSaleBasePrice());
        contentValues.put("vat_fraction", article.getVatFraction());
        contentValues.put("offer_start_date", article.getOfferStartDate() == null ? null : article.getOfferStartDate().toString());
        contentValues.put("offer_end_date", article.getOfferEndDate() == null ? null : article.getOfferEndDate().toString());
        contentValues.put("offer_sale_base_price", article.getOfferSaleBasePrice());
        getReadableDatabase().insert(TABLE_ARTICLES, null, contentValues);
    }

    public void insertBarcode(Barcode barcode) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("internal_code", barcode.getInternalCode());
        contentValues.put("barcode", barcode.getBarcode());
        getReadableDatabase().insert(TABLE_BARCODES, null, contentValues);
    }

    public void insertCustomerType(CustomerType customerType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", customerType.getId());
        contentValues.put("description", customerType.getDescription());
        getReadableDatabase().insert(TABLE_CUSTOMERS_TYPES, null, contentValues);
    }

    public void wipeTable(String tableName) {
        getReadableDatabase().delete(tableName, null, null);
    }

}
