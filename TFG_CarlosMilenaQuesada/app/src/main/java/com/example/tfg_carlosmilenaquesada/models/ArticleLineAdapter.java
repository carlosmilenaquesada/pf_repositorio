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

public class ArticleLineAdapter extends RecyclerView.Adapter<ArticleLineAdapter.ArticleLineViewHolder> {

    private ArrayList<ArticleLine> articleLinesList;


    private SimpleCallback simpleCallback;

    public ArticleLineAdapter() {
        this.articleLinesList = new ArrayList<>();
        simpleCallback = new SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                removeArticleLine(position);
            }
        };



    }

    @NonNull
    @Override
    public ArticleLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item_layout, parent, false);
        return new ArticleLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleLineViewHolder holder, int position) {
        ArticleLine articleLine = articleLinesList.get(position);
        holder.tvItemArticleName.setText(articleLine.getName());
        holder.tvItemArticleUnitPrice.setText(String.valueOf(articleLine.getUnitPrice()));
        holder.tvItemArticleQuantity.setText(String.valueOf(articleLine.getQuantity()));
        holder.tvItemLineTotalAmount.setText(String.valueOf(articleLine.getTotalLineAmount()));

        holder.itemView.setOnClickListener(v -> {
        });
    }


    @Override
    public int getItemCount() {
        return articleLinesList.size();
    }

    public ArrayList<ArticleLine> getArticleLinesList() {
        return this.getArticleLinesList();
    }

    public SimpleCallback getSimpleCallback() {
        return simpleCallback;
    }


    public void removeArticleLine(int position) {
        articleLinesList.remove(position);
        notifyItemRemoved(position);
    }

    public void addArticleLine(ArticleLine articleLine, int position) {
        articleLinesList.add(articleLine);
        notifyItemInserted(position);
    }


    public static class ArticleLineViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemArticleName;
        public TextView tvItemArticleUnitPrice;
        public TextView tvItemArticleQuantity;
        public TextView tvItemLineTotalAmount;


        public ArticleLineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemArticleName = itemView.findViewById(R.id.tvItemArticleName);
            tvItemArticleUnitPrice = itemView.findViewById(R.id.tvItemArticleUnitPrice);
            tvItemArticleQuantity = itemView.findViewById(R.id.tvItemArticleQuantity);
            tvItemLineTotalAmount = itemView.findViewById(R.id.tvItemLineTotalAmount);
        }
    }


}
