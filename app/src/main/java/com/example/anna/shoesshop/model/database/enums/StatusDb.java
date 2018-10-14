package com.example.anna.shoesshop.model.database.enums;

import com.example.anna.shoesshop.model.order.Status;

import io.realm.RealmObject;

public class StatusDb extends RealmObject {
    private String status;

    public StatusDb(){
        status = "delivered";
    }

    public StatusDb(String status) {
        this.status = status;
    }

    public StatusDb(Status status){
        this.status = status.toString();
    }

    public Status transferToEnum(){
        if(status.contains("delivered")){
            return Status.delivered;
        } else{
            if(status.contains("canceled")){
                return Status.canceled;
            }
            return Status.during;
        }
    }

}
