package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    private Database db;

    public DAO(Database db) {
        this.db = db;
    }

    public Connection connection() {
        try {
            if (db.connection() == null || db.connection().isClosed()) {
                throw new ConnectionFailureException();
            }
        } catch (SQLException e) {
            throw new ConnectionFailureException();
        }
        return db.connection();
    }
} 
