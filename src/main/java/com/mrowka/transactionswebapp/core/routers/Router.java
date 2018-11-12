package com.mrowka.transactionswebapp.core.routers;

import com.mrowka.transactionswebapp.core.methods.Routes;

import static spark.Spark.get;


public class Router {

    public void setUpRoutes(){

        Routes routes = new Routes();

        get("/",routes::mainSite);
        get("/transactions",routes::showTransactions);
}
}
