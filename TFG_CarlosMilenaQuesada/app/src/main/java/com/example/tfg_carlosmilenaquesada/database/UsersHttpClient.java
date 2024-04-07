package com.example.tfg_carlosmilenaquesada.database;

import static com.example.tfg_carlosmilenaquesada.database.DbHelper.NODE_SERVER;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.activities.MainActivity;
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
        String url = NODE_SERVER + "users";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        MainActivity.getDbHelper().wipeTable(DbHelper.TABLE_USERS);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject userJson = response.getJSONObject(i);
                            User user = new User(
                                    userJson.getString("id"),
                                    userJson.getString("password"),
                                    userJson.getString("privileges")
                            );
                            MainActivity.getDbHelper().insertUser(user);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context, "Error al procesar la respuesta JSON de usuarios", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(context, "Error en la solicitud HTTP de usuarios", Toast.LENGTH_LONG).show());
        queue.add(jsonArrayRequest);
    }
}

