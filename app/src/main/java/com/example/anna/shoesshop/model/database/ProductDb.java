package com.example.anna.shoesshop.model.database;

import android.graphics.Bitmap;
import android.media.Image;

import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.database.enums.CategoryDb;
import com.example.anna.shoesshop.model.database.enums.CollectionDb;
import com.example.anna.shoesshop.model.database.enums.SizeDb;
import com.example.anna.shoesshop.model.database.enums.TypeDb;
import com.example.anna.shoesshop.model.product.Category;
import com.example.anna.shoesshop.model.product.Collection;
import com.example.anna.shoesshop.model.product.Size;
import com.example.anna.shoesshop.model.product.Type;
import com.example.anna.shoesshop.model.repo.DBUtil;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ProductDb extends RealmObject {
    //generalForSegregation
    private TypeDb typeOfProduct;
    private CategoryDb category;
    private CollectionDb typeOfCollection;
    //generalInfo
    private long numberOfProduct;
    private Price normalPrice;
    private Price price;
    private String name;
    private RealmList<byte[]> pictures;
    //details
    private RealmList<SizeDb> listOfSizes;
    private SizeDb selectedSize = new SizeDb("universal");

    public static ProductDb newInstance(String name,
                                        Category category,
                                        RealmList<SizeDb> sizes,
                                        int numberOfProduct,
                                        double price,
                                        Type type,
                                        Collection collection,
                                        List<Bitmap> bitmaps){
        ProductDb result = new ProductDb();
        result.setCategory(new CategoryDb(category));
        result.setName(name);
        result.setListOfSizes(sizes);
        result.setNumberOfProduct(numberOfProduct);
        result.setPrice(new Price(price, "ZŁ"));
        result.setNormalPrice(new Price(price, "ZŁ"));
        result.setTypeOfProduct(new TypeDb(type));
        result.setTypeOfCollection(collection);
        RealmList<byte[]> pictures = DBUtil.transferToByteArray(bitmaps);
        result.setPictures(pictures);
        return result;
    }

    public static ProductDb newInstance(String name,
                                        Category category,
                                        RealmList<SizeDb> sizes,
                                        int numberOfProduct,
                                        double price,
                                        double normalprice,
                                        Type type,
                                        Collection collection,
                                        List<Bitmap> bitmaps){
        ProductDb result = new ProductDb();
        result.setCategory(new CategoryDb(category));
        result.setName(name);
        result.setListOfSizes(sizes);
        result.setNumberOfProduct(numberOfProduct);
        result.setPrice(new Price(price, "ZŁ"));
        result.setNormalPrice(new Price(normalprice, "ZŁ"));
        result.setTypeOfProduct(new TypeDb(type));
        result.setTypeOfCollection(collection);
        RealmList<byte[]> pictures = DBUtil.transferToByteArray(bitmaps);
        result.setPictures(pictures);
        return result;
    }

    public static ProductDb newInstance(String name,
                                        Category category,
                                        List<Size> sizes,
                                        long numberOfProduct,
                                        Price price,
                                        Price normalprice,
                                        Type type,
                                        Collection collection,
                                        List<Bitmap> bitmaps){
        ProductDb result = new ProductDb();
        result.setCategory(new CategoryDb(category));
        result.setName(name);
        result.setListOfSizes(DBUtil.transferToSizesList(sizes));
        result.setNumberOfProduct(numberOfProduct);
        result.setPrice(price);
        result.setNormalPrice(normalprice);
        result.setTypeOfProduct(new TypeDb(type));
        result.setTypeOfCollection(collection);
        RealmList<byte[]> pictures = DBUtil.transferToByteArray(bitmaps);
        result.setPictures(pictures);
        return result;
    }

    public Price getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(Price normalPrice) {
        this.normalPrice = normalPrice;
    }

    public TypeDb getTypeOfProduct() {
        return typeOfProduct;
    }

    public CategoryDb getCategory() {
        return category;
    }

    public CollectionDb getTypeOfCollection() {
        return typeOfCollection;
    }

    public long getNumberOfProduct() {
        return numberOfProduct;
    }

    public Price getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public RealmList<byte[]> getPictures() {
        return pictures;
    }

    public RealmList<SizeDb> getListOfSizes() {
        return listOfSizes;
    }

    public SizeDb getSelectedSize() {
        return selectedSize;
    }

    public void setTypeOfProduct(TypeDb typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public void setCategory(CategoryDb category) {
        this.category = category;
    }

    public void setTypeOfCollection(CollectionDb typeOfCollection) {
        this.typeOfCollection = typeOfCollection;
    }

    public void setTypeOfCollection(Collection typeOfCollection) {
        this.typeOfCollection = DBUtil.transferToEnum(typeOfCollection);
    }

    public void setNumberOfProduct(long numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPictures(RealmList<byte[]> pictures) {
        this.pictures = pictures;
    }

    public void setListOfSizes(RealmList<SizeDb> listOfSizes) {
        this.listOfSizes = listOfSizes;
    }

    public void setSelectedSize(SizeDb selectedSize) {
        this.selectedSize = selectedSize;
    }
}
