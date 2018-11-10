package com.example.anna.shoesshop.model.userInfo;

import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.database.AccountDb;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.DBUtil;

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

    public Account(AccountDb accountDb) {
        transfer(accountDb);
    }

    private Account transfer(AccountDb accountDb) {
        this.email = accountDb.getEmail();
        this.password = accountDb.getPassword();
        this.client = DBUtil.transferToEnum(accountDb.getClient());
        this.addressList = accountDb.getAddressList();
        this.orders = DBUtil.transferToOrderList(accountDb.getOrders());
        this.favourites = DBUtil.transferToProductList(accountDb.getFavourites());
        return this;
    }

    public Client getClientInfo() {
        return client;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Product> getFavourites() {
        return favourites;
    }
}
