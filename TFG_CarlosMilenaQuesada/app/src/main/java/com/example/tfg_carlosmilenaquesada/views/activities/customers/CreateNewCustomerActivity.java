package com.example.tfg_carlosmilenaquesada.views.activities.customers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.tools.Tools;
import com.example.tfg_carlosmilenaquesada.models.customer.Customer;

public class CreateNewCustomerActivity extends AppCompatActivity {

    EditText etCustomerTaxIdCreate;
    EditText etLegalCompanyNameCreate;
    EditText etNameCreate;
    EditText etLegalCompanyAddressCreate;
    EditText etLegalCountryCreate;
    EditText etLegalLocationCreate;
    EditText etLegalZipCodeCreate;
    Button btApplyCustomerCreate;
    Button btWipeFieldCustomerCreate;
    Button btBackFromCreateNewCustomerActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etCustomerTaxIdCreate = findViewById(R.id.etCustomerTaxIdCreate);
        etLegalCompanyNameCreate = findViewById(R.id.etLegalCompanyNameCreate);
        etNameCreate = findViewById(R.id.etNameCreate);
        etLegalCompanyAddressCreate = findViewById(R.id.etLegalCompanyAddressCreate);
        etLegalCountryCreate = findViewById(R.id.etLegalCountryCreate);
        etLegalLocationCreate = findViewById(R.id.etLegalLocationCreate);
        etLegalZipCodeCreate = findViewById(R.id.etLegalZipCodeCreate);
        btApplyCustomerCreate = findViewById(R.id.btApplyCustomerCreate);
        btWipeFieldCustomerCreate = findViewById(R.id.btWipeFieldCustomerCreate);
        btBackFromCreateNewCustomerActivity = findViewById(R.id.btBackFromCreateNewCustomerActivity);

        btApplyCustomerCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer createdCustomer = new Customer(
                        etCustomerTaxIdCreate.getText().toString(),
                        etLegalCompanyNameCreate.getText().toString(),
                        etNameCreate.getText().toString().isEmpty() ? null : etNameCreate.getText().toString(),
                        etLegalCompanyAddressCreate.getText().toString(),
                        etLegalCountryCreate.getText().toString(),
                        etLegalLocationCreate.getText().toString(),
                        etLegalZipCodeCreate.getText().toString()
                );

                ContentValues newContentValues = Tools.getContentValuesFromCustomer(createdCustomer);

                for (String key : newContentValues.keySet()) {
                    if ((newContentValues.get(key) != null && newContentValues.get(key).equals(""))) {
                        Toast.makeText(CreateNewCustomerActivity.this, "Errores en la obtención de datos", Toast.LENGTH_SHORT).show();
                        if (etCustomerTaxIdCreate.getText().toString().isEmpty())
                            etCustomerTaxIdCreate.setError("El id fiscal del cliente es obligatorio");
                        if (etLegalCompanyNameCreate.getText().toString().isEmpty())
                            etLegalCompanyNameCreate.setError("La razón social del cliente es obligatorio");
                        if (etLegalCompanyAddressCreate.getText().toString().isEmpty())
                            etLegalCompanyAddressCreate.setError("La dirección fiscal del cliente es obligatorio");
                        if (etLegalCountryCreate.getText().toString().isEmpty())
                            etLegalCountryCreate.setError("El país fiscal del cliente es obligatorio");
                        if (etLegalLocationCreate.getText().toString().isEmpty())
                            etLegalLocationCreate.setError("La localidad fiscal del cliente es obligatorio");
                        if (etLegalZipCodeCreate.getText().toString().isEmpty())
                            etLegalZipCodeCreate.setError("El código postal fiscal del cliente es obligatorio");
                        return;
                    }
                }
                try {
                    SqliteConnector.getInstance(CreateNewCustomerActivity.this).getReadableDatabase().insertOrThrow(SqliteConnector.TABLE_CUSTOMERS, null, newContentValues);
                    Toast.makeText(CreateNewCustomerActivity.this, "Cliente creado con éxito", Toast.LENGTH_SHORT).show();
                } catch (SQLiteConstraintException E) {
                    Toast.makeText(CreateNewCustomerActivity.this, E.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });
        btBackFromCreateNewCustomerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNewCustomerActivity.this, CustomersManagementMenuActivity.class));
            }
        });
    }
}