package com.example.anna.shoesshop.model.repo;

import com.example.anna.shoesshop.model.userInfo.Account;

public class Session {
    private Account account;

    public Session(DBHelper helper) {
        account = helper.getLoggedAccount();
    }

    public Account getAccount() {
        return account;
    }
}
