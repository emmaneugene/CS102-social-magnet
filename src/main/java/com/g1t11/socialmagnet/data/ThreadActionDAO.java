package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ThreadActionDAO extends DAO {
    public ThreadActionDAO(Database db) {
        super(db);
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
    public int addThread(String fromUser, String toUser, String content, List<String> usernameTags) {
        String queryString = "CALL add_thread_return_id(?, ?, ?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, fromUser);
            stmt.setString(2, toUser);
            stmt.setString(3, content);

            rs = stmt.executeQuery();
            rs.next();

            addTags(rs.getInt("new_id"), usernameTags);
            return rs.getInt("new_id");
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

    /*
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
     * Toggle between adding or removing a user as a liker of a thread.
     * 
     * @param threadId The ID of the thread to like.
     * @param username The user liking the thread.
     */
    public void toggleLikeThread(int threadId, String username) {
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
     * Toggle between adding or removing a user as a disliker of a thread.
     * 
     * @param threadId The ID of the thread to like.
     * @param username The user liking the thread.
     */
    public void toggleDislikeThread(int threadId, String username) {
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
}
