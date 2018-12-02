package com.mrowka.transactionswebapp.core.routers;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.core.methods.Routes;
import com.mrowka.transactionswebapp.util.Urls;
import com.mrowka.transactionswebapp.util.UrlsProtected;
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
        get(UrlsProtected.GET_STORES.getUrl(), routes::getShops);
        get(UrlsProtected.GET_USERS.getUrl(), routes::getUsers);
        //Transactions
        get(UrlsProtected.TRANSACTIONS.getUrl(), routes::showTransactions);
        get(UrlsProtected.GET_TRANSACTIONS.getUrl(), routes::getTransactions);
        get(UrlsProtected.UPDATE_TRANSACTIONS.getUrl(), routes::updateTransactions);
        get(UrlsProtected.ADD_TRANSACTIONS.getUrl(), routes::addTransaction);
        //Login
        get(Urls.LOGIN.getUrl(), routes::showLoginPage);
        post(Urls.LOGIN.getUrl(), routes::processLoginRequest);

        //Manage users
        get(UrlsProtected.MANAGE_OTHERS.getUrl(), routes::renderManageOthersPage);
        post(UrlsProtected.USERS_IN_STORE_ENDPOINT.getUrl(), routes::getAllUsersInStore);
        get(UrlsProtected.MANAGE_OTHERS_USERINFO.getUrl(), routes::manageOthersGetInfo);
        get(UrlsProtected.MANAGE_OTHERS_UPDATEUSER.getUrl(), routes::manageOthersUpdateUser);
        get(UrlsProtected.MANAGE_OTHERS_ADDUSER.getUrl(), routes::manageOthersAddUser);

    }

}
