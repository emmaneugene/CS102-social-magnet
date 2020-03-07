package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Post;

public class PostDAO extends DAO {
    public PostDAO(Connection conn) {
        super(conn);
    }

    /**
     * News feed posts include all latest posts on the current user's wall or
     * the current user's friends' walls.
     * 
     * @param username The user whose news feed to load
     * @param limit The number of latest posts to retrieve
     * @return Posts to be displayed on the news feed
     */
    public List<Post> getNewsFeedPosts(String username, int limit) {
        ResultSet rs = null;
        List<Post> posts = new ArrayList<>();

        String queryString = String.join(" ",
            "SELECT post_id, author, recipient, content",
            "FROM post",
            "WHERE recipient = ? OR recipient IN",
            "(SELECT user_1 FROM friend WHERE user_2 = ?",
            "UNION",
            "SELECT user_2 FROM friend WHERE user_1 = ?)",
            "ORDER BY posted_on DESC",
            "LIMIT ?"
        );

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, username);
            stmt.setInt(4, limit);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Post p = new Post(rs.getInt("post_id"));

                p.setFromUsername(rs.getString("author"));
                p.setToUsername(rs.getString("recipient"));
                p.setContent(rs.getString("content"));

                posts.add(p);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return posts;
    }
}
