package com.example.anna.shoesshop.controller.tasks;

import android.app.Activity;

import com.example.anna.shoesshop.controller.FavouritesFragment;
import com.example.anna.shoesshop.controller.adapters.FavouritesCardAdapter;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

public class DeleteProductFromFavTask extends ProgressTask<Product> {

    private FavouritesFragment fragment;

    public DeleteProductFromFavTask(FavouritesCardAdapter adapter,
                                    Activity activity,
                                    FavouritesFragment fragment) {
        super(adapter,activity);
        this.dialog.setTitle("Proszę chwilę poczekać...");
        this.dialog.setMessage("Usuwam produkt z ulubionych...");
        this.fragment = fragment;
    }

    @Override
    protected Void doInBackground(Product... products) {
        LocalDatabase localDatabase = new LocalDatabase(activity);
        for (Product product : products) {
            localDatabase.removeFromFavourites(product);
        }
        return null;
    }

    @Override
    protected void onPostExecute(final Void success) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        adapter.notifyDataSetChanged();
        if (((FavouritesCardAdapter) adapter).hasEmptyFavList()) {
            fragment.setNoProducts();
        }
    }
}
