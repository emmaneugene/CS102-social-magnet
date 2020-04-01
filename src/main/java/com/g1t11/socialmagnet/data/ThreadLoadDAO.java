package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;

public class ThreadLoadDAO extends DAO {
    public ThreadLoadDAO(Database db) {
        super(db);
    }

    /**
     * Gets a thread from the perspective of user. This is needed to determine
     * whether the thread should be marked as a tagged thread.
     * @param id The thread id.
     * @param username The user that is retrieving the thread.
     */
    public Thread getThread(int threadId, String username) {
        String queryString = "CALL get_thread(?, ?)";

        ResultSet rs = null;
        Thread thread = null;
        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setInt(1, threadId);
            stmt.setString(2, username);

            rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new DatabaseException(SQLErrorCode.THREAD_NOT_FOUND);
            }

            thread = new Thread(
                rs.getInt("thread_id"),
                rs.getString("author"),
                rs.getString("recipient"),
                rs.getString("content"),
                rs.getInt("comment_count"),
                rs.getBoolean("is_tagged"));
            setCommentsLatestLast(thread, 3);
            setLikers(thread);
            setDislikers(thread);
            thread.formatContentTags(getTaggedUsernames(thread.getId()));
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return thread;
    }

    /**
     * Updates the comments of a {@link Thread} in-place based on latest data
     * from the database.
     * @param thread The thread to update.
     * @param limit Number of comments to load.
     */
    public void setCommentsLatestLast(Thread thread, int limit) {
        String queryString = "CALL get_thread_comments_latest_last(?, ?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());
            stmt.setInt(2, limit);

            rs = stmt.executeQuery();
            thread.getComments().clear();

            while (rs.next()) {
                Comment c = new Comment();
                c.setUsername(rs.getString("commenter"));
                c.setContent(rs.getString("content"));

                thread.getComments().add(c);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
    }

    /**
     * Updates the likers of a {@link Thread} in-place based on latest data
     * from the database.
     * @param thread The thread to update.
     */
    public void setLikers(Thread thread) {
        String queryString = "CALL get_likers(?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();
            thread.getLikers().clear();

            while (rs.next()) {
                User u = new User(
                        rs.getString("username"), rs.getString("fullname"));

                thread.getLikers().add(u);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
    }

    /**
     * Updates the dislikers of a {@link Thread} in-place based on latest data
     * from the database.
     * @param thread The thread to update.
     */
    public void setDislikers(Thread thread) {
        String queryString = "CALL get_dislikers(?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();
            thread.getDislikers().clear();

            while (rs.next()) {
                User u = new User(
                        rs.getString("username"), rs.getString("fullname"));

                thread.getDislikers().add(u);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
    }

    /**
     * Gets the usernames of users who were tagged in this thread.
     * @param threadId The ID of the thread to get tags from.
     * @return A list of usernames.
     */
    public List<String> getTaggedUsernames(int threadId) {
        String queryString = "CALL get_tagged_usernames(?)";

        ResultSet rs = null;
        List<String> usernames = new ArrayList<>();
        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setInt(1, threadId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                usernames.add(rs.getString("tagged_user"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return usernames;
    }

    /**
     * Retrieves a list of latest threads on a user's news feed.
     * <p>
     * News feed threads include all latest threads on the current user's wall
     * or the current user's friends' walls.
     * @param username The user whose news feed to load
     * @param limit The number of latest posts to retrieve
     * @return Posts to be displayed on the news feed
     * @see #getWallThreads(String, int)
     */
    public List<Thread> getNewsFeedThreads(String username, int limit) {
        String queryString = "CALL get_news_feed_threads(?, ?)";

        ResultSet rs = null;
        List<Thread> threads = new ArrayList<>();
        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setInt(2, limit);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Thread thread = new Thread(rs.getInt("thread_id"),
                        rs.getString("author"), rs.getString("recipient"),
                        rs.getString("content"), rs.getInt("comment_count"),
                        rs.getBoolean("is_tagged"));
                setCommentsLatestLast(thread, 3);
                setLikers(thread);
                setDislikers(thread);
                thread.formatContentTags(getTaggedUsernames(thread.getId()));

                threads.add(thread);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return threads;
    }

    /**
     * Retrieves a list of latest threads on a user's wall.
     * <p>
     * Wall threads include:
     * <ul>
     * <li> Threads by the current user
     * <li> Threads to the current user's wall
     * <li> Threads with the current user tagged
     * <li> City Farmer gifts
     * </ul><p>
     * @param username The user whose wall to load
     * @param limit The number of latest posts to retrieve
     * @return Posts to be displayed on the wall
     */
    public List<Thread> getWallThreads(String username, int limit) {
        String queryString = "CALL get_wall_threads(?, ?)";

        ResultSet rs = null;
        List<Thread> threads = new ArrayList<>();
        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setInt(2, limit);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Thread thread = new Thread(rs.getInt("thread_id"),
                        rs.getString("author"), rs.getString("recipient"),
                        rs.getString("content"), rs.getInt("comment_count"),
                        rs.getBoolean("is_tagged"));
                setCommentsLatestLast(thread, 3);
                setLikers(thread);
                setDislikers(thread);
                thread.formatContentTags(getTaggedUsernames(thread.getId()));

                threads.add(thread);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return threads;
    }
}
