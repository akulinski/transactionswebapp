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
    USERS_IN_STORE_ENDPOINT("/manageothers/getusers"),
    GET_STORES("/getStores"),
    GET_USERS("/getUsers"),
    GET_TRANSACTIONS("/getTransactions"),
    UPDATE_TRANSACTIONS("/updateTransactions");

    private String url;

    Urls(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
