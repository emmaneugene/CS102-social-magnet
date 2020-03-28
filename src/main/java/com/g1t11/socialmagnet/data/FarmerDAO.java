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
import com.g1t11.socialmagnet.model.social.User;

public class FarmerDAO extends DAO {
    public FarmerDAO(Database db) {
        super(db);
    }

    public Farmer getFarmer(User user) {
        ResultSet rs = null;
        Farmer f = null;

        String queryString = "CALL get_farmer_detail(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());

            rs = stmt.executeQuery();
            rs.next();

            f = new Farmer(
                user.getUsername(),
                user.getFullname(),
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
    public Map<String, Integer> getInventoryCrops(Farmer farmer) {
        ResultSet rs = null;
        Map<String, Integer> invCrops = new LinkedHashMap<>();

        String queryString = "CALL get_inventory(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, farmer.getUsername());

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

    public List<Plot> getPlots(Farmer farmer) {
        ResultSet rs = null;

        Plot[] emptyPlots = new Plot[farmer.getMaxPlotCount()];
        Arrays.fill(emptyPlots, new Plot());
        List<Plot> plots = new ArrayList<>(Arrays.asList(emptyPlots));

        String queryString = "CALL get_plots(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, farmer.getUsername());

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

    public void plantCrop(Farmer farmer, int plotNumber, String cropName) {
        String queryString = "CALL plant_crop_auto_yield(?, ?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, farmer.getUsername());
            stmt.setInt(2, plotNumber);
            stmt.setString(3, cropName);

            stmt.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    public void clearPlotUpdateWealth(Farmer farmer, int plotNumber) {
        String queryString = "CALL clear_plot_update_wealth(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, farmer.getUsername());
            stmt.setInt(2, plotNumber);

            stmt.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    public void harvest(Farmer farmer) {
        String queryString = "CALL harvest(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, farmer.getUsername());

            stmt.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }
}
