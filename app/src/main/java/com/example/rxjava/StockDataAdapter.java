package com.example.rxjava;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxjava.databinding.StockUpdateItemBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StockDataAdapter extends RecyclerView.Adapter<StockDataAdapter.StockUpdateViewHolder> {
    private final List<StockUpdate> data = new ArrayList<>();

    @Override
    public StockUpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        StockUpdateItemBinding binding = StockUpdateItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StockUpdateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StockUpdateViewHolder holder, int position) {
        StockUpdate val = data.get(position);
        holder.bind(val);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(StockUpdate stockSymbol){
        this.data.add(stockSymbol);
        notifyItemInserted(data.size() -1);
    }



    public class StockUpdateViewHolder extends RecyclerView.ViewHolder {
        StockUpdateItemBinding binding;

        private final NumberFormat PRICE_FORMAT = new DecimalFormat("#0.00");

        public StockUpdateViewHolder(StockUpdateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(StockUpdate val) {

            binding.stockItemSymbol.setText(val.getStockSymbol());
            binding.stockItemPrice.setText(PRICE_FORMAT.format(val.getPrice().floatValue()));
            binding.stockItemDate.setText(DateFormat.format("yyyy-MM-dd hh:mm", val.getDate()));
        }
    }
}


