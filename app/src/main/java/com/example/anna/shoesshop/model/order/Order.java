package com.example.anna.shoesshop.model.order;

import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.database.OrderDb;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.DBUtil;
import com.example.anna.shoesshop.model.userInfo.Client;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class Order {
    private long numberOfOrder;
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

    public Order transfer(OrderDb orderDb){
        numberOfOrder = orderDb.getNumberOfOrder();
        products = DBUtil.transferToProductList(orderDb.getProducts());
        dateOfDelivery = orderDb.getDateOfDelivery();
        dateOfOrder = orderDb.getDateOfOrder();
        deliveryInformation = DBUtil.transferToEnum(orderDb.getDeliveryInformation());
        statusOfOrder = orderDb.getStatusOfOrder().transferToEnum();
        return this;
    }

    public Status getStatusOfOrder() {
        return statusOfOrder;
    }
}
