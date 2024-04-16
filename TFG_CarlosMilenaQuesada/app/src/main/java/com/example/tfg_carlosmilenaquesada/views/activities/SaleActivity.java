package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_CUSTOMERS;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_CUSTOMERS_TYPES;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.views.loaders.SalesLoaderActivity;

import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends AppCompatActivity {
    Spinner spCustomersTypes;
    AutoCompleteTextView actvCustomerId;
    TextView tvVISUALIZADOR;
    EditText etndArticleQuantity;
    EditText etArticleCode;

    Button btPutArticle;

    JsonHttpGetter jsonHttpGetterCustomers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sale);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spCustomersTypes = findViewById(R.id.spCustomersTypes);
        actvCustomerId = findViewById(R.id.actvCustomerId);
        Cursor cursorCustomersTypes = SqliteConnector.getInstance(getApplication()).getReadableDatabase().rawQuery("SELECT description as _id FROM " + TABLE_CUSTOMERS_TYPES, null);
        String[] fromColumns = {"_id"};
        int[] toViews = {android.R.id.text1};
        CursorAdapter cursorAdapterCustomersTypes = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorCustomersTypes, fromColumns, toViews, 0);
        spCustomersTypes.setAdapter(cursorAdapterCustomersTypes);
        spCustomersTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((Cursor) spCustomersTypes.getAdapter().getItem(position)).getString(0).equals("Cliente fiscal")) {
                    actvCustomerId.setVisibility(View.GONE);
                } else {
                    if (jsonHttpGetterCustomers == null) {
                        jsonHttpGetterCustomers = new JsonHttpGetter(getApplication(), SqliteConnector.TABLE_CUSTOMERS);
                        jsonHttpGetterCustomers.getJsonFromHttp();
                    } else {
                        if (!jsonHttpGetterCustomers.isDone()) {
                            jsonHttpGetterCustomers.getJsonFromHttp();
                        }
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            while (!jsonHttpGetterCustomers.isDone()) {
                                try {
                                    Thread.sleep(250);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            Cursor cursorCustomers = SqliteConnector.getInstance(getApplication()).getReadableDatabase().rawQuery("SELECT customer_tax_id FROM " + TABLE_CUSTOMERS, null);
                            ArrayList<String> customersTaxIds = new ArrayList<>();
                            while (cursorCustomers.moveToNext()) {
                                customersTaxIds.add(cursorCustomers.getString(0));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(SaleActivity.this, android.R.layout.simple_dropdown_item_1line, customersTaxIds);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    actvCustomerId.setAdapter(adapter);
                                    actvCustomerId.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }.start();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tvVISUALIZADOR = findViewById(R.id.tvVISUALIZADOR);
        etndArticleQuantity = findViewById(R.id.etndArticleQuantity);
        etArticleCode = findViewById(R.id.etArticleCode);
        btPutArticle = findViewById(R.id.btPutArticle);

        btPutArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float cantidad = Float.parseFloat(String.valueOf(etndArticleQuantity.getText()));
                String codigo = String.valueOf(etArticleCode.getText());
                System.out.println(codigo);
                String sentencia = "SELECT * FROM " + SqliteConnector.TABLE_ARTICLES +" WHERE article_id = '" + codigo+"'";

                Cursor cursor = SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().rawQuery(sentencia, null);
                System.out.println("contador: "+cursor.getCount());

                float precioBase;
                float fraccionIva;
                String nombreArticulo;
                //SELECT A.article_id, A.name, A.sale_base_price, V.vat_fraction, A.offer_start_date, A.offer_end_date, A.offer_sale_base_price FROM articles A LEFT OUTER JOIN vats V ON A.vat_id = V.vat_id';
                if(cursor.moveToNext()){
                    nombreArticulo = cursor.getString(1);

                    precioBase = cursor.getFloat(2);
                    fraccionIva = cursor.getFloat(3);
                    float precioConIva = (precioBase + (precioBase * fraccionIva));
                    String resultado = "nombre: " +nombreArticulo + " - " + "precio con iva: " +  precioConIva + " - " +
                            "cantidad: " + cantidad +" - " + "total: " + precioConIva * cantidad;
                    tvVISUALIZADOR.setText(resultado);
                }








            }
        });


    }

}