package com.g1t11.socialmagnet.data;

import java.sql.Connection;

public class DAO {
    private Connection conn;

    public DAO(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return conn;
    }
} 
