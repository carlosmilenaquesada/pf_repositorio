package com.example.tfg_carlosmilenaquesada.database;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyHttpClient {

    private static final String TAG = "errores";
    private Context context;

    public MyHttpClient(Context context) {
        this.context = context;
    }

    public void getUsersFromServer() {
        String url = "http://192.168.0.3:3000/sync/users";
        //he añadido android:usesCleartextTraffic="true" al manifest para eludir la seguridd de http
        // Inicializar la cola de solicitudes de Volley
        RequestQueue queue = Volley.newRequestQueue(context);

        // Crear la solicitud GET para obtener los usuarios
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Procesar la respuesta JSON
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userJson = response.getJSONObject(i);
                                String id = userJson.getString("id");
                                String password = userJson.getString("password");

                                // Aquí puedes guardar los datos en tu base de datos SQLite
                                // Por ejemplo:
                                // myDbHelper.insertUser(id, password);
                                DbHelper dbHelper = new DbHelper(context);
                                dbHelper.insertUser(id, password);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Error al procesar la respuesta JSON", e);
                            showToast("Error al procesar la respuesta JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                        showToast("Error en la solicitud HTTP");
                    }
                });

        // Añadir la solicitud a la cola
        queue.add(jsonArrayRequest);
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

