package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    private Database db;

    public DAO(Database db) {
        this.db = db;
    }

    /**
     * The current connection to the database. If the connection is not 
     * established, a {@link DatabaseException} is thrown to be handled by the
     * application event loop.
     * @return A connection to the database.
     */
    public Connection connection() {
        if (db.connection() == null) {
            throw new DatabaseException(new SQLException("Connection not established", "08S01", 0));
        }
        return db.connection();
    }
} 
