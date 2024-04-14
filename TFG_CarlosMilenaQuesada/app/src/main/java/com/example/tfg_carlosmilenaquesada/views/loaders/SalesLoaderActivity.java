package com.example.tfg_carlosmilenaquesada.views.loaders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.views.activities.MainMenuActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity;

public class SalesLoaderActivity extends AppCompatActivity {
    ProgressBar pbSalesLoader;

    boolean dataIsLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sales_loader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pbSalesLoader = findViewById(R.id.pbSalesLoader);

        dataIsLoaded = false;

        SqliteConnector.getInstance(getApplication());

        JsonHttpGetter jsonHttpGetterArticles = new JsonHttpGetter(getApplication(), SqliteConnector.TABLE_ARTICLES);
        jsonHttpGetterArticles.getJsonFromHttp();
        JsonHttpGetter jsonHttpGetterBarcodes = new JsonHttpGetter(getApplication(), SqliteConnector.TABLE_BARCODES);
        jsonHttpGetterBarcodes.getJsonFromHttp();
        JsonHttpGetter jsonHttpGetterCustomersTypes = new JsonHttpGetter(getApplication(), SqliteConnector.TABLE_CUSTOMERS_TYPES);
        jsonHttpGetterCustomersTypes.getJsonFromHttp();


        new Thread() {
            @Override
            public void run() {
                while (!jsonHttpGetterArticles.isDone() || !jsonHttpGetterBarcodes.isDone() || !jsonHttpGetterCustomersTypes.isDone()) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                dataIsLoaded = true;
                startActivity(new Intent(SalesLoaderActivity.this, SaleActivity.class));

            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataIsLoaded) {
            startActivity(new Intent(SalesLoaderActivity.this, MainMenuActivity.class));
        }

    }
}