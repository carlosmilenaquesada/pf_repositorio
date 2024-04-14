package com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;

import org.json.JSONArray;

public class JsonHttpGetter {

    Context context;
    String table;

    private boolean done;

    public JsonHttpGetter(Context context, String table) {
        this.context = context;
        this.table = table;
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void getJsonFromHttp() {
        String url = SqliteConnector.NODE_SERVER + table;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("########## " + table + " ##########");
                        SqliteConnector.getInstance(context).wipeTable(table);
                        SqliteConnector.getInstance(context).insert(response, table);
                        setDone(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setDone(true);
            }
        });

        queue.add(jsonArrayRequest);


    }
}

