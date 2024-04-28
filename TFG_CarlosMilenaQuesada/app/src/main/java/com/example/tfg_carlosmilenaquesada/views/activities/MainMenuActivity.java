package com.example.tfg_carlosmilenaquesada.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.views.loaders.SalesLoaderActivity;

public class MainMenuActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = getSharedPreferences(LoginActiviy.SHARED_PREFS, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString(LoginActiviy.USER_ID, null);
        String userPassword = sharedPreferences.getString(LoginActiviy.USER_PASSWORD, null);
        String userPrivileges = sharedPreferences.getString(LoginActiviy.USER_PRIVILEGES, null);
        if (userId == null || userPassword == null || userPrivileges == null) {
            Toast.makeText(MainMenuActivity.this, "No tiene permiso para acceder a esta sección", Toast.LENGTH_LONG).show();
            return;
        }
        switch (userPrivileges) {
            case "basic":
                findViewById(R.id.btSalesManagement).setVisibility(View.GONE);
                findViewById(R.id.btCashDeskManagement).setVisibility(View.GONE);
                findViewById(R.id.btCustomersManagement).setVisibility(View.GONE);
                break;
            case "manager":
                findViewById(R.id.btSalesManagement).setVisibility(View.GONE);
                findViewById(R.id.btCashDeskManagement).setVisibility(View.GONE);
                break;
            case "super":
                break;
        }
        ((TextView) findViewById(R.id.tvGreetingUser)).setText("¡Hola, " + userId + "!");

        findViewById(R.id.btNewSale).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, SalesLoaderActivity.class)));
        //findViewById(R.id.btCustomersManagement).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, null)));
        findViewById(R.id.btSalesManagement).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, SalesManagementMenuActivity.class)));
        //findViewById(R.id.btCashDeskManagement).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, null)));
        findViewById(R.id.btLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(MainMenuActivity.this, "Ha cerrado la sesión", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainMenuActivity.this, LoginActiviy.class));
            }
        });
    }
}