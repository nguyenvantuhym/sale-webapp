package com.emddi.dao;


import com.emddi.database.Database;
import com.emddi.database.tableCol.EmployeeCol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDao extends Database implements IDAO{

    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException, JSONException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "INSERT INTO employee (employee_id, name, employee_code, user_name, id_card, birthday, address " +
                ", last_login, originally, office_id, password, image_path, status, position_id , sync, created_at, updated_at)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        JSONArray idsSuccess = new JSONArray();
        JSONArray idsFailed = new JSONArray();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject employee = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);
            statement.setObject(1, employee.isNull(EmployeeCol.employeeId)? null: employee.get(EmployeeCol.employeeId));
            statement.setString(2, employee.isNull(EmployeeCol.name)? null: employee.getString(EmployeeCol.name));
            statement.setString(3, employee.isNull(EmployeeCol.employeeCode)? null: employee.getString(EmployeeCol.employeeCode));
            statement.setString(4, employee.isNull(EmployeeCol.username)? null: employee.getString(EmployeeCol.username));
            statement.setObject(5, employee.isNull(EmployeeCol.idCard)? null: employee.get(EmployeeCol.idCard));
            //todo: hard code
            statement.setString(6, "2023-01-07 08:28:24");
            statement.setString(7, employee.isNull(EmployeeCol.address)? null: employee.getString(EmployeeCol.address));
            statement.setString(8, employee.isNull(EmployeeCol.lastLogin)? null: employee.getString(EmployeeCol.lastLogin));
            statement.setString(9, employee.isNull(EmployeeCol.originally)? null: employee.getString(EmployeeCol.originally));
            statement.setObject(10, employee.isNull(EmployeeCol.officeId)? null: employee.get(EmployeeCol.officeId));

            statement.setString(11, employee.isNull(EmployeeCol.password)? null: employee.getString(EmployeeCol.password));
            statement.setString(12, employee.isNull(EmployeeCol.imagePath)? null: employee.getString(EmployeeCol.imagePath));
            statement.setObject(13, employee.isNull(EmployeeCol.status)? null: employee.get(EmployeeCol.status));
            statement.setObject(14, employee.isNull(EmployeeCol.positionId)? null: employee.get(EmployeeCol.positionId));

//            statement.setObject(14, employee.isNull(EmployeeCol.)? null: employee.get(EmployeeCol.positionId));
            statement.setObject(15, 1);
            statement.setString(16, employee.isNull(EmployeeCol.createdAt)? null: employee.getString(EmployeeCol.createdAt));
            statement.setString(17, employee.isNull(EmployeeCol.updatedAt)? null: employee.getString(EmployeeCol.updatedAt));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.put(employee.getInt(EmployeeCol.employeeId));
                }
            } catch (SQLException | JSONException e){
                idsFailed.put(employee.getInt(EmployeeCol.employeeId));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

    @Override
    public JSONObject syncDownUpdateDB(JSONArray jsonArray) throws SQLException, JSONException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "UPDATE employee SET name=?, employee_code=?, user_name=?, id_card=?, birthday=?, address=?, " +
                " last_login=?, originally=?, office_id=?, password=?, image_path=?, status=?, position_id=?, sync=?, created_at=?, updated_at=?" +
                " WHERE employee_id=?";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject employee = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);
            statement.setString(1, employee.isNull(EmployeeCol.name)? null: employee.getString(EmployeeCol.name));
            statement.setString(2, employee.isNull(EmployeeCol.employeeCode)? null: employee.getString(EmployeeCol.employeeCode));
            statement.setString(3, employee.isNull(EmployeeCol.username)? null: employee.getString(EmployeeCol.username));
            statement.setObject(4, employee.isNull(EmployeeCol.idCard)? null: employee.getInt(EmployeeCol.idCard));
            //todo: Hard code
            statement.setString(5, "2023-01-07 08:28:24");
            statement.setString(6, employee.isNull(EmployeeCol.address)? null: employee.getString(EmployeeCol.address));
            statement.setString(7, employee.isNull(EmployeeCol.lastLogin)? null: employee.getString(EmployeeCol.lastLogin));
            statement.setString(8, employee.isNull(EmployeeCol.originally)? null: employee.getString(EmployeeCol.originally));
            statement.setObject(9, employee.isNull(EmployeeCol.officeId)? null: employee.getInt(EmployeeCol.officeId));

            statement.setString(10, employee.isNull(EmployeeCol.password)? null: employee.getString(EmployeeCol.password));
            statement.setString(11, employee.isNull(EmployeeCol.imagePath)? null: employee.getString(EmployeeCol.imagePath));
            statement.setObject(12, employee.isNull(EmployeeCol.status)? null: employee.getInt(EmployeeCol.status));
            statement.setObject(13, employee.isNull(EmployeeCol.positionId)? null: employee.getInt(EmployeeCol.positionId));
            statement.setObject(14, 1);
            statement.setString(15, employee.isNull(EmployeeCol.createdAt)? null: employee.getString(EmployeeCol.createdAt));
            statement.setString(16, employee.isNull(EmployeeCol.updatedAt)? null: employee.getString(EmployeeCol.updatedAt));
            statement.setObject(17, employee.isNull(EmployeeCol.employeeId)? null: employee.getInt(EmployeeCol.employeeId));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(employee.getInt(EmployeeCol.employeeId));
                }
            } catch (SQLException e){
                idsFailed.add(employee.getInt(EmployeeCol.employeeId));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }
}
