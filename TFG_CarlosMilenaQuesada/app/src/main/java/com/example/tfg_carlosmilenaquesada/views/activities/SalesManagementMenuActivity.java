package com.example.tfg_carlosmilenaquesada.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.views.activities.tickets.AllTicketsActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.tickets.ReservedTicketsActivity;

public class SalesManagementMenuActivity extends AppCompatActivity {
    Button btShowReservedTickets;
    Button btShowAllTickets;
    Button btBackFromSaleManagementMenuActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sales_management_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btShowReservedTickets = findViewById(R.id.btShowReservedTickets);
        btShowAllTickets = findViewById(R.id.btShowAllTickets);
        btBackFromSaleManagementMenuActivity = findViewById(R.id.btBackFromSaleManagementMenuActivity);
        btShowReservedTickets.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalesManagementMenuActivity.this, ReservedTicketsActivity.class));
            }
        });
        btShowAllTickets.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalesManagementMenuActivity.this, AllTicketsActivity.class));
            }
        });
        btBackFromSaleManagementMenuActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalesManagementMenuActivity.this, MainMenuActivity.class));
            }
        });

    }
}