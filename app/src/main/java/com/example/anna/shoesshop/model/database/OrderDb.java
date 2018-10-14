package com.example.anna.shoesshop.model.database;

import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.database.enums.StatusDb;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class OrderDb extends RealmObject {
    private long numberOfOrder; //TODO test
    private RealmList<ProductDb> products;

    private Address address;
    private ClientDb client;
    private Date dateOfOrder; //TODO test

    private Date dateOfDelivery; //TODO test
    private DeliveryDb deliveryInformation;

    private StatusDb statusOfOrder;


}
