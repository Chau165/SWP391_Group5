package DAO;

import DTO.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import mylib.DBUtils;

public class UsersDAO {
    private String lastErrorMessage = null;

    public Users checkLogin(String email, String password) {
        Users user = null;
        String query = "SELECT ID, FullName, Phone, Email, Password, Role, Station_ID "
                + "FROM Users WHERE Email = ? AND Password = ?";

        try ( Connection connect = DBUtils.getConnection();  PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new Users(
                            rs.getInt("ID"),
                            rs.getString("FullName"),
                            rs.getString("Phone"),
                            rs.getString("Email"),
                            rs.getString("Password"),
                            rs.getString("Role"),
                            rs.getObject("Station_ID") != null ? rs.getInt("Station_ID") : null
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT ID FROM Users WHERE Email=?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insertUser(Users u) {
        // Note: DB's Users table contains a Status column (NOT NULL). Include it here with a default value.
        String sql = "INSERT INTO Users(FullName, Phone, Email, Password, Role, Status, Station_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( Connection conn = DBUtils.getConnection();  
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getFullName());
            ps.setString(2, u.getPhone());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getRole());
            // set a default status for new accounts; change value if you want a different initial state
            ps.setString(6, "Active");
            if (u.getStationId() != null) {
                ps.setInt(7, u.getStationId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lastErrorMessage = e.getMessage();
        }
        return -1;
    }

    // expose last error message for debug purposes
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    // new helper: get user by id
    public Users getUserById(int id) {
        String sql = "SELECT ID, FullName, Phone, Email, Password, Role, Station_ID FROM Users WHERE ID = ?";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Users(
                        rs.getInt("ID"),
                        rs.getString("FullName"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Role"),
                        rs.getObject("Station_ID") != null ? rs.getInt("Station_ID") : null
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
