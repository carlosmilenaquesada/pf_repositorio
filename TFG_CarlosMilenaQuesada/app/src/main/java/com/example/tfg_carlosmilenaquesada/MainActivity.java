package com.example.tfg_carlosmilenaquesada;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView actvUsuario;
    EditText etPassword;
    CheckBox cbRecordarUsuario;
    CheckBox cbRecordarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        actvUsuario = findViewById(R.id.actvUsuario);
        etPassword = findViewById(R.id.etPassword);
        cbRecordarUsuario = findViewById(R.id.cbRecordarUsuario);

        cbRecordarPassword = findViewById(R.id.cbRecordarPassword);
        cbRecordarPassword.setEnabled(false);
        cbRecordarUsuario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbRecordarPassword.setEnabled(isChecked);
                if(!isChecked) {
                    cbRecordarPassword.setChecked(false);
                }
            }
        });


    }


}