package com.example.tfg_carlosmilenaquesada.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;

import java.util.ArrayList;

public class TicketLineItemAdapter extends RecyclerView.Adapter<TicketLineItemAdapter.TicketLineItemViewHolder> {

    private ArrayList<TicketLineItem> ticketLineItemsList;


    private SimpleCallback simpleCallback;

    public TicketLineItemAdapter() {
        this.ticketLineItemsList = new ArrayList<>();
        simpleCallback = new SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                removeTicketLineItem(position);
            }
        };



    }




    @NonNull
    @Override
    public TicketLineItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_line_item_layout, parent, false);
        return new TicketLineItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TicketLineItemViewHolder holder, int position) {
        TicketLineItem ticketLineItem = ticketLineItemsList.get(position);
        holder.tvItemArticleName.setText(ticketLineItem.getArticle_name());
        holder.tvItemArticleUnitPrice.setText(String.valueOf(ticketLineItem.getUnit_sale_base_price()));
        holder.tvItemArticleUnitPriceWithVat.setText(String.valueOf((ticketLineItem.getUnit_sale_base_price() * (1 + ticketLineItem.getVat_fraction()))));
        holder.tvItemArticleQuantity.setText(String.valueOf(ticketLineItem.getArticle_quantity()));
        holder.tvItemLineTotalAmount.setText(String.valueOf((ticketLineItem.getUnit_sale_base_price() * (1 + ticketLineItem.getVat_fraction())) * ticketLineItem.getArticle_quantity()));
        holder.tvItemLineIsInOffer.setText(String.valueOf(ticketLineItem.getIs_in_offer()));
        holder.itemView.setOnClickListener(v -> {
        });
    }


    @Override
    public int getItemCount() {
        return ticketLineItemsList.size();
    }

    public SimpleCallback getSimpleCallback() {
        return simpleCallback;
    }

public ArrayList<TicketLineItem> getTicketLinesList(){
        return this.ticketLineItemsList;
}

    public void removeTicketLineItem(int position) {
        ticketLineItemsList.remove(position);
        notifyItemRemoved(position);
    }

    public void addTicketLineItem(TicketLineItem ticketLineItem, int position) {
        ticketLineItemsList.add(ticketLineItem);
        notifyItemInserted(position);
    }


    public static class TicketLineItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemArticleName;
        public TextView tvItemArticleUnitPrice;
        public TextView tvItemArticleUnitPriceWithVat;
        public TextView tvItemArticleQuantity;
        public TextView tvItemLineTotalAmount;
        public TextView tvItemLineIsInOffer;


        public TicketLineItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemArticleName = itemView.findViewById(R.id.tvItemArticleName);
            tvItemArticleUnitPrice = itemView.findViewById(R.id.tvItemArticleUnitPrice);
            tvItemArticleUnitPriceWithVat = itemView.findViewById(R.id.tvItemArticleUnitPriceWithVat);
            tvItemArticleQuantity = itemView.findViewById(R.id.tvItemArticleQuantity);
            tvItemLineTotalAmount = itemView.findViewById(R.id.tvItemLineTotalAmount);
            tvItemLineIsInOffer = itemView.findViewById(R.id.tvItemLineIsInOffer);
        }
    }


}
