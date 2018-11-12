package com.example.anna.shoesshop.controller.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.tasks.AddProductToFavTask;
import com.example.anna.shoesshop.model.product.Product;

import java.util.List;

public class CategoriesCardAdapter extends RecyclerView.Adapter<CategoriesCardAdapter.ViewHolder> {

    public static final int CARD_CATEGORIES_1 = R.layout.card_categories;
    public static final int CARD_CATEGORIES_2 = R.layout.card_categories2;

    private static List<Product> dataset;
    private static MainMenuActivity activity;
    private int valueOfCard;

    public CategoriesCardAdapter(MainMenuActivity activity, List<Product> products) {
        this.activity =activity;
        dataset = products;
        valueOfCard = CARD_CATEGORIES_1;
    }

    public CategoriesCardAdapter(MainMenuActivity activity, List<Product> products, int view) {
        this.activity =activity;
        dataset = products;
        valueOfCard = view;
    }

    @Override
    public CategoriesCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(valueOfCard, parent, false);
        return new ViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final CategoriesCardAdapter.ViewHolder holder, int position) {
        Product product = dataset.get(position);
        Resources resources = activity.getApplicationContext().getResources();

        holder.position = position;
        holder.name.setText(product.getName());
        if(!product.onPromotion()){
            holder.priceActuall.setText(product.getNormalPrice().toString());
            holder.priceNormal.setText(product.getPrice().toString());
        } else {
            holder.priceNormal.setVisibility(View.INVISIBLE);
            holder.priceActuall.setText(product.getPrice().toString());
        }
        holder.picture.setImageBitmap(product.getMainPicture());
        holder.adapter = this;

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addNewProducts(Product product) {
        dataset.add(product);
        this.notifyDataSetChanged();
    }

    private void addProductToFavourites(int position) {
        Product product = dataset.get(position);
        new AddProductToFavTask(this, activity).execute(product);
    }

    private Product getActualProduct(int position) {
        return dataset.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int position;
        TextView name, priceActuall, priceNormal;
        ImageView picture;
        public ImageButton imageButton;
        private Context context;
        private CategoriesCardAdapter adapter;

        ViewHolder(View v, final Context context) {
            super(v);
            this.context = context;
            name = v.findViewById(R.id.name_categ_prod);
            priceActuall = v.findViewById(R.id.categ_actuall_price);
            priceNormal = v.findViewById(R.id.categ_normal_price);
            picture = v.findViewById(R.id.categ_imageView);
            imageButton = v.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(view -> adapter.addProductToFavourites(position));

            v.setOnClickListener(view -> activity.changeViewToProductDetails(
                    adapter.getActualProduct(position)));
        }
    }

}
