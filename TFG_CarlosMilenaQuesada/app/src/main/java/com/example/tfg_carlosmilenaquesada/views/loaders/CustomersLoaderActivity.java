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
import com.example.tfg_carlosmilenaquesada.views.activities.LoginActiviy;
import com.example.tfg_carlosmilenaquesada.views.activities.MainMenuActivity;
import com.example.tfg_carlosmilenaquesada.views.activities.customers.CustomersManagementMenuActivity;

public class CustomersLoaderActivity extends AppCompatActivity {
    ProgressBar pbCustomersLoader;
    boolean dataIsLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customers_loader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pbCustomersLoader = findViewById(R.id.pbCustomersLoader);
        dataIsLoaded = false;
        SqliteConnector.getInstance(getApplication());
        JsonHttpGetter jsonHttpGetter = new JsonHttpGetter(getApplication(), SqliteConnector.TABLE_CUSTOMERS);
        jsonHttpGetter.getJsonFromHttp();

        new Thread() {
            @Override
            public void run() {
                while (!jsonHttpGetter.isDone()) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                dataIsLoaded = true;
                startActivity(new Intent(CustomersLoaderActivity.this, CustomersManagementMenuActivity.class));
            }
        }.start();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(dataIsLoaded){
            startActivity(new Intent(CustomersLoaderActivity.this, MainMenuActivity.class));
        }

    }
}