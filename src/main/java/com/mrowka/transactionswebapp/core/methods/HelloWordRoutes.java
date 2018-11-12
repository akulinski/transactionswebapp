package com.mrowka.transactionswebapp.core.methods;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class HelloWordRoutes {

    public Object helloWord(Request request, Response response){
        Map<String, Object> model = new HashMap<>();
        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "index.vm")
        );
    }
}
