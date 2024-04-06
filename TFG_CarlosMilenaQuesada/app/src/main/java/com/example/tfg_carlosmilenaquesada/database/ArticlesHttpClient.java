package com.example.tfg_carlosmilenaquesada.database;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ArticlesHttpClient {

    private Context context;

    public ArticlesHttpClient(Context context) {
        this.context = context;
    }

    public void getArticlesFromServer() {
        String url = "http://192.168.0.3:3000/sync/articles";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        DbHelper dbHelper = new DbHelper(context);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject userJson = response.getJSONObject(i);
                            dbHelper.insertUser(new User(userJson.getString("id"), userJson.getString("password"), userJson.getString("privileges")));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context,"Error al procesar la respuesta JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Error en la solicitud HTTP", Toast.LENGTH_SHORT).show();
                    }
                });

        // Añadir la solicitud a la cola
        queue.add(jsonArrayRequest);
    }
}
