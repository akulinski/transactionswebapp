package com.mrowka.transactionswebapp.core.interceptors;

import com.mrowka.transactionswebapp.hibernate.controllers.ControllerFactory;
import com.mrowka.transactionswebapp.hibernate.controllers.UserController;
import com.mrowka.transactionswebapp.util.FactoriesTypes;
import spark.Filter;

import static spark.Spark.before;


public class LoginInterceptor {

    private static final String ATTRIBUTE = "username";
    private static final String LOGIN_ENDPOINT = "/login";

    private UserController userController = null;

    public LoginInterceptor() {
        userController = (UserController) ControllerFactory.provideController(FactoriesTypes.USER_CONTROLLER.getType());

        String[] protectedRoutes = new String[]{"/transactions", "/users", "/add"};

        Filter f = (req, res) -> {
            if (req.session().attribute(ATTRIBUTE) == null) {
                res.redirect(LOGIN_ENDPOINT);
            }
        };

        for (String route : protectedRoutes) {
            before(route, f);
        }

    }

}
