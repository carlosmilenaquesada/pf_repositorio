package com.example.tfg_carlosmilenaquesada;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
import com.example.tfg_carlosmilenaquesada.database.MyHttpClient;

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
        MyHttpClient myHttpClient = new MyHttpClient(this);
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
        btLogOn.setOnClickListener(v -> {
            try{
                if(dbHelper.isValidUser(actvUser.getText().toString(), etPassword.getText().toString())){
                    Toast.makeText(MainActivity.this, "Usuario encontrado", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario No encontrado", Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
            }

        });



    }
}