package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity.ARTICLE_LINES_LIST;
import static com.example.tfg_carlosmilenaquesada.views.activities.SaleActivity.TICKET_AMOUNT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.models.ArticleLine;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    TextView tvTotal;

    EditText etCash;

    TextView tvChange;

    Button btCompleteCashPayment;

    Button btResetCashPaymentForm;
    Button btCalculateChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        ArrayList<ArticleLine> articleLinesList = (ArrayList<ArticleLine>) intent.getSerializableExtra(ARTICLE_LINES_LIST);
        final Float ticketAmount = (Float) intent.getSerializableExtra(TICKET_AMOUNT);

        tvTotal = findViewById(R.id.tvTotal);
        etCash = findViewById(R.id.etCash);
        tvChange = findViewById(R.id.tvChange);
        btCalculateChange = findViewById(R.id.btCalculateChange);
        btResetCashPaymentForm = findViewById(R.id.btResetCashPaymentForm);
        btCompleteCashPayment = findViewById(R.id.btCompleteCashPayment);

        resetCashPaymentForm(ticketAmount);
        btCalculateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCash.getText().toString().isEmpty()) {
                    etCash.setError("Debe introducir el importe entregado por el cliente");
                    return;
                }
                if(Float.parseFloat(etCash.getText().toString()) < Float.parseFloat(tvTotal.getText().toString())){
                    etCash.setError("El importe entregado no puede ser menor que el importe total");
                    return;
                }
                Float change = Float.parseFloat(etCash.getText().toString()) - Float.parseFloat(tvTotal.getText().toString());
                tvChange.setText(change.toString());
                btCalculateChange.setEnabled(false);
                btCompleteCashPayment.setEnabled(true);
                tvChange.setEnabled(false);

            }
        });
        btResetCashPaymentForm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetCashPaymentForm(ticketAmount);
            }
        });

        btCompleteCashPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "Pago en efectivo realizado", Toast.LENGTH_SHORT).show();
                //guardar en la base de datos

                startActivity(new Intent(PaymentActivity.this, SaleActivity.class));
            }
        });


    }
    private void resetCashPaymentForm(Float ticketAmount){
        tvTotal.setText(ticketAmount.toString());
        etCash.setText("");
        tvChange.setText("");
        tvChange.setEnabled(true);
        btCalculateChange.setEnabled(true);
        btCompleteCashPayment.setEnabled(false);
    }
}