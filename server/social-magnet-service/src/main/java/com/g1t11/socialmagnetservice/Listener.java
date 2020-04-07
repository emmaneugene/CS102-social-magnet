package com.g1t11.socialmagnetservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.g1t11.socialmagnetservice.data.Database;

public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context started.");
        InputStream input = sce.getServletContext().getResourceAsStream("/WEB-INF/my.properties");
        Properties props = new Properties();
        try {
            props.load(input);
            Database db = Database.shared();
            db.setDbUser(props.getProperty("db.user"));
            db.setDbPass(props.getProperty("db.pass"));
            db.establishConnection();
        } catch (IOException e) {
            System.out.println("Could not load properties.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context dying.");
    }
}