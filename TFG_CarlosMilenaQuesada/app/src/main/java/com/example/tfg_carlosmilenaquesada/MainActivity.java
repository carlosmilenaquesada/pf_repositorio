package com.example.tfg_carlosmilenaquesada;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.database.DbHelper;
import com.example.tfg_carlosmilenaquesada.database.UsersHttpClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView actvUser;
    EditText etPassword;
    CheckBox cbRememberUser;
    CheckBox cbRememberPassword;

    Button btLogOn;

    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        UsersHttpClient myHttpClient = new UsersHttpClient(this);
        myHttpClient.getUsersFromServer();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        actvUser = findViewById(R.id.actvUser);
        etPassword = findViewById(R.id.etPassword);
        cbRememberUser = findViewById(R.id.cbRememberUser);
        cbRememberPassword = findViewById(R.id.cbRememberPassword);
        btLogOn = findViewById(R.id.btLogOn);


        cbRememberPassword.setEnabled(false);
        cbRememberUser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cbRememberPassword.setEnabled(isChecked);
            if (!isChecked) {
                cbRememberPassword.setChecked(false);
            }
        });


        dbHelper = new DbHelper(this);

        ArrayList<String> ids = new ArrayList<>();
        Cursor cursor = dbHelper.getUsersIds();
        while (cursor.moveToNext()) {
            ids.add(cursor.getString(0));
        }
        actvUser.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ids));


        btLogOn.setOnClickListener(v -> {
            try {
                if (dbHelper.isValidUser(actvUser.getText().toString(), etPassword.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Usuario encontrado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario No encontrado", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
            }

        });


    }
}