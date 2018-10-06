package com.example.anna.shoesshop.model;

public class Price {
    private double normalPrice;
    private double actuallPrice;
    private String currency;


    public Price(double normalPrice, String currency) {
        this.normalPrice = normalPrice;
        this.currency = currency;
    }
}
