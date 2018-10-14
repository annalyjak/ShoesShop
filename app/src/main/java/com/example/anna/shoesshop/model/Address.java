package com.example.anna.shoesshop.model;

import io.realm.RealmObject;

public class Address extends RealmObject {
    private String country;
    private String city;
    private String ZIPcode;
    private String street;

    public Address() {
    }
}
