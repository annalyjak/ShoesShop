package com.example.anna.shoesshop.model.repo;

import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.order.Status;
import com.example.anna.shoesshop.model.product.Category;
import com.example.anna.shoesshop.model.product.Collection;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.product.Type;
import com.example.anna.shoesshop.model.userInfo.Account;
import com.example.anna.shoesshop.model.userInfo.Client;

import java.util.List;

public interface DBHelper {

    Client getClientData();
    Account getLoggedAccount();

    List<Order> getAllClientOrders(Account account);
    List<Order> getAllClientOrders(Client client);

    List<Order> getClientOrdersForStatus(Account account, Status status);
    List<Order> getClientOrdersForStatus(Client client, Status status);

    List<Product> getAllFavouritesProducts(Account account);
    List<Product> getAllFavouritesProducts(Client client);

    List<Product> getAllProducts();
    List<Product> getAllProductsForCollCat(Type type, Collection collection, Category category);
    List<Product> getAllProductsForCollection(Type type, Collection collection);
    List<Product> getAllProductsForCategory(Type type, Category category);
    List<Product> getAllProductsType(Type type);


}
