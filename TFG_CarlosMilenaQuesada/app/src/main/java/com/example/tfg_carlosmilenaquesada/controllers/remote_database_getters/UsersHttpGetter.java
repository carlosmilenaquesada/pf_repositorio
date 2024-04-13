package com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.DbHelper;
import com.example.tfg_carlosmilenaquesada.views.activities.MainActivity;
import com.example.tfg_carlosmilenaquesada.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UsersHttpGetter {

    Application application;

    public UsersHttpGetter(Application application) {
        this.application = application;
    }

    public void getUsersFromServer() {
        String url = DbHelper.NODE_SERVER + "users";
        RequestQueue queue = Volley.newRequestQueue(application);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
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
                        Toast.makeText(application, "Error al procesar la respuesta JSON de usuarios", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(application, "Error en la solicitud HTTP de usuarios", Toast.LENGTH_LONG).show());
        queue.add(jsonArrayRequest);
    }
}

