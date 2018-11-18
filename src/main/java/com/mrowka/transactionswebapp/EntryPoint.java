package com.mrowka.transactionswebapp;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.core.routers.Router;
import org.apache.log4j.BasicConfigurator;

import static spark.Spark.port;
import static spark.Spark.staticFiles;
import static spark.debug.DebugScreen.enableDebugScreen;

public class EntryPoint {

    public static void main(String[] args) {

        BasicConfigurator.configure();

        port(8080);
        enableDebugScreen();
        String projectDir = System.getProperty("user.dir");
        String staticDir = "/src/main/resources";
        staticFiles.externalLocation(projectDir + staticDir);


        ApplicationEngine.initInterceptor();

        Router router = new Router();
        router.setUpRoutes();

        new MockUp().mockData();

    }
}
