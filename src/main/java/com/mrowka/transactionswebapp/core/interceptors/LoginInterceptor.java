package com.mrowka.transactionswebapp.core.interceptors;

import com.mrowka.transactionswebapp.hibernate.controllers.ControllerFactory;
import com.mrowka.transactionswebapp.hibernate.controllers.UserController;
import com.mrowka.transactionswebapp.util.ControllerTypes;
import com.mrowka.transactionswebapp.util.Urls;
import spark.Filter;

import static spark.Spark.before;


public class LoginInterceptor {

    private static final String ATTRIBUTE = "username";

    private UserController userController = null;

    public LoginInterceptor() {
        userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());

        String[] protectedRoutes = new String[]{Urls.TRANSACTIONS.getUrl(), Urls.USERS.getUrl(), Urls.ADD.getUrl(),Urls.REGISTRATION.getUrl(),Urls.MY_ACCOUNT.getUrl(),Urls.MANAGE_OTHERS.getUrl()};

        Filter f = (req, res) -> {
            if (req.session().attribute(ATTRIBUTE) == null) {

                req.session().attribute("endpoint",req.url());

                res.redirect(Urls.LOGIN.getUrl());
            }
        };

        for (String route : protectedRoutes) {
            before(route, f);
        }

    }

}
