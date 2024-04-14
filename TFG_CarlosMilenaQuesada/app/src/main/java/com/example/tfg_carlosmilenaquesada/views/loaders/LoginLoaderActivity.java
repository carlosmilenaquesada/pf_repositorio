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

import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.DbHelper;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.views.activities.LoginActiviy;

public class LoginLoaderActivity extends AppCompatActivity {
    ProgressBar pbLoginLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_loader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pbLoginLoader = findViewById(R.id.pbLoginLoader);

        DbHelper.getInstance(getApplication());
        JsonHttpGetter jsonHttpGetter = new JsonHttpGetter(getApplication(), DbHelper.TABLE_USERS);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LoginLoaderActivity.this, LoginActiviy.class));
                    }
                });
            }
        }.start();
    }


}