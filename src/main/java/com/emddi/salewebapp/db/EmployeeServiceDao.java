package com.emddi.salewebapp.db;

import com.emddi.salewebapp.db.tablecol.EmployeeServiceCol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeServiceDao extends Database implements IDAO{
    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW = "INSERT INTO employee_service (employee_id, service_id, sync, created_at)" +
                " VALUES (?,?,?,?)";

        ArrayList<String> idsSuccess = new ArrayList<>();
        ArrayList<String> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject employeeService = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW);
            statement.setObject(1, employeeService.isNull(EmployeeServiceCol.employeeId)? null: employeeService.get(EmployeeServiceCol.employeeId));
            statement.setObject(2, employeeService.isNull(EmployeeServiceCol.serviceId)? null: employeeService.get(EmployeeServiceCol.serviceId));
            statement.setObject(3, 1);
            statement.setString(4, employeeService.isNull(EmployeeServiceCol.createdAt)? null: employeeService.getString(EmployeeServiceCol.createdAt));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    String employeeId = Integer.toString(employeeService.getInt(EmployeeServiceCol.employeeId));
                    String serviceId = Integer.toString(employeeService.getInt(EmployeeServiceCol.serviceId));
                    idsSuccess.add(employeeId+ "|"+serviceId);
                }
            } catch (SQLException e){
                String employeeId = Integer.toString(employeeService.getInt(EmployeeServiceCol.employeeId));
                String serviceId = Integer.toString(employeeService.getInt(EmployeeServiceCol.serviceId));
                idsFailed.add(employeeId+ "|"+serviceId);
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

    @Override
    public JSONObject syncDownUpdateDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW = "INSERT INTO employee_service (employee_id, service_id, sync, created_at)" +
                " VALUES (?,?,?,?)";

        ArrayList<String> idsSuccess = new ArrayList<>();
        ArrayList<String> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject employeeService = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW);
            statement.setObject(1, employeeService.isNull(EmployeeServiceCol.employeeId)? null: employeeService.get(EmployeeServiceCol.employeeId));
            statement.setObject(2, employeeService.isNull(EmployeeServiceCol.serviceId)? null: employeeService.get(EmployeeServiceCol.serviceId));
            statement.setObject(3, 1);
            statement.setString(4, employeeService.isNull(EmployeeServiceCol.createdAt)? null: employeeService.getString(EmployeeServiceCol.createdAt));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    String employeeId = Integer.toString(employeeService.getInt(EmployeeServiceCol.employeeId));
                    String serviceId = Integer.toString(employeeService.getInt(EmployeeServiceCol.serviceId));
                    idsSuccess.add(employeeId+ "|"+serviceId);
                }
            } catch (SQLException e){
                String employeeId = Integer.toString(employeeService.getInt(EmployeeServiceCol.employeeId));
                String serviceId = Integer.toString(employeeService.getInt(EmployeeServiceCol.serviceId));
                idsFailed.add(employeeId+ "|"+serviceId);
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }
}
