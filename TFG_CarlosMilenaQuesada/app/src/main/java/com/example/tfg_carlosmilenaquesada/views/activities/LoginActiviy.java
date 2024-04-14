package com.example.tfg_carlosmilenaquesada.views.activities;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.DbHelper;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.models.User;

public class LoginActiviy extends AppCompatActivity {
    public static final String USER = "com.example.tfg_carlosmilenaquesada.mainactivity.user";
    EditText etUser;
    EditText etPassword;
    Button btLogOn;

    //he añadido android:usesCleartextTraffic="true" al manifest para eludir la seguridad de http
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activiy_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btLogOn = findViewById(R.id.btLogOn);
        btLogOn.setOnClickListener(v -> {
            if (etUser.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                Toast.makeText(LoginActiviy.this, "Debe introducir usuario y password", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                User user = DbHelper.getInstance(getApplication()).getValidUser(etUser.getText().toString(), etPassword.getText().toString());
                if (user == null) {
                    Toast.makeText(LoginActiviy.this, "Usuario No encontrado", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(LoginActiviy.this, "Usuario encontrado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActiviy.this, MainMenuActivity.class);
                intent.putExtra(USER, user);
                startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(LoginActiviy.this, ex.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }


}