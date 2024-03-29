package com.mrowka.transactionswebapp.core.validators;

import com.mrowka.transactionswebapp.hibernate.controllers.ControllerFactory;
import com.mrowka.transactionswebapp.hibernate.controllers.UserController;
import com.mrowka.transactionswebapp.util.ControllerTypes;

public class LoginValidator {

    private static LoginValidator ourInstance = new LoginValidator();

    public static LoginValidator getInstance() {
        return ourInstance;
    }

    private UserController userController;


    private LoginValidator() {
        userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());
    }

    public boolean validate(String username, String password){
        boolean result = false;

        try {
            result = userController.login(username, password);
        }catch (IllegalStateException ex){
            ex.printStackTrace();
        }

        return result;
    }
}
