package com.example.anna.shoesshop.model.database;

import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.DBUtil;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AccountDb extends RealmObject {
    private String email;
    private String password;
    private ClientDb client;
    private RealmList<Address> addressList;
    private RealmList<OrderDb> orders;
    private RealmList<ProductDb> favourites;

    public AccountDb(){

    }

    public AccountDb(String email,
                     String password,
                     ClientDb client) {
        this.email = email;
        this.password = password;
        this.client = client;
        this.addressList = new RealmList<>();
        this.orders = new RealmList<>();
        this.favourites = new RealmList<>();
    }

    public AccountDb(String email,
                     String password,
                     ClientDb client,
                     List<Address> addressList,
                     List<OrderDb> orders,
                     List<ProductDb> favourites) {
        this.email = email;
        this.password = password;
        this.client = client;
        this.addressList = (RealmList<Address>) addressList;
        this.orders = (RealmList<OrderDb>) orders;
        this.favourites = (RealmList<ProductDb>) favourites;
    }

    public static AccountDb newInstance(String email,
                                        String password,
                                        ClientDb client,
                                        List<Address> addressList,
                                        List<Order> orders,
                                        List<Product> favourites) {
        return new AccountDb(
                email,
                password,
                client,
                addressList,
                DBUtil.transferFromEnumOrderList(orders),
                DBUtil.transferFromEnumProductList(favourites));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientDb getClient() {
        return client;
    }

    public void setClient(ClientDb client) {
        this.client = client;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(RealmList<Address> addressList) {
        this.addressList = addressList;
    }

    public List<OrderDb> getOrders() {
        return orders;
    }

    public void setOrders(RealmList<OrderDb> orders) {
        this.orders = orders;
    }

    public List<ProductDb> getFavourites() {
        return favourites;
    }

    public void setFavourites(RealmList<ProductDb> favourites) {
        this.favourites = favourites;
    }
}
