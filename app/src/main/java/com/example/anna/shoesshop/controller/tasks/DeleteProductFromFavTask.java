package com.example.anna.shoesshop.controller.tasks;

import android.app.Activity;

import com.example.anna.shoesshop.controller.adapters.CategoriesCardAdapter;
import com.example.anna.shoesshop.controller.adapters.FavouritesAdapter;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

public class DeleteProductFromFavTask extends ProgressTask<Product> {

    public DeleteProductFromFavTask(FavouritesAdapter adapter, Activity activity) {
        super(adapter,activity);
        this.dialog.setTitle("Proszę chwilę poczekać...");
        this.dialog.setMessage("Usuwam produkt z ulubionych...");
    }

    @Override
    protected Void doInBackground(Product... products) {
        LocalDatabase localDatabase = new LocalDatabase(activity);
        for (Product product : products) {
            localDatabase.removeFromFavourites(product);
        }
        return null;
    }
}
