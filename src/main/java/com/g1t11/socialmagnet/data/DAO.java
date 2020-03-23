package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    private Database db;

    public DAO(Database db) {
        this.db = db;
    }

    public Connection connection() {
        if (db.connection() == null) {
            throw new DatabaseException(new SQLException("Connection not established", "08S01", 0));
        }
        return db.connection();
    }
} 
