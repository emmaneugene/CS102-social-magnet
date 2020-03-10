package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;

public class ThreadDAO extends DAO {
    public ThreadDAO(Connection conn) {
        super(conn);
    }

    /**
     * News feed posts include all latest posts on the current user's wall or
     * the current user's friends' walls.
     * <p>
     * Posts on the current user's wall are either posted to the wall, or exist
     * due to the user being tagged.
     * 
     * @param username The user whose news feed to load
     * @param limit The number of latest posts to retrieve
     * @return Posts to be displayed on the news feed
     */
    public List<Thread> getNewsFeedThreads(User user, int limit) {
        ResultSet rs = null;
        List<Thread> posts = new ArrayList<>();

        String queryString = String.join(" ",
            "SELECT post_id, author, recipient, content",
            "FROM post",
            "WHERE recipient = ?",
            "OR recipient IN",
            "(SELECT user_1 FROM friend WHERE user_2 = ?",
            "UNION",
            "SELECT user_2 FROM friend WHERE user_1 = ?)",
            "OR post_id IN",
            "(SELECT post_id FROM tag WHERE tagged_user = ?)",
            "ORDER BY posted_on DESC",
            "LIMIT ?"
        );

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getUsername());
            stmt.setInt(5, limit);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Thread p = new Thread(rs.getInt("post_id"));

                p.setFromUsername(rs.getString("author"));
                p.setToUsername(rs.getString("recipient"));
                p.setContent(rs.getString("content"));

                p.setActualCommentsCount(getCommentsCount(p));
                p.setComments(getCommentsLatestLast(p, 3));

                posts.add(p);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return posts;
    }

    public int getCommentsCount(Thread thread) {
        ResultSet rs = null;
        int commentCount = 0;

        String queryString = String.join(" ",
            "SELECT COUNT(*)",
            "FROM comment",
            "WHERE post_id = ?"
        );

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();
            rs.next();
            commentCount = rs.getInt("COUNT(*)");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return commentCount;
    }

    public List<Comment> getCommentsLatestLast(Thread thread, int limit) {
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();

        String queryString = String.join(" ",
            "SELECT commenter, content",
            "FROM",
            "(SELECT * FROM comment WHERE post_id = ?",
            "ORDER BY commented_on DESC",
            "LIMIT ?) AS c",
            "ORDER BY commented_on"
        );

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());
            stmt.setInt(2, limit);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Comment c = new Comment();

                c.setUsername(rs.getString("commenter"));
                c.setContent(rs.getString("content"));

                comments.add(c);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return comments;
    }
}
