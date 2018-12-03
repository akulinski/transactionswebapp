package com.mrowka.transactionswebapp.util;

/**
 * Types of controllers for controller factory
 */
public enum ControllerTypes {

    USER_CONTROLLER("user"),
    PRIVILEGE_CONTROLLER("privilege"),
    STORE_CONTROLLER("store"),
    TRANSACTION_CONTROLLER("transaction"),
    CASH_REGISTER_CONTROLLER("cash");

    private String type;

    ControllerTypes(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
