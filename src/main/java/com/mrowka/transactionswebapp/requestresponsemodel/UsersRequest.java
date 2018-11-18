package com.mrowka.transactionswebapp.requestresponsemodel;

import com.google.gson.annotations.Expose;

public class UsersRequest {

    @Expose
    private String store;

    public UsersRequest(String store) {
        this.store = store;
    }

    public UsersRequest() {

    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
