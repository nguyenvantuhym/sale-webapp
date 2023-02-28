
package com.emddi.salewebapp.servlet;

import com.emddi.salewebapp.db.EmployeeDao;
import com.emddi.salewebapp.models.Employee;
import com.emddi.salewebapp.pages.common.Auth;
import com.emddi.salewebapp.util.Md5;
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

@WebServlet(name = "ChangePasswordServlet", urlPatterns = "/change-password")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("pages/changePassword.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Auth.authentication(req, resp)) return;

        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String retypePassword = req.getParameter("retypePassword");

        HttpSession session = req.getSession();
        JSONObject jsonObject = new JSONObject();
        try {
            Employee employee = (Employee) session.getAttribute("currentEmployee");
            String hashPassword = Md5.hash(oldPassword);
            if (employee.getPassword().toUpperCase().equals(hashPassword)) {
                if(newPassword.equals(retypePassword) && retypePassword.length() > 0) {
                    EmployeeDao employeeDao = new EmployeeDao();

                    boolean success = employeeDao.changePasswordByUsername(employee.getUsername(), Md5.hash(newPassword));
                    jsonObject.put("success", success);
                    if(success) jsonObject.put("message", "Thay đổi password thành công");
                    else  jsonObject.put("message", "Có lỗi xảy ra");
                } else {
                    jsonObject.put("success", false);
                    jsonObject.put("message", "Mật khẩu bị bỏ trống hoặc mật khẩu mới chưa khớp , xin vui lòng nhập lại");
                }
            } else {
                jsonObject.put("success", false);
                jsonObject.put("message", "Mật khẩu cũ không khớp");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonObject);
        out.flush();
    }

}


