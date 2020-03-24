package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.g1t11.socialmagnet.util.PromptInput;

import com.mysql.cj.jdbc.Driver;

public class Database {
    /**
     * Connection to the database.
     * <p>
     * Reconnecting and closing a connection is slow and expensive. Instead,
     * maintain a single connection during the application's runtime and
     * re-establish it if the connection is dropped.
     * 
     * @see <a href="https://docs.oracle.com/en/database/oracle/oracle-database/19/adfns/connection_strategies.html#GUID-90D1249D-38B8-47BF-9829-BA0146BD814A">docs.oracle.com</a>
     */
    private static Connection conn;

    public Database() {
        // JDBC Driver must be instantiated before we can use DriverManager.
        try {
            new Driver();

            /*
             * Get credentials from environment variables that were loaded
             * through .env file.
             * If environment variables cannot be read, prompt the user
             */
            String dbName = System.getenv("DB_NAME");
            if (dbName == null) {
                PromptInput input =  new PromptInput("Enter database name");
                dbName = input.nextLine();
            }

            String dbUrl  = "jdbc:mysql://localhost/" + dbName + "?serverTimezone=UTC";

            String dbUser = System.getenv("DB_USER");
            if (dbUser == null) {
                PromptInput input =  new PromptInput("Enter database user");
                dbUser = input.nextLine();
            }

            String dbPass = System.getenv("DB_PASS");
            if (dbPass == null) {
                PromptInput input =  new PromptInput("Enter database password");
                dbPass = input.readPassword();
            }

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
