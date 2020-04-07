package com.g1t11.socialmagnetservice.data;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    /**
     * The current connection to the database. If the connection is not
     * established, a {@link DatabaseException} is thrown to be handled by the
     * application event loop.
     * @return A connection to the database.
     */
    public static Connection conn() {
        if (Database.shared().connection() == null) {
            throw new DatabaseException(new SQLException(
                    "Connection not established", "08S01", 0));
        }
        return Database.shared().connection();
    }
}
