package com.example.anna.shoesshop.model.database.enums;

import com.example.anna.shoesshop.model.product.Collection;

import io.realm.RealmObject;

public class CollectionDb extends RealmObject {

    private String collection;

    public CollectionDb() {
        collection = Collection.autumn.toString();
    }

    public CollectionDb(String collection) {
        this.collection = collection;
    }


    public CollectionDb(Collection collection) {
        this.collection = collection.toString();
    }

    @Override
    public String toString() {
        return collection;
    }
}
