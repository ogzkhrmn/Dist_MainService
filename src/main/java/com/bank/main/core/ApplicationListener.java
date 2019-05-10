package com.bank.main.core;

import com.bank.main.threads.TransactionThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Notification that the servlet context is about to be shut down.
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ApplicationProperties.readProperties();
        HibernateConfiguration.getSessionFactory();
        ApplicationLoader.loadApp();
        ApplicationLoader.addPrxoy();
        TransactionThread transactionThread = new TransactionThread();
        transactionThread.start();
    }

}
