package com.mrowka.transactionswebapp.core.methods;

import com.mrowka.transactionswebapp.core.validators.LoginValidator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.template.velocity.VelocityTemplateEngine;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class Routes {

    public Object mainSite(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "index.vm")
        );
    }

    public Object showTransactions(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "transactionsSite.vm")
        );
    }

    /**
     * Renders login page contains no login logic
     *
     * @param request
     * @param response
     * @return
     */
    public Object showLoginPage(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "login/login.vm")
        );
    }

    public Object processLoginRequest(Request request,Response response){
        String password = request.queryParams("password");
        String username = request.queryParams("username");

        if(LoginValidator.getInstance().validate(username,password)){

            request.session(true).attribute("username",username);
            response.redirect("/");
        }else{
            response.redirect("/login");
        }

        return null;
    }
}

