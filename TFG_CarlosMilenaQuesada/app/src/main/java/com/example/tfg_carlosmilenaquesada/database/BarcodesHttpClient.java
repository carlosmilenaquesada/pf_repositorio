package com.example.tfg_carlosmilenaquesada.database;

import static com.example.tfg_carlosmilenaquesada.database.DbHelper.NODE_SERVER;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.activities.MainActivity;
import com.example.tfg_carlosmilenaquesada.models.Article;
import com.example.tfg_carlosmilenaquesada.models.Barcode;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class BarcodesHttpClient {

    private Context context;

    public BarcodesHttpClient(Context context) {
        this.context = context;
    }

    public void getBarcodesFromServer() {
        String url = NODE_SERVER + "barcodes";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        MainActivity.getDbHelper().wipeTable(DbHelper.TABLE_BARCODES);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject userJson = response.getJSONObject(i);
                            Barcode barcode = new Barcode(
                                    userJson.getString("internal_code"),
                                    userJson.getString("barcode")
                            );
                            MainActivity.getDbHelper().insertBarcode(barcode);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Error al procesar la respuesta JSON de códigos de barras", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(context, "Error en la solicitud HTTP de códigos de barras", Toast.LENGTH_LONG).show());

        queue.add(jsonArrayRequest);
    }
}
