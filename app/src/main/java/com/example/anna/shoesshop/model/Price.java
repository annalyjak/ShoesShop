package com.example.anna.shoesshop.model;

import android.annotation.SuppressLint;

import io.realm.RealmObject;

import static java.lang.String.format;

public class Price extends RealmObject {
    private double normalPrice;
    private double actuallPrice;
    private String currency;

    public Price() {
    }

    public Price(double normalPrice, String currency) {
        this.normalPrice = normalPrice;
        this.currency = currency;
        this.actuallPrice = normalPrice;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return format("%.2f", normalPrice) + " " + currency;
    }
}
