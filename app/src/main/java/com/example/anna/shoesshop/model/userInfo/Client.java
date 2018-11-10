package com.example.anna.shoesshop.model.userInfo;

import com.example.anna.shoesshop.model.database.ClientDb;
import com.example.anna.shoesshop.model.repo.DBUtil;

public class Client {
    private String name;
    private String surname;
    private String phoneNumber;
    private Gender sex;

    public Client(String name, String surname, String phoneNumber, Gender sex) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }

    public Client(ClientDb clientDb) {
        this.name = clientDb.getName();
        this.surname =clientDb.getSurname();
        this.phoneNumber = clientDb.getPhoneNumber();
        this.sex = DBUtil.transferToEnum(clientDb.getSex());
    }

    public ClientDb transferToDb() {
        return new ClientDb(name, surname, phoneNumber, sex);
    }
}
