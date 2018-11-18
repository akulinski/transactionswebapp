package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;

public class UserController extends GenericController {

    private Logger logger = null;

    public UserController(SessionFactory sessionFactory) {
        super(sessionFactory);
        logger = ApplicationEngine.provideLogger();
    }

    /**
     * Adds user to database
     *
     * @param login    username of user
     * @param password pre-hashed password
     * @param email    email of user
     * @return id of added user
     */
    public Integer addUser(String login, String password, String email, StoreEntity storeEntity) {
        Session session = factory.openSession();

        Transaction transaction = null;

        Integer userId = null;

        try {
            transaction = session.beginTransaction();
            UserEntity userEntity = new UserEntity(login, password, email, new Date(), storeEntity);
            userId = (Integer) session.save(userEntity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return userId;
    }

    public Integer addUserByEntity(UserEntity userEntity, StoreEntity storeEntity) {
        Session session = factory.openSession();

        Transaction transaction = null;

        Integer userId = null;

        try {
            transaction = session.beginTransaction();
            userEntity.setStoreEntity(storeEntity);
            userId = (Integer) session.save(userEntity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return userId;
    }

    public int UserEntity (int id) {
        Session session = factory.openSession();

        Transaction transaction = null;

        Integer userId = null;
        try {
            transaction = session.beginTransaction();
            String hq = "FROM UserEntity U WHERE U.login=:username AND U.password=:password";
            Query query = session.createQuery(hq);



            UserEntity userEntity = (UserEntity) query.getSingleResult();

            transaction.commit();
            if (userEntity != null) {
                return 1;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return 0;
    }

    public boolean login(String username, String password) {
        Session session = factory.openSession();

        Transaction transaction = null;

        Integer userId = null;
        try {
            transaction = session.beginTransaction();
            String hq = "FROM UserEntity U WHERE U.login=:username AND U.password=:password";
            Query query = session.createQuery(hq);

            query.setParameter("username", username);
            query.setParameter("password", password);

            UserEntity userEntity = (UserEntity) query.getSingleResult();

            transaction.commit();
            if (userEntity != null) {
                return true;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public ArrayList<UserEntity> getUsersByStoreEntity(StoreEntity storeEntity) {
        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            String hq = "FROM UserEntity U WHERE U.storeEntity=:storeEntity";
            Query query = session.createQuery(hq);

            query.setParameter("storeEntity", storeEntity);

            ArrayList<UserEntity> userEntity = (ArrayList<UserEntity>) query.getResultList();

            transaction.commit();
            if (userEntity != null) {
                return userEntity;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public UserEntity getUser(String username, String password) {
        Session session = factory.openSession();

        Transaction transaction = null;

        UserEntity userEntity = null;

        try {
            transaction = session.beginTransaction();
            String hq = "FROM UserEntity U WHERE U.login=:username AND U.password=:password";
            Query query = session.createQuery(hq);

            query.setParameter("username", username);
            query.setParameter("password", password);

            userEntity = (UserEntity) query.getSingleResult();

            transaction.commit();
            if (userEntity != null) {
                return userEntity;
            } else {
                throw new IllegalStateException("No user found");
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    public UserEntity getUserByUserName(String username) {
        Session session = factory.openSession();

        Transaction transaction = null;

        UserEntity userEntity = null;

        try {
            transaction = session.beginTransaction();

            String hq = "FROM UserEntity U WHERE U.login=:username";
            Query query = session.createQuery(hq);

            query.setParameter("username", username);

            userEntity = (UserEntity) query.getSingleResult();

            transaction.commit();
            if (userEntity != null) {
                return userEntity;
            } else {
                throw new IllegalStateException("No user found");
            }

        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getMessage());
        } finally {
            session.close();
        }

    }
}
