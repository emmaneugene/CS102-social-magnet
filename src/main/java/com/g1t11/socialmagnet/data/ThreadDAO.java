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
        String queryString = "CALL get_thread(?, ?)";

        ResultSet rs = null;
        Thread thread = null;
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
            setCommentsLatestLast(thread, 3);
            setLikers(thread);
            setDislikers(thread);
            thread.formatContentTags(getTaggedUsernames(thread));
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
        String queryString = "CALL get_news_feed_threads(?, ?)";
        
        ResultSet rs = null;
        List<Thread> threads = new ArrayList<>();
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
                setCommentsLatestLast(thread, 3);
                setLikers(thread);
                setDislikers(thread);
                thread.formatContentTags(getTaggedUsernames(thread));

                threads.add(thread);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return threads;
    }

    public List<Thread> getWallThreads(User user, int limit) {
        String queryString = "CALL get_wall_threads(?, ?)";
        
        ResultSet rs = null;
        List<Thread> threads = new ArrayList<>();
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
                setCommentsLatestLast(thread, 3);
                setLikers(thread);
                setDislikers(thread);
                thread.formatContentTags(getTaggedUsernames(thread));

                threads.add(thread);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return threads;
    }

    public void setCommentsLatestLast(Thread thread, int limit) {
        String queryString = "CALL get_thread_comments_latest_last(?, ?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
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
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
    }

    public void setLikers(Thread thread) {
        String queryString = "CALL get_likers(?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();
            thread.getLikers().clear();

            while (rs.next()) {
                User u = new User(rs.getString("username"), rs.getString("fullname"));

                thread.getLikers().add(u);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
    }

    public void setDislikers(Thread thread) {
        String queryString = "CALL get_dislikers(?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();
            thread.getDislikers().clear();

            while (rs.next()) {
                User u = new User(rs.getString("username"), rs.getString("fullname"));

                thread.getDislikers().add(u);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
    }

    public List<String> getTaggedUsernames(Thread thread) {
        String queryString = "CALL get_tagged_usernames(?)";

        ResultSet rs = null;
        List<String> usernames = new ArrayList<>();
        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setInt(1, thread.getId());

            rs = stmt.executeQuery();

            while (rs.next()) {
                usernames.add(rs.getString("tagged_user"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return usernames;
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

    public void addTags(int threadId, List<String> usernames) {
        if (usernames == null || usernames.size() == 0) return;
        String queryString = "CALL add_tag(?, ?)";
        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            for (String username : usernames) {
                stmt.setInt(1, threadId);
                stmt.setString(2, username);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void addTags(Thread thread, List<String> usernames) {
        addTags(thread.getId(), usernames);
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

    public void addThread(String fromUser, String toUser, String content, List<String> usernameTags) {
        String queryString = "CALL add_thread_return_id(?, ?, ?)";

        ResultSet rs = null;
        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, fromUser);
            stmt.setString(2, toUser);
            stmt.setString(3, content);

            rs = stmt.executeQuery();
            rs.next();

            System.out.println(rs.getInt("new_id"));
            System.out.println(usernameTags);
            addTags(rs.getInt("new_id"), usernameTags);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void addThread(User fromUser, User toUser, String content, List<String> usernameTags) {
        addThread(fromUser.getUsername(), toUser.getUsername(), content, usernameTags);
    }
}
