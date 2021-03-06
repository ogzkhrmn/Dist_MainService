package com.bank.main.threads;

import com.bank.main.core.ApplicationProperties;
import com.bank.main.dao.impl.MailSendDAO;
import com.bank.main.model.ResponseModel;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class TransactionThread extends Thread {

    private static final MailSendDAO msend = new MailSendDAO();

    private Gson gson = new Gson();

    @Override
    public void run() {
        try {
            while (true) {
                if (!checkTcmb()) {
                    msend.generalControl("TCMB_SERVICE");
                }
                if (!checkSecurity()) {
                    msend.generalControl("SECURITY_SERVICE");
                }
                if (!checkMail()) {
                    msend.generalControl("MAIL_SERVICE");
                }
                if (!checkAccount()) {
                    msend.generalControl("ACCOUNT_SERVICE");
                }
                if (!checkError()) {
                    msend.generalControl("ERROR_SERVICE");
                }
                if (!checkUserInterface()) {
                    msend.generalControl("INTERFACE_SERVICE");
                }
                if (!checkDB()) {
                    msend.generalControl("DB_SERVICE");
                }
                TimeUnit.MINUTES.sleep(1);
            }
        } catch (Exception e) {
            this.start();
        }
    }

    private boolean checkTcmb() {
        try {
            URL url = new URL(ApplicationProperties.getProperty("app.control.tcmb.service"));
            return getResponse(url).isSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkSecurity() {
        try {
            URL url = new URL(ApplicationProperties.getProperty("app.control.security.service"));
            return getResponse(url).isSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkMail() {
        try {
            URL url = new URL(ApplicationProperties.getProperty("app.control.mail.service"));
            return getResponse(url).isSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkAccount() {
        try {
            URL url = new URL(ApplicationProperties.getProperty("app.control.account.service"));
            return getResponse(url).isSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkError() {
        try {
            URL url = new URL(ApplicationProperties.getProperty("app.control.error.service"));
            return getResponse(url).isSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkUserInterface() {
        try {
            URL url = new URL(ApplicationProperties.getProperty("app.control.user.service"));
            return getResponse(url).isSuccess();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkDB() {
        try (Connection connection = DriverManager.getConnection(ApplicationProperties.getProperty("db.url"),
                ApplicationProperties.getProperty("db.user"), ApplicationProperties.getProperty("db.password"))) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private ResponseModel getResponse(URL url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
        wr.flush();
        wr.close();
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new Exception();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String output = IOUtils.toString(br);

        conn.disconnect();
        return gson.fromJson(output, ResponseModel.class);
    }

}
