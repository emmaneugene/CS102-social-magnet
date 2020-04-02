package com.g1t11.socialmagnet;

import java.sql.SQLException;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.WelcomeController;
import com.g1t11.socialmagnet.data.Database;
import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class App {
    public Database db = null;

    public Navigator nav = null;

    public App() {
        try {
            db = new Database();
            nav = new Navigator(db);
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
                nav.currController().run();
            } catch (DatabaseException e) {
                handleDatabaseException(e);
            }
        }
    }

    private void handleDatabaseException(DatabaseException e) {
        Throwable cause = e.getCause();
        System.out.println(cause);
        // CommunicationsException is only thrown when Database cannot establish
        // a connection. CommunicationsException inherits from SQLException.
        if (cause instanceof CommunicationsException) {
            handleInitConnectionException((CommunicationsException) cause);
        // If a previously established connection becomes invalid, then an
        // SQLException that is not an instance of CommunicationsException is
        // thrown.
        } else if (cause instanceof SQLException) {
            handleSQLException((SQLException) cause);
        }
    }

    private void handleInitConnectionException(CommunicationsException e) {
        nav.popToFirst();
        nav.setCurrStatus(Painter.paint(
                "Failed to connect to database.", Color.RED));
    }

    private void handleSQLException(SQLException sqlE) {
        System.out.println(sqlE.getMessage());
        System.out.println(sqlE.getErrorCode());
        if (sqlE.getErrorCode() == SQLErrorCode.NO_CONNECTION.value) {
            nav.popToFirst();
            nav.setCurrStatus(Painter.paint(
                    "Failed to connect to database. Retrying...", Color.RED));
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
