package com.example.anna.shoesshop.model.userInfo;

public class Client {
    private String name;
    private String surname;
    private String phoneNumber;
    private Sex sex;

    public Client(String name, String surname, String phoneNumber, Sex sex) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }
}
