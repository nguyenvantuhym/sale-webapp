package com.emddi.servlet;


import com.emddi.synchronize.sync.task.employee.SyncEmployeeUpdate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "home", urlPatterns = "/")
public class Home extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("employeeUpdate", SyncEmployeeUpdate.getInstance());
        req.getRequestDispatcher("pages/syncManager.jsp").forward(req, resp);
    }


}