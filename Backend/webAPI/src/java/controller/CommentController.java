package controller;

import DAO.CommentDAO;
import DAO.UsersDAO;
import DTO.Comment;
import DTO.Users;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CommentController", urlPatterns = {"/api/comments"})
public class CommentController extends HttpServlet {

    private final CommentDAO commentDAO = new CommentDAO();
    private final UsersDAO usersDAO = new UsersDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");
        try ( PrintWriter out = response.getWriter();  BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            Comment payload = gson.fromJson(sb.toString(), Comment.class);

            // require session user and use its ID (do not trust client-sent userId)
            HttpSession session = request.getSession(false);
            Users sessionUser = null;
            if (session != null) sessionUser = (Users) session.getAttribute("User");
            if (sessionUser == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("{\"status\":\"fail\",\"message\":\"Login required\"}");
                return;
            }

            if (payload == null || payload.getSwapId() == null || payload.getContent() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"status\":\"fail\",\"message\":\"Missing fields (swapId and content required)\"}");
                return;
            }

            // enforce server-side user id
            payload.setUserId(sessionUser.getId());

            int res = commentDAO.insertComment(payload);
            if (res > 0) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print("{\"status\":\"success\",\"commentId\":" + res + "}");
            } else if (res == -2) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"status\":\"fail\",\"message\":\"User not found\"}");
            } else if (res == -5) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"status\":\"fail\",\"message\":\"Missing swapId\"}");
            } else if (res == -6) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print("{\"status\":\"fail\",\"message\":\"Swap does not belong to user or not a swap-type transaction\"}");
            } else if (res == -4) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print("{\"status\":\"fail\",\"message\":\"Role not allowed to post comments\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"status\":\"error\",\"message\":\"Insert failed\"}");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try ( PrintWriter out = response.getWriter()) {
                out.print("{\"status\":\"error\",\"message\":\"Server error: " + e.getMessage().replace("\"", "'") + "\"}");
            }
        }
    }

    // GET: admin-only - return all comments
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setContentType("application/json;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String adminIdParam = request.getParameter("adminUserId");
            // also allow session-based check
            HttpSession session = request.getSession(false);
            Users sessionUser = null;
            if (session != null) sessionUser = (Users) session.getAttribute("User");

            Users adminUser = null;
            if (adminIdParam != null) {
                try { adminUser = usersDAO.getUserById(Integer.parseInt(adminIdParam)); } catch (Exception ex) {}
            } else if (sessionUser != null) {
                adminUser = sessionUser;
            }

            if (adminUser == null || adminUser.getRole() == null || !adminUser.getRole().equalsIgnoreCase("Admin")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                out.print("{\"status\":\"fail\",\"message\":\"Only admin can view comments\"}");
                return;
            }

            List<Comment> list = commentDAO.getAllComments();
            String json = gson.toJson(list);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(json);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try ( PrintWriter out = response.getWriter()) {
                out.print("{\"status\":\"error\",\"message\":\"Server error: " + e.getMessage().replace("\"", "'") + "\"}");
            }
        }
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
