package com.example.tfg_carlosmilenaquesada.activities;

import static com.example.tfg_carlosmilenaquesada.activities.MainActivity.USER;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.database.ArticlesHttpClient;
import com.example.tfg_carlosmilenaquesada.database.BarcodesHttpClient;
import com.example.tfg_carlosmilenaquesada.database.CustomersTypesHttpClient;
import com.example.tfg_carlosmilenaquesada.models.User;

public class SecondActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        User user = (User) getIntent().getSerializableExtra(USER);

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
        ((TextView) findViewById(R.id.tvGreetingUser)).setText("¡Hola, " + user.getId() + "!");


        ArticlesHttpClient articlesHttpClient = new ArticlesHttpClient(this);
        articlesHttpClient.getArticlesFromServer();

        BarcodesHttpClient barcodesHttpClient = new BarcodesHttpClient(this);
        barcodesHttpClient.getBarcodesFromServer();

        CustomersTypesHttpClient customersTypesHttpClient = new CustomersTypesHttpClient(this);
        customersTypesHttpClient.getCustomersTypesFromServer();


        findViewById(R.id.btNewSale).setOnClickListener(v -> startActivity(new Intent(SecondActivity.this, SaleActivity.class)));
        findViewById(R.id.btCustomersManagement).setOnClickListener(v -> startActivity(new Intent(SecondActivity.this, null)));
        findViewById(R.id.btSalesManagement).setOnClickListener(v -> startActivity(new Intent(SecondActivity.this, null)));
        findViewById(R.id.btCashDeskManagement).setOnClickListener(v -> startActivity(new Intent(SecondActivity.this, null)));
        findViewById(R.id.btLogOut).setOnClickListener(v -> startActivity(new Intent(SecondActivity.this, null)));
    }
}