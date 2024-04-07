package com.example.tfg_carlosmilenaquesada.activities;

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
import com.example.tfg_carlosmilenaquesada.database.ArticlesHttpClient;
import com.example.tfg_carlosmilenaquesada.database.DbHelper;
import com.example.tfg_carlosmilenaquesada.database.UsersHttpClient;
import com.example.tfg_carlosmilenaquesada.models.User;

public class MainActivity extends AppCompatActivity {
    EditText etUser;
    EditText etPassword;
    Button btLogOn;
    DbHelper dbHelper;
    //he añadido android:usesCleartextTraffic="true" al manifest para eludir la seguridad de http
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UsersHttpClient myUsersHttpClient = new UsersHttpClient(this);
        myUsersHttpClient.getUsersFromServer();

        ArticlesHttpClient myArticlesHttpClient = new ArticlesHttpClient(this);
        myArticlesHttpClient.getArticlesFromServer();

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
                User user = dbHelper.getValidUser(etUser.getText().toString(), etPassword.getText().toString());
                if (user == null) {
                    Toast.makeText(MainActivity.this, "Usuario No encontrado", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Usuario encontrado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }
}