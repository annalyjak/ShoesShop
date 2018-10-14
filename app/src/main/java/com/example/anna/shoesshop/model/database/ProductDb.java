package com.example.anna.shoesshop.model.database;

import android.media.Image;

import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.database.enums.CategoryDb;
import com.example.anna.shoesshop.model.database.enums.CollectionDb;
import com.example.anna.shoesshop.model.database.enums.SizeDb;
import com.example.anna.shoesshop.model.database.enums.TypeDb;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ProductDb extends RealmObject {
    //generalForSegregation
    private TypeDb typeOfProduct;
    private CategoryDb category;
    private CollectionDb typeOfCollection;
    //generalInfo
    private long numberOfProduct; //TODO test
    private Price price;
    private String name;
    private RealmList<byte[]> pictures; //TODO test
    //details
    private RealmList<SizeDb> listOfSizes;
    private SizeDb selectedSize = new SizeDb("universal");
}
