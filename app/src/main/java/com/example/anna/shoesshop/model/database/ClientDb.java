package com.example.anna.shoesshop.model.database;

import com.example.anna.shoesshop.model.database.enums.SexDb;
import com.example.anna.shoesshop.model.repo.DBUtil;
import com.example.anna.shoesshop.model.userInfo.Sex;

import io.realm.RealmObject;

public class ClientDb extends RealmObject {
    private String name;
    private String surname;
    private String phoneNumber;
    private SexDb sex;

    public ClientDb(){

    }

    public ClientDb(String name, String surname, String phoneNumber, SexDb sex) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }

    public ClientDb(String name, String surname, String phoneNumber, Sex sex) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.sex = DBUtil.transferToEnum(sex);
    }
}
