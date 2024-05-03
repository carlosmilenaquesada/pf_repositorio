package com.example.tfg_carlosmilenaquesada.models.ticket;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.controllers.local_sqlite_manager.SqliteConnector;
import com.example.tfg_carlosmilenaquesada.views.activities.tickets.TicketDetailInterface;
import com.example.tfg_carlosmilenaquesada.views.activities.tickets.TicketRescueInterface;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private Context context;
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
                ((TicketDetailInterface) context).wipeTicketDetails();
            }
        };
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_ticket, parent, false);
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
            ((TicketDetailInterface) context).showTicketDetails(ticket.getTicket_id());
            if(context instanceof TicketRescueInterface){
                ((TicketRescueInterface) context).rescueTicket(ticket);
            }
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
        int deleteResultCount = SqliteConnector.getInstance(context).getReadableDatabase().delete(
                SqliteConnector.TABLE_TICKETS,
                "ticket_id=?",
                new String[]{ticketList.get(position).getTicket_id()
                }
        );
        if (deleteResultCount == 1) {
            ticketList.remove(position);
            notifyItemRemoved(position);
        }else {
            Toast.makeText(context, "Error al borrar el ticket", Toast.LENGTH_LONG).show();
        }
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