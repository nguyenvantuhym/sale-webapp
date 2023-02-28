package com.emddi.salewebapp.servlet;

import com.emddi.salewebapp.db.TicketDao;
import com.emddi.salewebapp.models.FilterTicket;
import com.emddi.salewebapp.pages.common.Auth;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "SalesReportServlet", urlPatterns = "/sale-report")
public class SalesReportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Auth.authentication(req, resp)) return;
        String dateFrom = req.getParameter("dateFrom");
        String service = req.getParameter("service");
        String ticketCode = req.getParameter("ticketCode");
        String dateTo = req.getParameter("dateTo");
        String staff = req.getParameter("staff");
        String ticketStatus = req.getParameter("ticketStatus");
        String ticketType= req.getParameter("ticketType");
        String paymentMethod = req.getParameter("paymentMethod");

        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        FilterTicket filterTicket = new FilterTicket(dateFrom, service, ticketCode, ticketType, dateTo, staff, ticketStatus, paymentMethod, pageSize, pageNumber);
        TicketDao ticketDao = new TicketDao();
        JSONObject jsonObj;
        try {
            jsonObj = ticketDao.saleReport(filterTicket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonObj);
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/TicketReport.jsp").forward(req, resp);
    }
}

