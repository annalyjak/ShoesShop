package com.example.anna.shoesshop.model.repo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.AsyncListUtil;
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
        if (Session.getProductsCache() != null) {
            return Session.getProductsCache();
        }
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
    public static void addProducts(Context context, List<ProductDb> productDb) {
        Realm.init(context);
        Realm realm = Realm.getDefaultInstance();
//         Copy to Realm
        realm.beginTransaction();
        for (ProductDb p: productDb) {
            realm.copyToRealm(p);
        }
        realm.commitTransaction();
        realm.close();
    }

    public static RealmList<SizeDb> getWomanSizes() {
        RealmList<SizeDb> sizes = new RealmList<>();
        sizes.add(new SizeDb(Size.woman36));
        sizes.add(new SizeDb(Size.woman37));
        sizes.add(new SizeDb(Size.woman38));
        sizes.add(new SizeDb(Size.woman39));
        sizes.add(new SizeDb(Size.woman40));
        sizes.add(new SizeDb(Size.woman41));
        return sizes;
    }

    public static RealmList<SizeDb> getManSizes() {
        RealmList<SizeDb> sizes = new RealmList<>();
        sizes.add(new SizeDb(Size.man41));
        sizes.add(new SizeDb(Size.man42));
        sizes.add(new SizeDb(Size.man43));
        sizes.add(new SizeDb(Size.man44));
        sizes.add(new SizeDb(Size.man45));
        sizes.add(new SizeDb(Size.man46));
        return sizes;
    }

    public static RealmList<SizeDb> getKidSizes() {
        RealmList<SizeDb> sizes = new RealmList<>();
        sizes.add(new SizeDb(Size.kid30));
        sizes.add(new SizeDb(Size.kid31));
        sizes.add(new SizeDb(Size.kid32));
        sizes.add(new SizeDb(Size.kid33));
        sizes.add(new SizeDb(Size.kid34));
        sizes.add(new SizeDb(Size.kid35));
        return sizes;
    }

    public static RealmList<SizeDb> getUniversalSizes() {
        RealmList<SizeDb> sizes = new RealmList<>();
        sizes.add(new SizeDb(Size.universal));
        return sizes;
    }

    public static void addKidProducts(Context context) {
        List<Bitmap> bitmaps1 = createListOfPictures(context,
                R.drawable.bo5,
                R.drawable.bo4,
                R.drawable.bo6,
                R.drawable.bo7);
        ProductDb pr1 = ProductDb.newInstance("Botki Love Winter",
                Category.shoes,
                getKidSizes(),
                78576,
                49.99,
                Type.kid,
                Collection.winter,
                bitmaps1
        );

        List<Bitmap> bitmaps2 = createListOfPictures(context,
                R.drawable.sn1,
                R.drawable.sn2,
                R.drawable.sn3,
                R.drawable.sn4);
        ProductDb pr2 = ProductDb.newInstance("Śniegowce Awards",
                Category.shoes, getKidSizes(),
                28329,
                69.99,
                Type.kid,
                Collection.winter,
                bitmaps2
        );

        List<Bitmap> bitmaps3 = createListOfPictures(context,
                R.drawable.kur6,
                R.drawable.kur14);
        ProductDb pr3 = ProductDb.newInstance("Kurtka Frozen Skill",
                Category.clothes, getUniversalSizes(),
                74196,
                219.99,
                Type.kid,
                Collection.winter,
                bitmaps3
        );

        List<Bitmap> bitmaps4 = createListOfPictures(context,
                R.drawable.rek22,
                R.drawable.rek23,
                R.drawable.rek24);
        ProductDb pr4 = ProductDb.newInstance("Rękawiczki Defender",
                Category.accessories,
                getUniversalSizes(),
                75815,
                14.99,
                Type.kid,
                Collection.winter,
                bitmaps4
        );

        ArrayList<ProductDb> productDbs = new ArrayList<>();
        productDbs.add(pr1);
        productDbs.add(pr2);
        productDbs.add(pr3);
        productDbs.add(pr4);

        addProducts(context, productDbs);
    }

    public static void addMProcucts(Context context) {
        List<Bitmap> bitmaps1 = createListOfPictures(context,
                R.drawable.botm1,
                R.drawable.botm3,
                R.drawable.botm4,
                R.drawable.botm12);
        ProductDb pr1 = ProductDb.newInstance("Botki Retrofuture",
                Category.shoes, getManSizes(),
                24258,
                129.99,
                Type.men,
                Collection.autumn,
                bitmaps1
        );

        List<Bitmap> bitmaps2 = createListOfPictures(context,
                R.drawable.bluza1,
                R.drawable.bluza2,
                R.drawable.bluza3,
                R.drawable.bluza4);
        ProductDb pr2 = ProductDb.newInstance("Bluza Enclosure",
                Category.clothes,
                getUniversalSizes(),
                28865,
                156.99,
                Type.men,
                Collection.autumn,
                bitmaps2
        );

        List<Bitmap> bitmaps3 = createListOfPictures(context,
                R.drawable.torba20,
                R.drawable.torba17,
                R.drawable.torba19,
                R.drawable.torba18);
        ProductDb pr3 = ProductDb.newInstance("Torba Roll It Over",
                Category.accessories,
                getUniversalSizes(),
                73025,
                46.99,
                Type.men,
                Collection.summer,
                bitmaps3
        );

        ArrayList<ProductDb> productDbs = new ArrayList<>();
        productDbs.add(pr1);
        productDbs.add(pr2);
        productDbs.add(pr3);

        addProducts(context, productDbs);
    }

    public static void addWomanClothes(Context context) {
        RealmList<SizeDb> sizes = getUniversalSizes();
        List<Bitmap> bitmaps1 = createListOfPictures(context,
                R.drawable.scarcity1,
                R.drawable.scarcity2,
                R.drawable.scarcity3,
                R.drawable.scarcity4);

        ProductDb pr1 = ProductDb.newInstance("Szara Sukienka Scarcity",
                Category.clothes, sizes,
                81292,
                79.99,
                119.99,
                Type.women,
                Collection.winter,
                bitmaps1
        );

        List<Bitmap> bitmaps2 = createListOfPictures(context,
                R.drawable.kurtka1,
                R.drawable.kurtka2,
                R.drawable.kurtka3,
                R.drawable.kurtka4);
        ProductDb pr2 = ProductDb.newInstance("Kurtka Milk&Honey",
                Category.clothes, sizes,
                82469,
                249.99,
                Type.women,
                Collection.winter,
                bitmaps2
        );

        List<Bitmap> bitmaps3 = createListOfPictures(context,
                R.drawable.legg1,
                R.drawable.legg2,
                R.drawable.legg4,
                R.drawable.legg5);
        ProductDb pr3 = ProductDb.newInstance("Legginsy Uncontrolled",
                Category.clothes, sizes,
                82368,
                69.99,
                Type.women,
                Collection.spring,
                bitmaps3
        );

        List<Bitmap> bitmaps4 = createListOfPictures(context,
                R.drawable.mar1,
                R.drawable.mar2,
                R.drawable.mar3,
                R.drawable.mar4);
        ProductDb pr4 = ProductDb.newInstance("Marynarka Formally",
                Category.clothes, sizes,
                42659,
                79.99,
                179.99,
                Type.women,
                Collection.spring,
                bitmaps4
        );

        ArrayList<ProductDb> productDbs = new ArrayList<>();
        productDbs.add(pr1);
        productDbs.add(pr2);
        productDbs.add(pr3);
        productDbs.add(pr4);

        addProducts(context, productDbs);
    }

    public static void addWomanAccessories(Context context) {
        RealmList<SizeDb> sizes = getUniversalSizes();
        List<Bitmap> bitmaps1 = createListOfPictures(context,
                R.drawable.szal1,
                R.drawable.szal2,
                R.drawable.szal3,
                R.drawable.szal23);

        ProductDb pr1 = ProductDb.newInstance("Szalik Jocularity",
                Category.accessories, sizes,
                74728,
                29.99,
                Type.women,
                Collection.autumn,
                bitmaps1
        );

        List<Bitmap> bitmaps2 = createListOfPictures(context,
                R.drawable.bag17,
                R.drawable.bag18,
                R.drawable.bag19,
                R.drawable.bag20);
        ProductDb pr2 = ProductDb.newInstance("Torebka My Tears",
                Category.accessories, sizes,
                72323,
                89.99,
                Type.women,
                Collection.summer,
                bitmaps2
        );

        List<Bitmap> bitmaps3 = createListOfPictures(context,
                R.drawable.plec,
                R.drawable.plec20,
                R.drawable.plec17,
                R.drawable.plec18);
        ProductDb pr3 = ProductDb.newInstance("Plecak The Tropics",
                Category.accessories, sizes,
                69372,
                19.99,
                Type.women,
                Collection.summer,
                bitmaps3
        );

        List<Bitmap> bitmaps4 = createListOfPictures(context,
                R.drawable.beret5,
                R.drawable.beret7,
                R.drawable.beret30,
                R.drawable.beret31);
        ProductDb pr4 = ProductDb.newInstance("Camelowy Beret French",
                Category.accessories, sizes,
                29431,
                54.99,
                Type.women,
                Collection.autumn,
                bitmaps4
        );

        ArrayList<ProductDb> productDbs = new ArrayList<>();
        productDbs.add(pr1);
        productDbs.add(pr2);
        productDbs.add(pr3);
        productDbs.add(pr4);

        addProducts(context, productDbs);
    }


    public static void addProducts(Context context) {
        RealmList<SizeDb> sizes = getWomanSizes();

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
        ArrayList<ProductDb> productDbs = new ArrayList<>();
        productDbs.add(pr1);
        productDbs.add(pr2);
        productDbs.add(pr3);
        productDbs.add(pr4);
        addProducts(context, productDbs);
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

    private static List<Bitmap> createListOfPictures(Context context, int... id1){
        List<Bitmap> bitmaps = new ArrayList<>();
        for(int i = 0; i < id1.length; i++) {
            bitmaps.add(BitmapFactory.decodeResource(context.getResources(), id1[i]));
        }
        return bitmaps;
    }


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
        accountDb.addOrderToList(orderDb);
        realm.commitTransaction();
    }
}
