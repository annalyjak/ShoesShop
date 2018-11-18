package com.example.anna.shoesshop.model.repo;

import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.userInfo.Account;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private Account account;
    private List<Product> order;

    public Session(DBHelper helper) {
        account = helper.getLoggedAccount();
        order = new ArrayList<>();
    }

    public void addProductToOrder(Product product) {
        order.add(product);
    }

    public List<Product> getOrder() {
        return order;
    }

    public Account getAccount() {
        return account;
    }

    public void removeProductFromBasket(Product product) {
        if (order.contains(product)) {
            order.remove(product);
        }
    }

    public void clearOrder() {
        order = new ArrayList<>();
    }
}
