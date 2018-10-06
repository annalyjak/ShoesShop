package com.example.anna.shoesshop.model.order;

import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.userInfo.Client;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class Order {
    private BigInteger numberOfOrder;
    private List<Product> products;

    private Address address;
    private Client client;
    private Date dateOfOrder;

    private Date dateOfDelivery;
    private Delivery deliveryInformation;

    private Status statusOfOrder;

//    private Price totalPriceOfOrder;

    public Price getTotalPriceOfOrder() {
        double total = 0.0;
//        total += products.stream().mapToDouble(p -> (double) p.getPrice()).sum();
        return new Price(total, "PLN");
    }

}
