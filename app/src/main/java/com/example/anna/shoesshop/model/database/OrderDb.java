package com.example.anna.shoesshop.model.database;

import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.database.enums.StatusDb;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class OrderDb extends RealmObject {
    private long numberOfOrder;
    private RealmList<ProductDb> products;

    private Address address;
    private ClientDb client;
    private Date dateOfOrder;

    private Date dateOfDelivery;
    private DeliveryDb deliveryInformation;

    private StatusDb statusOfOrder;

    public OrderDb() {
    }

    public OrderDb(long numberOfOrder, RealmList<ProductDb> products, Address address, ClientDb client, Date dateOfOrder, DeliveryDb deliveryInformation, StatusDb statusOfOrder) {
        this.numberOfOrder = numberOfOrder;
        this.products = products;
        this.address = address;
        this.client = client;
        this.dateOfOrder = dateOfOrder;
        this.deliveryInformation = deliveryInformation;
        this.statusOfOrder = statusOfOrder;
    }

    public long getNumberOfOrder() {
        return numberOfOrder;
    }

    public RealmList<ProductDb> getProducts() {
        return products;
    }

    public Address getAddress() {
        return address;
    }

    public ClientDb getClient() {
        return client;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public Date getDateOfDelivery() {
        return dateOfDelivery;
    }

    public DeliveryDb getDeliveryInformation() {
        return deliveryInformation;
    }

    public StatusDb getStatusOfOrder() {
        return statusOfOrder;
    }
}
