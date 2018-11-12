package com.mrowka.transactionswebapp.core.routers;

import com.mrowka.transactionswebapp.core.methods.HelloWordRoutes;

import static spark.Spark.get;


public class HelloWordRouter {

    public void setUpRoutes(){

        HelloWordRoutes helloWordRoutes = new HelloWordRoutes();

        get("/helloword",helloWordRoutes::helloWord);
    }
}
