package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class StoreController extends GenericController {

    public StoreController(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public Integer addStore(String storeName, String address, boolean active) {
        Session session = factory.openSession();

        Transaction transaction = null;

        Integer storeId = null;

        try {
            transaction = session.beginTransaction();
            StoreEntity storeEntity = new StoreEntity(storeName, address, active);
            storeId = (Integer) session.save(storeEntity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return storeId;
    }

    public StoreEntity getStoreById(String storeName) {
        Session session = factory.openSession();

        Transaction transaction = null;

        Integer storeId = null;

        try {
            transaction = session.beginTransaction();

            String hq = "FROM StoreEntity S WHERE S.storeName =:storeName";

            Query query = session.createQuery(hq);

            query.setParameter("storeName", storeName);

            StoreEntity storeEntity = (StoreEntity) query.getSingleResult();

            transaction.commit();

            return storeEntity;
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

}
