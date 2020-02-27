package data;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database sharedInstance = null;
    private static Connection conn;

    private Database() {
        try {
            new Driver();
            String dbName = System.getenv("DB_NAME");
            String dbUser = System.getenv("DB_USER");
            String dbPass = System.getenv("DB_PASS");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, dbUser, dbPass);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public static Database shared() {
        if (sharedInstance == null)
            sharedInstance = new Database();
        return sharedInstance;
    }

    public Connection connection() {
        return conn;
    }
} 
