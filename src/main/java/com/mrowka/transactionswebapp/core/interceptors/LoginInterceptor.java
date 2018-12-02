package com.mrowka.transactionswebapp.core.interceptors;

import com.mrowka.transactionswebapp.hibernate.controllers.ControllerFactory;
import com.mrowka.transactionswebapp.hibernate.controllers.UserController;
import com.mrowka.transactionswebapp.util.ControllerTypes;
import com.mrowka.transactionswebapp.util.Urls;
import com.mrowka.transactionswebapp.util.UrlsProtected;
import spark.Filter;

import java.util.ArrayList;

import static spark.Spark.before;


public class LoginInterceptor {

    private static final String ATTRIBUTE = "username";

    private UserController userController = null;

    public LoginInterceptor() {
        userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());

        UrlsProtected[] urls = UrlsProtected.values();
        ArrayList<String> protectedRoutes = new ArrayList<String>();
        for(UrlsProtected url : urls){
            protectedRoutes.add(url.getUrl());
        }

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
