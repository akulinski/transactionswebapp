package com.mrowka.transactionswebapp.core.methods;


import com.mrowka.transactionswebapp.core.validators.LoginValidator;
import com.mrowka.transactionswebapp.hibernate.controllers.ControllerFactory;
import com.mrowka.transactionswebapp.hibernate.controllers.PrivilegeController;
import com.mrowka.transactionswebapp.hibernate.controllers.StoreController;
import com.mrowka.transactionswebapp.hibernate.controllers.UserController;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import com.mrowka.transactionswebapp.util.ControllerTypes;
import com.mrowka.transactionswebapp.util.ToJson;
import com.mrowka.transactionswebapp.util.Urls;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.hibernate.controllers.ControllerFactory;
import com.mrowka.transactionswebapp.hibernate.controllers.StoreController;
import com.mrowka.transactionswebapp.hibernate.controllers.TransactionController;
import com.mrowka.transactionswebapp.hibernate.controllers.UserController;
import com.mrowka.transactionswebapp.hibernate.entites.TransactionEntity;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.*;

import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;

public class Routes {
    Logger logger = Logger.getLogger(getClass().getName());

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

        UserEntity userEntity = userController.getUserByUserName(username);

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

    public Object processRegistrationPage(Request request,Response response) {

        Map<String, Object> model = new HashMap<>();

        String username = request.queryParams("username");
        String password = request.queryParams("password");
        String email = request.queryParams("email");

        String store = request.queryParams("store");

        String type = request.queryParams("type");

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        //checking if no store was selected if so registration comes form manager not director and store is managers store
        String registratorName = request.session().attribute("username");

        if (store == null) {
            UserEntity registrator = userController.getUserByUserName(registratorName);
            store = registrator.getStoreEntity().getStoreName();
        }

        if (type == null) {
            type = "1";
        }
        UserEntity userEntity = new UserEntity(username, password, email, new Date());

        userController.addUserByEntity(userEntity, storeController.getStoreByName(store));

        privilegeController.addPrivilege(Integer.parseInt(type), userEntity);

        model.put("createdUser", userEntity);

        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "registration/registerPage.vm")
        );
    }
    public Object getShops(Request request, Response response){
        Gson gson = new GsonBuilder().create();
        StoreController storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        ArrayList<StoreEntity> storeEntities = storeController.getAllStores();

        return new ToJson().storeToJson(storeEntities);
    }

    public Object getUsers(Request request, Response response){
        int id = Integer.parseInt(request.queryParams("shopId"));

        StoreController storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        UserController userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());

        StoreEntity storeEntity = storeController.getStoreById(id);
        ArrayList<UserEntity> userEntities = userController.getUsersByStoreEntity(storeEntity);

        return new ToJson().userToJson(userEntities);
    }



    public Object getTransactions(Request request, Response response) throws ParseException {
        TransactionController transactionController = (TransactionController) ControllerFactory.provideController(ControllerTypes.TRANSACTION_CONTROLLER.getType());
        UserController userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());

        String dateFrom = request.queryParams("datePickerFrom").replace("-", "/");
        String dateTo = request.queryParams("datePickerTo").replace("-", "/");
        int selectId = Integer.parseInt(request.queryParams("selectId"));
        String userName = request.queryParams("selectUser");

        System.out.println("request params: "+dateFrom+dateTo+selectId+userName);

        UserEntity userEntity = userController.getUserByUserName(userName);
        if (userEntity==null)return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dateFromD = null;
        Date dateToD = null;
        dateFromD = simpleDateFormat.parse(dateFrom);
        dateToD = simpleDateFormat.parse(dateTo);


        ArrayList<TransactionEntity> transactionEntities = transactionController.getTransactionsByUserEntityAndDate(userEntity, dateFromD, dateToD);

        return new ToJson().transactionToJson(transactionEntities);
    }

    public Object updateTransactions(Request request, Response response){
        boolean isApproved = Boolean.getBoolean(request.queryParams("isApproved"));
        int transactionId = Integer.parseInt(request.queryParams("id"));
        String dateOfCreation = request.queryParams("dateOfCreation");
        String dateOfModification = request.queryParams("dateOfModification");
        int modifierId = Integer.parseInt(request.queryParams("modifierId"));

        TransactionController transactionController = (TransactionController) ControllerFactory.provideController(ControllerTypes.TRANSACTION_CONTROLLER.getType());

        transactionController.updateTransaction(transactionId, modifierId, dateOfModification, isApproved);

        return Boolean.toString(isApproved)+transactionId+dateOfCreation+dateOfModification+modifierId;
    }
}


