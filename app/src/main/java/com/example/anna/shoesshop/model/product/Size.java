package com.example.anna.shoesshop.model.product;

import android.support.annotation.NonNull;

public enum Size {
    universal("Uniwersalny"),
    woman36("36"),
    woman37("37"),
    woman38("38"),
    woman39("39"),
    woman40("40"),
    woman41("41"),
    woman42("42"),
    man41("41"),
    man42("42"),
    man43("43"),
    man44("44"),
    man45("45"),
    man46("46"),
    kid30("30"),
    kid31("31"),
    kid32("32"),
    kid33("33"),
    kid34("34"),
    kid35("35");

    private final String text;

    Size(String text) {
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }

}
