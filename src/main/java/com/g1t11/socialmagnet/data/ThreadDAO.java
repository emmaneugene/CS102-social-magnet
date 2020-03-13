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
     * Gets a thread from the perspective of user. This is needed to determine
     * whether the thread should be marked as a tagged thread.
     * 
     * @param id The thread id.
     * @param user The user that is retrieving the thread.
     */
    public Thread getThread(int id, User user) {
        ResultSet rs = null;
        Thread thread = null;

        String queryString = "CALL get_thread(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, id);
            stmt.setString(2, user.getUsername());

            rs = stmt.executeQuery();
            rs.next();

            thread = new Thread(
                rs.getInt("thread_id"),
                rs.getString("author"),
                rs.getString("recipient"),
                rs.getString("content"),
                rs.getInt("comment_count"),
                rs.getBoolean("is_tagged"));

            thread.setComments(getCommentsLatestLast(thread, 3));
            thread.setLikers(getLikers(thread));
            thread.setDislikers(getDislikers(thread));
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return thread;
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
        List<Thread> threads = new ArrayList<>();

        String queryString = "CALL get_news_feed_threads(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());
            stmt.setInt(2, limit);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Thread thread = new Thread(
                    rs.getInt("thread_id"),
                    rs.getString("author"),
                    rs.getString("recipient"),
                    rs.getString("content"),
                    rs.getInt("comment_count"),
                    rs.getBoolean("is_tagged"));

                thread.setComments(getCommentsLatestLast(thread, 3));
                thread.setLikers(getLikers(thread));
                thread.setDislikers(getDislikers(thread));

                threads.add(thread);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return threads;
    }

    public List<Comment> getCommentsLatestLast(Thread thread, int limit) {
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();

        String queryString = "CALL get_thread_comments_latest_last(?, ?)";

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

    /**
     * Returns all users who liked a thread in alphabetical order of their usernames
     */
    public List<User> getLikers(Thread thread) {
        ResultSet rs = null;
        List<User> likers = new ArrayList<>();

        String queryString = "CALL get_likers(?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();

            while (rs.next()) {
                User u = new User(rs.getString("username"), rs.getString("fullname"));

                likers.add(u);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return likers;
    }

    /**
     * Returns all users who disliked a thread in alphabetical order of their usernames
     */
    public List<User> getDislikers(Thread thread) {
        ResultSet rs = null;
        List<User> dislikers = new ArrayList<>();

        String queryString = "CALL get_dislikers(?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();

            while (rs.next()) {
                User u = new User(rs.getString("username"), rs.getString("fullname"));

                dislikers.add(u);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return dislikers;
    }

    public void addTag(Thread thread, User user) {
        String queryString = "CALL add_tag(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());
            stmt.setString(2, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void removeTag(Thread thread, User user) {
        String queryString = "CALL remove_tag(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());
            stmt.setString(2, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void deleteThread(Thread thread, User user) {
        String queryString = "CALL delete_thread(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());
            stmt.setString(2, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void replyToThread(Thread thread, User user, String content) {
        String queryString = "CALL reply_to_thread(?, ?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, content);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void likeThread(Thread thread, User user) {
        String queryString = "CALL like_thread(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());
            stmt.setString(2, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void dislikeThread(Thread thread, User user) {
        String queryString = "CALL dislike_thread(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());
            stmt.setString(2, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
}
