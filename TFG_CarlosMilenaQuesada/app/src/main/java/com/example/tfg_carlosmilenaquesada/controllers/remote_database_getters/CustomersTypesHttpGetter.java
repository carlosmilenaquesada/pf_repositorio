package com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.DbHelper.NODE_SERVER;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.views.activities.LoginActiviy;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.DbHelper;

public class CustomersTypesHttpGetter {
    private final Context context;

    public CustomersTypesHttpGetter(Context context) {
        this.context = context;
    }

    public void getCustomersTypesFromServer() {
        String url = NODE_SERVER + "customers_types";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        DbHelper.getInstance(context).wipeTable(DbHelper.TABLE_CUSTOMERS_TYPES);
                        /*for (int i = 0; i < response.length(); i++) {
                            JSONObject userJson = response.getJSONObject(i);
                            CustomerType customerType = new CustomerType(
                                    userJson.getString("id"),
                                    userJson.getString("description")
                            );
                            LoginActiviy.getDbHelper().insertCustomerType(customerType);
                        }*/
                        DbHelper.getInstance(context).insert(response, DbHelper.TABLE_CUSTOMERS_TYPES);
                    } catch (Exception e) {
                        Toast.makeText(context, "Error al procesar la respuesta JSON de tipos de clientes", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(context, "Error en la solicitud HTTP de tipos de clientes", Toast.LENGTH_LONG).show());

        queue.add(jsonArrayRequest);
    }
}
