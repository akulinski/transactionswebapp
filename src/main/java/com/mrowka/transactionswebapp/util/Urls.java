package com.mrowka.transactionswebapp.util;

public enum Urls {

    INDEX("/"),
    TRANSACTIONS("/transactions"),
    LOGIN("/login"),
    ADD("/add"),
    USERS("/users"),
    REGISTRATION("/register"),
    MY_ACCOUNT("/myaccount"),
    MANAGE_OTHERS("/manageothers"),
    USERS_IN_STORE_ENDPOINT("/manageothers/getusers");

    private String url;

    Urls(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
