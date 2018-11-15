package com.mrowka.transactionswebapp.util;

/**
 * Types of controllers for controller factory
 */
public enum FactoriesTypes {

    USER_CONTROLLER("user"),
    PRIVILEGE_CONTROLLER("privilege"),
    STORE_CONTROLLER("store"),
    TRANSACTION_CONTROLLER("transaction");

    private String type;

    FactoriesTypes(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
