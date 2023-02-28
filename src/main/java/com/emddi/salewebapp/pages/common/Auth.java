package com.emddi.salewebapp.pages.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthControl", urlPatterns = "/logout")
public class Auth extends HttpServlet {
    public static boolean authentication (HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

        if (loggedIn == null || !loggedIn) {
            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        return false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("loggedIn", false);
        resp.sendRedirect("/login");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
