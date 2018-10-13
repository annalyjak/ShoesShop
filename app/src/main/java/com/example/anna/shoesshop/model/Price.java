package com.example.anna.shoesshop.model;

import android.annotation.SuppressLint;

import static java.lang.String.format;

public class Price {
    private double normalPrice;
    private double actuallPrice;
    private String currency;


    public Price(double normalPrice, String currency) {
        this.normalPrice = normalPrice;
        this.currency = currency;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return format("%.2f", normalPrice) + " " + currency;
    }
}
