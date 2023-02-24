package com.emddi.dao;

import com.emddi.constants.SyncStatus;
import com.emddi.database.Database;
import com.emddi.database.tableCol.PriceCol;
import com.emddi.database.tableCol.TicketCol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.emddi.utils.DateSQL;

public class TicketDao extends Database implements IDAO, IDAOSyncUp{

    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException, JSONException {
        Connection connection = createConnection();

        ArrayList<String> idsSuccess = new ArrayList<>();

        ArrayList<String> idsFailed = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject ticket = (JSONObject) jsonArray.get(i);
            String INSERT_SQL = "INSERT INTO ticket (uuid, time_using, price_id, printed, payment_method, status, created_at, updated_at, " +
                    " service_id, gate_id, employee_id, card_no," +
                    " usage_time, customer_name, customer_phone, type, future_ticket_id, printed_time, price, sync," +
                    " employee_scan, user_id, booking_id, checkin_logs)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(INSERT_SQL);

                statement.setString(1, ticket.isNull(TicketCol.uuid)? null: ticket.getString(TicketCol.uuid));
                statement.setObject(2, ticket.isNull(TicketCol.timeUsing)? null: ticket.get(TicketCol.timeUsing));
                statement.setObject(3, ticket.isNull(TicketCol.priceId)? null: ticket.get(TicketCol.priceId));
                statement.setObject(4, ticket.isNull(TicketCol.printed)? null: ticket.get(TicketCol.printed));
                statement.setObject(5, ticket.isNull(TicketCol.paymentMethod)? null: ticket.get(TicketCol.paymentMethod));
                statement.setObject(6, ticket.isNull(TicketCol.status)? null: ticket.get(TicketCol.status));
                statement.setObject(7, ticket.isNull(TicketCol.createdAt)? null: ticket.get(TicketCol.createdAt));
                statement.setObject(8, ticket.isNull(TicketCol.updatedAt)? null: ticket.get(TicketCol.updatedAt));
                statement.setObject(9, ticket.isNull(TicketCol.serviceId)? null: ticket.get(TicketCol.serviceId));
                statement.setObject(10, ticket.isNull(TicketCol.gateId)? null: ticket.get(TicketCol.gateId));
                statement.setObject(11, ticket.isNull(TicketCol.employeeId)? null: ticket.get(TicketCol.employeeId));
                statement.setString(12, ticket.isNull(TicketCol.cardNo)? null: ticket.getString(TicketCol.cardNo));
                statement.setString(13, ticket.isNull(TicketCol.usageTime)? null: ticket.getString(TicketCol.usageTime));
                statement.setString(14, ticket.isNull(TicketCol.customerName)? null: ticket.getString(TicketCol.customerName));
                statement.setString(15, ticket.isNull(TicketCol.customerPhone)? null: ticket.getString(TicketCol.customerPhone));
                statement.setObject(16, ticket.isNull(TicketCol.type)? null: ticket.get(TicketCol.type));
                statement.setObject(17, ticket.isNull(TicketCol.futureTicketId)? null: ticket.get(TicketCol.futureTicketId));
                statement.setString(18, ticket.isNull(TicketCol.printedTime)? null: ticket.getString(TicketCol.printedTime));
                statement.setObject(19, ticket.isNull(TicketCol.price)? null: ticket.get(TicketCol.price));
                statement.setObject(20, SyncStatus.SYNC);
                statement.setObject(21, ticket.isNull(TicketCol.employeeScan)? null: ticket.get(TicketCol.employeeScan));
                statement.setObject(22, ticket.isNull(TicketCol.userId)? null: ticket.get(TicketCol.userId));
                statement.setString(23, ticket.isNull(TicketCol.bookingId)? null: ticket.getString(TicketCol.bookingId));
                statement.setString(24, ticket.isNull(TicketCol.checkinLogs)? null: ticket.getString(TicketCol.checkinLogs));

                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(ticket.getString(TicketCol.uuid));
                } else {

                    idsFailed.add(ticket.getString(TicketCol.uuid));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                idsFailed.add(ticket.getString(TicketCol.uuid));
            }

        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

    @Override
    public JSONObject syncDownUpdateDB(JSONArray jsonArray) throws SQLException, JSONException {
        Connection connection = createConnection();

        ArrayList<String> idsSuccess = new ArrayList<>();

        ArrayList<String> idsFailed = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject ticket = (JSONObject) jsonArray.get(i);
            String UPDATE_SQL = "" +
                    "UPDATE ticket " +
                    "SET cancel_time = ?, printed_time = ?, type = ?, employee_cancel = ?, printed = ?, " +
                    "card_no = ?, updated_at = ?, employee_scan = ?, checkin_logs = ?, card_no_unique = ?, user_cancel = ?, " +
                    " status = ?, sync = 1, user_id=?, booking_id=? " +
                    "WHERE uuid = ?";
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(UPDATE_SQL);

                statement.setString(1, ticket.isNull(TicketCol.cancelTime)? null: ticket.getString(TicketCol.cancelTime));
                statement.setString(2, ticket.isNull(TicketCol.printedTime)? null: ticket.getString(TicketCol.printedTime));
                statement.setObject(3, ticket.isNull(TicketCol.type)? null: ticket.get(TicketCol.type));
                statement.setString(4, ticket.isNull(TicketCol.employeeCancel)? null: ticket.getString(TicketCol.employeeCancel));
                statement.setObject(5, ticket.isNull(TicketCol.printed)? null: ticket.get(TicketCol.printed));
                statement.setString(6, ticket.isNull(TicketCol.cardNo)? null: ticket.getString(TicketCol.cardNo));
                statement.setString(7, ticket.isNull(TicketCol.updatedAt)? null: ticket.getString(TicketCol.updatedAt));
                statement.setObject(8, ticket.isNull(TicketCol.employeeScan)? null: ticket.get(TicketCol.employeeScan));
                statement.setString(9, ticket.isNull(TicketCol.checkinLogs)? null: ticket.getString(TicketCol.checkinLogs));
                statement.setString(10, ticket.isNull(TicketCol.cardNoUnique)? null: ticket.getString(TicketCol.cardNoUnique));
                statement.setObject(11, ticket.isNull(TicketCol.userCancel)? null: ticket.get(TicketCol.userCancel));
                statement.setObject(12, ticket.isNull(TicketCol.status)? null: ticket.get(TicketCol.status));
                statement.setObject(13, ticket.isNull(TicketCol.userId)? null: ticket.get(TicketCol.userId));
                statement.setString(14, ticket.isNull(TicketCol.bookingId)? null: ticket.getString(TicketCol.bookingId));

                statement.setString(15, ticket.isNull(TicketCol.uuid)? null: ticket.getString(TicketCol.uuid));

                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(ticket.isNull(TicketCol.uuid)? null: ticket.getString(TicketCol.uuid));
                } else {
                    idsFailed.add(ticket.isNull(TicketCol.uuid)? null: ticket.getString(TicketCol.uuid));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                idsFailed.add(ticket.isNull(TicketCol.uuid)? null: ticket.getString(TicketCol.uuid));
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

    @Override
    public JSONArray findToSync(int syncStatus) {
        System.out.println("Ticket::findTicketToSync: Start " + DateSQL.currentDateTime());
        Connection connection = createConnection();
        String FIND_NEW_CREATE_SQL = "SELECT * FROM ticket WHERE sync = ? LIMIT 50";

        JSONArray tickets = new JSONArray();

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(FIND_NEW_CREATE_SQL);
            statement.setInt(1, syncStatus);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                JSONObject ticket = new JSONObject();
                ticket.put(TicketCol.uuid, rs.getString(TicketCol.uuid));
                ticket.put(TicketCol.status, rs.getInt(TicketCol.status));
                ticket.put(TicketCol.printed, rs.getInt(TicketCol.printed));
                ticket.put(TicketCol.printedTime, rs.getString(TicketCol.printedTime));
                ticket.put(TicketCol.updatedAt, rs.getString(TicketCol.updatedAt));
                if (syncStatus == SyncStatus.NEW) {
                    ticket.put(TicketCol.serviceId, rs.getInt(TicketCol.serviceId));
                    ticket.put(TicketCol.priceId, rs.getInt(TicketCol.priceId));
                    ticket.put(TicketCol.employeeId, rs.getInt(TicketCol.employeeId));
                    ticket.put(TicketCol.gateId, rs.getInt(TicketCol.gateId));
                    ticket.put(TicketCol.cardNo, rs.getString(TicketCol.cardNo));
                    ticket.put(TicketCol.type, rs.getInt(TicketCol.type));
                    ticket.put(TicketCol.price, rs.getInt(TicketCol.price));
                    ticket.put(TicketCol.paymentMethod, rs.getInt(TicketCol.paymentMethod));
                    ticket.put(TicketCol.timeUsing, rs.getInt(TicketCol.timeUsing));
                    ticket.put(TicketCol.usageTime, rs.getString(TicketCol.usageTime));
                    ticket.put(TicketCol.createdAt, rs.getString(TicketCol.createdAt));

                    ticket.put(TicketCol.cardNoUnique, rs.getString(TicketCol.cardNoUnique));
                    ticket.put(TicketCol.futureTicketId, rs.getString(TicketCol.futureTicketId));
                    ticket.put(TicketCol.employeeScan, rs.getInt(TicketCol.employeeScan));
                    ticket.put(TicketCol.userId, rs.getInt(TicketCol.userId));
                    ticket.put(TicketCol.bookingId, rs.getString(TicketCol.bookingId));
                    ticket.put(TicketCol.checkinLogs, rs.getString(TicketCol.checkinLogs));
                }
                if (syncStatus == SyncStatus.UPDATE) {
                    ticket.put(TicketCol.employeeScan, rs.getInt(TicketCol.employeeScan));
                    ticket.put(TicketCol.checkinLogs, rs.getString(TicketCol.checkinLogs));

                    ticket.put(TicketCol.employeeCancel, rs.getString(TicketCol.employeeCancel));
                    ticket.put(TicketCol.userCancel, rs.getString(TicketCol.userCancel));
                    ticket.put(TicketCol.cancelTime, rs.getString(TicketCol.cancelTime));
                }

                tickets.put(ticket);
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Ticket::findTicketToSync: Done " + DateSQL.currentDateTime());

        return tickets;
    }

    @Override
    public boolean updateSyncStatus(JSONArray uuids, int status) {
        if (uuids.length() == 0) return true;
        final String UPDATE_SYNC_STATUS = "UPDATE ticket SET sync = ? WHERE uuid = ?";

        int[] inserted = new int[0];
        Connection connection = createConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(UPDATE_SYNC_STATUS);

            for (Object uuid : uuids) {
                statement.setInt(1, status);
                statement.setString(2, uuid.toString());

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
