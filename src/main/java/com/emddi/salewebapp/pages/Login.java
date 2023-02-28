package com.emddi.salewebapp.pages;

import com.emddi.salewebapp.db.EmployeeDao;
import com.emddi.salewebapp.db.GateDao;
import com.emddi.salewebapp.models.Employee;
import com.emddi.salewebapp.models.Gate;
import com.emddi.salewebapp.util.DateSQL;
import com.emddi.salewebapp.util.Md5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LoginControl", urlPatterns = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String username = req.getParameter("username");
            String password = req.getParameter("password");

            HttpSession session = req.getSession();

            System.out.println("LOGIN: --- username " + username + "\t" + DateSQL.currentDateTimeMs());
            EmployeeDao employeeDao = new EmployeeDao();
            Employee employee = employeeDao.getByUserName(username);
            if (employee == null) {
                req.setAttribute("loginMessage", "Không tìm thấy người dùng");
                System.out.println("LOGIN: --- username " + username + "\t" + DateSQL.currentDateTimeMs() + "\tKhông tìm thấy người dùng");
                doGet(req, resp);
            } else {
                String hashPassword = Md5.hash(password);
                if (!employee.getPassword().toUpperCase().equals(hashPassword)) {
                    req.setAttribute("loginMessage", "Mật khẩu không đúng");
                    System.out.println("LOGIN: --- username " + username + "\t" + DateSQL.currentDateTimeMs() + "\tMật khẩu không đúng");
                    doGet(req, resp);
                } else {
                    System.out.println("LOGIN: --- username " + username + "\t" + DateSQL.currentDateTimeMs() + "\tThành công");
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("prices", new ArrayList<>());
                    session.setAttribute("currentEmployee", employee);

                    session.setAttribute("currentGate", Gate.builder().build());
                    session.setAttribute("listGate", new GateDao().getAllAvailable());
                    resp.sendRedirect("/sell-ticket?cmd=add&type=single");
                }
            }
        } catch (Exception e) {
            doGet(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/Login.jsp").forward(req, resp);
    }
}
