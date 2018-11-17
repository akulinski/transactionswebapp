package com.mrowka.transactionswebapp.core.routers;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.core.methods.Routes;
import com.mrowka.transactionswebapp.requestresponsemodel.LoginModel;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Basic routing class in future needs splitting so one router contains logic for one part of application
 */
public class Router {
    private Logger logger = ApplicationEngine.provideLogger();

    public void setUpRoutes() {


        Routes routes = new Routes();

        get("/", routes::mainSite);
        get("/transactions", routes::showTransactions);

        get("/login", routes::showLoginPage);

        get("/user/transactions",(r,q)-> "USERS");
        post("/login",routes::processLoginRequest);


    }
}
