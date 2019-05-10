package com.bank.main.core;

import com.bank.main.core.util.PackageScanner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public final class HibernateConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateConfiguration.class);
    private static final SessionFactory sessionFactory = getSessionFactory();

    static SessionFactory getSessionFactory() {
        SessionFactory factory = null;
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, ApplicationProperties.getProperty("db.url"));
                settings.put(Environment.USER, ApplicationProperties.getProperty("db.user"));
                settings.put(Environment.PASS, ApplicationProperties.getProperty("db.password"));
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                settings.put(Environment.POOL_SIZE, "10");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.DEFAULT_SCHEMA, "public");
                settings.put(Environment.SHOW_SQL, ApplicationProperties.getProperty("db.show.sql"));
                settings.put(Environment.FORMAT_SQL, ApplicationProperties.getProperty("db.show.sql"));
                configuration.setProperties(settings);
                for (Class clazz : PackageScanner.getClasses(ApplicationProperties.getProperty("entity.package"))) {
                    configuration.addAnnotatedClass(clazz);
                }
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                factory = configuration.buildSessionFactory(serviceRegistry);
                LOGGER.debug("Session Created");
            } catch (Exception e) {
                LOGGER.error("Session creation error ", e);
                return null;
            }
        }
        return factory;
    }

    public static SessionFactory getSession(){
        return sessionFactory;
    }
}
