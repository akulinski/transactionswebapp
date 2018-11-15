package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.core.ApplicationEngine;

/**
 * Provides controllers for different objects
 */
public class ControllerFactory {

    private static PrivilegeController privilegeController = new PrivilegeController(ApplicationEngine.provideFactory());
    private static StoreController storeController = new StoreController(ApplicationEngine.provideFactory());
    private static TransactionController transactionController = new TransactionController(ApplicationEngine.provideFactory());
    private static UserController userController = new UserController(ApplicationEngine.provideFactory());

    public static GenericController provideController(String type) {
        switch (type.toLowerCase()) {
            case "user":
                return userController;
            case "store":
                return storeController;
            case "transaction":
                return transactionController;
            case "privilege":
                return privilegeController;
            default:
                throw new IllegalStateException("No Controller found");
        }
    }
}
