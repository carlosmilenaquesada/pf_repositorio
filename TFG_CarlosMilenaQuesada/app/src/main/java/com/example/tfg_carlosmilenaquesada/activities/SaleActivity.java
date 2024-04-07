package com.example.tfg_carlosmilenaquesada.activities;

import static com.example.tfg_carlosmilenaquesada.database.DbHelper.TABLE_CUSTOMERS_TYPES;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.database.DbHelper;

import com.example.tfg_carlosmilenaquesada.R;

public class SaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sale);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner = findViewById(R.id.spCustomersTypes);
        System.out.println("hola");
        Cursor cursorCustomersTypes = MainActivity.getDbHelper().getReadableDatabase().rawQuery("SELECT description as _id FROM " + TABLE_CUSTOMERS_TYPES, null);
        String[] fromColumns = { "_id" };
        int[] toViews = { android.R.id.text1 };
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorCustomersTypes, fromColumns, toViews, 0);
        spinner.setAdapter(cursorAdapter);


    }
}