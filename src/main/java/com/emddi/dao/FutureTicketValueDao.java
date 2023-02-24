package com.emddi.dao;

import com.emddi.database.Database;
import com.emddi.database.tableCol.FutureTicketValueCol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FutureTicketValueDao extends Database implements IDAO{

    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER_VALUES = "INSERT INTO future_ticket_value (future_ticket_id, price_id, value, code, sync, created_at, updated_at, id) VALUES" +
                " (?,?,?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject futureTicketValue = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER_VALUES);
            statement.setObject(1, futureTicketValue.isNull(FutureTicketValueCol.futureTicketId)? null: futureTicketValue.get(FutureTicketValueCol.futureTicketId));
            statement.setObject(2, futureTicketValue.isNull(FutureTicketValueCol.priceId)? null: futureTicketValue.getInt(FutureTicketValueCol.priceId));
            statement.setObject(3, futureTicketValue.isNull(FutureTicketValueCol.value)? null: futureTicketValue.get(FutureTicketValueCol.value));
            statement.setString(4, futureTicketValue.isNull(FutureTicketValueCol.code)? null: futureTicketValue.getString(FutureTicketValueCol.code));
            statement.setObject(5, 1);
            statement.setString(6, futureTicketValue.isNull(FutureTicketValueCol.createdAt)? null: futureTicketValue.getString(FutureTicketValueCol.createdAt));
            statement.setString(7, futureTicketValue.isNull(FutureTicketValueCol.updatedAt)? null: futureTicketValue.getString(FutureTicketValueCol.updatedAt));
            statement.setObject(8, futureTicketValue.isNull(FutureTicketValueCol.id)? null: futureTicketValue.getInt(FutureTicketValueCol.id));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(futureTicketValue.getInt(FutureTicketValueCol.id));
                }
            } catch (SQLException e){
                idsFailed.add(futureTicketValue.getInt(FutureTicketValueCol.id));
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

        String SQL_NEW_ORDER_VALUES = "UPDATE future_ticket_value SET future_ticket_id=?, price_id=?, value=?, code=?, sync=?, created_at=?, updated_at=? " +
                " WHERE id=?";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject futureTicketValue = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER_VALUES);
            statement.setObject(1, futureTicketValue.isNull(FutureTicketValueCol.futureTicketId)? null: futureTicketValue.get(FutureTicketValueCol.futureTicketId));
            statement.setObject(2, futureTicketValue.isNull(FutureTicketValueCol.priceId)? null: futureTicketValue.get(FutureTicketValueCol.priceId));
            statement.setObject(3, futureTicketValue.isNull(FutureTicketValueCol.value)? null: futureTicketValue.get(FutureTicketValueCol.value));
            statement.setString(4, futureTicketValue.isNull(FutureTicketValueCol.code)? null: futureTicketValue.getString(FutureTicketValueCol.code));
            statement.setObject(5, 1);
            statement.setString(6, futureTicketValue.isNull(FutureTicketValueCol.createdAt)? null: futureTicketValue.getString(FutureTicketValueCol.createdAt));
            statement.setString(7, futureTicketValue.isNull(FutureTicketValueCol.updatedAt)? null: futureTicketValue.getString(FutureTicketValueCol.updatedAt));
            statement.setObject(8, futureTicketValue.isNull(FutureTicketValueCol.id)? null: futureTicketValue.getInt(FutureTicketValueCol.id));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(futureTicketValue.getInt(FutureTicketValueCol.id));
                }
            } catch (SQLException e){
                idsFailed.add(futureTicketValue.getInt(FutureTicketValueCol.id));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }
}
