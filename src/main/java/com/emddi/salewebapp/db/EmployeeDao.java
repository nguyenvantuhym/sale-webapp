package com.emddi.salewebapp.db;

import com.emddi.salewebapp.db.tablecol.EmployeeCol;
import com.emddi.salewebapp.models.Employee;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDao extends Database implements IDAO, IDAOSyncUp{
    public Employee getByUserName(String username) throws SQLException {
        Connection connection = createConnection();
        // use getByUsername for login feature
        PreparedStatement statement = connection.prepareStatement("SELECT employee_id, name, user_name, password FROM employee WHERE user_name = ? AND (position_id = 1 OR position_id = 2) ");
        statement.setMaxRows(1);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) return null;

        Employee employee = Employee.builder()
                .employeeId(resultSet.getInt(EmployeeCol.employeeId))
                .name(resultSet.getString(EmployeeCol.name))
                .username(resultSet.getString(EmployeeCol.username))
                .password(resultSet.getString(EmployeeCol.password))
                .build();


        connection.close();

        return employee;
    }


    public ArrayList<Employee> getAll() throws SQLException {
        Connection connection = createConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT employee_id, name, user_name, password FROM employee ");
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Employee> staffArrayList = new ArrayList<>();
        while (resultSet.next()) {
            Employee employee = Employee.builder()
            .employeeId(resultSet.getInt("employee_id"))
            .name(resultSet.getString("name"))
            .username(resultSet.getString("user_name"))
            .password(resultSet.getString("password"))
                    .build();
            staffArrayList.add(employee);
        }

        connection.close();
        return staffArrayList;
    }

    public boolean changePasswordByUsername(String username, String hashPassword) throws SQLException {
        Connection connection = createConnection();

        String SQL = "UPDATE employee SET password = ?, sync=2 WHERE user_name = ? AND (position_id = 1 OR position_id = 2) ";

        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setString(1, hashPassword);
        statement.setString(2, username);

        int success = statement.executeUpdate();

        connection.close();

        return success > 0;
    }

    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "INSERT INTO employee (employee_id, name, employee_code, user_name, id_card, birthday, address " +
                ", last_login, originally, office_id, password, image_path, status, position_id , sync, created_at, updated_at)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

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
            statement.setObject(15, 1);
            statement.setString(16, employee.isNull(EmployeeCol.createdAt)? null: employee.getString(EmployeeCol.createdAt));
            statement.setString(17, employee.isNull(EmployeeCol.updatedAt)? null: employee.getString(EmployeeCol.updatedAt));

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

    @Override
    public JSONObject syncDownUpdateDB(JSONArray jsonArray) throws SQLException {
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

    @Override
    public JSONArray findToSync(int syncStatus) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "SELECT employee_id, password, updated_at FROM employee WHERE sync=?";

        PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);
        statement.setObject(1, syncStatus);
        ResultSet resultSet = statement.executeQuery();

        JSONArray employees = new JSONArray();
        while (resultSet.next()) {
            JSONObject employee = new JSONObject();
            employee.put(EmployeeCol.employeeId, resultSet.getInt(EmployeeCol.employeeId));
            employee.put(EmployeeCol.password, resultSet.getString(EmployeeCol.password));
            employee.put(EmployeeCol.updatedAt, resultSet.getString(EmployeeCol.updatedAt));

            employees.put(employee);
        }
        connection.close();

        return employees;
    }

    @Override
    public boolean updateSyncStatus(JSONArray idsSuccess, int sync) {

        String SQL = "UPDATE employee SET sync=? WHERE employee_id=? ";
        if (idsSuccess.length() == 0) return true;

        int[] inserted = new int[0];
        Connection connection = createConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(SQL);

            for (Object employeeId : idsSuccess) {
                statement.setInt(1, sync);
                statement.setString(2, employeeId.toString());

                statement.addBatch();
            }

            inserted = statement.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted.length > 0;
    }
}
