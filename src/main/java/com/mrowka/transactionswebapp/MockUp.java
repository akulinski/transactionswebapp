package com.mrowka.transactionswebapp;

import com.mrowka.transactionswebapp.hibernate.controllers.*;
import com.mrowka.transactionswebapp.hibernate.entites.TransactionEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import com.mrowka.transactionswebapp.util.ControllerTypes;
import com.mrowka.transactionswebapp.util.RandomStringGenerator;

import java.util.Date;

/**
 * Object used for inserting mock data to database
 */
public class MockUp {

    private UserController userController;
    private StoreController storeController;
    private PrivilegeController privilegeController;
    private TransactionController transactionController;

    public MockUp() {
        userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());
        storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        privilegeController = (PrivilegeController) ControllerFactory.provideController(ControllerTypes.PRIVILEGE_CONTROLLER.getType());
        transactionController = (TransactionController) ControllerFactory.provideController(ControllerTypes.TRANSACTION_CONTROLLER.getType());
    }

    public void mockData() {
        storeController.addStore("test1", "test1", true);
        storeController.addStore("test2", "test2", true);

        for (int i = 0; i < 20; i++) {
            UserEntity userEntity = new UserEntity(RandomStringGenerator.generateRandomString(6), RandomStringGenerator.generateRandomString(6), RandomStringGenerator.generateRandomString(6), new Date());
            TransactionEntity transactionEntity = new TransactionEntity(userEntity, false, new Date(), new Date(), 0);

            if (i % 2 == 0) {
                userController.addUserByEntity(userEntity, storeController.getStoreByName("test1"));
                privilegeController.addPrivilege(1, userEntity);
                transactionController.addTransaction(userEntity, transactionEntity);
            } else {
                userController.addUserByEntity(userEntity, storeController.getStoreByName("test2"));
                privilegeController.addPrivilege(1, userEntity);
                transactionController.addTransaction(userEntity, transactionEntity);
            }
        }

        UserEntity userEntity = new UserEntity("username", "password", "email",new Date());
        userController.addUserByEntity(userEntity, storeController.getStoreById(1));

        privilegeController.addPrivilege(3,userEntity);

        transactionController.addTransaction(userEntity,new TransactionEntity(null,true,new Date(),new Date(),0));

    }
}