package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.Comment;
import com.g1t11.socialmagnet.model.social.Thread;
import com.g1t11.socialmagnet.model.social.User;

public class ThreadDAO extends DAO {
    public ThreadDAO(Database db) {
        super(db);
    }

    /**
     * Gets a thread from the perspective of user. This is needed to determine
     * whether the thread should be marked as a tagged thread.
     * 
     * @param id The thread id.
     * @param username The user that is retrieving the thread.
     */
    public Thread getThread(int threadId, String username) {
        String queryString = "CALL get_thread(?, ?)";

        ResultSet rs = null;
        Thread thread = null;
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setInt(1, threadId);
            stmt.setString(2, username);

            rs = stmt.executeQuery();
            rs.next();

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
     * Retrieves a list of latest threads on a user's news feed.
     * <p>
     * News feed threads include all latest threads on the current user's wall or
     * the current user's friends' walls.
     * <p>
     * Threads on the current user's wall are either posted to the wall, or exist
     * due to the user being tagged.
     * 
     * @param username The user whose news feed to load
     * @param limit The number of latest posts to retrieve
     * @return Posts to be displayed on the news feed
     */
    public List<Thread> getNewsFeedThreads(String username, int limit) {
        String queryString = "CALL get_news_feed_threads(?, ?)";
        
        ResultSet rs = null;
        List<Thread> threads = new ArrayList<>();
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);
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
     * Wall threads include all threads by the current user, threads to the
     * current user's wall, or threads with the current user tagged.
     * 
     * @param username The user whose wall to load
     * @param limit The number of latest posts to retrieve
     * @return Posts to be displayed on the wall
     */
    public List<Thread> getWallThreads(String username, int limit) {
        String queryString = "CALL get_wall_threads(?, ?)";
        
        ResultSet rs = null;
        List<Thread> threads = new ArrayList<>();
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);
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
     * Updates the comments of a {@link Thread} in-place based on latest data 
     * from the database.
     * 
     * @param thread The thread to update.
     * @param limit Number of comments to load.
     */
    public void setCommentsLatestLast(Thread thread, int limit) {
        String queryString = "CALL get_thread_comments_latest_last(?, ?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
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
     * 
     * @param thread The thread to update.
     */
    public void setLikers(Thread thread) {
        String queryString = "CALL get_likers(?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();
            thread.getLikers().clear();

            while (rs.next()) {
                User u = new User(rs.getString("username"), rs.getString("fullname"));

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
     * 
     * @param thread The thread to update.
     */
    public void setDislikers(Thread thread) {
        String queryString = "CALL get_dislikers(?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();
            thread.getDislikers().clear();

            while (rs.next()) {
                User u = new User(rs.getString("username"), rs.getString("fullname"));

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
     * 
     * @param threadId The ID of the thread to get tags from.
     * @return A list of usernames.
     */
    public List<String> getTaggedUsernames(int threadId) {
        String queryString = "CALL get_tagged_usernames(?)";

        ResultSet rs = null;
        List<String> usernames = new ArrayList<>();
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
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
     * Remove a tag from a thread on the database.
     * 
     * @param threadId The ID of the thread to untag.
     * @param usernames The username tag to remove.
     */
    public void removeTag(int threadId, String username) {
        String queryString = "CALL remove_tag(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setInt(1, threadId);
            stmt.setString(2, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Deletes a thread from the database, given the deleting user is
     * authorized to do so.
     * 
     * @param threadId The ID of the thread to delete.
     * @param username The user deleting the thread.
     */
    public void deleteThread(int threadId, String username) {
        String queryString = "CALL delete_thread(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setInt(1, threadId);
            stmt.setString(2, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Add a reply to a thread on the database.
     * 
     * @param threadId The ID of the thread to reply to.
     * @param username The user replying to the thread.
     * @param content The content of the reply.
     */
    public void replyToThread(int threadId, String username, String content) {
        String queryString = "CALL reply_to_thread(?, ?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setInt(1, threadId);
            stmt.setString(2, username);
            stmt.setString(3, content);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Add a user as a liker of a thread.
     * 
     * @param threadId The ID of the thread to like.
     * @param username The user liking the thread.
     */
    public void likeThread(int threadId, String username) {
        String queryString = "CALL toggle_like_thread(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setInt(1, threadId);
            stmt.setString(2, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Add a user as a disliker of a thread.
     * 
     * @param threadId The ID of the thread to like.
     * @param username The user liking the thread.
     */
    public void dislikeThread(int threadId, String username) {
        String queryString = "CALL toggle_dislike_thread(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setInt(1, threadId);
            stmt.setString(2, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Post a new thread from a user to another user's wall on the database,
     * and add the tags associated to the database.
     * 
     * @param fromUser The user posting the thread.
     * @param toUser The user receiving the thread.
     * @param content The content of the thread.
     * @param usernameTags A list of usernames to tag the thread with.
     */
    public void addThread(String fromUser, String toUser, String content, List<String> usernameTags) {
        String queryString = "CALL add_thread_return_id(?, ?, ?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, fromUser);
            stmt.setString(2, toUser);
            stmt.setString(3, content);

            rs = stmt.executeQuery();
            rs.next();

            addTags(rs.getInt("new_id"), usernameTags);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Add a list of username tags to a thread on the database.
     * 
     * @param threadId The ID of the thread to tag.
     * @param usernames A list of usernames to tag the thread with.
     */
    public void addTags(int threadId, List<String> usernames) {
        if (usernames == null || usernames.size() == 0) return;
        String queryString = "CALL add_tag(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            for (String username : usernames) {
                stmt.setInt(1, threadId);
                stmt.setString(2, username);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }
}
