package com.example.anna.shoesshop.controller.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.order.Delivery;

import java.util.ArrayList;

public class DeliveryAdapter extends ArrayAdapter<Delivery> {
    public DeliveryAdapter(Context context, ArrayList<Delivery> deliveryArrayList) {
        super(context, 0, deliveryArrayList);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Delivery deliv = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.delivery_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.delivery_name);
        TextView price = convertView.findViewById(R.id.delivery_price);
        TextView time = convertView.findViewById(R.id.delivery_time);

        name.setText(deliv.getDeliveryFirmName() + " - " + deliv.getTypeOfDelivery());
        price.setText("Koszt: " + deliv.getPriceOfDelivery());
        time.setText("Czas dostawy: " + deliv.getDeliveryTime());

        return convertView;
    }
}
