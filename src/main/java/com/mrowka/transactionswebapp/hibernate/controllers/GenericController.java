package com.mrowka.transactionswebapp.hibernate.controllers;

import org.hibernate.SessionFactory;

public class GenericController {

    protected SessionFactory factory;

    public GenericController(SessionFactory sessionFactory){
        factory = sessionFactory;
    }
}
