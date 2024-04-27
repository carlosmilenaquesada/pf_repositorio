package com.example.tfg_carlosmilenaquesada.views.activities;

import android.database.Cursor;
import android.os.Bundle;

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

public class ReserveActivity extends AppCompatActivity {
    RecyclerView rvTicketsInReserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reserve);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvTicketsInReserve = findViewById(R.id.rvTicketsInReserve);
        rvTicketsInReserve.setLayoutManager(new LinearLayoutManager(this));
        rvTicketsInReserve.setAdapter(new TicketAdapter());
        new ItemTouchHelper(((TicketAdapter) rvTicketsInReserve.getAdapter()).getSimpleCallback()).attachToRecyclerView(rvTicketsInReserve);
        Cursor cursor = SqliteConnector.getInstance(this).getReadableDatabase().rawQuery(
                /*"SELECT * FROM " + SqliteConnector.TABLE_TICKETS + " WHERE ticket_status_id = ?",
                new String[]{"reserved"}*/
                "SELECT * FROM " + SqliteConnector.TABLE_TICKETS, null
        );
        while (cursor.moveToNext()) {
            Ticket ticket = new Ticket(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            ((TicketAdapter) rvTicketsInReserve.getAdapter()).addTicket(ticket, rvTicketsInReserve.getAdapter().getItemCount());
        }
    }
}