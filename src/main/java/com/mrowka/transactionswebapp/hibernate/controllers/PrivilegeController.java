package com.mrowka.transactionswebapp.hibernate.controllers;

import com.mrowka.transactionswebapp.hibernate.entites.PrivilegeEntity;
import com.mrowka.transactionswebapp.hibernate.entites.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;

public class PrivilegeController extends GenericController {

    private Logger logger = null;

    public PrivilegeController(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Integer addPrivilege(int type, UserEntity userEntity) {

        Session session = factory.openSession();

        Transaction transaction = null;

        Integer privilegeId = null;

        try {
            transaction = session.beginTransaction();
            PrivilegeEntity privilegeEntity = new PrivilegeEntity(type, userEntity);
            privilegeId = (Integer) session.save(privilegeEntity);
            transaction.commit();
        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return privilegeId;
    }
}
