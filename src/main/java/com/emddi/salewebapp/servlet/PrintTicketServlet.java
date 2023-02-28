package com.emddi.salewebapp.servlet;


import com.emddi.salewebapp.db.TicketDao;
import com.emddi.salewebapp.models.Employee;
import com.emddi.salewebapp.models.Ticket;
import com.emddi.salewebapp.pages.common.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "PrintTicketServlet", urlPatterns = "/ticket-print")
public class PrintTicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("pages/Sell.jsp").forward(req, resp);
    }
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Auth.authentication(req, resp)) return;
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("currentEmployee");
        TicketDao ticketDao = new TicketDao();
        ArrayList<Ticket> tickets = null;
//
        String option = req.getParameter("option");
        if (option != null && option.equals("demo")) {
            tickets = ticketDao.getTicketDemo();
        } else {
            String[] idsString = req.getParameterValues("ids");
            ArrayList<Integer> ids = new ArrayList<>();
            for (String idString : idsString) {
                Integer parseInt = Integer.parseInt(idString);
                ids.add(parseInt);
            }
            try {
                ticketDao.updatePrintTimeByIds(ids, employee.getEmployeeId());
                tickets = (ArrayList<Ticket>) ticketDao.findByIds(ids);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(tickets.size() < 1){
                req.getRequestDispatcher("pages/TicketReport.jsp").forward(req, resp);
            }
        }
        req.setAttribute("ticketsToExport", tickets);
        doGet(req, resp);

    }

}
