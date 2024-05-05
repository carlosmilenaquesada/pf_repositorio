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
import com.example.tfg_carlosmilenaquesada.views.activities.MainMenuActivity;

public class CustomersManagementMenuActivity extends AppCompatActivity {
    Button btShowAndModifyCustomers;
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
        btShowAndModifyCustomers = findViewById(R.id.btShowAndModifyCustomers);
        btCreateNewCustomer = findViewById(R.id.btCreateNewCustomer);
        btBackFromCustomersManagementMenuActivity = findViewById(R.id.btBackFromCustomersManagementMenuActivity);
        btShowAndModifyCustomers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomersManagementMenuActivity.this, ShowAndModifyCustomersActivity.class));
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