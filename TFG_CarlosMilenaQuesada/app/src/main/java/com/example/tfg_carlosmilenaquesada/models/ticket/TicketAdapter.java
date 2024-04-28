package com.example.tfg_carlosmilenaquesada.models.ticket;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.views.activities.TicketDetailActivity;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private Context context;
    public static final String TICKET = "com.example.tfg_carlosmilenaquesada.models.ticket.ticket_adapter.ticket";
    private ArrayList<Ticket> ticketList;
    private ItemTouchHelper.SimpleCallback simpleCallback;

    public TicketAdapter(Context context) {
        this.context = context;
        this.ticketList = new ArrayList<>();
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                removeTicket(position);
            }
        };
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item_layout, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.tvItemTicketId.setText(ticket.getTicket_id());
        holder.tvItemTicketSaleDate.setText(ticket.getSale_date());
        holder.tvItemCustomerTaxId.setText(ticket.getCustomer_tax_id());
        holder.tvItemTicketStatusId.setText(ticket.getTicket_status_id());
        holder.tvItemTicketPaymentMethod.setText(ticket.getPayment_method_id());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TicketDetailActivity.class);
            intent.putExtra(TICKET, ticket);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public ItemTouchHelper.SimpleCallback getSimpleCallback() {

        return simpleCallback;
    }

    public ArrayList<Ticket> getTicketList() {
        return this.ticketList;
    }

    public void removeTicket(int position) {
        ticketList.remove(position);
        SqliteConnector.getInstance(context).getReadableDatabase().delete(
                SqliteConnector.TABLE_TICKETS,
                "ticket_id=?",
                new String[]{ticketList.get(position).getTicket_id()
                }
        );
        notifyItemRemoved(position);
    }

    public void addTicket(Ticket ticket, int position) {
        ticketList.add(ticket);
        notifyItemInserted(position);
    }


    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemTicketId;
        public TextView tvItemTicketSaleDate;
        public TextView tvItemCustomerTaxId;
        public TextView tvItemTicketStatusId;
        public TextView tvItemTicketPaymentMethod;


        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTicketId = itemView.findViewById(R.id.tvItemTicketId);
            tvItemTicketSaleDate = itemView.findViewById(R.id.tvItemTicketSaleDate);
            tvItemCustomerTaxId = itemView.findViewById(R.id.tvItemCustomerTaxId);
            tvItemTicketStatusId = itemView.findViewById(R.id.tvItemTicketStatusId);
            tvItemTicketPaymentMethod = itemView.findViewById(R.id.tvItemTicketPaymentMethod);

        }
    }


}