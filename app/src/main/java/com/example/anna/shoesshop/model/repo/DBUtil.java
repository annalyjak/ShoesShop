package com.example.anna.shoesshop.model.repo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.anna.shoesshop.model.database.AccountDb;
import com.example.anna.shoesshop.model.database.ClientDb;
import com.example.anna.shoesshop.model.database.DeliveryDb;
import com.example.anna.shoesshop.model.database.OrderDb;
import com.example.anna.shoesshop.model.database.ProductDb;
import com.example.anna.shoesshop.model.database.enums.CategoryDb;
import com.example.anna.shoesshop.model.database.enums.CollectionDb;

import com.example.anna.shoesshop.model.database.enums.GenderDb;
import com.example.anna.shoesshop.model.database.enums.SizeDb;
import com.example.anna.shoesshop.model.database.enums.StatusDb;
import com.example.anna.shoesshop.model.database.enums.TypeDb;
import com.example.anna.shoesshop.model.database.enums.TypeOfDeliveryDb;
import com.example.anna.shoesshop.model.order.Delivery;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.order.Status;
import com.example.anna.shoesshop.model.order.TypeOfDelivery;
import com.example.anna.shoesshop.model.product.Category;
import com.example.anna.shoesshop.model.product.Collection;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.product.Size;
import com.example.anna.shoesshop.model.product.Type;
import com.example.anna.shoesshop.model.userInfo.Account;
import com.example.anna.shoesshop.model.userInfo.Client;
import com.example.anna.shoesshop.model.userInfo.Gender;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class DBUtil {

    private static List<Order> list;

    public static TypeOfDeliveryDb transferToEnum(TypeOfDelivery type){
        return new TypeOfDeliveryDb(type.toString());
    }

    public static StatusDb transferToEnum(Status status){
        return new StatusDb(status.toString());
    }

    public static GenderDb transferToEnum(Gender sex){
        return new GenderDb(sex.toString());
    }

    public static Gender transferToEnum(GenderDb sex){
        return Gender.valueOf(sex.toString());
    }

    //TODO
    public static OrderDb transferToEnum(Order order){
        return new OrderDb();
    }

    public static Order transferToEnum(OrderDb order){
        return new Order().transfer(order);
    }

    public static Client transferToEnum(ClientDb clientDb) {
        return new Client(clientDb);
    }

    public static ClientDb transferToEnum(Client clientDb) {
        return clientDb.transferToDb();
    }

    public static Account transferToEnum(AccountDb accountDb) {
        return new Account(accountDb);
    }

    public static Delivery transferToEnum(DeliveryDb deliveryInformation) {
        return new Delivery(deliveryInformation);
    }

    public static ProductDb transferToEnum(Product product){
        return product.transferToDBObject();
    }

    public static Product transferToEnum(ProductDb product){
        Product result = new Product(product.getTypeOfProduct(),
                product.getCategory(),
                product.getTypeOfCollection(),
                product.getNumberOfProduct(),
                product.getPrice(),
                product.getName(),
                DBUtil.transferToEnumListOfBitmap(product.getPictures()),
                DBUtil.transferToEnumList(product.getListOfSizes()),
                product.getSelectedSize(),
                product.getNormalPrice() );
        return result;
    }

    private static List<Bitmap> transferToEnumListOfBitmap(RealmList<byte[]> pictures) {
        List<Bitmap> list = new ArrayList<>();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        for (byte[] p : pictures) {
            list.add(BitmapFactory.decodeByteArray(p, 0, p.length, options));
        }
        return list;
    }

    private static List<Size> transferToEnumList(RealmList<SizeDb> listOfSizes) {
        List<Size> res = new ArrayList<>();
        for (SizeDb l : listOfSizes) {
            res.add(DBUtil.transferToEnum(l));
        }
        return res;
    }

    public static CollectionDb transferToEnum(Collection typeOfCollection) {
        return new CollectionDb(typeOfCollection.toString());
    }

    public static Category transferToEnum(CategoryDb categoryDb) {
        if (categoryDb == null) {
            return Category.shoes;
        }
        return (categoryDb.equals("shoes"))? Category.shoes :
                (categoryDb.equals("clothes")? Category.clothes : Category.accessories);
    }

    public static Type transferToEnum(TypeDb typeDb) {
        if(typeDb == null) {
            return Type.women;
        }
        return (typeDb.equals("women"))? Type.women : (typeDb.equals("men")? Type.men : Type.kid);
    }

    public static Size transferToEnum(SizeDb selectedSize) {
        switch (selectedSize.toString()){
            case ("woman36"): return Size.woman36;
            case ("woman37"): return Size.woman37;
            case ("woman38"): return Size.woman38;
            case ("woman39"): return Size.woman39;
            case ("woman40"): return Size.woman40;
            case ("woman41"): return Size.woman41;
            case ("woman42"): return Size.woman42;
            case ("man41"): return Size.man41;
            case ("man42"): return Size.man42;
            case ("man43"): return Size.man43;
            case ("man44"): return Size.man44;
            case ("man45"): return Size.man45;
            case ("man46"): return Size.man46;
            case ("kid30"): return Size.kid30;
            case ("kid31"): return Size.kid31;
            case ("kid32"): return Size.kid32;
            case ("kid33"): return Size.kid33;
            case ("kid34"): return Size.kid34;
            case ("kid35"): return Size.kid35;

        }
        return Size.universal;
    }

    public static Collection transferToEnum(CollectionDb typeOfCollection) {
        if(typeOfCollection == null) {
            return Collection.spring;
        }
        switch (typeOfCollection.toString()) {
            case ("summer"): return Collection.summer;
            case ("autumn"): return Collection.autumn;
            case ("winter"): return Collection.winter;
            case ("spring"): return Collection.spring;
            default: return Collection.spring;
        }
    }

    public static List<OrderDb> transferFromEnumOrderList(List<Order> list){
        ArrayList<OrderDb> result = new ArrayList<>();
        for (Order o : list) {
            result.add(DBUtil.transferToEnum(o));
        }
        return result;
    }

    public static List<ProductDb> transferFromEnumProductList(List<Product> list){
        ArrayList<ProductDb> result = new ArrayList<>();
        for (Product o : list) {
            result.add(DBUtil.transferToEnum(o));
        }
        return result;
    }

    public static List<Product> transferToProductList(List<ProductDb> list){
        ArrayList<Product> result = new ArrayList<>();
        if(list != null) {
            for (ProductDb o : list) {
                result.add(DBUtil.transferToEnum(o));
            }
        }
        return result;
    }

    public static List<Order> transferToOrderList(List<OrderDb> orders) {
        ArrayList<Order> result = new ArrayList<>();
        for (OrderDb o : orders) {
            result.add(DBUtil.transferToEnum(o));
        }
        return result;
    }

    public static RealmList<byte[]> transferToByteArray(List<Bitmap> bitmaps) {
        RealmList<byte[]> result = new RealmList<>();
        for (Bitmap b : bitmaps) {
            result.add(bitmapToByte(b));
        }
        return result;
    }

    private static byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }

    public static RealmList<SizeDb> transferToSizesList(List<Size> sizes) {
        RealmList<SizeDb> result = new RealmList<>();
        for (Size s : sizes) {
            result.add(new SizeDb(s));
        }
        return result;
    }
}
