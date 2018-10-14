package com.example.anna.shoesshop.model.database.enums;

import com.example.anna.shoesshop.model.order.TypeOfDelivery;

import io.realm.RealmObject;

public class TypeOfDeliveryDb extends RealmObject {
    private String type;

    public TypeOfDeliveryDb() {
        type = TypeOfDelivery.HomeDelivery.toString();
    }

    public TypeOfDeliveryDb(String type) {
        this.type = type;
    }

    public TypeOfDeliveryDb(TypeOfDelivery type) {
        this.type = type.toString();
    }

    public TypeOfDelivery transferToEnum(){
        if(type.contains("dostawa za pobraniem")){
            return TypeOfDelivery.HomeDelivery;
        } else{
            if(type.contains("odbiór w punkcie")){
                return TypeOfDelivery.ParcelPickUp;
            }
            return TypeOfDelivery.ClickAndCollectInStationaryShop;
        }
    }

    public String getType() {
        return type;
    }
}
