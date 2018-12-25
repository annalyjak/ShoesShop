package com.example.anna.shoesshop.controller.tasks;

import android.app.Activity;
import android.util.Log;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.controller.adapters.CategoriesCardAdapter;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.DialogUtil;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

public class AddProductToFavTask extends ProgressTask<Product> {

    String msg;

    public AddProductToFavTask(CategoriesCardAdapter adapter, Activity activity) {
        super(adapter,activity);
        this.dialog.setTitle("Proszę chwilę poczekać...");
        this.dialog.setMessage("Dodaję produkt do ulubionych...");
    }

    @Override
    protected Void doInBackground(Product... products) {
        LocalDatabase localDatabase = new LocalDatabase(activity);
        for (Product product : products) {
            boolean result = localDatabase.addProductToFavourites(product);
            if (result) {
                MainMenuActivity.getSession().getAccount().addProductToFavourites(product);
                Log.i("TAG", "Produkt " + product.getName() + " został dodany do ulubionych");
            } else {
                msg = "Produkt " + product.getName() + " jest już w ulubionych";
                Log.i("TAG", msg);
            }
        }
        return null;
    }
}
