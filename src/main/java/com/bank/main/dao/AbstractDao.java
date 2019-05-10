package com.bank.main.dao;

import com.bank.main.core.HibernateConfiguration;
import org.hibernate.Session;

public class AbstractDao {

    protected Session getSessionFactory(){
        return HibernateConfiguration.getSession().getCurrentSession();
    }

}
