package com.example.anna.shoesshop.controller.tasks;

import android.app.Activity;

import com.example.anna.shoesshop.controller.adapters.CategoriesCardAdapter;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

public class AddProductToFavTask extends ProgressTask<Product> {

    public AddProductToFavTask(CategoriesCardAdapter adapter, Activity activity) {
        super(adapter,activity);
        this.dialog.setTitle("Proszę chwilę poczekać...");
        this.dialog.setMessage("Dodaję produkt do ulubionych...");
    }

    @Override
    protected Void doInBackground(Product... products) {
        LocalDatabase localDatabase = new LocalDatabase(activity);
        for (Product product : products) {
            localDatabase.addProductToFavourites(product);
        }
        return null;
    }
}
