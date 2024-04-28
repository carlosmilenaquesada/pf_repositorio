package com.example.tfg_carlosmilenaquesada.views.activities;

import static com.example.tfg_carlosmilenaquesada.models.ticket.TicketAdapter.TICKET;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.models.ticket.Ticket;
import com.example.tfg_carlosmilenaquesada.models.ticket.TicketAdapter;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLine;
import com.example.tfg_carlosmilenaquesada.models.ticket_line.TicketLineAdapter;

public class TicketDetailActivity extends AppCompatActivity {
    RecyclerView rvTicketDetailLines;
    TextView tvTicketDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        Ticket ticket = (Ticket) intent.getSerializableExtra(TICKET);

        rvTicketDetailLines = findViewById(R.id.rvTicketDetailLines);
        tvTicketDetails = findViewById(R.id.tvTicketDetails);
        tvTicketDetails.setText(ticket.getTicket_id());

        rvTicketDetailLines.setLayoutManager(new LinearLayoutManager(this));
        rvTicketDetailLines.setAdapter(new TicketLineAdapter());
        String query = "SELECT * FROM " + SqliteConnector.TABLE_TICKETS_LINES + " WHERE ticket_id = ?";
        Cursor cursor = SqliteConnector.getInstance(TicketDetailActivity.this).getReadableDatabase().rawQuery(query, new String[]{ticket.getTicket_id()});
        System.out.println(cursor.getCount());
        while (cursor.moveToNext()){
            ((TicketLineAdapter) rvTicketDetailLines.getAdapter()).addTicketLineItem(
                    new TicketLine(
                            cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getFloat(4), cursor.getFloat(5), cursor.getString(6),cursor.getFloat(7)
                    ),
                    rvTicketDetailLines.getAdapter().getItemCount()
            );
        }
    }
}














