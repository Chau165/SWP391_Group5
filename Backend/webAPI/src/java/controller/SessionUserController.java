package controller;

import DTO.Users;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
