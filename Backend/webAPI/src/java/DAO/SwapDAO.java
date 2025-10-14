package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mylib.DBUtils;

public class SwapDAO {

    public static class Swap {
        public int swapId;
        public int userId;
        public Integer stationId;
        public Date timeSwap;
        public String serviceType;
    }

    public List<Swap> getSwapsByUserId(int userId) {
        List<Swap> list = new ArrayList<>();
        String sql = "SELECT Swap_ID, User_ID, Station_ID, Time_Swap, Service_Type FROM Swap_Transaction WHERE User_ID = ? ORDER BY Time_Swap DESC";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Swap s = new Swap();
                s.swapId = rs.getInt("Swap_ID");
                s.userId = rs.getInt("User_ID");
                Object st = rs.getObject("Station_ID");
                s.stationId = st != null ? rs.getInt("Station_ID") : null;
                java.sql.Timestamp ts = rs.getTimestamp("Time_Swap");
                s.timeSwap = ts != null ? new Date(ts.getTime()) : null;
                s.serviceType = rs.getString("Service_Type");
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isSwapOwnedByUserAndIsSwapType(int swapId, int userId) {
        String sql = "SELECT Service_Type FROM Swap_Transaction WHERE Swap_ID = ? AND User_ID = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, swapId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String type = rs.getString("Service_Type");
                return type != null && type.equalsIgnoreCase("swap");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
