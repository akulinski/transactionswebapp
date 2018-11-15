package com.mrowka.transactionswebapp;

import com.mrowka.transactionswebapp.core.routers.HelloWordRouter;
import org.apache.log4j.BasicConfigurator;

import static spark.Spark.port;

public class EntryPoint {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        port(8080);

        HelloWordRouter helloWordRouter = new HelloWordRouter();
        helloWordRouter.setUpRoutes();

        new MockUp().mockData();
    }
}
