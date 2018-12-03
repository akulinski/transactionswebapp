package com.mrowka.transactionswebapp.util;

public enum Urls {

    //Basic
    INDEX("/"),
    LOGIN("/login"),
    GET_ALL_CASHES("/getAllCashes");
    private String url;

    Urls(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
