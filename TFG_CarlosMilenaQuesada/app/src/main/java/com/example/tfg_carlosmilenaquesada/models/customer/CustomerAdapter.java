package com.example.tfg_carlosmilenaquesada.models.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg_carlosmilenaquesada.R;
import com.example.tfg_carlosmilenaquesada.views.activities.customers.DropdownUpdateMenuCustomerInterface;


import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerItemViewHolder> {
    private ArrayList<Customer> customerItemsList;
    Context context;

    private ItemTouchHelper.SimpleCallback simpleCallback;

    public CustomerAdapter(Context context) {
        this.customerItemsList = new ArrayList<>();
        this.context = context;
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                removeCustomerItem(position);
            }
        };


    }


    @NonNull
    @Override
    public CustomerAdapter.CustomerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_customer, parent, false);
        return new CustomerAdapter.CustomerItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerItemViewHolder holder, int position) {
        Customer customer = customerItemsList.get(position);
        holder.tvItemCustomerTaxId.setText(customer.getCustomer_tax_id());
        holder.tvItemLegalCompanyName.setText(customer.getLegal_company_name());
        holder.tvItemName.setText(customer.getName());
        holder.tvItemLegalCompanyAddress.setText(customer.getLegal_company_address());
        holder.tvItemLegalCountry.setText(customer.getLegal_country());
        holder.tvItemLegalLocation.setText(customer.getLegal_location());
        holder.tvItemLegalZipCode.setText(customer.getLegal_zip_code());

        holder.itemView.setOnClickListener(v -> {
            ((DropdownUpdateMenuCustomerInterface) context).fillDropDownMenuCustomer(customer);
        });
    }


    @Override
    public int getItemCount() {
        return customerItemsList.size();
    }

    public ItemTouchHelper.SimpleCallback getSimpleCallback() {
        return simpleCallback;
    }

    public ArrayList<Customer> getCustomerList() {
        return this.customerItemsList;
    }

    public void removeCustomerItem(int position) {
        customerItemsList.remove(position);
        notifyItemRemoved(position);
    }

    public void addCustomer(Customer customer, int position) {
        customerItemsList.add(customer);
        notifyItemInserted(position);
    }


    public static class CustomerItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemCustomerTaxId;
        public TextView tvItemLegalCompanyName;
        public TextView tvItemName;
        public TextView tvItemLegalCompanyAddress;
        public TextView tvItemLegalCountry;
        public TextView tvItemLegalLocation;
        public TextView tvItemLegalZipCode;


        public CustomerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemCustomerTaxId = itemView.findViewById(R.id.tvItemCustomerTaxId);
            tvItemLegalCompanyName = itemView.findViewById(R.id.tvItemLegalCompanyName);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemLegalCompanyAddress = itemView.findViewById(R.id.tvItemLegalCompanyAddress);
            tvItemLegalCountry = itemView.findViewById(R.id.tvItemLegalCountry);
            tvItemLegalLocation = itemView.findViewById(R.id.tvItemLegalLocation);
            tvItemLegalZipCode = itemView.findViewById(R.id.tvItemLegalZipCode);


        }
    }
}
