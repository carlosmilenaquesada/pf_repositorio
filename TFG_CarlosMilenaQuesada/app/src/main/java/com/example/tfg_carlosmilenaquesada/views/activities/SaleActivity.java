package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_CUSTOMERS;
import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector.TABLE_CUSTOMERS_TYPES;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters.JsonHttpGetter;
import com.example.tfg_carlosmilenaquesada.models.ArticleLine;
import com.example.tfg_carlosmilenaquesada.models.ArticleLineAdapter;
import com.example.tfg_carlosmilenaquesada.views.loaders.SalesLoaderActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends AppCompatActivity {
    public static final String TICKET_AMOUNT = "com.example.tfg_carlosmilenaquesada.views.activities.saleactivity.ticket_amount";
    public static final String ARTICLE_LINES_LIST = "com.example.tfg_carlosmilenaquesada.views.activities.saleactivity.article_lines_list";
    Spinner spCustomersTypes;
    AutoCompleteTextView actvCustomerId;

    EditText etndArticleQuantity;
    EditText etArticleCode;

    Button btOpenScanner;
    Button btPutArticle;

    RecyclerView rvArticlesOnTicket;
    TextView tvTicketTotalAmount;
    Button btPayTicket;
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

        tvTicketTotalAmount = findViewById(R.id.tvTicketTotalAmount);
        spCustomersTypes = findViewById(R.id.spCustomersTypes);
        actvCustomerId = findViewById(R.id.actvCustomerId);
        btOpenScanner = findViewById(R.id.btOpenScanner);
        etndArticleQuantity = findViewById(R.id.etndArticleQuantity);
        etArticleCode = findViewById(R.id.etArticleCode);
        btPutArticle = findViewById(R.id.btPutArticle);
        rvArticlesOnTicket = findViewById(R.id.rvArticlesOnTicket);
        btPayTicket = findViewById(R.id.btPayTicket);

        if (tvTicketTotalAmount.getText().toString().isEmpty()) {
            tvTicketTotalAmount.setText("0.00");
        }


        Cursor cursorCustomersTypes = SqliteConnector.getInstance(getApplication()).getReadableDatabase().rawQuery("SELECT description as _id FROM " + TABLE_CUSTOMERS_TYPES, null);
        String[] fromColumns = {"_id"};
        int[] toViews = {android.R.id.text1};
        CursorAdapter cursorAdapterCustomersTypes = new SimpleCursorAdapter(SaleActivity.this, android.R.layout.simple_spinner_item, cursorCustomersTypes, fromColumns, toViews, 0);
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

        IntentIntegrator intentIntegrator = new IntentIntegrator(SaleActivity.this);
        //Define el tipo de código de de barras que se pretenden scanear.
        //En este caso, voy a elegir códigos de barras PRODUCT_CODE_TYPES(los que
        // normalmente
        // usan los productos comerciales UPC_A, UPC_E, EAN_8, EAN_13, RSS_14)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        //Promp en la pantalla de lector
        intentIntegrator.setPrompt("Lector - CDP");
        //Cámara que va a usarse (delantera, trasera, etc)
        //0 es trasera
        intentIntegrator.setCameraId(0);
        //beep de sonido al escanear
        intentIntegrator.setBeepEnabled(true);
        //
        intentIntegrator.setBarcodeImageEnabled(true);
        //bloquea/desbloquea la orientación del teléfono (he tenido que agregar lo
        // siguiente al manifest:)
				/*  <activity
					android:name="com.journeyapps.barcodescanner.CaptureActivity"
					android:screenOrientation="fullSensor"
					tools:replace="screenOrientation" />* */
        intentIntegrator.setOrientationLocked(false);
        btOpenScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicializa el scaneo
                intentIntegrator.initiateScan();
            }
        });


        btPutArticle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String barcode = String.valueOf(etArticleCode.getText());
                String query = "SELECT A.* FROM " + SqliteConnector.TABLE_ARTICLES + " A JOIN " + SqliteConnector.TABLE_BARCODES + " B ON A.article_id = B.article_id" +
                        " AND B.barcode = '" + barcode + "'";

                Cursor cursor = SqliteConnector.getInstance(SaleActivity.this).getReadableDatabase().rawQuery(query, null);

                if (cursor.moveToNext()) {
                    /*"article_id TEXT PRIMARY KEY NOT NULL, " +
                            "name TEXT  NOT NULL, " +
                            "sale_base_price REAL NOT NULL," +
                            "vat_fraction REAL NOT NULL," +
                            "offer_start_date TEXT," +
                            "offer_end_date TEXT," +
                            "offer_sale_base_price REAL" +
                            ")");*/


                    String articleId = cursor.getString(0);
                    String name = cursor.getString(1);

                    Float unitBasePrice = null;
                    boolean isInOffer = false;
                    String offerStartDate = cursor.getString(4);
                    String offerEndDate = cursor.getString(5);
                    if (offerStartDate != null && offerEndDate != null) {
                        Instant startDateTime = Instant.parse(offerStartDate);
                        Instant endDateTime = Instant.parse(offerEndDate);
                        LocalDateTime currentDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
                        if ((currentDateTime.isEqual(LocalDateTime.ofInstant(startDateTime, ZoneId.systemDefault())) || currentDateTime.isAfter(LocalDateTime.ofInstant(startDateTime, ZoneId.systemDefault())))
                                && (currentDateTime.isEqual(LocalDateTime.ofInstant(endDateTime, ZoneId.systemDefault())) || currentDateTime.isBefore(LocalDateTime.ofInstant(endDateTime, ZoneId.systemDefault())))) {
                            unitBasePrice = cursor.getFloat(6);
                            isInOffer = true;
                        }
                    }
                    if (unitBasePrice == null) {
                        unitBasePrice = cursor.getFloat(2);
                    }
                    float quantity = Float.parseFloat(String.valueOf(etndArticleQuantity.getText()));

                    float vatFraction = cursor.getFloat(3);



                    ArticleLine articleLine = new ArticleLine(articleId, name, unitBasePrice, isInOffer, quantity, vatFraction);

                    ((ArticleLineAdapter) rvArticlesOnTicket.getAdapter()).addArticleLine(articleLine, rvArticlesOnTicket.getAdapter().getItemCount());
                    float totalLineAmount = (unitBasePrice * (1 + vatFraction)) * quantity;
                    float totalAmount = Float.parseFloat(String.valueOf(tvTicketTotalAmount.getText())) + totalLineAmount;
                    tvTicketTotalAmount.setText(String.valueOf(totalAmount));
                } else {
                    Toast.makeText(SaleActivity.this, "El código proporcionado no pertenece a ningún artículo", Toast.LENGTH_LONG).show();
                }

            }
        });


        rvArticlesOnTicket.setLayoutManager(new LinearLayoutManager(this));
        rvArticlesOnTicket.setAdapter(
                new ArticleLineAdapter()
        );
        //Se usa para poder borrar líneas de ticket arrastrándolas a la izquierda
        new ItemTouchHelper(((ArticleLineAdapter) rvArticlesOnTicket.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvArticlesOnTicket);
        btPayTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleActivity.this, PaymentActivity.class);
                intent.putExtra(TICKET_AMOUNT, Float.parseFloat(tvTicketTotalAmount.getText().toString()));
                intent.putExtra(ARTICLE_LINES_LIST, ((ArticleLineAdapter) rvArticlesOnTicket.getAdapter()).getArticleLinesList());
                startActivity(intent);
            }
        });

    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() != null) {
                etArticleCode.setText(intentResult.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}