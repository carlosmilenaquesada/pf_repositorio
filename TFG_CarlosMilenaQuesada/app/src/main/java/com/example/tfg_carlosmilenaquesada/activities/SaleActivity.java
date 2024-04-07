package com.example.tfg_carlosmilenaquesada.activities;

import static com.example.tfg_carlosmilenaquesada.database.DbHelper.TABLE_CUSTOMERS_TYPES;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.database.DbHelper;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.models.CustomerType;

public class SaleActivity extends AppCompatActivity {
    Spinner spCustomersTypes;

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

        spCustomersTypes = findViewById(R.id.spCustomersTypes);
        Cursor cursorCustomersTypes = MainActivity.getDbHelper().getReadableDatabase().rawQuery("SELECT description as _id FROM " + TABLE_CUSTOMERS_TYPES, null);
        String[] fromColumns = {"_id"};
        int[] toViews = {android.R.id.text1};
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorCustomersTypes, fromColumns, toViews, 0);
        spCustomersTypes.setAdapter(cursorAdapter);
        spCustomersTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int showEtCustomerId = ((Cursor) spCustomersTypes.getAdapter().getItem(position)).getString(0).equals("Cliente fiscal") ?
                        View.VISIBLE : View.GONE;
                findViewById(R.id.etCustomerId).setVisibility(showEtCustomerId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}