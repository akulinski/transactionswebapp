package com.mrowka.transactionswebapp.core.routers;

import com.mrowka.transactionswebapp.MockUp;
import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.core.methods.Routes;
import com.mrowka.transactionswebapp.requestresponsemodel.LoginModel;
import com.mrowka.transactionswebapp.util.Urls;
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

        get(Urls.INDEX.getUrl(), routes::mainSite);
        get(Urls.TRANSACTIONS.getUrl(), routes::showTransactions);
        get(Urls.GETSTORES.getUrl(), routes::getShops);
        get(Urls.GETUSERS.getUrl(), routes::getUsers);
        get(Urls.GETTRANSACTIONS.getUrl(), routes::getTransactions);
        get(Urls.UPDATETRANSACTIONS.getUrl(), routes::updateTransactions);

        get(Urls.LOGIN.getUrl(), routes::showLoginPage);

        post(Urls.LOGIN.getUrl(),routes::processLoginRequest);

        get(Urls.REGISTRATION.getUrl(),routes::renderRegistrationPage);
        post(Urls.REGISTRATION.getUrl(),routes::processRegistrationPage);

    }

}
