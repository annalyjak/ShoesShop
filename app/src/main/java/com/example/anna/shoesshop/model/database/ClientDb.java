package com.example.anna.shoesshop.model.database;

import com.example.anna.shoesshop.model.database.enums.GenderDb;
import com.example.anna.shoesshop.model.repo.DBUtil;
import com.example.anna.shoesshop.model.userInfo.Gender;

import io.realm.RealmObject;

public class ClientDb extends RealmObject {
    private String name;
    private String surname;
    private String phoneNumber;
    private GenderDb sex;

    public ClientDb(){

    }

    public ClientDb(String name, String surname, String phoneNumber, GenderDb sex) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }

    public ClientDb(String name, String surname, String phoneNumber, Gender sex) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.sex = DBUtil.transferToEnum(sex);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public GenderDb getSex() {
        return sex;
    }
}
