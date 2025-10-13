package controller;

import DTO.Users;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SessionUserController", urlPatterns = {"/api/sessionUser"})
public class SessionUserController extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json;charset=UTF-8");

        try ( PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                out.print("{}");
                return;
            }
            Users u = (Users) session.getAttribute("User");
            if (u == null) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                out.print("{}");
                return;
            }
            String json = gson.toJson(u);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print(json);
        }
    }
}
