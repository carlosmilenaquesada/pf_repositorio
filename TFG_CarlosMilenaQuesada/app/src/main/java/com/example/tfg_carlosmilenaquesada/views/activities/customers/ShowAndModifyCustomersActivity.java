package com.example.tfg_carlosmilenaquesada.views.activities.customers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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
import com.example.tfg_carlosmilenaquesada.controllers.tools.Tools;
import com.example.tfg_carlosmilenaquesada.models.customer.Customer;
import com.example.tfg_carlosmilenaquesada.models.customer.CustomerAdapter;

import java.util.ArrayList;


public class ShowAndModifyCustomersActivity extends AppCompatActivity implements DropdownUpdateMenuCustomerInterface {

    RecyclerView rvAllCustomers;

    LinearLayout llUpdateCustomerDropdownMenu;
    Button btBackFromAllCustomersActivity;

    EditText etCustomerTaxIdUpdate;
    EditText etLegalCompanyNameUpdate;
    EditText etNameUpdate;
    EditText etLegalCompanyAddressUpdate;
    EditText etLegalCountryUpdate;
    EditText etLegalLocationUpdate;
    EditText etLegalZipCodeUpdate;
    Button btApplyCustomerUpdate;
    Button btCloseUpdateDropdownMenu;

    Customer oldCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_and_modify_customers);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvAllCustomers = findViewById(R.id.rvAllCustomers);
        rvAllCustomers.setLayoutManager(new LinearLayoutManager(this));
        rvAllCustomers.setAdapter(new CustomerAdapter(this));

        llUpdateCustomerDropdownMenu = findViewById(R.id.llUpdateCustomerDropdownMenu);
        llUpdateCustomerDropdownMenu.setVisibility(View.GONE);
        etCustomerTaxIdUpdate = findViewById(R.id.etCustomerTaxIdUpdate);
        etLegalCompanyNameUpdate = findViewById(R.id.etLegalCompanyNameUpdate);
        etNameUpdate = findViewById(R.id.etNameUpdate);
        etLegalCompanyAddressUpdate = findViewById(R.id.etLegalCompanyAddressUpdate);
        etLegalCountryUpdate = findViewById(R.id.etLegalCountryUpdate);
        etLegalLocationUpdate = findViewById(R.id.etLegalLocationUpdate);
        etLegalZipCodeUpdate = findViewById(R.id.etLegalZipCodeUpdate);
        btApplyCustomerUpdate = findViewById(R.id.btApplyCustomerUpdate);
        btCloseUpdateDropdownMenu = findViewById(R.id.btCloseUpdateDropdownMenu);


        btBackFromAllCustomersActivity = findViewById(R.id.btBackFromAllCustomersActivity);

        new ItemTouchHelper(((CustomerAdapter) rvAllCustomers.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvAllCustomers);
        Cursor cursor = SqliteConnector.getInstance(this).getReadableDatabase().rawQuery("SELECT * FROM " + SqliteConnector.TABLE_CUSTOMERS, null);
        while (cursor.moveToNext()) {
            ((CustomerAdapter) rvAllCustomers.getAdapter()).addCustomer(new Customer(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)), rvAllCustomers.getAdapter().getItemCount());
        }
        btBackFromAllCustomersActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAndModifyCustomersActivity.this, CustomersManagementMenuActivity.class));
            }
        });
        btApplyCustomerUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer updatedCustomer = new Customer(
                        etCustomerTaxIdUpdate.getText().toString(),
                        etLegalCompanyNameUpdate.getText().toString(),
                        etNameUpdate.getText().toString().isEmpty() ? null : etNameUpdate.getText().toString(),
                        etLegalCompanyAddressUpdate.getText().toString(),
                        etLegalCountryUpdate.getText().toString(),
                        etLegalLocationUpdate.getText().toString(),
                        etLegalZipCodeUpdate.getText().toString()
                );

                ContentValues newContentValues = Tools.getContentValuesFromCustomer(updatedCustomer);

                for (String key : newContentValues.keySet()) {
                    if ((newContentValues.get(key) != null && newContentValues.get(key).equals(""))) {
                        System.out.println(key + ": " + newContentValues.get(key));
                        return;
                    }
                }

                if (SqliteConnector.getInstance(ShowAndModifyCustomersActivity.this).getReadableDatabase().update(SqliteConnector.TABLE_CUSTOMERS, newContentValues, "customer_tax_id=?", new String[]{oldCustomer.getCustomer_tax_id()}) == 1) {
                    ArrayList<Customer> customersList = ((CustomerAdapter) rvAllCustomers.getAdapter()).getCustomerList();
                    customersList.set(customersList.indexOf(oldCustomer), updatedCustomer);
                    rvAllCustomers.getAdapter().notifyItemChanged(customersList.indexOf(updatedCustomer));
                }
            }
        });

        btCloseUpdateDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llUpdateCustomerDropdownMenu.getVisibility() != View.GONE) {
                    llUpdateCustomerDropdownMenu.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void fillDropDownMenuCustomer(Customer oldCustomer) {
        this.oldCustomer = oldCustomer;
        if (llUpdateCustomerDropdownMenu.getVisibility() == View.GONE) {
            llUpdateCustomerDropdownMenu.setVisibility(View.VISIBLE);
            etCustomerTaxIdUpdate.setText(oldCustomer.getCustomer_tax_id());
            etLegalCompanyNameUpdate.setText(oldCustomer.getLegal_company_name());
            etNameUpdate.setText(oldCustomer.getName());
            etLegalCompanyAddressUpdate.setText(oldCustomer.getLegal_company_address());
            etLegalCountryUpdate.setText(oldCustomer.getLegal_country());
            etLegalLocationUpdate.setText(oldCustomer.getLegal_location());
            etLegalZipCodeUpdate.setText(oldCustomer.getLegal_zip_code());
        } else {
            llUpdateCustomerDropdownMenu.setVisibility(View.GONE);
        }

    }


}