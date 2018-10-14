package com.example.anna.shoesshop.model.repo;

import com.example.anna.shoesshop.model.order.Delivery;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.order.Status;
import com.example.anna.shoesshop.model.product.Category;
import com.example.anna.shoesshop.model.product.Collection;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.product.Type;
import com.example.anna.shoesshop.model.userInfo.Account;
import com.example.anna.shoesshop.model.userInfo.Client;
import com.example.anna.shoesshop.model.userInfo.Sex;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Client getClientData() {
        return new Client("Guest", "", "", Sex.woman);
    }

    @Override
    public Account getLoggedAccount() {
        return new Account("guestAccount@gmail.com", "", getClientData());
    }

    @Override
    public List<Order> getAllClientOrders(Account account) {
        return null;
    }

    @Override
    public List<Order> getAllClientOrders(Client client) {
        return null;
    }

    @Override
    public List<Order> getClientOrdersForStatus(Account account, Status status) {
        return null;
    }

    @Override
    public List<Order> getClientOrdersForStatus(Client client, Status status) {
        return null;
    }

    @Override
    public List<Product> getAllFavouritesProducts(Account account) {
        return null;
    }

    @Override
    public List<Product> getAllFavouritesProducts(Client client) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public List<Product> getAllProductsForCollCat(Type type, Collection collection, Category category) {
        return null;
    }

    @Override
    public List<Product> getAllProductsForCollection(Type type, Collection collection) {
        return null;
    }

    @Override
    public List<Product> getAllProductsForCategory(Type type, Category category) {
        return null;
    }

    @Override
    public List<Product> getAllProductsType(Type type) {
        return null;
    }
}
