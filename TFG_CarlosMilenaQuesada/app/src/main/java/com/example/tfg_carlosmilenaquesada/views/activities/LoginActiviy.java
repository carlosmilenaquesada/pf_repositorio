package com.example.tfg_carlosmilenaquesada.views.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.models.User;

public class LoginActiviy extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String USER_ID = "com.example.tfg_carlosmilenaquesada.views.activities.loginactiviy.user_id";
    public static final String USER_PASSWORD = "com.example.tfg_carlosmilenaquesada.views.activities.loginactiviy.user_password";
    public static final String USER_PRIVILEGES = "com.example.tfg_carlosmilenaquesada.views.activities.loginactiviy.user_privileges";
    public static final String SHARED_PREFS = "com.example.tfg_carlosmilenaquesada.views.activities.loginactiviy.shared_prefs";
    EditText etUserId;
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
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        etUserId = findViewById(R.id.etUserId);
        etPassword = findViewById(R.id.etPassword);
        etUserId.setText("super");
        etPassword.setText("super");
        btLogOn = findViewById(R.id.btLogOn);
        btLogOn.setOnClickListener(v -> {
            if (etUserId.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                Toast.makeText(LoginActiviy.this, "Debe introducir usuario y password", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                String userId = etUserId.getText().toString();
                String userPassword = etPassword.getText().toString();
                String userPrivileges = SqliteConnector.getInstance(getApplication()).getUserPrivileges(userId, userPassword);
                if (userPrivileges == null) {
                    Toast.makeText(LoginActiviy.this, "Usuario No encontrado", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(LoginActiviy.this, "Usuario encontrado", Toast.LENGTH_LONG).show();
                sharedpreferences.edit().putString(USER_ID, userId).apply();
                sharedpreferences.edit().putString(USER_PASSWORD, userPassword).apply();
                sharedpreferences.edit().putString(USER_PRIVILEGES, userPrivileges).apply();
                startActivity(new Intent(LoginActiviy.this, MainMenuActivity.class));
            } catch (Exception ex) {
                Toast.makeText(LoginActiviy.this, ex.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }


}