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
import com.example.tfg_carlosmilenaquesada.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsersHttpClient {

    private Context context;

    public UsersHttpClient(Context context) {
        this.context = context;
    }

    public void getUsersFromServer() {
        String url = "http://192.168.0.3:3000/sync/users";
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
                error -> Toast.makeText(context,"Error en la solicitud HTTP", Toast.LENGTH_SHORT).show());
        queue.add(jsonArrayRequest);
    }
}

