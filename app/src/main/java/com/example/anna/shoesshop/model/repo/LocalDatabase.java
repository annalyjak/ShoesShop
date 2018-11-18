package com.example.anna.shoesshop.model.repo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.Address;
import com.example.anna.shoesshop.model.database.AccountDb;
import com.example.anna.shoesshop.model.database.ClientDb;
import com.example.anna.shoesshop.model.database.DeliveryDb;
import com.example.anna.shoesshop.model.database.OrderDb;
import com.example.anna.shoesshop.model.database.ProductDb;
import com.example.anna.shoesshop.model.database.enums.SizeDb;
import com.example.anna.shoesshop.model.database.enums.StatusDb;
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
import com.example.anna.shoesshop.model.userInfo.Gender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LocalDatabase implements DBHelper {

    Context context;
    Realm realm;

    public LocalDatabase(Context context) {
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Client getClientData() {
        Account loggedAccount = getLoggedAccount();
        return loggedAccount.getClientInfo();
    }

    @Override
    public Account getLoggedAccount() {
        Account account;

        RealmResults<AccountDb> all = realm.where(AccountDb.class).findAll();
        all.load();
        if(all.isEmpty()) {

            Client client = new Client("Guest", "", "", Gender.woman);
            account = new Account("guest", "", client);
            ClientDb clientDb = new ClientDb("Guest", "", "", Gender.woman);
            AccountDb accountDb = new AccountDb("guest", "", clientDb);
            realm.beginTransaction();
            realm.copyToRealm(accountDb);
            realm.commitTransaction();
            realm.close();
        } else {
            account = DBUtil.transferToEnum(all.get(0));
        }

        return account;
    }

    @Override
    public List<Order> getAllClientOrders(Account account) {
        return account.getOrders();
    }

    @Override
    public List<Order> getAllClientOrders() {
        return getLoggedAccount().getOrders();
    }

    @Override
    public List<Order> getClientOrdersForStatus(Account account, Status status) {
        List<Order> orders = new ArrayList<>();
        for (Order order : account.getOrders()) {
            if(order.getStatusOfOrder().equals(status)) {
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<Product> getAllFavouritesProducts(Account account) {
        if(account == null){
            account = getLoggedAccount();
        }
        return account.getFavourites();
    }

    @Override
    public List<Product> getAllProducts() {
        RealmResults<ProductDb> allAsync = realm.where(ProductDb.class).findAllAsync();
        allAsync.load();
        List<ProductDb> allProducts = realm.copyFromRealm(allAsync);
        return DBUtil.transferToProductList(allProducts);
    }

    @Override
    public List<Product> getAllProductsForCollCat(Type type, Collection collection, Category category) {
        List<Product> result = new ArrayList<>();
        List<Product> allProducts = getAllProductsForCollection(type, collection);
        for (Product product : allProducts) {
            if(product.getCategory() == category){
                result.add(product);
            }
        }
        return result;
    }

    @Override
    public List<Product> getAllProductsForCollection(Type type, Collection collection) {
        List<Product> result = new ArrayList<>();
        List<Product> allProducts = getAllProductsType(type);
        for (Product product : allProducts) {
            if(product.getTypeOfCollection() == collection){
                result.add(product);
            }
        }
        return result;
    }

    @Override
    public List<Product> getAllProductsForCategory(Type type, Category category) {
        List<Product> result = new ArrayList<>();
        List<Product> allProducts = getAllProductsType(type);
        for (Product product : allProducts) {
            if(product.getCategory() == category){
                result.add(product);
            }
        }
        return result;
    }

    @Override
    public List<Product> getAllProductsType(Type type) {
        List<Product> result = new ArrayList<>();
        List<Product> allProducts = getAllProducts();
        for (Product product : allProducts) {
            if(product.getTypeOfProduct() == type){
                result.add(product);
            }
        }
        return result;
    }

    @Override
    public void addProductToFavourites(Product product) {
        Account loggedAccount = getLoggedAccount();
        loggedAccount.addProductToFavourites(product);

        AccountDb all = getAccountDb(loggedAccount);
        ProductDb productDb = getProductDb(product);

        if(!all.checkIsFavourite(productDb)) {
            realm.beginTransaction();
            all.addProductToFavourites(productDb);
            realm.commitTransaction();
            realm.close();
//            showAlert("Dodano " + product.getName() + " do ulubionych");
        } else {
//            showAlert("Ten produkt jest już ulubiony!");
        }
    }

    @Override
    public void removeFromFavourites(Product product) {
        Account loggedAccount = getLoggedAccount();
        loggedAccount.removeFromFavourites(product);

        AccountDb all = getAccountDb(loggedAccount);
        ProductDb productDb = getProductDb(product);

        if(all.checkIsFavourite(productDb)) {
            realm.beginTransaction();
            all.removeFromFavourites(productDb);
            realm.commitTransaction();
            realm.close();
//            showAlert("Usunięto " + product.getName() + " z ulubionych");
        }
    }

    //helpers
    private AccountDb getAccountDb(Account account) {
        AccountDb all = realm.where(AccountDb.class)
                .equalTo("email", account.getEmail())
                .equalTo("password", account.getPassword())
                .findFirst();
        all.load();
        return all;
    }

    private ProductDb getProductDb(Product product) {
        ProductDb productDb = realm.where(ProductDb.class)
                .equalTo("name", product.getName())
                .equalTo("numberOfProduct", product.getNumberOfProduct())
                .findFirst();
        productDb.load();
        return productDb;
    }

    //CONFIG
    private void showAlert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    // MANIPULACJA NA DANYCH Z BAZY
    public void addProduct(ProductDb productDb) {
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

    public void removeAllProducts() {
        realm.beginTransaction();
        realm.delete(ProductDb.class);

        realm.commitTransaction();
        Log.i("REALM_TEST", "Deleting");

    }

    public void saveNewOrder(List<Product> orderedProducts, Delivery selectedDelivery) {
        AccountDb accountDb = getAccountDb(getLoggedAccount());
        Address address = new Address(); // default
        RealmList<ProductDb> productDbs = new RealmList<>();
        for (Product p : orderedProducts) {
            productDbs.add(getProductDb(p));
        }
        Date dateOfOrder = new Date(System.currentTimeMillis());

        Random random = new Random();
        long id = dateOfOrder.getTime() + random.nextInt();

        DeliveryDb deliveryDb = new DeliveryDb(selectedDelivery);

        StatusDb statusDb = new StatusDb(Status.during);

        OrderDb orderDb = new OrderDb(id,
                productDbs,
                address,
                accountDb.getClient(),
                dateOfOrder,
                deliveryDb,
                statusDb);

        realm.beginTransaction();
        realm.copyToRealm(orderDb);
        realm.commitTransaction();

    }
}
