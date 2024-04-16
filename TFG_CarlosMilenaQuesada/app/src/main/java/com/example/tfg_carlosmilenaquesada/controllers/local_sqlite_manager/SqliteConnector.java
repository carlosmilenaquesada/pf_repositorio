package com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tfg_carlosmilenaquesada.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SqliteConnector extends SQLiteOpenHelper {

    //SQLite solo permite tipos INTEGER, REAL, TEXT, BLOB, NUMERIC y NULL,
    //sin necesidad de especificar ni longuitud ni precisión.
    private static SqliteConnector sqliteConnector;
    public static final String NODE_SERVER = "http://192.168.0.3:3000/sync/";
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "tpv.db";
    public static final String TABLE_ARTICLES = "articles";
    public static final String TABLE_BARCODES = "barcodes";
    public static final String TABLE_CUSTOMERS = "taxable_customers";
    public static final String TABLE_CUSTOMERS_TYPES = "customers_types";
    public static final String TABLE_TICKETS = "tickets";
    public static final String TABLE_TICKETS_LINES = "tickets_lines";
    public static final String TABLE_USERS = "users";


    private SqliteConnector(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static SqliteConnector getInstance(Context context) {
        if (sqliteConnector == null) {
            sqliteConnector = new SqliteConnector(context);
        }
        return sqliteConnector;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ARTICLES + "(" +
                "article_id TEXT PRIMARY KEY NOT NULL, " +
                "name TEXT  NOT NULL, " +
                "sale_base_price REAL NOT NULL," +
                "vat_fraction REAL NOT NULL," +
                "offer_start_date TEXT," +
                "offer_end_date TEXT," +
                "offer_sale_base_price REAL" +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_BARCODES + "(" +
                "barcode TEXT PRIMARY KEY NOT NULL," +
                "article_id TEXT NOT NULL " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_CUSTOMERS + "(" +
                "customer_tax_id TEXT PRIMARY KEY NOT NULL," +
                "legal_company_name TEXT UNIQUE NOT NULL, " +
                "name TEXT, " +
                "legal_company_address TEXT NOT NULL, " +
                "legal_country TEXT NOT NULL, " +
                "legal_location TEXT NOT NULL, " +
                "legal_zip_code TEXT NOT NULL " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_CUSTOMERS_TYPES + "(" +
                "customer_type_id TEXT PRIMARY KEY NOT NULL," +
                "description TEXT NOT NULL " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_TICKETS + "(" +
                "ticket_id TEXT PRIMARY KEY NOT NULL," +
                "sale_date TEXT NOT NULL, " +
                "customer_tax_id, " +
                "ticket_status_id NOT NULL, " +
                "payment_method_id NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE " + TABLE_TICKETS_LINES + "(" +
                "ticket_line_id TEXT PRIMARY KEY NOT NULL," +
                "ticket_id TEXT NOT NULL, " +
                "article_id TEXT NOT NULL, " +
                "article_quantity REAL NOT NULL, " +
                "sale_base_price_at_sale_moment TEXT NOT NULL, " +
                "vat_fraction REAL NOT NULL " +
                ")");
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                "user_id TEXT PRIMARY KEY NOT NULL," +
                "password TEXT NOT NULL, " +
                "privileges TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void insert(JSONArray elements, String table) {
        JSONObject elementJson;
        ContentValues contentValues;
        for (int i = 0; i < elements.length(); i++) {
            try {
                elementJson = elements.getJSONObject(i);
                contentValues = new ContentValues();
                for (int j = 0; j < elementJson.names().length(); j++) {
                    String key = (String) elementJson.names().get(j);
                    Object value = elementJson.get(key);
                    if (value instanceof String) {
                        contentValues.put(key, (String) value);
                        continue;
                    }
                    if (value instanceof Integer) {
                        contentValues.put(key, (Integer) value);
                        continue;
                    }
                    if (value instanceof Double) {
                        contentValues.put(key, (Double) value);
                        continue;
                    }
                    if (value instanceof Long) {
                        contentValues.put(key, (Long) value);
                        continue;
                    }
                    if (value == null) {
                        contentValues.putNull(key);
                    }
                }
                getReadableDatabase().insert(table, null, contentValues);
                //System.out.println(contentValues);
            } catch (JSONException e) {
                System.out.println(e);
            }
        }
    }

    public User getValidUser(String userId, String password) {
        String[] selectionArgs = {userId, password};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_USERS + " where user_id = ? AND password = ?", selectionArgs);
        User user = cursor.moveToNext() ? new User(cursor.getString(0), cursor.getString(1), cursor.getString(2)) : null;
        cursor.close();
        return user;
    }

    public void wipeTable(String tableName) {
        getReadableDatabase().delete(tableName, null, null);
    }

}
