package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import com.mrowka.transactionswebapp.util.ControllerTypes;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserControllerTest {
    private UserController userController;
    private StoreController storeController;

    @Before
    public void init(){
        userController = (UserController) ControllerFactory.provideController(ControllerTypes.USER_CONTROLLER.getType());
        storeController = (StoreController) ControllerFactory.provideController(ControllerTypes.STORE_CONTROLLER.getType());
        storeController.addStore("store","store",true);
    }

    @Test
    public void addUser() {

        userController.addUser("test","test","test",storeController.getStoreByName("store"));
        UserEntity userEntity = userController.getUser("test","test");
        assertNotNull(userEntity);
    }

    @Test(expected = NoResultException.class)
    public void getUserThatDostExists() {
        userController.getUser("aaaaaaaaaaaaaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test
    public void addUserByEntity() {
        UserEntity userEntity = new UserEntity("test2","test2","test2",new Date());
        userController.addUserByEntity(userEntity,storeController.getStoreByName("store"));

        UserEntity getFromDb = userController.getUser("test2","test2");
        assertNotNull(getFromDb);
    }

    @Test
    public void login() {
        userController.addUser("mock","mock","mock",storeController.getStoreByName("store"));
        boolean login = userController.login("mock","mock");
        assertTrue(login);
    }

}