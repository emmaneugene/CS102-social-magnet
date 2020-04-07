package com.g1t11.socialmagnetservice.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class Database {
    private static Database instance = null;
    private String dbUser = null;
    private String dbPass = null;

    public static Database shared() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    /**
     * Connection to the database.
     * <p>
     * Reconnecting and closing a connection is slow and expensive. Instead,
     * maintain a single connection during the application's runtime and
     * re-establish it if the connection is dropped.
     * @see <a href="https://docs.oracle.com/en/database/oracle/oracle-database/19/adfns/connection_strategies.html#GUID-90D1249D-38B8-47BF-9829-BA0146BD814A">docs.oracle.com</a>
     */
    private Connection conn;

    private Database() {
        // JDBC Driver must be instantiated before we can use DriverManager.
        try {
            new Driver();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
        }
    }

    public void setDbUser(String user) {
        this.dbUser = user;
    }

    public void setDbPass(String pass) {
        this.dbPass = pass;
    }

    public void establishConnection() {
        try {
            String dbUrl  = "jdbc:mysql://localhost/magnet?serverTimezone=UTC";
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
        }
    }

    /**
     * @return {@link #conn}
     */
    public Connection connection() {
        return conn;
    }
}
