package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.hibernate.entites.CashRegisterEntity;
import com.mrowka.transactionswebapp.hibernate.entites.StoreEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class CashRegisterController extends GenericController {

    public CashRegisterController(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Integer addCashRegister(CashRegisterEntity cashRegisterEntity) {

        Session session = factory.openSession();

        Transaction transaction = null;

        Integer cashRegisterId = null;

        try {
            transaction = session.beginTransaction();
            cashRegisterId = (Integer) session.save(cashRegisterEntity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return cashRegisterId;
    }

    public CashRegisterEntity findByStoreAndNumber(StoreEntity storeEntity,int cashNumber){

        Session session = factory.openSession();

        Transaction transaction = null;

        CashRegisterEntity cashRegisterEntity = null;

        try{
            transaction = session.beginTransaction();

            List<CashRegisterEntity> cashRegisterEntities = storeEntity.getCashRegisterEntities().stream().filter(c -> c.getCashNumber() == cashNumber).collect(Collectors.toList());
            cashRegisterEntity = cashRegisterEntities.get(0); //assuming that only one

            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return cashRegisterEntity;
    }

    public void updateCashRegister(CashRegisterEntity cashRegisterEntity) throws IllegalStateException {

        Session session = factory.openSession();

        Transaction transaction = null;

        try {

            transaction = session.beginTransaction();
            session.update(cashRegisterEntity);
            transaction.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex.getCause());
        } finally {
            session.close();
        }
    }
}
