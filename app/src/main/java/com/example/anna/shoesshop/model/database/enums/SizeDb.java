package com.example.anna.shoesshop.model.database.enums;

import android.support.annotation.NonNull;

import com.example.anna.shoesshop.model.product.Size;

import io.realm.RealmObject;

public class SizeDb extends RealmObject {

    private String size;

    public SizeDb() {
        size = "universal";
    }

    public SizeDb(String size) {
        this.size = size;
    }

    public SizeDb(Size size) {
        this.size = size.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return size;
    }
}
