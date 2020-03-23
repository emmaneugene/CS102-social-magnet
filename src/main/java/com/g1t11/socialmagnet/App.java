package com.g1t11.socialmagnet;

import java.sql.SQLException;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.controller.WelcomeController;
import com.g1t11.socialmagnet.data.Database;
import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.util.Painter;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.g1t11.socialmagnet.data.SQLErrorCode;

public class App {
    public Database db = null;

    public Navigation nav = null;

    public App() {
        try {
            db = new Database();
            nav = new Navigation(db);
            nav.setFirstController(new WelcomeController(nav));
            db.establishConnection();
        } catch (DatabaseException e) {
            handleDatabaseException(e);
        }
    }

    /**
     * Initiate the event loop of the application.
     */
    public void run() {
        while (true) {
            try {
                nav.currentController().run();
            } catch (DatabaseException e) {
                handleDatabaseException(e);
            }
        }
    }

    private void handleDatabaseException(DatabaseException e) {
        Throwable cause = e.getCause();
        System.out.println(cause);
        /* 
         * CommunicationsException is only thrown when Database cannot establish a connection.
         * CommunicationsException inherits from SQLException.
         */
        if (cause instanceof CommunicationsException) {
            handleInitConnectionException((CommunicationsException) cause);
        /*
         * If a previously established connection becomes invalid, then an SQLException that is not
         * an instance of CommunicationsException is thrown.
         */
        } else if (cause instanceof SQLException) {
            handleSQLException((SQLException) cause);
        }
    }

    private void handleInitConnectionException(CommunicationsException e) {
        nav.popToFirst();
        nav.currentController().view.setStatus(Painter.paint("Failed to connect to database.", Painter.Color.RED));
    }

    private void handleSQLException(SQLException sqlE) {
        System.out.println(sqlE.getMessage());
        System.out.println(sqlE.getErrorCode());
        if (sqlE.getErrorCode() == SQLErrorCode.noConnection) {
            nav.popToFirst();
            nav.currentController().view.setStatus(Painter.paint("Failed to connect to database. Retrying...", Painter.Color.RED));
            try {
                db.establishConnection();
            } catch (DatabaseException dbE) {
                handleDatabaseException(dbE);
            }
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
