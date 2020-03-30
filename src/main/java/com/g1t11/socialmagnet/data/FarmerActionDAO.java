package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.farm.StealingRecord;

public class FarmerActionDAO extends DAO {
    public FarmerActionDAO(Database db) {
        super(db);
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
