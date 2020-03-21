package com.g1t11.socialmagnet.data;

import java.sql.Connection;

public class DAO {
    private Database db;

    public DAO(Database db) {
        this.db = db;
    }

    public Connection connection() {
        if (db.connection() == null) {
            throw new ConnectionFailureException();
        }
        return db.connection();
    }
} 
