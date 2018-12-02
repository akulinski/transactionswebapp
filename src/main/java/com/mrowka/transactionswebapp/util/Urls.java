package com.mrowka.transactionswebapp.util;

public enum Urls {

    //Basic
    INDEX("/"),
    LOGIN("/login");

    private String url;

    Urls(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
