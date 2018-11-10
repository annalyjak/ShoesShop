package com.example.anna.shoesshop.model.database;

import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.database.enums.TypeOfDeliveryDb;
import com.example.anna.shoesshop.model.order.TypeOfDelivery;
import com.example.anna.shoesshop.model.repo.DBUtil;

import io.realm.RealmObject;

public class DeliveryDb extends RealmObject {
    private TypeOfDeliveryDb typeOfDelivery;
    private String deliveryFirmName;
    private String deliveryTime;
    private Price priceOfDelivery;

    public DeliveryDb() {
    }

    public DeliveryDb(TypeOfDeliveryDb typeOfDelivery, String deliveryFirmName, String deliveryTime, Price priceOfDelivery) {
        this.typeOfDelivery = typeOfDelivery;
        this.deliveryFirmName = deliveryFirmName;
        this.deliveryTime = deliveryTime;
        this.priceOfDelivery = priceOfDelivery;
    }

    public DeliveryDb(TypeOfDelivery typeOfDelivery, String deliveryFirmName, String deliveryTime, Price priceOfDelivery) {
        this.typeOfDelivery = DBUtil.transferToEnum(typeOfDelivery);
        this.deliveryFirmName = deliveryFirmName;
        this.deliveryTime = deliveryTime;
        this.priceOfDelivery = priceOfDelivery;
    }

    public TypeOfDeliveryDb getTypeOfDelivery() {
        return typeOfDelivery;
    }

    public String getDeliveryFirmName() {
        return deliveryFirmName;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public Price getPriceOfDelivery() {
        return priceOfDelivery;
    }
}
