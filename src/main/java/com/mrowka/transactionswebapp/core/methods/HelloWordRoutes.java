package com.mrowka.transactionswebapp.core.methods;

import spark.Request;
import spark.Response;

public class HelloWordRoutes {

    public Object helloWord(Request request, Response response){
        return "Hello Word";
    }
}
