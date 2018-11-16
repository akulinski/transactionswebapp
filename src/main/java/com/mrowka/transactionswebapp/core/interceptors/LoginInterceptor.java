package com.mrowka.transactionswebapp.core.interceptors;

import com.mrowka.transactionswebapp.hibernate.controllers.ControllerFactory;
import com.mrowka.transactionswebapp.hibernate.controllers.UserController;
import com.mrowka.transactionswebapp.util.FactoriesTypes;

import static spark.Spark.before;


public class LoginInterceptor {

    private static final String ATTRIBUTE = "user";
    private static final String LOGIN_ENDPOINT = "/login";

    private UserController userController = null;

    public LoginInterceptor() {
        userController = (UserController) ControllerFactory.provideController(FactoriesTypes.USER_CONTROLLER.getType());

        before("/*/", (req, resp) -> {

            if (req.attribute(ATTRIBUTE) == null) {
                resp.redirect(LOGIN_ENDPOINT);

            }

        });
    }

}
