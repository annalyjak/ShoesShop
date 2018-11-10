package com.example.anna.shoesshop.model.product;

import android.graphics.Bitmap;
import android.media.Image;

import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.database.enums.CategoryDb;
import com.example.anna.shoesshop.model.database.enums.CollectionDb;
import com.example.anna.shoesshop.model.database.enums.SizeDb;
import com.example.anna.shoesshop.model.database.enums.TypeDb;
import com.example.anna.shoesshop.model.repo.DBUtil;

import java.math.BigInteger;
import java.util.List;

public class Product {
    //generalForSegregation
    private Type typeOfProduct;
    private Category category;
    private Collection typeOfCollection;
    //generalInfo
    private long numberOfProduct;
    private Price price;
    private String name;
    private List<Bitmap> pictures;
    //details
    private List<Size> listOfSizes;
    private Size selectedSize = Size.universal;
    //promo
    private Price normalPrice;

    public Product(Type typeOfProduct,
                     Category category,
                     Collection typeOfCollection,
                     long numberOfProduct,
                     Price price,
                     String name,
                     List<Bitmap> pictures,
                     List<Size> listOfSizes,
                     Size selectedSize,
                     Price normalPrice) {
        this.typeOfProduct = typeOfProduct;
        this.category = category;
        this.typeOfCollection = typeOfCollection;
        this.numberOfProduct = numberOfProduct;
        this.price = price;
        this.name = name;
        this.pictures = pictures;
        this.listOfSizes = listOfSizes;
        this.selectedSize = selectedSize;
        this.normalPrice = normalPrice;
    }

    public Product(TypeDb typeOfProduct,
                   CategoryDb category,
                   CollectionDb typeOfCollection,
                   long numberOfProduct,
                   Price price,
                   String name,
                   List<Bitmap> pictures,
                   List<Size> listOfSizes,
                   SizeDb selectedSize,
                   Price normalPrice) {
        this.typeOfProduct = DBUtil.transferToEnum(typeOfProduct);
        this.category = DBUtil.transferToEnum(category);
        this.typeOfCollection = DBUtil.transferToEnum(typeOfCollection);
        this.numberOfProduct = numberOfProduct;
        this.price = price;
        this.name = name;
        this.pictures = pictures;
        this.listOfSizes = listOfSizes;
        this.selectedSize = DBUtil.transferToEnum(selectedSize);
        this.normalPrice = normalPrice;
    }

    public Price getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Price getNormalPrice() {
        return normalPrice;
    }

    public Bitmap getMainPicture() {
        if(pictures.isEmpty()) {
            return null;
        }
        return pictures.get(0);
    }

    public List<Bitmap> getPictures() {
        return pictures;
    }

    public Type getTypeOfProduct() {
        return typeOfProduct;
    }

    public Category getCategory() {
        return category;
    }

    public Collection getTypeOfCollection() {
        return typeOfCollection;
    }

    public boolean onPromotion() {
        return !(normalPrice == price);
    }
}
