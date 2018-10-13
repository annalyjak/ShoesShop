package com.example.anna.shoesshop.model.order;

public enum TypeOfDelivery {
    HomeDelivery("dostawa za pobraniem"),
    ParcelPickUp("odbiór w punkcie"),
    ClickAndCollectInStationaryShop("odbiór w sklepie stacjonarnym");

    private final String text;

    TypeOfDelivery(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
