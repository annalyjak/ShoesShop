package com.example.anna.shoesshop.model.product;

import android.media.Image;

import com.example.anna.shoesshop.model.Price;

import java.math.BigInteger;
import java.util.List;

public class Product {
    //generalForSegregation
    private Type typeOfProduct;
    private Category category;
    private Collection typeOfCollection;
    //generalInfo
    private BigInteger numberOfProduct;
    private Price price;
    private String name;
    private List<Image> pictures;
    //details
    private List<Size> listOfSizes;
    private Size selectedSize = Size.universal;

    public Price getPrice() {
        return price;
    }
}
