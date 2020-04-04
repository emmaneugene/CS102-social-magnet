package com.g1t11.socialmagnetservice;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.g1t11.socialmagnetservice.data.Database;

public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context started.");
        Database.shared();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context dying.");
    }
}