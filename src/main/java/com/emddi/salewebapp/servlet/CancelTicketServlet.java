package com.emddi.salewebapp.servlet;

import com.emddi.salewebapp.db.TicketDao;
import com.emddi.salewebapp.models.Employee;
import com.emddi.salewebapp.pages.common.Auth;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(name = "CancelTicketServlet", urlPatterns = "/ticket-cancel")
public class CancelTicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("pages/TicketReport.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Auth.authentication(req, resp)) return;
        TicketDao ticketDao = new TicketDao();
        String[] idsString = req.getParameterValues("ids[]");
        HttpSession session = req.getSession();
        Employee employee = (Employee) session.getAttribute("currentEmployee");
        ArrayList<Integer> ids = new ArrayList<>();
        for (String idString : idsString) {
            Integer parseInt = Integer.parseInt(idString);
            ids.add(parseInt);
        }
        boolean result = false;
        try {
            result = ticketDao.cancelTicketByIds(ids, employee.getEmployeeId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", result);
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonObject);
        out.flush();
    }

}

