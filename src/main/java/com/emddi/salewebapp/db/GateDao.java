package com.emddi.salewebapp.db;

import com.emddi.salewebapp.db.tablecol.GateCol;
import com.emddi.salewebapp.models.Gate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GateDao extends Database implements IDAO {
    public ArrayList<Gate> getAllAvailable() {
        Connection connection = createConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Gate> gates = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT id, name FROM gate WHERE status = 1");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Gate gate = Gate.builder()
                        .id(resultSet.getInt(GateCol.id))
                        .name(resultSet.getString(GateCol.name))
                        .build();

                gates.add(gate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gates;
    }

    public Gate getById(int gateId) throws SQLException {
        Connection connection = createConnection();
        PreparedStatement statement = null;
        Gate gate = null;

        statement = connection.prepareStatement("SELECT id, name FROM gate WHERE id = ?");
        statement.setInt(1, gateId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            gate = Gate.builder()
                    .id(resultSet.getInt(GateCol.id))
                    .name(resultSet.getString(GateCol.name))
                    .build();
        }

        return gate;
    }

    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "INSERT INTO gate (office_id, name, status, description, created_at, updated_at, sync, id)" +
                " VALUES (?,?,?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject gate = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);
            statement.setObject(1, gate.isNull(GateCol.officeId)? null: gate.get(GateCol.officeId));
            statement.setString(2, gate.isNull(GateCol.name)? null: gate.getString(GateCol.name));
            statement.setObject(3, gate.isNull(GateCol.status)? null: gate.get(GateCol.status));
            statement.setString(4, gate.isNull(GateCol.description)? null: gate.getString(GateCol.description));
            statement.setString(5, gate.isNull(GateCol.createdAt)? null: gate.getString(GateCol.createdAt));
            statement.setString(6, gate.isNull(GateCol.updatedAt)? null: gate.getString(GateCol.updatedAt));
            statement.setObject(7, 1);
            statement.setObject(8, gate.isNull(GateCol.id)? null: gate.get(GateCol.id));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(gate.getInt(GateCol.id));
                }
            } catch (SQLException e){
                idsFailed.add(gate.getInt(GateCol.id));
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

        String SQL_NEW_ORDER = "UPDATE gate SET office_id=?, name=?, status=?, description=?, created_at=?, updated_at=?, sync=? " +
                " WHERE id=? ";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject gate = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);
            statement.setObject(1, gate.isNull(GateCol.officeId)? null: gate.get(GateCol.officeId));
            statement.setString(2, gate.isNull(GateCol.name)? null: gate.getString(GateCol.name));
            statement.setObject(3, gate.isNull(GateCol.status)? null: gate.get(GateCol.status));
            statement.setString(4, gate.isNull(GateCol.description)? null: gate.getString(GateCol.description));
            statement.setString(5, gate.isNull(GateCol.createdAt)? null: gate.getString(GateCol.createdAt));
            statement.setString(6, gate.isNull(GateCol.updatedAt)? null: gate.getString(GateCol.updatedAt));
            statement.setObject(7, 1);
            statement.setObject(8, gate.isNull(GateCol.id)? null: gate.get(GateCol.id));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(gate.getInt(GateCol.id));
                }
            } catch (SQLException e){
                idsFailed.add(gate.getInt(GateCol.id));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

}
