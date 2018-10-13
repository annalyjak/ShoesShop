package com.example.anna.shoesshop.model.order;

import com.example.anna.shoesshop.model.Price;

public class Delivery {
    private TypeOfDelivery typeOfDelivery;
    private String deliveryFirmName;
    private String deliveryTime;
    private Price priceOfDelivery;

    public Delivery(TypeOfDelivery typeOfDelivery, String deliveryFirmName, String deliveryTime, Price priceOfDelivery) {
        this.typeOfDelivery = typeOfDelivery;
        this.deliveryFirmName = deliveryFirmName;
        this.deliveryTime = deliveryTime;
        this.priceOfDelivery = priceOfDelivery;
    }

    public static Delivery createDPDDelivery(){
        return new Delivery(TypeOfDelivery.HomeDelivery,
                "DPD",
                "24H",
                new Price(17.90,"PLN"));
    }

    public static Delivery createPocztaPLDelivery(){
        return new Delivery(TypeOfDelivery.HomeDelivery,
                "Poczta Polska",
                "48H",
                new Price(16.90,"PLN"));
    }

    public static Delivery createPocztaPLinDelivery(){
        return new Delivery(TypeOfDelivery.ParcelPickUp,
                "Poczta Polska",
                "48H",
                new Price(15.90,"PLN"));
    }

    public static Delivery createInShopDelivery(){
        return new Delivery(TypeOfDelivery.ClickAndCollectInStationaryShop,
                "Odbiór w sklepie stacjonarnym",
                "24H",
                new Price(0.00,"PLN"));
    }

    public TypeOfDelivery getTypeOfDelivery() {
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

    @Override
    public String toString() {
        return deliveryFirmName + ": " + typeOfDelivery.toString() +
                "; czas dostawy: " + deliveryTime + "; cena: " + priceOfDelivery;
    }
}
