package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;

public class FarmerDAO extends UserDAO {
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
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return f;
    }
}
