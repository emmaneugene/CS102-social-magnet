package com.g1t11.socialmagnet.data;

public class TestDAO {
    Database db;

    public TestDAO() {
        db = new Database();
        db.establishConnection();
    }
}