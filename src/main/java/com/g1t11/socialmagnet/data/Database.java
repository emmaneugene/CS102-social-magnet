package com.g1t11.socialmagnet.data;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  /**
   * Singleton instance of the application's associated database.
   * Handles database connections and queries.
   */
  private static Database sharedInstance = null;

  /**
   * Connection to the database.
   * Reconnecting and closing a connection is slow and expensive. Instead,
   * maintain a single connection during the application's runtime and
   * re-establish it if the connection is dropped.
   * @see <a href="https://docs.oracle.com/en/database/oracle/oracle-database/19/adfns/connection_strategies.html#GUID-90D1249D-38B8-47BF-9829-BA0146BD814A">docs.oracle.com</a>
   */
  private static Connection conn;

  private Database() {
    try {
      /* JDBC Driver must be instantiated before we can use DriverManager. */
      new Driver();

      /*
       * Get credentials from environment variables that were loaded
       * through .env file.
       */
      String dbName = System.getenv("DB_NAME");
      String dbUrl  = "jdbc:mysql://localhost/" + dbName;
      String dbUser = System.getenv("DB_USER");
      String dbPass = System.getenv("DB_PASS");
      conn          = DriverManager.getConnection(dbUrl, dbUser, dbPass);
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
    }
  }

  /**
   * Singleton access point.
   * Instantiate the database connection if not yet instantiated.
   * @return Singleton {@link Database}.
   */
  public static Database shared() {
    if (sharedInstance == null)
      sharedInstance = new Database();
    return sharedInstance;
  }

  /**
   * @return {@link #conn}
   */
  public Connection connection() {
    return conn;
  }
} 
