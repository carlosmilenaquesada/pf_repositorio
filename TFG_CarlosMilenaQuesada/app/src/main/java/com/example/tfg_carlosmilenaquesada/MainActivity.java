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
    EditText etUser;
    EditText etPassword;
    Button btLogOn;

    DbHelper dbHelper;

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

        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btLogOn = findViewById(R.id.btLogOn);


        dbHelper = new DbHelper(this);


        btLogOn.setOnClickListener(v -> {
            if (etUser.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Debe introducir usuario y password", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                if (!dbHelper.isValidUser(etUser.getText().toString(), etPassword.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Usuario No encontrado", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Usuario encontrado", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
            }

        });


    }
}