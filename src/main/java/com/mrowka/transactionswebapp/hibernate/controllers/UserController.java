package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.core.ApplicationEngine;
import com.mrowka.transactionswebapp.hibernate.entites.PrivilegeEntity;
import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import javax.persistence.NoResultException;
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
            throw new IllegalStateException(ex.getMessage());
        } catch (NoResultException ex) {
            throw new IllegalStateException(ex.getMessage());
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

    public UserEntity checkIfUserExists(String username) {
        Session session = factory.openSession();

        Transaction transaction = null;

        UserEntity userEntity = null;

        try {
            transaction = session.beginTransaction();

            String hq = "FROM UserEntity U WHERE U.login=:username";
            Query query = session.createQuery(hq);

            query.setParameter("username", username);

            userEntity = (UserEntity) query.uniqueResult();

            transaction.commit();
            return userEntity;

        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getMessage());
        } finally {
            session.close();
        }
    }

    public UserEntity getUserById(int id) {
        Session session = factory.openSession();

        Transaction transaction = null;

        UserEntity userEntity = null;

        try {
            transaction = session.beginTransaction();

            String hq = "FROM UserEntity U WHERE U.id=:id";
            Query query = session.createQuery(hq);

            query.setParameter("id", id);

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

    public void updateUserEntity(UserEntity userEntity) throws IllegalStateException {

        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(userEntity);
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getCause());
        } finally {
            session.close();
        }
    }


    public ArrayList<UserEntity> getAllUsersInStore(StoreEntity storeEntity) throws IllegalStateException {

        Session session = factory.openSession();

        Transaction transaction = null;

        ArrayList<UserEntity> userEntities = null;

        try {
            transaction = session.beginTransaction();
            String hq = "FROM UserEntity U WHERE U.storeEntity=:storeEntity";

            Query query = session.createQuery(hq);

            query.setParameter("storeEntity", storeEntity);
            userEntities = (ArrayList<UserEntity>) query.getResultList();

            transaction.commit();

            if (userEntities != null && userEntities.size() > 0) {
                return userEntities;
            } else {
                throw new IllegalStateException("No users found for that store");
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getCause());
        } finally {
            session.close();
        }
    }

    public int updateUser(int ID, String userName, String password, String email, StoreEntity store){
        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hq = "UPDATE ";
            Query query = session.createQuery("UPDATE UserEntity U SET U.login =: login, U.email =: email, U.password =: password, U.storeEntity =: storeEntity  WHERE U.id =: id");
            query.setParameter("login", userName);
            query.setParameter("id", ID);
            query.setParameter("email", email);
            query.setParameter("password", password);
            query.setParameter("storeEntity", store);
            int result = query.executeUpdate();

            transaction.commit();
            return result;
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return -1;
    }

}
