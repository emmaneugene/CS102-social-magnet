package com.g1t11.socialmagnetservice.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnetservice.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnetservice.model.farm.StealingRecord;

public class FarmerActionDAO extends DAO {
    /**
     * Plant a specified crop on a specified plot of a given farmer in the
     * database.
     * <p>
     * The farmer's inventory will have the corresponding crop deducted.
     * @param username The username of the farmer planting the crop.
     * @param plotNumber The number of the plot to plant the crop in.
     * @param cropName The name of the crop to plant.
     */
    public static void plantCrop(String username, int plotNumber, String cropName) {
        String queryString = "CALL plant_crop_auto_yield(?, ?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setInt(2, plotNumber);
            stmt.setString(3, cropName);

            stmt.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLErrorCode.DUPLICATE_KEY.value) {
                throw new DatabaseException(
                        SQLErrorCode.PLANT_ON_EXISTING_CROP);
            } else if (e.getMessage().contains("Not enough seeds")) {
                throw new DatabaseException(SQLErrorCode.NOT_ENOUGH_INVENTORY);
            } else if (e.getMessage().contains("Plot inaccessible")) {
                throw new DatabaseException(SQLErrorCode.PLOT_INACCESSIBLE);
            }
            throw new DatabaseException(e);
        }
    }

    /**
     * Clears a specified plot of a given user in the database.
     * <p>
     * If the crop is wilted, then 5 gold will be deducted from the farmer's
     * wealth.
     * @param username The username of the farmer planting the crop.
     * @param plotNumber The number of the plot to clear the crop from.
     */
    public static void clearPlot(String username, int plotNumber) {
        String queryString = "CALL clear_plot(?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
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
     * @param username The username of the farmer harvesting crops.
     */
    public static void harvest(String username) {
        String queryString = "CALL harvest(?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
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
     * One user is allowed to steal a random integer <code>[1, 5]</code> percent
     * of crops from a plot, and one plot can only have a maximum of 20% of
     * crops stolen from it by all users.
     * <p>
     * The generated gold and XP will be added to the stealer's wealth and XP.
     * <p>
     * Once stolen from, a plot cannot be stolen from again until it is
     * replanted.
     * @param stealerName The username of the farmer stealing.
     * @param victimName The username of the farmer being stolen from.
     * @return A list of what was stolen, in the form of {@link StealingRecord}.
     */
    public static List<StealingRecord> steal(String stealerName, String victimName) {
        ResultSet rs = null;
        List<StealingRecord> stolenCrops = new ArrayList<>();

        String queryString = "CALL steal(?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
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

    public static void sendGifts(String sender, String[] recipients, String cropName) {
        String queryString = "CALL send_gift(?, ?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            for (String recipient : recipients) {
                stmt.setString(1, sender);
                stmt.setString(2, recipient);
                stmt.setString(3, cropName);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    public static void acceptGifts(String username) {
        String queryString = "CALL accept_gifts(?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }
}
