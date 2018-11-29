package com.mrowka.transactionswebapp.core.routers;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.core.methods.Routes;
import com.mrowka.transactionswebapp.util.Urls;
import org.slf4j.Logger;

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
        get(Urls.GET_STORES.getUrl(), routes::getShops);
        get(Urls.GET_USERS.getUrl(), routes::getUsers);

        get(Urls.GET_TRANSACTIONS.getUrl(), routes::getTransactions);
        get(Urls.UPDATE_TRANSACTIONS.getUrl(), routes::updateTransactions);
        get(Urls.ADD_TRANSACTIONS.getUrl(), routes::addTransaction);

        get(Urls.LOGIN.getUrl(), routes::showLoginPage);

        post(Urls.LOGIN.getUrl(), routes::processLoginRequest);

        get(Urls.REGISTRATION.getUrl(), routes::renderRegistrationPage);
        post(Urls.REGISTRATION.getUrl(), routes::processRegistrationPage);

        get(Urls.MY_ACCOUNT.getUrl(), routes::renderManageAccountPage);
        post(Urls.MY_ACCOUNT.getUrl(), routes::processUpdate);

        get(Urls.MANAGE_OTHERS.getUrl(), routes::renderManageOthersPage);
        post(Urls.USERS_IN_STORE_ENDPOINT.getUrl(), routes::getAllUsersInStore);
        get(Urls.MANAGE_OTHERS_USERINFO.getUrl(), routes::manageOthersGetInfo);
        get(Urls.MANAGE_OTHERS_UPDATEUSER.getUrl(), routes::manageOthersUpdateUser);
        get(Urls.MANAGE_OTHERS_ADDUSER.getUrl(), routes::manageOthersAddUser);

    }

}
