package com.emddi.salewebapp.servlet.ticket;

import com.emddi.salewebapp.constants.RouteConstant;
import com.emddi.salewebapp.constants.ScanConstant;
import com.emddi.salewebapp.constants.TicketType;
import com.emddi.salewebapp.db.TicketDao;
import com.emddi.salewebapp.models.Ticket;
import com.emddi.salewebapp.models.TicketOnCloud;
import com.emddi.salewebapp.pages.common.Auth;
import com.emddi.salewebapp.synchronize.CloudRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "CheckTicketServlet", urlPatterns = {RouteConstant.CHECK_TICKET})
public class CheckTicketServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        if (Auth.authentication(req, resp)) return;

        String cmd = req.getParameter(ScanConstant.Cmd.PARAM_KEY);

        if (cmd != null) {
            if (cmd.equals(ScanConstant.Cmd.SCAN)) {
                String cardNumber = req.getParameter(ScanConstant.Params.TICKET_CODE);
                req.setAttribute("ticketCode", cardNumber);

                JSONObject response = new JSONObject();
                response.put("success", false);
                response.put("queryCode", cardNumber);

                int firstNumber = Integer.parseInt(cardNumber.substring(0, 1));
                int secondNumber = Integer.parseInt(cardNumber.substring(1, 2));

                if (firstNumber < 1 || firstNumber > 7) {
                    return;
                }


                Ticket ticket = null;
                JSONObject data = new JSONObject();
                if (secondNumber == 0 && firstNumber <= 5) {
                    TicketDao ticketDao = new TicketDao();
                    try {
                        ticket = ticketDao.getByCardNumberBeforeNow(cardNumber);
                        if (ticket != null)
                            data.put("available", ticket.checkAvailability());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } else if (secondNumber > 0 && secondNumber <= 9 && firstNumber <= 5 || secondNumber == 0) {
                    TicketOnCloud ticketOnCloud = CloudRequest.checkTicketOnCloud(cardNumber);
                    data.put("available", ticketOnCloud.isAvailable());
                    if (ticketOnCloud.getTicket() != null) ticket = ticketOnCloud.getTicket();
                }

                if (ticket != null) {
                    data.put("cardNo", ticket.getCardNo());
                    data.put("ticketType", ticket.getPriceObj().getPriceName());
                    data.put("createdAt", ticket.getCreatedAt());
                    data.put("gateName", ticket.getGate().getName());
                    data.put("staffName", ticket.getEmployee().getName());
                    data.put("source", ticket.getTypeText());

                    JSONArray printTime = new JSONArray();
                    String print = ticket.getPrintedTime();
                    if (print != null && !print.isEmpty()) {
                        printTime = new JSONArray(print);
                    }

                    data.put("printTime", printTime);

                    JSONArray checkinLogs = new JSONArray();
                    String checkin = ticket.getCheckinLogs();
                    if (checkin != null && !checkin.isEmpty()) {
                        checkinLogs = new JSONArray(checkin);
                    }

                    data.put("checkinLogs", checkinLogs);

                    String usageTime = ticket.getUsageTime();
                    int ticketType = ticket.getType();
                    if (ticketType != TicketType.VE_IN_TRUOC_DUNG_THEO_GIO) {
                        usageTime = usageTime.substring(0, 10);
                    } else {
                        usageTime += " +" + ticket.getTimeUsing() + "h";
                    }
                    data.put("usageTime", usageTime);


                    response.put("data", data);
                    response.put("success", true);
                }


                PrintWriter out = resp.getWriter();
                out.write(response.toString());
                out.flush();
                out.close();
                return;

            }
        }

        req.getRequestDispatcher("pages/CheckTicket.jsp").forward(req, resp);
    }
}
