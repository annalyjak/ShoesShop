package com.example.anna.shoesshop.model.database.enums;

import com.example.anna.shoesshop.model.product.Type;

import io.realm.RealmObject;

public class TypeDb extends RealmObject {
    private String type;

    public TypeDb() {
        type = "women";
    }

    public TypeDb(String type) {
        this.type = type;
    }
    public TypeDb(Type type) {
        this.type = type.toString();
    }

    @Override
    public String toString() {
        return type;
    }
}
