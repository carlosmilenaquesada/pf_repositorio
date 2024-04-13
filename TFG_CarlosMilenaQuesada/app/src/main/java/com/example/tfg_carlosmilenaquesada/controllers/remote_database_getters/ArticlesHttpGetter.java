package com.example.tfg_carlosmilenaquesada.controllers.remote_database_getters;

import static com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.DbHelper.NODE_SERVER;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tfg_carlosmilenaquesada.views.activities.MainActivity;
import com.example.tfg_carlosmilenaquesada.models.Article;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.DbHelper;


import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RequiresApi(api = Build.VERSION_CODES.O)
public class ArticlesHttpGetter {

    private Context context;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));


    public ArticlesHttpGetter(Context context) {
        this.context = context;
    }

    public void getArticlesFromServer() {
        String url = NODE_SERVER + "articles";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        MainActivity.getDbHelper().wipeTable(DbHelper.TABLE_ARTICLES);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject userJson = response.getJSONObject(i);
                            Article article = new Article(
                                    userJson.getString("internal_code"),
                                    userJson.getString("name"),
                                    userJson.getDouble("sale_base_price"),
                                    userJson.getString("vat_fraction"),
                                    userJson.getString("offer_start_date").equalsIgnoreCase("null") ? null : LocalDateTime.parse(userJson.getString("offer_start_date"), formatter),
                                    userJson.getString("offer_end_date").equalsIgnoreCase("null") ? null : LocalDateTime.parse(userJson.getString("offer_end_date"), formatter),
                                    userJson.getString("offer_sale_base_price").equalsIgnoreCase("null") ? null : Double.parseDouble(userJson.getString("offer_sale_base_price"))
                            );
                            MainActivity.getDbHelper().insertArticle(article);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Error al procesar la respuesta JSON de artículos", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(context, "Error en la solicitud HTTP de artículos", Toast.LENGTH_LONG).show());

        queue.add(jsonArrayRequest);
    }
}
