package com.example.tfg_carlosmilenaquesada.views.activities.customers;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.models.customer.Customer;
import com.example.tfg_carlosmilenaquesada.models.customer.CustomerAdapter;





public class AllCustomersActivity extends AppCompatActivity {

    RecyclerView rvAllCustomers;
    Button btBackFromAllCustomersActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_customers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvAllCustomers = findViewById(R.id.rvAllCustomers);
        rvAllCustomers.setLayoutManager(new LinearLayoutManager(this));
        rvAllCustomers.setAdapter(new CustomerAdapter());

        btBackFromAllCustomersActivity = findViewById(R.id.btBackFromAllCustomersActivity);

        new ItemTouchHelper(((CustomerAdapter) rvAllCustomers.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvAllCustomers);
        Cursor cursor = SqliteConnector.getInstance(this).getReadableDatabase().rawQuery(
                "SELECT * FROM " + SqliteConnector.TABLE_CUSTOMERS, null
        );
        while (cursor.moveToNext()) {
            ((CustomerAdapter) rvAllCustomers.getAdapter()).addCustomer(
                    new Customer(
                            cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)
                    ),
                    rvAllCustomers.getAdapter().getItemCount()
            );
        }
        btBackFromAllCustomersActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllCustomersActivity.this, CustomersManagementMenuActivity.class));
            }
        });
    }


}