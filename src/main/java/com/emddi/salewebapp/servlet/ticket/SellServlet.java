package com.emddi.salewebapp.servlet.ticket;

import com.emddi.salewebapp.constants.FutureTicketStatus;
import com.emddi.salewebapp.constants.RouteConstant;
import com.emddi.salewebapp.constants.SellConstant;
import com.emddi.salewebapp.db.FutureTicketDao;
import com.emddi.salewebapp.db.GateDao;
import com.emddi.salewebapp.db.PriceDao;
import com.emddi.salewebapp.db.TicketDao;
import com.emddi.salewebapp.models.*;
import com.emddi.salewebapp.pages.common.Auth;
import com.emddi.salewebapp.util.DateSQL;
import com.emddi.salewebapp.util.RandomString;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet(name = "SellControl", urlPatterns = {RouteConstant.SELL_TICKET})
public class SellServlet extends HttpServlet {
    RandomString randomHead = new RandomString(1, new SecureRandom(), "12345");
    RandomString randomTail = new RandomString(7, new SecureRandom(), RandomString.digits);

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=UTF-8");
        if (Auth.authentication(req, resp)) return;
        HttpSession session = req.getSession();

        String cmd = req.getParameter(SellConstant.Cmd.PARAM_KEY);
        if (cmd == null) cmd = SellConstant.Cmd.ADD;
        String action = req.getParameter(SellConstant.Action.PARAM_KEY);
        String type = req.getParameter(SellConstant.Type.PARAM_KEY);
        if (type == null) type = SellConstant.Type.SINGLE;

        ArrayList<Price> prices = (ArrayList<Price>) session.getAttribute("prices");
        Employee currentEmployee = (Employee) session.getAttribute("currentEmployee");
        int employeeId = currentEmployee.getEmployeeId();

        switch (cmd) {
            case SellConstant.Cmd.ADD:

                // Lưu trữ những loại vé được bán vào session
                if (prices.size() <= 0) {
                    PriceDao priceDao = new PriceDao();
                    prices = priceDao.getAllActiveByEmployee(employeeId);
                    session.setAttribute("prices", prices);
                }

                // Chọn quầy
                if (action != null && action.equals(SellConstant.Action.SET_GATE)) {
                    int gateId = Integer.parseInt(req.getParameter("gateId"));
                    Gate gate = new GateDao().getById(gateId);

                    session.setAttribute("currentGate", gate);
                    resp.sendRedirect("/sell-ticket?cmd=" + cmd + "&type=" + type);
                    return;
                }
                break;
            case SellConstant.Cmd.EXPORT:
                if (action.equals(SellConstant.Action.CREATE)) {
                    try {
                        createTicket(req);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case SellConstant.Cmd.QUERY:
                if (type.equals(SellConstant.Type.GROUP)) {
                    String orderCode = req.getParameter("orderCode");
                    FutureTicketDao futureTicketDao = new FutureTicketDao();
                    FutureTicket order = futureTicketDao.getLastByOrderCode(orderCode);

                    JSONObject response = new JSONObject();
                    response.put("success", false);
                    if (order == null || order.getStatus() == FutureTicketStatus.CANCELLED) {
                        response.put("message", "Không tìm thấy mã hợp đồng");
                    } else if (order.getStatus() == FutureTicketStatus.GENERATED) {
                        response.put("message", "Hợp đồng đã được xuất vé");
                    } else {
                        response.put("success", true);

                        JSONArray groupTickets = new JSONArray();
                        ArrayList<Price> finalTicketTypes = prices;
                        order.getFutureTicketValues().forEach(value -> {
                            Price price = Price.builder().build();

                            for (Price _price : finalTicketTypes) {
                                if (_price.getPriceId() == value.getPriceId()) price = _price;
                            }

                            JSONObject ticketBill = new JSONObject();
                            ticketBill.put("priceName", price.getPriceName());
                            ticketBill.put("price", price.getPrice());
                            ticketBill.put("quantity", value.getValue());
                            ticketBill.put("priceId", price.getPriceId());
                            ticketBill.put("usageTime", DateSQL.toDisplayFormat(order.getCheckinTime()));
                            ticketBill.put("vat", price.getVat());
                            ticketBill.put("applyDiscount", price.getApplyDiscount());

                            groupTickets.put(ticketBill);
                        });

                        JSONObject data = new JSONObject();
                        data.put("bills", groupTickets);
                        data.put("orderId", order.getId());
                        data.put("orderCode", order.getCode());
                        data.put("fullName", order.getFullName());
                        data.put("phoneNumber", order.getPhoneNumber());
                        data.put("email", order.getEmail());
                        data.put("discount", order.getDiscount());
                        data.put("preMoney", order.getPreMoney());
                        data.put("usageTime", DateSQL.toDisplayFormat(order.getCheckinTime()));
                        data.put("paymentMethod", order.getTypePay());

                        response.put("data", data);
                    }


                    PrintWriter out = resp.getWriter();
                    out.write(response.toString());
                    out.flush();
                    out.close();
                }
                return;
        }
        req.getRequestDispatcher("pages/Sell.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    private void createTicket(HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession();

        String bookingPersonName = req.getParameter("bookingPersonName");
        String bookingPersonPhone = req.getParameter("bookingPersonPhone");
        int gateId = Integer.parseInt(req.getParameter("gateId"));
        int customerType = Integer.parseInt(req.getParameter("customerType"));
        int paymentMethod = Integer.parseInt(req.getParameter("paymentMethod"));
        int orderId;
        if (req.getParameter("orderId") != null) {
            orderId = Integer.parseInt(req.getParameter("orderId"));
        } else {
            orderId = 0;
        }
        String batch = createBatch();

        Employee currentEmployee = (Employee) session.getAttribute("currentEmployee");
        int employeeId = currentEmployee.getEmployeeId();
        Gate currentGate = (Gate) session.getAttribute("currentGate");

        String[] billsParam = req.getParameterValues("bills[]");
        ArrayList<JSONObject> bills = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();

        if (billsParam == null) return;

        for (String billString : billsParam) {
            JSONObject bill = new JSONObject(billString);
            bills.add(bill);
        }


        ArrayList<Price> prices = (ArrayList<Price>) session.getAttribute("prices");

        bills.forEach(bill -> {
            int quantity = bill.getInt("quantity");
            int priceId = bill.getInt("priceId");

            Price price = Price.builder().build();

            for (Price item : prices) {
                if (item.getPriceId() == priceId) price = item;
            }

            for (int i = 0; i < quantity; i++) {
                UUID uniqueId = UUID.randomUUID();

                String pin = createCardNo();
                Ticket ticket = Ticket.builder().build();
                ticket.setUuid(uniqueId.toString());
                ticket.setServiceId(price.getServiceId());
                ticket.setPriceId(price.getPriceId());
                ticket.setGateId(gateId);
                ticket.setEmployeeId(employeeId);
                ticket.setType(customerType);
                ticket.setCardNo(pin);
                ticket.setUsageTime(DateSQL.toSqlFormat(bill.getString("usageTime")));
                ticket.setCustomerName(bookingPersonName);
                ticket.setCustomerPhone(bookingPersonPhone);
                ticket.setPriceObj(price);
                ticket.setEmployee(currentEmployee);
                ticket.setGate(currentGate);
                ticket.setPaymentMethod(paymentMethod);
                if (orderId > 0)
                    ticket.setFutureTicketId(orderId);

                JSONArray printedArray = new JSONArray();
                JSONObject printedObject = new JSONObject();
                printedObject.put("emp_id", employeeId);
                printedObject.put("time", DateSQL.currentDateTime());
                printedArray.put(printedObject);
                ticket.setPrintedTime(printedArray.toString());

                tickets.add(ticket);
            }
        });

        TicketDao ticketDao = new TicketDao();
        boolean result = ticketDao.saveBatch(tickets);

        if (result) {
            if (orderId > 0) {
                FutureTicketDao futureTicketDao = new FutureTicketDao();
                futureTicketDao.updateStatus(FutureTicketStatus.GENERATED, orderId);
            }
        }

        req.setAttribute("ticketsToExport", tickets);
    }

    private String createBatch() {
        LocalDateTime now = LocalDateTime.now();
        String batch;

        batch = String.format("%d%s%s%s%s%s", now.getYear(), fillZero(now.getMonthValue()), fillZero(now.getDayOfMonth()),
                fillZero(now.getHour()), fillZero(now.getMinute()), fillZero(now.getSecond()));

        return batch;
    }

    private String fillZero(int val) {
        if (val < 10) return ("0" + val);
        else return String.valueOf(val);
    }

    private String createCardNo() {
        return (randomHead.nextString() + "0" + randomTail.nextString());
    }
}
