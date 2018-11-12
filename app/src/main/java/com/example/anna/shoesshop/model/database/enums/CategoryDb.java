package com.example.anna.shoesshop.model.database.enums;

import android.support.annotation.NonNull;

import com.example.anna.shoesshop.model.product.Category;

import io.realm.RealmObject;

public class CategoryDb extends RealmObject {

    private String category;

    public CategoryDb() {
        category = Category.shoes.toString();
    }

    public CategoryDb(String category) {
        this.category = category;
    }

    public CategoryDb(Category category) {
        this.category = category.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return category;
    }
}
