package com.emddi.servlet;


import com.emddi.synchronize.schedule.ScheduleManager;
import com.emddi.synchronize.sync.task.SyncTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@WebServlet(name = "SyncManagerServlet", urlPatterns = "/sync-manager")
public class SyncManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tasks", ScheduleManager.tasks);
        req.getRequestDispatcher("pages/syncManager.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskClass = req.getParameter("task");
        for(SyncTask task : ScheduleManager.tasks) {
            if (task.getClass().getSimpleName().equals(taskClass)) {

                int initDelay = Integer.parseInt(req.getParameter("initDelay"));
                int period = Integer.parseInt(req.getParameter("period"));
                TimeUnit unit = TimeUnit.valueOf(req.getParameter("unit"));

                task.setInitDelay(initDelay);
                task.setPeriod(period);
                task.setUnit(unit);
                ScheduleManager.restartTaskByTask(task);
            }
        }
        resp.sendRedirect("/sync-manager");



//        if (Auth.authentication(req, resp)) return;
//        HttpSession session = req.getSession();
//        Employee employee = (Employee) session.getAttribute("currentEmployee");
//        TicketDao ticketDao = new TicketDao();
//        ArrayList<Ticket> tickets = null;
////
//        String option = req.getParameter("option");
//        if (option != null && option.equals("demo")) {
//            tickets = ticketDao.getTicketDemo();
//        } else {
//            String[] idsString = req.getParameterValues("ids");
//            ArrayList<Integer> ids = new ArrayList<>();
//            for (String idString : idsString) {
//                Integer parseInt = Integer.parseInt(idString);
//                ids.add(parseInt);
//            }
//            try {
//                ticketDao.updatePrintTimeByIds(ids, employee.getEmployeeId());
//                tickets = (ArrayList<Ticket>) ticketDao.findByIds(ids);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            if(tickets.size() < 1){
//                req.getRequestDispatcher("pages/TicketReport.jsp").forward(req, resp);
//            }
//        }
//        req.setAttribute("ticketsToExport", tickets);
//        doGet(req, resp);

    }

}