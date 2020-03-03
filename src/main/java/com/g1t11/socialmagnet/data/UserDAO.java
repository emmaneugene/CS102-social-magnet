package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.model.User;

public class UserDAO {
    private Connection conn = App.shared().getDatabase().connection();
    public User getUser(String username) {
        ResultSet rs = null;
        User u = new User();

        String queryString = String.join("\n",
            "SELECT username, fullname",
            "FROM user",
            "WHERE username = ?"
        );

        try ( PreparedStatement stmt = conn.prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            rs.next();

            String fullname = rs.getString("fullname");
            
            u.setUsername(username);
            u.setFullName(fullname);
            u.setFriends(getFriends(u));
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {}
        }

        return u;
    }

    /**
     * Only loads simple attributes of friends.
     * Includes username and fullname
     */
    public List<User> getFriends(User user) {
        ResultSet rs = null;
        List<User> friends = new ArrayList<>();

        String queryString = String.join("\n",
            "SELECT username, fullname",
            "FROM",
            "(SELECT user_1 AS friend_name FROM friend WHERE user_2 = ?",
            "UNION",
            "SELECT user_2 FROM friend WHERE user_1 = ?) AS f",
            "JOIN user ON friend_name = user.username;"
        );

        try ( PreparedStatement stmt = conn.prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getUsername());

            rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");

                User u = new User();
                u.setUsername(username);
                u.setFullName(fullname);
                friends.add(u);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {}
        }

        return friends;
    }
}
