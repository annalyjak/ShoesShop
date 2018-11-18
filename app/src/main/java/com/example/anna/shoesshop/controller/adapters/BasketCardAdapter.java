package com.example.anna.shoesshop.controller.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.BasketFragment;
import com.example.anna.shoesshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class BasketCardAdapter extends RecyclerView.Adapter<BasketCardAdapter.ViewHolder> {

    private static List<Product> dataset;
    private static Activity activity;
    private BasketFragment fragment;

    public BasketCardAdapter(FragmentActivity activity,
                             List<Product> favProducts,
                             BasketFragment fragment) {
        this.activity =activity;
        dataset = favProducts;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public BasketCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.card_basket, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final BasketCardAdapter.ViewHolder holder, int position) {
        Product product = dataset.get(position);
        Resources resources = activity.getApplicationContext().getResources();

        holder.position = position;
        holder.name.setText(product.getName());
        holder.priceActuall.setText(product.getNormalPrice().toString());
        holder.textViewSize.setText(
                String.format("Rozmiar: %s", product.getSelectedSize().toString()));
        holder.picture.setImageBitmap(product.getMainPicture());
        holder.adapter = this;

    }

    @Override
    public int getItemCount() {
        if(dataset == null) {
            dataset = new ArrayList<>();
            return 0;
        }
        return dataset.size();
    }

    public void addNewProducts(Product product) {
        if(dataset == null){
            dataset = new ArrayList<>();
        }
        dataset.add(product);
        this.notifyDataSetChanged();
    }

    private void removeProductFromOrder(int position) {
        Product product = dataset.get(position);
        dataset.remove(position);
        MainMenuActivity.getSession().removeProductFromBasket(product);
        new AlertDialog.Builder(activity)
                .setTitle("Info")
                .setMessage("Usunięto produkt z koszyka")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> dialog.cancel())
                .show();
        this.notifyDataSetChanged();
    }

    public boolean hasEmptyFavList() {
        return dataset.isEmpty();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        TextView name, priceActuall, textViewSize;
        ImageView picture;
        ImageButton deleteButton;
        Spinner spinnerCount;
        private BasketCardAdapter adapter;

        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name_categ_prod);
            priceActuall = v.findViewById(R.id.categ_actuall_price);
            textViewSize = v.findViewById(R.id.textView_Size);
            picture = v.findViewById(R.id.categ_imageView);
            deleteButton = v.findViewById(R.id.imageDelete);
            spinnerCount = v.findViewById(R.id.spinnerCount);
            spinnerCount.setVisibility(View.INVISIBLE);

            deleteButton.setOnClickListener(view -> adapter.removeProductFromOrder(position));
        }
    }

}
