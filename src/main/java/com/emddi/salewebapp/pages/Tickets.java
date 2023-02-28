package com.emddi.salewebapp.pages;


import com.emddi.salewebapp.pages.common.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TicketsControl", urlPatterns = {"/list-ticket"})
public class Tickets extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Auth.authentication(req, resp)) return;

        req.getRequestDispatcher("pages/Tickets.jsp").forward(req, resp);


    }
}
