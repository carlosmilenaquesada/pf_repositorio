package com.example.tfg_carlosmilenaquesada.views.activities.customers;

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
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.views.activities.MainMenuActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.SalesManagementMenuActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.tickets.AllTicketsActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.tickets.ReservedTicketsActivity;

public class CustomersManagementMenuActivity extends AppCompatActivity {
    Button btShowAllCustomers;
    Button btCreateNewCustomer;
    Button btBackFromCustomersManagementMenuActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customers_management_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btShowAllCustomers = findViewById(R.id.btShowAllCustomers);
        btCreateNewCustomer = findViewById(R.id.btCreateNewCustomer);
        btBackFromCustomersManagementMenuActivity = findViewById(R.id.btBackFromCustomersManagementMenuActivity);
        btShowAllCustomers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomersManagementMenuActivity.this, AllCustomersActivity.class));
            }
        });
        btCreateNewCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomersManagementMenuActivity.this, CreateNewCustomerActivity.class));
            }
        });
        btBackFromCustomersManagementMenuActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomersManagementMenuActivity.this, MainMenuActivity.class));
            }
        });
    }
}