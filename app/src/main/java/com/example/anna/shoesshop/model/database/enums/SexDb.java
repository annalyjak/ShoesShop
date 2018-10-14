package com.example.anna.shoesshop.model.database.enums;

import com.example.anna.shoesshop.model.userInfo.Sex;

import io.realm.RealmObject;

public class SexDb extends RealmObject {
    private String sex;

    public SexDb() {
        sex = "woman";
    }

    public SexDb(String sex) {
        this.sex = sex;
    }

    public SexDb(Sex sex) {
        this.sex = sex.toString();
    }
}
