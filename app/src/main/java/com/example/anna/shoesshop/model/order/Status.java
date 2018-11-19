package com.example.anna.shoesshop.model.order;

public enum Status {
    delivered("Dostarczono"),
    during("W realizacji"),
    canceled("Odwołano");

    private final String text;

    Status(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
