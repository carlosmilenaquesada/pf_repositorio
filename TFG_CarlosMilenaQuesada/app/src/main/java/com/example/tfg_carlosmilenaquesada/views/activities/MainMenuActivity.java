package com.example.tfg_carlosmilenaquesada.views.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.models.User;
import com.example.tfg_carlosmilenaquesada.views.loaders.SalesLoaderActivity;

public class MainMenuActivity extends AppCompatActivity {

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

        User user = (User) getIntent().getSerializableExtra(LoginActiviy.USER);

        switch (user.getPrivileges()) {
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
        ((TextView) findViewById(R.id.tvGreetingUser)).setText("¡Hola, " + user.getUserId() + "!");

        findViewById(R.id.btNewSale).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, SalesLoaderActivity.class)));
        //findViewById(R.id.btCustomersManagement).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, null)));
        //findViewById(R.id.btSalesManagement).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, null)));
        //findViewById(R.id.btCashDeskManagement).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, null)));
        //findViewById(R.id.btLogOut).setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, null)));
    }
}