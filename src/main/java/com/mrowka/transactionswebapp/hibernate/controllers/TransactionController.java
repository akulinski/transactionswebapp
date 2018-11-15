package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.hibernate.entites.TransactionEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TransactionController extends GenericController {

    public TransactionController(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Integer addTransaction(UserEntity userEntity, TransactionEntity transactionEntity) {

        Session session = factory.openSession();

        Transaction transaction = null;

        Integer transactionId = null;

        try {
            transaction = session.beginTransaction();
            transactionEntity.setUserEntity(userEntity);
            transactionEntity.setModifierId(userEntity.getId());
            transactionId = (Integer) session.save(transactionEntity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return transactionId;
    }
}
