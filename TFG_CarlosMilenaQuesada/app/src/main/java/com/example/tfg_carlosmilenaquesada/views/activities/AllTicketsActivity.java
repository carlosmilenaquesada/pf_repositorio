package com.example.tfg_carlosmilenaquesada.views.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.models.Ticket;
import com.example.tfg_carlosmilenaquesada.models.TicketAdapter;

public class AllTicketsActivity extends AppCompatActivity {

    RecyclerView rvAllTickets;
    Button btBackFromAllTicketsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_tickets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvAllTickets = findViewById(R.id.rvAllTickets);
        btBackFromAllTicketsActivity = findViewById(R.id.btBackFromAllTicketsActivity);
        rvAllTickets.setLayoutManager(new LinearLayoutManager(this));
        rvAllTickets.setAdapter(new TicketAdapter(AllTicketsActivity.this));
        new ItemTouchHelper(((TicketAdapter) rvAllTickets.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvAllTickets);
        Cursor cursor = SqliteConnector.getInstance(this).getReadableDatabase().rawQuery(
                "SELECT * FROM " + SqliteConnector.TABLE_TICKETS, null
        );
        while (cursor.moveToNext()) {
            ((TicketAdapter) rvAllTickets.getAdapter()).addTicket(
                    new Ticket(
                            cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    ),
                    rvAllTickets.getAdapter().getItemCount()
            );
        }
        btBackFromAllTicketsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllTicketsActivity.this, SalesManagementMenuActivity.class));
            }
        });

    }


}