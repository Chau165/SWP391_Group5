package controller;

import DAO.SwapDAO;
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
import java.util.List;

@WebServlet("/api/user/swaps")
public class SwapController extends HttpServlet {

    private final SwapDAO swapDAO = new SwapDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        try ( PrintWriter out = resp.getWriter()) {
            HttpSession session = req.getSession(false);
            Users sessionUser = null;
            if (session != null) sessionUser = (Users) session.getAttribute("User");
            if (sessionUser == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print("[]");
                return;
            }
            List<SwapDAO.Swap> swaps = swapDAO.getSwapsByUserId(sessionUser.getId());
            out.print(gson.toJson(swaps));
        }
    }
}
