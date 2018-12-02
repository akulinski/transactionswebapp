package com.mrowka.transactionswebapp.util;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.hibernate.controllers.*;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import spark.Request;

import java.util.Map;

public class ModelHelper {
    private UserController userController;
    private StoreController storeController;
    private PrivilegeController privilegeController;
    private TransactionController transactionController;

    public ModelHelper() {
        userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());
        storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        privilegeController = (PrivilegeController) ControllerFactory.provideController(ControllerTypes.PRIVILEGE_CONTROLLER.getType());
        transactionController = (TransactionController) ControllerFactory.provideController(ControllerTypes.TRANSACTION_CONTROLLER.getType());
    }

    public void putUser(Request request, Map<String, Object> model) {
        if(request.session().attribute("username")!=null&&!request.session().attribute("username").equals("")){
            String username = request.session().attribute("username");

            UserEntity userEntity = userController.getUserByUserName(username);

            model.put("entity", userEntity);
            model.put("director", false);

            userEntity.getPrivilegeEntity().forEach(privilegeEntity -> {
                if (privilegeEntity.getType() == 3) {
                    model.put("director", true);
                }
            });
        }
    }

    public void putUserAndStore(Request request, Map<String, Object> model){
        String username = request.session().attribute("username");

        UserEntity userEntity = userController.getUserByUserName(username);

        model.put("director", false);
        model.put("allStores", storeController.getAllStores());

        model.put("entity", userEntity);

        //needs refactoring
        userEntity.getPrivilegeEntity().forEach(privilegeEntity -> {
            if (privilegeEntity.getType() == 3) {
                model.put("director", true);
            }
        });
    }
}
