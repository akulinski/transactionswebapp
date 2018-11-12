package com.mrowka.transactionswebapp;

import com.mrowka.transactionswebapp.core.routers.Router;
import org.apache.log4j.BasicConfigurator;

import static spark.Spark.port;

public class EntryPoint {

    public static void main(String[] args){
        BasicConfigurator.configure();
        port(8080);

        Router router = new Router();
        router.setUpRoutes();
    }
}
