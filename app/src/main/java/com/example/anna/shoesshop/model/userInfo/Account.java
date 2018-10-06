package com.example.anna.shoesshop.model.userInfo;

import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.order.Order;

import java.util.List;

public class Account {
    private String email;
    private String password;
    private Client client;
    private List<Address> addressList;
    private List<Order> orders;
}
