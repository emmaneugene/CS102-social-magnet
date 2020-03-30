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
import com.g1t11.socialmagnet.model.farm.StealingRecord;
import com.g1t11.socialmagnet.model.social.UserNotFoundException;

public class FarmerDAO extends DAO {
    public FarmerDAO(Database db) {
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
     * Plant a specified crop on a specified plot of a given farmer in the database.
     * <p>
     * The farmer's inventory will have the corresponding crop deducted.
     * 
     * @param username The username of the farmer planting the crop.
     * @param plotNumber The number of the plot to plant the crop in.
     * @param cropName The name of the crop to plant.
     */
    public void plantCrop(String username, int plotNumber, String cropName) {
        String queryString = "CALL plant_crop_auto_yield(?, ?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setInt(2, plotNumber);
            stmt.setString(3, cropName);

            stmt.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Clears a specified plot of a given user in the database.
     * <p>
     * If the crop is wilted, then 5 gold will be deducted from the farmer's wealth.
     * 
     * @param username The username of the farmer planting the crop.
     * @param plotNumber The number of the plot to clear the crop from.
     */
    public void clearPlot(String username, int plotNumber) {
        String queryString = "CALL clear_plot(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setInt(2, plotNumber);

            stmt.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Harvests all ready crops from all plots of a farmer on the database.
     * <p>
     * The generated gold and XP will be added to the farmer's wealth and XP.
     * <p>
     * Any record of previous stealers of harvested plots will be cleared,
     * allowing others to steal from the plot again should another crop be
     * ready to harvest.
     * 
     * @param username The username of the farmer harvesting crops.
     */
    public void harvest(String username) {
        String queryString = "CALL harvest(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            stmt.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Steal harvestable crops from another user.
     * <p>
     * One user is allowed to steal a random integer <code>[1, 5]</code> 
     * percent of crops from a plot, and one plot can only have a maximum of
     * 20% of crops stolen from it by all users.
     * <p>
     * The generated gold and XP will be added to the stealer's wealth and XP.
     * <p>
     * Once stolen from, a plot cannot be stolen from again until it is replanted.
     * 
     * @param stealerName The username of the farmer stealing.
     * @param victimName The username of the farmer being stolen from.
     * @return A list of what was stolen, in the form of {@link StealingRecord}.
     */
    public List<StealingRecord> steal(String stealerName, String victimName) {
        ResultSet rs = null;
        List<StealingRecord> stolenCrops = new ArrayList<>();

        String queryString = "CALL steal(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, stealerName);
            stmt.setString(2, victimName);

            rs = stmt.executeQuery();
            while (rs.next()) {
                StealingRecord sr = new StealingRecord(
                    rs.getString("crop_name"),
                    rs.getInt("quantity"),
                    rs.getInt("total_xp_gained"),
                    rs.getInt("total_wealth_gained"));
                stolenCrops.add(sr);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        
        return stolenCrops;
    }

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

    public void sendGifts(int threadId, List<String> usernames) {
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
