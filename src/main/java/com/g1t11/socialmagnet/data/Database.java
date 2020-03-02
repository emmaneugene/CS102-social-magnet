package com.g1t11.socialmagnet.data;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  /**
   * Connection to the database.
   * Reconnecting and closing a connection is slow and expensive. Instead,
   * maintain a single connection during the application's runtime and
   * re-establish it if the connection is dropped.
   * @see <a href="https://docs.oracle.com/en/database/oracle/oracle-database/19/adfns/connection_strategies.html#GUID-90D1249D-38B8-47BF-9829-BA0146BD814A">docs.oracle.com</a>
   */
  private static Connection conn;

  public Database() {
    /* JDBC Driver must be instantiated before we can use DriverManager. */
    try {
      new Driver();
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
    }
  }

  public void establishDefaultConnection() {
    try {
      /*
       * Get credentials from environment variables that were loaded
       * through .env file.
       */
      String dbName = System.getenv("DB_NAME");
      String dbUrl  = "jdbc:mysql://localhost/" + dbName;
      String dbUser = System.getenv("DB_USER");
      String dbPass = System.getenv("DB_PASS");

      conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
    }
  }

  /**
   * @return {@link #conn}
   */
  public Connection connection() {
    return conn;
  }
} 
