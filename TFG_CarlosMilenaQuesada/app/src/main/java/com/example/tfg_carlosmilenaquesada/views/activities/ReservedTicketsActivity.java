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
import com.example.tfg_carlosmilenaquesada.models.ticket.Ticket;
import com.example.tfg_carlosmilenaquesada.models.ticket.TicketAdapter;

public class ReservedTicketsActivity extends AppCompatActivity {
    RecyclerView rvTicketsInReserve;
    Button btBackFromReservedTicketsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reserved_tickets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvTicketsInReserve = findViewById(R.id.rvTicketsInReserve);
        btBackFromReservedTicketsActivity =findViewById(R.id.btBackFromReservedTicketsActivity);
        rvTicketsInReserve.setLayoutManager(new LinearLayoutManager(this));
        rvTicketsInReserve.setAdapter(new TicketAdapter(ReservedTicketsActivity.this));
        new ItemTouchHelper(((TicketAdapter) rvTicketsInReserve.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvTicketsInReserve);
        Cursor cursor = SqliteConnector.getInstance(this).getReadableDatabase().rawQuery(
                "SELECT * FROM " + SqliteConnector.TABLE_TICKETS + " WHERE ticket_status_id = ?",
                new String[]{"reserved"}
        );
        while (cursor.moveToNext()) {
            ((TicketAdapter) rvTicketsInReserve.getAdapter()).addTicket(
                    new Ticket(
                            cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                    ),
                    rvTicketsInReserve.getAdapter().getItemCount()
            );
        }
        btBackFromReservedTicketsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReservedTicketsActivity.this, SalesManagementMenuActivity.class));
            }
        });

    }
}