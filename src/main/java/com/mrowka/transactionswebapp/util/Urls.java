package com.mrowka.transactionswebapp.util;

public enum Urls {

    INDEX("/"),
    TRANSACTIONS("/transactions"),
    LOGIN("/login"),
    ADD("/add"),
    USERS("/users"),
    REGISTRATION("/register"),
    MY_ACCOUNT("/myaccount");

    private String url;

    Urls(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
