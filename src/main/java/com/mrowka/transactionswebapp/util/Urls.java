package com.mrowka.transactionswebapp.util;

public enum Urls {

    INDEX("/"),
    TRANSACTIONS("/transactions"),
    LOGIN("/login"),
    ADD("/add"),
    USERS("/users"),
    REGISTRATION("/register"),
    GETSTORES("/getStores"),
    GETUSERS("/getUsers"),
    GETTRANSACTIONS("/getTransactions"),
    UPDATETRANSACTIONS("/updateTransactions");

    private String url;

    Urls(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
