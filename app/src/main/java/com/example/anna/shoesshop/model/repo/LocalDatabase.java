package com.example.anna.shoesshop.model.repo;

import com.example.anna.shoesshop.model.order.Delivery;

import java.util.ArrayList;

public class LocalDatabase implements DBHelper {

    /**
     * This method creates the standard list of deliver's types with standard prices
     * @return list of Delivery
     */
    public static ArrayList<Delivery> getDeliveryList(){
        ArrayList<Delivery> arrayOfDelivery = new ArrayList<Delivery>();
        arrayOfDelivery.add(Delivery.createDPDDelivery());
        arrayOfDelivery.add(Delivery.createPocztaPLDelivery());
        arrayOfDelivery.add(Delivery.createPocztaPLinDelivery());
        arrayOfDelivery.add(Delivery.createInShopDelivery());
        return arrayOfDelivery;
    }
}
