package com.example.anna.shoesshop.model.repo;

import android.annotation.SuppressLint;

import com.example.anna.shoesshop.model.database.OrderDb;
import com.example.anna.shoesshop.model.database.ProductDb;
import com.example.anna.shoesshop.model.database.enums.SexDb;
import com.example.anna.shoesshop.model.database.enums.StatusDb;
import com.example.anna.shoesshop.model.database.enums.TypeOfDeliveryDb;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.order.Status;
import com.example.anna.shoesshop.model.order.TypeOfDelivery;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.userInfo.Sex;

import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    private static List<Order> list;

    public static TypeOfDeliveryDb transferToEnum(TypeOfDelivery type){
        return new TypeOfDeliveryDb(type.toString());
    }

    public static StatusDb transferToEnum(Status status){
        return new StatusDb(status.toString());
    }

    public static SexDb transferToEnum(Sex sex){
        return new SexDb(sex.toString());
    }

    //TODO
    public static OrderDb transferToEnum(Order order){
        return new OrderDb();
    }

    //TODO
    public static ProductDb transferToEnum(Product product){
        return new ProductDb();
    }

    @SuppressLint("NewApi")
    public static List<OrderDb> transferFromEnumOrderList(List<Order> list){
        ArrayList<OrderDb> result = new ArrayList<>();
        list.forEach(o -> result.add(DBUtil.transferToEnum(o)));
        return result;
    }

    @SuppressLint("NewApi")
    public static List<ProductDb> transferFromEnumProductList(List<Product> list){
        ArrayList<ProductDb> result = new ArrayList<>();
        list.forEach(o -> result.add(DBUtil.transferToEnum(o)));
        return result;
    }
}
