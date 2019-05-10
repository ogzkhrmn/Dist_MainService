package com.bank.main.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationProperties {
    private static final Properties properties = readProperties();

    static Properties readProperties() {
        try (InputStream input = ApplicationProperties.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException io) {
            return new Properties();
        }
    }

    public static String getProperty(String propertyName){
        return properties.getProperty(propertyName);
    }

}
