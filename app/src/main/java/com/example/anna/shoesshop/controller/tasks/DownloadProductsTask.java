package com.example.anna.shoesshop.controller.tasks;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.example.anna.shoesshop.controller.CategoriesFragment;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

import java.util.List;

public class DownloadProductsTask extends ProgressTask<Void> {

    CategoriesFragment fragment;

    public DownloadProductsTask(RecyclerView.Adapter adapter,
                                Activity activity,
                                CategoriesFragment fragment) {
        super(adapter, activity);
        this.dialog.setTitle("Proszę chwilę poczekać...");
        this.dialog.setMessage("Pobieram dane z bazy danych...");
        this.fragment = fragment;
    }

    //TODO create observable method getAllproducts
    @Override
    protected Void doInBackground(Void... voids) {
        LocalDatabase localDatabase = new LocalDatabase(activity);
//        List<Product> products = localDatabase.getAllProductsObservable();
//        fragment.changeProductList(products);
        return null;
    }
}
