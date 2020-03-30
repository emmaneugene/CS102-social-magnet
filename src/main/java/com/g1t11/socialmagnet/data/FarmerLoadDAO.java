package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.g1t11.socialmagnet.model.farm.Crop;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.model.social.UserNotFoundException;

public class FarmerLoadDAO extends DAO {
    public FarmerLoadDAO(Database db) {
        super(db);
    }

    /**
     * Get farmer details of a given user.
     * <p>
     * This allows us to minimise unnecessarily loading farmer information when
     * it is not required.
     * 
     * @param username The username of the farmer.
     * @return A unique farmer with the specified username.
     */
    public Farmer getFarmer(String username) {
        ResultSet rs = null;
        Farmer f = null;

        String queryString = "CALL get_farmer_detail(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            if (!rs.next()) throw new UserNotFoundException();

            f = new Farmer(
                rs.getString("username"),
                rs.getString("fullname"),
                rs.getInt("xp"),
                rs.getInt("wealth"),
                rs.getInt("wealth_rank"));
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return f;
    }

    /**
     * Get the inventory of a farmer.
     * 
     * @param farmer The username of the farmer whose inventory to access.
     * @return A sorted map of the names of crops in an inventory with its corresponding quantities.
     */
    public Map<String, Integer> getInventoryCrops(String username) {
        ResultSet rs = null;
        Map<String, Integer> invCrops = new LinkedHashMap<>();

        String queryString = "CALL get_inventory(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            while(rs.next()) {
                invCrops.put(rs.getString("crop_name"), rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return invCrops;
    }

    /**
     * Get the plots of a farmer.
     * 
     * @param username The username of the farmer whose plots to retrieve.
     * @param maxPlotCount The maximum number of plots the farmer can access.
     * @return The plots of a farmer.
     */
    public List<Plot> getPlots(String username, int maxPlotCount) {
        ResultSet rs = null;

        Plot[] emptyPlots = new Plot[maxPlotCount];
        Arrays.fill(emptyPlots, new Plot());
        List<Plot> plots = new ArrayList<>(Arrays.asList(emptyPlots));

        String queryString = "CALL get_plots(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Crop c = new Crop(
                    rs.getString("crop_name"),
                    rs.getInt("cost"),
                    rs.getInt("minutes_to_harvest"),
                    rs.getInt("xp"),
                    rs.getInt("min_yield"),
                    rs.getInt("max_yield"),
                    rs.getInt("sale_price")
                );
                Plot p = new Plot(c, rs.getTimestamp("time_planted"));
                int index = rs.getInt("plot_num") - 1;
                plots.set(index, p);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return plots;
    }

    /**
     * Get the number of gifts sent by the user today.
     * @param username The username of the gift sender.
     * @return The number of gifts sent today.
     */
    public int getGiftCountToday(String username) {
        ResultSet rs = null;
        int giftCount = 0;

        String queryString = "CALL get_gift_count_today(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            rs.next();
            giftCount = rs.getInt("gift_count");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return giftCount;
    }

    /**
     * For a given user, get a map of usernames to send to and whether gifts
     * were already sent today.
     * @param username The username of the sender.
     * @param recipients An array of users to check.
     * @return A map of recipient usernames to whether a gift was already sent today.
     */
    public Map<String, Boolean> sentGiftToUsersToday(String username, String[] recipients) {
        Map<String, Boolean> result = new LinkedHashMap<>();
        for (String recipient : recipients) {
            result.put(recipient, sentGiftToUserToday(username, recipient));
        }
        return result;
    }

    /**
     * Check if a gift was already sent to a specific user.
     * @param sender The username of the sender.
     * @param recipient The recipient of the gift.
     * @return Whether a gift was already sent today.
     */
    public boolean sentGiftToUserToday(String sender, String recipient) {
        ResultSet rs = null;
        boolean sentGiftToday = false;

        String queryString = "CALL sent_gift_today(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient);

            rs = stmt.executeQuery();
            rs.next();
            sentGiftToday = rs.getBoolean("sent");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return sentGiftToday;
    }
}
