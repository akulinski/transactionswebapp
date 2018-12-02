package com.mrowka.transactionswebapp.util;

public enum UrlsProtected {
    GET_STORES("/stores/getStores"),
    GET_USERS("/users/getUsers"),
    //Transactions
    TRANSACTIONS("/transactions"),
    USERS("/transactions/users"),
    UPDATE_TRANSACTIONS("/transactions/updateTransactions"),
    GET_TRANSACTIONS("/transactions/getTransactions"),
    ADD_TRANSACTIONS("/transactions/addTransaction"),
    //Manage users
    MANAGE_OTHERS("/manageothers"),
    MANAGE_OTHERS_USERINFO("/manageothers/getinfo"),
    MANAGE_OTHERS_UPDATEUSER("/manageothers/updateuser"),
    MANAGE_OTHERS_ADDUSER("/manageothers/adduser"),
    USERS_IN_STORE_ENDPOINT("/manageothers/getusers");

    private String url;

    UrlsProtected(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
