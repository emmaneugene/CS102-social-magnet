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

public class FarmerDAO extends DAO {
    public FarmerDAO(Database db) {
        super(db);
    }

    public Farmer getFarmer(String username) {
        ResultSet rs = null;
        Farmer f = null;

        String queryString = "CALL get_farmer_detail(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            rs.next();

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
     * @param farmer The farmer whose inventory to access.
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
