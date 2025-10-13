package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mylib.DBUtils;
import DTO.Package;

public class PackageDAO {

    public List<Package> getAllPackage() {
        List<Package> list = new ArrayList<>();
        String sql = "SELECT Package_ID, Name, Description, Price FROM Package";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Package(
                        rs.getInt("Package_ID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getDouble("Price"),
                        rs.getDouble("Required_SoH"),
                        rs.getInt("MinSoH"),
                        rs.getInt("MaxSoH")
                ));
                list.add(pkg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Package getPackage_ID(int id){
        
        String query = "select Package_ID, Name, Description, Price from Package\n" +
                        "where Package_ID = ?";
        try(Connection connect = DBUtils.getConnection();
                PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs!= null && rs.next()){
                Package pkg = new Package();
                    pkg.setPackageId(rs.getInt("Package_ID"));
                    pkg.setName(rs.getString("Name"));
                    pkg.setDescription(rs.getString("Description"));
                    pkg.setPrice(rs.getDouble("Price"));
                    pkg.setRequired_SoH(rs.getDouble("Required_SoH"));
                    pkg.setMinSoH(rs.getInt("MinSoH"));
                    pkg.setMaxSoH(rs.getInt("MaxSoH"));
                    return pkg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public boolean addPackage(Package pkg) {
        String sql = "INSERT INTO Package (Name, Description, Price, Required_SoH, MinSoH, MaxSoH) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDescription());
            ps.setDouble(3, pkg.getPrice());
            ps.setDouble(4, pkg.getRequired_SoH());
            ps.setInt(5, pkg.getMinSoH());
            ps.setInt(6, pkg.getMaxSoH());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
public List<Object[]> getPackageRevenueStatistics() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT\n" +
                "    P.Package_ID,\n" +
                "    P.Name,\n" +
                "    COUNT(PT.Package_ID) AS SoLuongMua,\n" +
                "    SUM(PT.Amount) AS TongDoanhThu\n" +
                "FROM\n" +
                "    Package AS P\n" +
                "LEFT JOIN\n" +
                "    PaymentTransaction AS PT ON P.Package_ID = PT.Package_ID\n" +
                "WHERE\n" +
                "    PT.Description LIKE N'Buy Battery Package'\n" +
                "    AND MONTH(PT.Transaction_Time) = MONTH(GETDATE())\n" +
                "    AND YEAR(PT.Transaction_Time) = YEAR(GETDATE())\n" +
                "GROUP BY\n" +
                "    P.Package_ID,\n" +
                "    P.Name\n" +
                "ORDER BY\n" +
                "    SoLuongMua DESC;";

      try (Connection conn = DBUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] row = new Object[4]; // Correct size
            row[0] = rs.getInt("Package_ID");
            row[1] = rs.getString("Name");
            row[2] = rs.getInt("Description"); // Get SoLuongMua
            row[3] = rs.getDouble("Price"); // Get TongDoanhThu
            list.add(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
public boolean deletePackage(int packageId) {
        String sql = "DELETE FROM Package WHERE Package_ID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, packageId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

