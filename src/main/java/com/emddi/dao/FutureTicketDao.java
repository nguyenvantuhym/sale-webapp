package com.emddi.dao;

import com.emddi.database.Database;
import com.emddi.database.tableCol.FutureTicketCol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FutureTicketDao extends Database implements IDAO {
    public static final Logger LOGGER = Logger.getLogger(FutureTicketDao.class.getName());

    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "INSERT INTO future_ticket (full_name, phone_number, email, code, service_id, adult, " +
                " kid, total_price, discount, pre_money, resources_id, type_pay, checkin_time, status, user_id, created_at, updated_at, id)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject futureTicket = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);

            statement.setString(1, futureTicket.isNull(FutureTicketCol.fullName)? null: futureTicket.getString(FutureTicketCol.fullName));
            statement.setString(2, futureTicket.isNull(FutureTicketCol.phoneNumber)? null: futureTicket.getString(FutureTicketCol.phoneNumber));
            statement.setString(3, futureTicket.isNull(FutureTicketCol.email)? null: futureTicket.getString(FutureTicketCol.email));
            statement.setString(4,  futureTicket.isNull(FutureTicketCol.code)? null: futureTicket.getString(FutureTicketCol.code));
            statement.setObject(5, futureTicket.isNull(FutureTicketCol.serviceId)? null: futureTicket.getInt(FutureTicketCol.serviceId));
            statement.setObject(6, futureTicket.isNull(FutureTicketCol.adult)? null: futureTicket.getInt(FutureTicketCol.adult));
            statement.setObject(7, futureTicket.isNull(FutureTicketCol.kid)? null: futureTicket.getInt(FutureTicketCol.kid));
            statement.setObject(8, futureTicket.isNull(FutureTicketCol.totalPrice)? null: futureTicket.getInt(FutureTicketCol.totalPrice));
            statement.setObject(9, futureTicket.isNull(FutureTicketCol.discount)? null: futureTicket.getInt(FutureTicketCol.discount));
            statement.setObject(10, futureTicket.isNull(FutureTicketCol.preMoney)? null: futureTicket.getInt(FutureTicketCol.preMoney));
            statement.setObject(11, futureTicket.isNull(FutureTicketCol.resourcesId)? null: futureTicket.getInt(FutureTicketCol.resourcesId));
            statement.setObject(12, futureTicket.isNull(FutureTicketCol.typePay)? null: futureTicket.getInt(FutureTicketCol.typePay));
            statement.setString(13, futureTicket.isNull(FutureTicketCol.checkinTime)? null: futureTicket.getString(FutureTicketCol.checkinTime));
            statement.setObject(14, futureTicket.isNull(FutureTicketCol.status)? null: futureTicket.getInt(FutureTicketCol.status));
            statement.setObject(15, futureTicket.isNull(FutureTicketCol.userId)? null: futureTicket.getInt(FutureTicketCol.userId));
            statement.setString(16, futureTicket.isNull(FutureTicketCol.createdAt)? null: futureTicket.getString(FutureTicketCol.createdAt));
            statement.setString(17, futureTicket.isNull(FutureTicketCol.updatedAt)? null: futureTicket.getString(FutureTicketCol.updatedAt));
            statement.setObject(18, 1);

            statement.setObject(19, futureTicket.isNull(FutureTicketCol.id)? null: futureTicket.getInt(FutureTicketCol.id));
            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(futureTicket.getInt(FutureTicketCol.id));
                }
            } catch (SQLException e){
                idsFailed.add(futureTicket.getInt(FutureTicketCol.id));
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

        String SQL_UPDATE = "UPDATE future_ticket " +
                "SET full_name=?,phone_number=?,email=?,code=?,service_id=?,adult=?,kid=?,total_price=?,discount=?, " +
                " pre_money=?,resources_id=?,type_pay=?,checkin_time=?,status=?,user_id=?,created_at=?,updated_at=?, sync=?" +
                "WHERE id=?;";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject futureTicket = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, futureTicket.isNull(FutureTicketCol.fullName)? null: futureTicket.getString(FutureTicketCol.fullName));
            statement.setString(2, futureTicket.isNull(FutureTicketCol.phoneNumber)? null: futureTicket.getString(FutureTicketCol.phoneNumber));
            statement.setString(3, futureTicket.isNull(FutureTicketCol.email)? null: futureTicket.getString(FutureTicketCol.email));
            statement.setString(4,  futureTicket.isNull(FutureTicketCol.code)? null: futureTicket.getString(FutureTicketCol.code));
            statement.setObject(5, futureTicket.isNull(FutureTicketCol.serviceId)? null: futureTicket.getInt(FutureTicketCol.serviceId));
            statement.setObject(6, futureTicket.isNull(FutureTicketCol.adult)? null: futureTicket.getInt(FutureTicketCol.adult));
            statement.setObject(7, futureTicket.isNull(FutureTicketCol.kid)? null: futureTicket.getInt(FutureTicketCol.kid));
            statement.setObject(8, futureTicket.isNull(FutureTicketCol.totalPrice)? null: futureTicket.getInt(FutureTicketCol.totalPrice));
            statement.setObject(9, futureTicket.isNull(FutureTicketCol.discount)? null: futureTicket.getInt(FutureTicketCol.discount));
            statement.setObject(10, futureTicket.isNull(FutureTicketCol.preMoney)? null: futureTicket.getInt(FutureTicketCol.preMoney));
            statement.setObject(11, futureTicket.isNull(FutureTicketCol.resourcesId)? null: futureTicket.getInt(FutureTicketCol.resourcesId));
            statement.setObject(12, futureTicket.isNull(FutureTicketCol.typePay)? null: futureTicket.getInt(FutureTicketCol.typePay));
            statement.setString(13, futureTicket.isNull(FutureTicketCol.checkinTime)? null: futureTicket.getString(FutureTicketCol.checkinTime));
            statement.setObject(14, futureTicket.isNull(FutureTicketCol.status)? null: futureTicket.getInt(FutureTicketCol.status));
            statement.setObject(15, futureTicket.isNull(FutureTicketCol.userId)? null: futureTicket.getInt(FutureTicketCol.userId));
            statement.setString(16, futureTicket.isNull(FutureTicketCol.createdAt)? null: futureTicket.getString(FutureTicketCol.createdAt));
            statement.setString(17, futureTicket.isNull(FutureTicketCol.updatedAt)? null: futureTicket.getString(FutureTicketCol.updatedAt));
            statement.setObject(18, 1);

            statement.setObject(19, futureTicket.isNull(FutureTicketCol.id)? null: futureTicket.getInt(FutureTicketCol.id));
            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(futureTicket.getInt(FutureTicketCol.id));
                }
            } catch (SQLException e){
                idsFailed.add(futureTicket.getInt(FutureTicketCol.id));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }
}
