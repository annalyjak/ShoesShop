package com.example.anna.shoesshop.model.userInfo;

import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String email;
    private String password;
    private Client client;
    private List<Address> addressList;
    private List<Order> orders;
    private List<Product> favourites;

    public Account(String email, String password, Client client) {
        this.email = email;
        this.password = password;
        this.client = client;
        this.addressList = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.favourites = new ArrayList<>();
    }
}
