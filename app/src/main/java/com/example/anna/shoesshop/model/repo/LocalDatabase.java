package com.example.anna.shoesshop.model.repo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.database.ProductDb;
import com.example.anna.shoesshop.model.database.enums.CategoryDb;
import com.example.anna.shoesshop.model.database.enums.SizeDb;
import com.example.anna.shoesshop.model.database.enums.TypeDb;
import com.example.anna.shoesshop.model.order.Delivery;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.order.Status;
import com.example.anna.shoesshop.model.product.Category;
import com.example.anna.shoesshop.model.product.Collection;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.product.Size;
import com.example.anna.shoesshop.model.product.Type;
import com.example.anna.shoesshop.model.userInfo.Account;
import com.example.anna.shoesshop.model.userInfo.Client;
import com.example.anna.shoesshop.model.userInfo.Sex;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LocalDatabase implements DBHelper {

    Context context;

    public LocalDatabase(Context context) {
        this.context = context;
    }

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
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ProductDb> allAsync = realm.where(ProductDb.class).findAllAsync();
        allAsync.load();
        List<ProductDb> allProducts = realm.copyFromRealm(allAsync);
        return DBUtil.transferToProductList(allProducts);
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

    public static void addProduct(Context context, ProductDb productDb) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
        // Copy to Realm
        realm.beginTransaction();
        realm.copyToRealm(productDb);
        realm.commitTransaction();
    }

    public static void addProducts(Context context) {
        RealmList<SizeDb> sizes = new RealmList<>();
        sizes.add(new SizeDb(Size.woman36));
        sizes.add(new SizeDb(Size.woman37));
        sizes.add(new SizeDb(Size.woman38));
        sizes.add(new SizeDb(Size.woman39));
        sizes.add(new SizeDb(Size.woman40));
        sizes.add(new SizeDb(Size.woman41));

        List<Bitmap> bitmaps1 = createListOfPictures(context,
                R.drawable.sz1,
                R.drawable.sz2,
                R.drawable.sz5,
                R.drawable.sz4);
        ProductDb pr1 = ProductDb.newInstance("Beżowe Szpilki Ombre",
                Category.shoes, sizes,
                6577,
                39.99,
                Type.women,
                Collection.winter,
                bitmaps1
        );

        List<Bitmap> bitmaps2 = createListOfPictures(context,
                R.drawable.sp1,
                R.drawable.sp2,
                R.drawable.sp3,
                R.drawable.sp4);
        ProductDb pr2 = ProductDb.newInstance("Sportowe Rising Day",
                Category.shoes, sizes,
                18716,
                49.99,
                Type.women,
                Collection.autumn,
                bitmaps2
        );

        List<Bitmap> bitmaps3 = createListOfPictures(context,
                R.drawable.tr1,
                R.drawable.tr2,
                R.drawable.tr4,
                R.drawable.tr5);
        ProductDb pr3 = ProductDb.newInstance("Traperki Untoward",
                Category.shoes, sizes,
                1,
                99.99,
                Type.women,
                Collection.winter,
                bitmaps3
        );

        List<Bitmap> bitmaps4 = createListOfPictures(context,
                R.drawable.szaregtraperki1,
                R.drawable.szaretraperki2,
                R.drawable.szaretraperki3,
                R.drawable.szaretraperki4);
        ProductDb pr4 = ProductDb.newInstance("Traperki Fancy Crazy",
                Category.shoes, sizes,
                27388,
                99.99,
                Type.women,
                Collection.winter,
                bitmaps4
        );

        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealm(pr1);
        realm.copyToRealm(pr2);
        realm.copyToRealm(pr3);
        realm.copyToRealm(pr4);
        realm.commitTransaction();

        realm.close();
    }

    private static List<Bitmap> createListOfPictures(Context context, int id1, int id2, int id3, int id4){
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(),
                id1);
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(),
                id2);
        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(),
                id3);
        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(),
                id4);
        List<Bitmap> bitmaps = new ArrayList<>();
        bitmaps.add(b1);
        bitmaps.add(b2);
        bitmaps.add(b3);
        bitmaps.add(b4);
        return bitmaps;
    }


//    public static void addProducts2(Context context){
//
//        Realm.init(context);
//        Realm realm = Realm.getDefaultInstance();
//
        // Creating products:
//        ProductDb productDb = new ProductDb();
//        productDb.setCategory(new CategoryDb(Category.shoes));
//        productDb.setName("Beżowe Szpilki Ombre");
//        RealmList<SizeDb> sizes = new RealmList<>();
//        sizes.add(new SizeDb(Size.woman36));
//        sizes.add(new SizeDb(Size.woman37));
//        sizes.add(new SizeDb(Size.woman38));
//        sizes.add(new SizeDb(Size.woman39));
//        sizes.add(new SizeDb(Size.woman40));
//        sizes.add(new SizeDb(Size.woman41));
//        productDb.setListOfSizes(sizes);
//        productDb.setNumberOfProduct(6577);
//        productDb.setPrice(new Price(39.99, "ZŁ"));
//        productDb.setTypeOfProduct(new TypeDb(Type.women));
//        productDb.setTypeOfCollection(Collection.winter);
//        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.sz1);
//        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.sz2);
//        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.sz4);
//        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.sz5);
//        List<Bitmap> bitmaps = new ArrayList<>();
//        bitmaps.add(b1);
//        bitmaps.add(b2);
//        bitmaps.add(b3);
//        bitmaps.add(b4);
//        RealmList<byte[]> pictures = DBUtil.transferToByteArray(bitmaps);
//        productDb.setPictures(pictures);
//
//        ProductDb productDb1 = new ProductDb();
//        productDb1.setCategory(new CategoryDb(Category.shoes));
//        productDb1.setName("Sportowe Rising Day");
//        productDb1.setListOfSizes(sizes);
//        productDb1.setNumberOfProduct(18716);
//        productDb1.setPrice(new Price(49.99, "ZŁ"));
//        productDb1.setTypeOfProduct(new TypeDb(Type.women));
//        productDb1.setTypeOfCollection(Collection.winter);
//        Bitmap b11 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.sp1);
//        Bitmap b21 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.sp2);
//        Bitmap b31 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.sp3);
//        Bitmap b41 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.sp4);
//        List<Bitmap> bitmaps1 = new ArrayList<>();
//        bitmaps1.add(b11);
//        bitmaps1.add(b21);
//        bitmaps1.add(b31);
//        bitmaps1.add(b41);
//        RealmList<byte[]> pictures1 = DBUtil.transferToByteArray(bitmaps1);
//        productDb1.setPictures(pictures1);
//
//        List<ProductDb> products = new ArrayList<>();
//        products.add(productDb);
//        products.add(productDb1);
//
//        // Copy to Realm
//        realm.beginTransaction();
//        realm.copyToRealm(products);
//        realm.commitTransaction();
//
//        //finding
//        RealmResults<ProductDb> allAsync = realm.where(ProductDb.class).findAllAsync();
//        allAsync.load();
//        Log.i("REALM_TEST", "" + allAsync.get(0).toString());
//
//        Log.i("REALM_TEST", "" + allAsync.size());
//    }
//
//    public static void addProduct(Context context){
//
//        Realm.init(context);
//        Realm realm = Realm.getDefaultInstance();
//
//        // Creating products:
//        ProductDb productDb = new ProductDb();
//        productDb.setCategory(new CategoryDb(Category.shoes));
//        productDb.setName("Szare Traperki Fancy Crazy");
//        RealmList<SizeDb> sizes = new RealmList<>();
//        sizes.add(new SizeDb(Size.woman36));
//        sizes.add(new SizeDb(Size.woman37));
//        sizes.add(new SizeDb(Size.woman38));
//        sizes.add(new SizeDb(Size.woman39));
//        sizes.add(new SizeDb(Size.woman40));
//        sizes.add(new SizeDb(Size.woman41));
//        productDb.setListOfSizes(sizes);
//        productDb.setNumberOfProduct(27388);
//        productDb.setPrice(new Price(99.99, "ZŁ"));
//        productDb.setTypeOfProduct(new TypeDb(Type.women));
//        productDb.setTypeOfCollection(Collection.winter);
//        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.szaregtraperki1);
//        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.szaretraperki2);
//        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.szaretraperki3);
//        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.szaretraperki4);
//        List<Bitmap> bitmaps = new ArrayList<>();
//        bitmaps.add(b1);
//        bitmaps.add(b2);
//        bitmaps.add(b3);
//        bitmaps.add(b4);
//        RealmList<byte[]> pictures = DBUtil.transferToByteArray(bitmaps);
//        productDb.setPictures(pictures);
//
//        // Copy to Realm
//        realm.beginTransaction();
//        realm.copyToRealm(productDb);
//        realm.commitTransaction();
//
//        //finding
//        RealmResults<ProductDb> allAsync = realm.where(ProductDb.class).findAllAsync();
//        allAsync.load();
//        Log.i("REALM_TEST", "" + allAsync.get(0).toString());
//
//        Log.i("REALM_TEST", "" + allAsync.size());
//    }

    public static void removeAllProducts(Context context) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.delete(ProductDb.class);

        realm.commitTransaction();
        Log.i("REALM_TEST", "Deleting");

    }
}
