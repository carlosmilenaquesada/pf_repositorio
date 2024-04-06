package com.example.tfg_carlosmilenaquesada.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.models.User;

public class SecondActivity extends AppCompatActivity {
    User user;
    TextView tvGreetingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = (User) getIntent().getSerializableExtra("user");

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
        ((TextView) findViewById(R.id.tvGreetingUser)).setText("¡Hola, " + user.getName() + "!");


    }
}