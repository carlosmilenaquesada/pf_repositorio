package com.example.tfg_carlosmilenaquesada.views.activities;



import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.example.tfg_carlosmilenaquesada.models.User;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.FutureTaskController;

public class MainActivity extends AppCompatActivity {

    public static final String USER = "com.example.tfg_carlosmilenaquesada.mainactivity.user";
    private static DbHelper dbHelper;

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

        dbHelper = new DbHelper(getApplication());
        new FutureTaskController(getApplication()).tableUsersFiller();


        EditText etUser = findViewById(R.id.etUser);
        EditText etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btLogOn).setOnClickListener(v -> {
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
                intent.putExtra(USER, user);
                startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public static DbHelper getDbHelper() {
        return MainActivity.dbHelper;
    }
}