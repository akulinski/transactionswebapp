package com.mrowka.transactionswebapp.core.methods;

import com.mrowka.transactionswebapp.core.validators.LoginValidator;
import com.mrowka.transactionswebapp.hibernate.controllers.ControllerFactory;
import com.mrowka.transactionswebapp.hibernate.controllers.PrivilegeController;
import com.mrowka.transactionswebapp.hibernate.controllers.StoreController;
import com.mrowka.transactionswebapp.hibernate.controllers.UserController;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import com.mrowka.transactionswebapp.util.ControllerTypes;
import com.mrowka.transactionswebapp.util.Urls;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Routes {

    private UserController userController;
    private StoreController storeController;
    private PrivilegeController privilegeController;

    public Routes(){
        userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());
        storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        privilegeController = (PrivilegeController) ControllerFactory.provideController(ControllerTypes.PRIVILEGE_CONTROLLER.getType());

    }

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

            request.session().attribute("username",username);
            response.redirect(Urls.INDEX.getUrl());
        }else{
            response.redirect(Urls.LOGIN.getUrl());
        }

        return null;
    }

    /**
     * Renders registration page if user has privilege of 3 (director) than he is able to chose store
     * @param request
     * @param response
     * @return
     */
    public Object renderRegistrationPage(Request request,Response response){

        Map<String, Object> model = new HashMap<>();


        String username = request.session().attribute("username");

        UserEntity userEntity = userController.getUsetByUsername(username);

        model.put("entity",userEntity);
        model.put("director",false);

        userEntity.getPrivilegeEntity().forEach(privilegeEntity -> {
            if (privilegeEntity.getType() == 3){
                model.put("director",true);
            }
        });

        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "registration/registerPage.vm")
        );
    }

    public Object processRegistrationPage(Request request,Response response){

        Map<String,Object> model = new HashMap<>();

        String username = request.queryParams("username");
        String password = request.queryParams("password");
        String email = request.queryParams("email");

        String store = request.queryParams("store");

        String type = request.queryParams("type");

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        //checking if no store was selected if so registration comes form manager not director and store is managers store
        String registratorName = request.session().attribute("username");

        if(store == null){
            UserEntity registrator = userController.getUsetByUsername(registratorName);
            store = registrator.getStoreEntity().getStoreName();
        }

        if(type == null){
            type = "1";
        }
        UserEntity userEntity = new UserEntity(username,password,email,new Date());

        userController.addUserByEntity(userEntity,storeController.getStoreById(store));

        privilegeController.addPrivilege(Integer.parseInt(type),userEntity);

        model.put("createdUser",userEntity);

        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "registration/registerPage.vm")
        );
    }
}

