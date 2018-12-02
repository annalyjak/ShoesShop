package com.example.anna.shoesshop.controller.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.order.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrdersCardAdapter extends RecyclerView.Adapter<OrdersCardAdapter.ViewHolder> {

    private static List<Order> dataset;

    public OrdersCardAdapter(List<Order> orders) {
        dataset = orders;
    }

    @NonNull
    @Override
    public OrdersCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.card_orders_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrdersCardAdapter.ViewHolder holder, int position) {
        Order order = dataset.get(holder.getAdapterPosition());

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.ROOT);
        String date = format.format(order.getDateOfOrder());

        holder.position = holder.getAdapterPosition();
        holder.orderId.setText(String.format("Zamówienie %s", date));
        holder.orderStatus.setText(order.getStatusOfOrder().toString());
        holder.orderProductsPrice.setText(
                String.format("Cena zamówienia: %s", order.getTotalPriceOfOrder()));
        holder.orderProductsNumber.setText(
                String.format("Liczba produktów: %s", order.getNumberOfProducts()));

    }

    @Override
    public int getItemCount() {
        if(dataset == null) {
            dataset = new ArrayList<>();
            return 0;
        }
        return dataset.size();
    }

    public void addNewOrder(Order order) {
        if(dataset == null){
            dataset = new ArrayList<>();
        }
        dataset.add(order);
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        TextView orderId, orderStatus, orderProductsPrice, orderProductsNumber;

        ViewHolder(View v) {
            super(v);
            orderId = v.findViewById(R.id.name_categ_prod);
            orderStatus = v.findViewById(R.id.textViewStatus);
            orderProductsPrice = v.findViewById(R.id.textViewPrice);
            orderProductsNumber = v.findViewById(R.id.textView11);

        }
    }

}
