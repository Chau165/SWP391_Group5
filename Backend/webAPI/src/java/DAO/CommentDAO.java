package DAO;

import DTO.Comment;
import DTO.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mylib.DBUtils;

public class CommentDAO {

    private final UsersDAO usersDAO = new UsersDAO();
    private final SwapDAO swapDAO = new SwapDAO();

    public int insertComment(Comment c) {
        // validate role: only Driver or Staff can post
        Users u = usersDAO.getUserById(c.getUserId());
        if (u == null) return -2; // user not found
        String role = u.getRole();
        if (role == null) return -3;
        if (!role.equalsIgnoreCase("Driver") && !role.equalsIgnoreCase("Staff")) {
            return -4; // not allowed
        }

        // Now Comment links to a Swap transaction. Require that the swap belongs to the user and is of type 'swap'.
        if (c.getSwapId() == null) return -5; // swap id missing
        if (!swapDAO.isSwapOwnedByUserAndIsSwapType(c.getSwapId(), c.getUserId())) return -6; // invalid swap ownership or type

        String sql = "INSERT INTO Comment(User_ID, Swap_ID, Content, Time_Post) VALUES (?, ?, ?, ?)";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getUserId());
            ps.setInt(2, c.getSwapId());
            ps.setString(3, c.getContent());
            ps.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // admin view: get all comments with user info
    public List<Comment> getAllComments() {
        List<Comment> list = new ArrayList<>();
    String sql = "SELECT c.Comment_ID, c.User_ID, c.Swap_ID, c.Content, c.Time_Post, u.FullName, u.Role "
        + "FROM Comment c JOIN Users u ON c.User_ID = u.ID ORDER BY c.Time_Post DESC";
        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setCommentId(rs.getInt("Comment_ID"));
                c.setUserId(rs.getInt("User_ID"));
        c.setSwapId(rs.getInt("Swap_ID"));
                c.setContent(rs.getString("Content"));
                java.sql.Timestamp ts = rs.getTimestamp("Time_Post");
                c.setTimePost(ts != null ? new Date(ts.getTime()) : null);
                c.setUserFullName(rs.getString("FullName"));
                c.setUserRole(rs.getString("Role"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
