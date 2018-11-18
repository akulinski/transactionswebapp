package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.hibernate.entites.TransactionEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import org.eclipse.jetty.server.Authentication;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;

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

    public ArrayList<TransactionEntity> getTransactionsByUserEntity(UserEntity userEntity) {
        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hq = "FROM TransactionEntity T WHERE T.userEntity =:userEntity";
            Query query = session.createQuery(hq);
            query.setParameter("userEntity", userEntity);

            ArrayList<TransactionEntity> transactionList = (ArrayList<TransactionEntity>) query.getResultList();

            transaction.commit();

            return transactionList;
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

    public TransactionEntity getTransactionEntityById(int id) {
        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hq = "FROM TransactionEntity T WHERE T.id =:id";
            Query query = session.createQuery(hq);
            query.setParameter("id", id);

            TransactionEntity transactionEntity = (TransactionEntity) query.getSingleResult();

            transaction.commit();

            return transactionEntity;
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

    public ArrayList<TransactionEntity> getTransactionsByUserEntityAndDate(UserEntity userEntity, Date dateFrom, Date dateTo){
        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hq = "FROM TransactionEntity T WHERE T.userEntity =:userEntity AND T.dateOfCreation BETWEEN :dateFrom AND :dateTo";
            Query query = session.createQuery(hq);
            query.setParameter("userEntity", userEntity);
            query.setParameter("dateFrom", dateFrom);
            query.setParameter("dateTo", dateTo);

            ArrayList<TransactionEntity> transactionList = (ArrayList<TransactionEntity>) query.getResultList();

            transaction.commit();

            return transactionList;
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

    public int updateTransaction(int id, int modifierId, String dateOfModification, boolean isApproved){
        Session session = factory.openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hq = "UPDATE ";
            Query query = session.createQuery("UPDATE TransactionEntity T SET T.modifierId =: modifierId WHERE T.id =: id");
            query.setParameter("modifierId", modifierId);
            query.setParameter("id", id);
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
