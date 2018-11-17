package com.mrowka.transactionswebapp.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mrowka.transactionswebapp.core.interceptors.LoginInterceptor;
import com.mrowka.transactionswebapp.hibernate.entites.PrivilegeEntity;
import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;
import com.mrowka.transactionswebapp.hibernate.entites.TransactionEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used for providing dependencies to different parts of application
 */
public class ApplicationEngine {

    private static SessionFactory factory = null;

    private static Logger logger = null;

    private static LoginInterceptor loginInterceptor = null;

    private static Gson gson;

    /**
     * Provies  same instance of Session factory for all controllers
     *
     * @return
     */
    public static SessionFactory provideFactory() {

        if (factory == null) {
            synchronized (ApplicationEngine.class) {//synchronisation of different threads for random access
                factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(StoreEntity.class).addAnnotatedClass(UserEntity.class).addAnnotatedClass(PrivilegeEntity.class).addAnnotatedClass(TransactionEntity.class).buildSessionFactory();
            }
        }

        return factory;
    }

    public static Logger provideLogger() {

        if (logger == null) {
            synchronized (ApplicationEngine.class) {
                logger = LoggerFactory.getLogger(ApplicationEngine.class);
            }
        }

        return logger;
    }


    public static Gson provideGson() {

        if (gson == null) {
            synchronized (ApplicationEngine.class) {
                gson = new GsonBuilder().create();
            }
        }

        return gson;
    }

    public static void initInterceptor() {
        loginInterceptor = new LoginInterceptor();
    }




}
