package com.example.anna.shoesshop.model.database.enums;

import com.example.anna.shoesshop.model.userInfo.Gender;

import io.realm.RealmObject;

public class GenderDb extends RealmObject {
    private String gender;

    public GenderDb() {
        gender = "woman";
    }

    public GenderDb(String sex) {
        this.gender = sex;
    }

    public GenderDb(Gender gender) {
        this.gender = gender.toString();
    }

    @Override
    public String toString() {
        return gender;
    }
}
