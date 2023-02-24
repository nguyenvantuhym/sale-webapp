package com.emddi.servlet;


import com.emddi.synchronize.schedule.ScheduleManager;
import com.emddi.synchronize.sync.task.SyncTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "SyncStopServlet", urlPatterns = "/sync-stop-start")
public class SyncStopStartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskClass = req.getParameter("task");
        String cmd = req.getParameter("cmd");
        for(SyncTask task : ScheduleManager.tasks) {
            if (task.getClass().getSimpleName().equals(taskClass)) {
                if(cmd.equals("start") && !task.getIsStarted()) {
                    ScheduleManager.restartTaskByTask(task);
                } else if(cmd.equals("stop") && task.getIsStarted()) {
                    task.stop();
                }
            }
        }
        resp.sendRedirect("/");



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