package com.mrowka.transactionswebapp.core.methods;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.core.validators.LoginValidator;
import com.mrowka.transactionswebapp.hibernate.controllers.*;
import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;
import com.mrowka.transactionswebapp.hibernate.entites.TransactionEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import com.mrowka.transactionswebapp.requestresponsemodel.UsersRequest;
import com.mrowka.transactionswebapp.util.ControllerTypes;
import com.mrowka.transactionswebapp.util.ModelHelper;
import com.mrowka.transactionswebapp.util.Urls;
import org.slf4j.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;
import spark.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Routes {

    private UserController userController;
    private StoreController storeController;
    private PrivilegeController privilegeController;
    private TransactionController transactionController;
    private Logger logger;
    private  ModelHelper modelHelper;

    public Routes() {
        userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());
        storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        privilegeController = (PrivilegeController) ControllerFactory.provideController(ControllerTypes.PRIVILEGE_CONTROLLER.getType());
        transactionController = (TransactionController) ControllerFactory.provideController(ControllerTypes.TRANSACTION_CONTROLLER.getType());
        logger = ApplicationEngine.provideLogger();
        modelHelper = new ModelHelper();
    }

    public Object mainSite(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();

        modelHelper.putUser(request, model);

        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "Main/index.vm")
        );
    }

    public Object showTransactions(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();

        modelHelper.putUser(request, model);

        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "Transactions/transactionsSite.vm")
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

    public Object processLoginRequest(Request request, Response response) {
        String password = request.queryParams("password");
        String username = request.queryParams("username");

        if (LoginValidator.getInstance().validate(username, password)) {

            request.session().attribute("username", username);

            if (StringUtils.isNotEmpty(request.session().attribute("endpoint"))
                    && request.session().attribute("endpoint") != null) {

                String url = request.session().attribute("endpoint");
                request.session().removeAttribute("endpoint");
                response.redirect(url);

            } else {
                response.redirect(Urls.INDEX.getUrl());
            }
        } else {
            response.redirect(Urls.LOGIN.getUrl());
        }

        return null;
    }

    public Object renderManageOthersPage(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();

        modelHelper.putUserAndStore(request, model);

        return new VelocityTemplateEngine().render(
                new ModelAndView(model, "accountmanagment/manageothers/manageothers.vm")
        );
    }

    public Object getAllUsersInStore(Request request, Response response) {

        response.type("application/json");

        UsersRequest usersRequest = ApplicationEngine.provideGson().fromJson(request.body(), UsersRequest.class);

        StoreEntity storeEntity = storeController.getStoreByName(usersRequest.getStore());

        ArrayList<UserEntity> users = userController.getAllUsersInStore(storeEntity);

        return ApplicationEngine.provideGsonWithExcludions().toJson(users);
    }

    public Object getShops(Request request, Response response) {
        StoreController storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        ArrayList<StoreEntity> storeEntities = storeController.getAllStores();

        return ApplicationEngine.provideGsonWithExcludions().toJson(storeEntities);
    }

    public Object getUsers(Request request, Response response) {
        int id = Integer.parseInt(request.queryParams("shopId"));

        StoreController storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        UserController userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());

        StoreEntity storeEntity = storeController.getStoreById(id);
        ArrayList<UserEntity> userEntities = userController.getUsersByStoreEntity(storeEntity);

        return ApplicationEngine.provideGsonWithExcludions().toJson(userEntities);
    }


    public Object getTransactions(Request request, Response response) throws ParseException {
        TransactionController transactionController = (TransactionController) ControllerFactory.provideController(ControllerTypes.TRANSACTION_CONTROLLER.getType());
        UserController userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());

        String dateFrom = request.queryParams("datePickerFrom").replace("-", "/");
        String dateTo = request.queryParams("datePickerTo").replace("-", "/");
        int selectId = Integer.parseInt(request.queryParams("selectId"));
        String userName = request.queryParams("selectUser");

        System.out.println("request params: " + dateFrom + dateTo + selectId + userName);

        UserEntity userEntity = userController.getUserByUserName(userName);
        if (userEntity == null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date dateFromD = null;
        Date dateToD = null;
        dateFromD = simpleDateFormat.parse(dateFrom);
        dateToD = simpleDateFormat.parse(dateTo);


        ArrayList<TransactionEntity> transactionEntities = transactionController.getTransactionsByUserEntityAndDate(userEntity, dateFromD, dateToD);

        return ApplicationEngine.provideGsonWithExcludions().toJson(transactionEntities);
    }

    public Object addTransaction(Request request, Response response){
        String userName = request.queryParams("username");

        UserEntity userEntity = userController.getUserByUserName(userName);

        Boolean isApproved = Boolean.valueOf(request.queryParams("isApproved"));

        TransactionEntity transactionEntity = new TransactionEntity(userEntity, isApproved, new Date(), new Date(), 0);

        Integer transactionId = transactionController.addTransaction(userEntity, transactionEntity);
        if(transactionId!=null)return "{\"Message\": \"Transakcja Zapisana\"}";

        return "{\"Message\": \"Transakcja nie została zapisana\"}";
    }

    public Object updateTransactions(Request request, Response response) {
        Boolean isApproved = Boolean.valueOf(request.queryParams("isApproved"));
        int transactionId = Integer.parseInt(request.queryParams("id"));
        String dateOfCreation = request.queryParams("dateOfCreation");
        String dateOfModification = request.queryParams("dateOfModification");
        int modifierId = Integer.parseInt(request.queryParams("modifierId"));

        transactionController.updateTransaction(transactionId, modifierId, dateOfModification, isApproved);

        return Boolean.toString(isApproved) + transactionId + dateOfCreation + dateOfModification + modifierId;
    }

    public Object manageOthersGetInfo(Request request, Response response){
        String userName = request.queryParams("userName");
        UserController userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());

        return ApplicationEngine.provideGsonWithExcludions().toJson(userController.getUserByUserName(userName));
    }

    public Object manageOthersUpdateUser(Request request, Response response){
        UserController userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());
        StoreController storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());

        int ID = Integer.parseInt(request.queryParams("ID"));
        String userName = request.queryParams("Username");
        String password = request.queryParams("password");
        String passwordRepeat = request.queryParams("passwordRepeat");
        String email = request.queryParams("email");
        int store = Integer.parseInt(request.queryParams("store"));
        int privilege = Integer.parseInt(request.queryParams("privilege"));

        if(userName.equals(""))return "{\"Message\": \"Proszę ustawić nazwę użytkownika\"}";

        if(password.equals(passwordRepeat)){
            if(password.equals("")) password = userController.getUserById(ID).getPassword();

            if(userController.updateUser(ID, userName, password, email, storeController.getStoreById(store)) >= 0)return "{\"Message\": \"Konto zostało zapisane!\"}";
            else return "{\"Message\": \"Konto nie zostało zapisane!\"}";
        }
        else return "{\"Message\": \"Hasła podane w polach się nie zgadzają\"}";
    }

    public Object manageOthersAddUser(Request request, Response response){
        String userName = request.queryParams("Username");
        String password = request.queryParams("password");
        String passwordRepeat = request.queryParams("passwordRepeat");
        String email = request.queryParams("email");
        int storeId = Integer.parseInt(request.queryParams("store"));
        int privilege = Integer.parseInt(request.queryParams("privilege"));

        UserController userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());
        PrivilegeController privilegeController = (PrivilegeController) ControllerFactory.provideController(ControllerTypes.PRIVILEGE_CONTROLLER.getType());

        if(!password.equals(passwordRepeat))return "{\"Message\": \"Hasła się nie zgadzają\"}";
        else if(password.equals(""))return "{\"Message\": \"Pole hasła jest puste\"}";
        else if(userController.checkIfUserExists(userName)!=null)return "{\"Message\": \"Już istnieje użytkownik o takiej nazwie\"}";

        UserEntity userEntity = new UserEntity(userName, password, email, new Date());
        if(userController.addUserByEntity(userEntity, storeController.getStoreById(storeId))==null)return "{\"Message\": \"Nie udało się założyć konta\"}";
        privilegeController.addPrivilege(privilege, userEntity);
        return "{\"Message\": \"Udało się utworzyć użytkownika\"}";
    }
}


