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
import com.example.anna.shoesshop.controller.fragments.requirements.FavouritesFragment;
import com.example.anna.shoesshop.controller.tasks.DeleteProductFromFavTask;
import com.example.anna.shoesshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class FavouritesCardAdapter extends RecyclerView.Adapter<FavouritesCardAdapter.ViewHolder> {

    private static final String TAG = FavouritesCardAdapter.class.getSimpleName();

    private static List<Product> dataset;
    private static MainMenuActivity activity;
    private FavouritesFragment fragment;

    public FavouritesCardAdapter(MainMenuActivity activity,
                                 List<Product> favProducts,
                                 FavouritesFragment fragment) {
        this.activity =activity;
        dataset = favProducts;
        this.fragment = fragment;
    }

    @Override
    public FavouritesCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.card_fav_products, parent, false);
        return new ViewHolder(v, parent.getContext());
    }
    //TODO add action addToBasket in fav
    @Override
    public void onBindViewHolder(final FavouritesCardAdapter.ViewHolder holder, int position) {
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
        holder.priceNormal.setText(product.getPrice().toString());
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

    private void removeProductFromFavourites(int position) {
        Product product = dataset.get(position);
        dataset.remove(position);
        new DeleteProductFromFavTask(this, activity, fragment).execute(product);
    }

    public boolean hasEmptyFavList() {
        return dataset.isEmpty();
    }

    private Product getActualProduct(int position) {
        return dataset.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int position;
        public TextView name, priceActuall, priceNormal;
        public ImageView picture;
        public ImageButton deleteButton;
        private Context context;
        private FavouritesCardAdapter adapter;

        public ViewHolder(View v, final Context context) {
            super(v);
            this.context = context;
            name = v.findViewById(R.id.name_categ_prod);
            priceActuall = v.findViewById(R.id.categ_actuall_price);
            priceNormal = v.findViewById(R.id.fav_normal_price);
            picture = v.findViewById(R.id.categ_imageView);
            deleteButton = v.findViewById(R.id.imageDelete);

            deleteButton.setOnClickListener(view -> adapter.removeProductFromFavourites(position));
            //TODO add on card click action -> open details
//            v.setOnClickListener(view -> activity.changeViewToProductDetails(
//                    adapter.getActualProduct(position)));
        }
    }

}
