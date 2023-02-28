package com.emddi.salewebapp.db;

import com.emddi.salewebapp.constants.SyncStatus;
import com.emddi.salewebapp.db.tablecol.FutureTicketCol;
import com.emddi.salewebapp.models.FutureTicket;
import com.emddi.salewebapp.models.FutureTicketValue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FutureTicketDao extends Database implements IDAO, IDAOSyncUp {
    public static final Logger LOGGER = Logger.getLogger(FutureTicketDao.class.getName());


    public FutureTicket getLastByOrderCode(String orderCode) {
        Connection connection = createConnection();
        String SQL_RO = "SELECT * FROM future_ticket WHERE code = ? AND status = 1 ORDER BY id DESC LIMIT 1";
        FutureTicket futureTicket = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_RO);
            statement.setString(1, orderCode);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                futureTicket = FutureTicket.builder()
                        .id(rs.getInt(FutureTicketCol.id))
                        .fullName(rs.getString(FutureTicketCol.fullName))
                        .phoneNumber(rs.getString(FutureTicketCol.phoneNumber))
                        .email(rs.getString(FutureTicketCol.email))
                        .code(rs.getString(FutureTicketCol.code))
                        .discount(rs.getInt(FutureTicketCol.discount))
                        .preMoney(rs.getInt(FutureTicketCol.preMoney))
                        .typePay(rs.getInt(FutureTicketCol.typePay))
                        .checkinTime(rs.getString(FutureTicketCol.checkinTime))
                        .status(rs.getInt(FutureTicketCol.status))
                        .userId(rs.getInt(FutureTicketCol.userId))
                        .resourcesId(rs.getInt(FutureTicketCol.resourcesId))
                        .sync(rs.getInt(FutureTicketCol.sync))
                        .createdAt(rs.getString(FutureTicketCol.createdAt))
                        .build();

                FutureTicketValueDao futureTicketValueDao = new FutureTicketValueDao();
                futureTicket.setFutureTicketValues(futureTicketValueDao.getByFutureTicketId(futureTicket.getId()));
            }

            connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.INFO, e.toString());
        }


        return futureTicket;
    }

    public boolean insertNewOrderSync(FutureTicket futureTicket) {
        Connection connection = createConnection();
        PreparedStatement stmtFT = null;
        PreparedStatement stmtFTV = null;

        String SQL_NEW_ORDER = "INSERT INTO future_ticket (id, full_name, phone_number, email, code, service_id, total_price, pre_money, type_pay, checkin_time, status, user_id, resources_id, sync, created_at)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String SQL_NEW_ORDER_VALUES = "INSERT INTO future_ticket_value (future_ticket_id, price_id, value, created_at) VALUES" +
                " (?,?,?,?)";

        boolean insertSuccess = false;

        try {
            connection.setAutoCommit(false);

            stmtFT = connection.prepareStatement(SQL_NEW_ORDER);

            stmtFT.setInt(1, futureTicket.getId());
            stmtFT.setString(2, futureTicket.getFullName());
            stmtFT.setString(3, futureTicket.getPhoneNumber());
            stmtFT.setString(4, futureTicket.getEmail());
            stmtFT.setString(5, futureTicket.getCode());
            stmtFT.setInt(6, futureTicket.getServiceId());
            stmtFT.setInt(7, futureTicket.getTotalPrice());
            stmtFT.setInt(8, futureTicket.getPreMoney());
            stmtFT.setInt(9, futureTicket.getTypePay());
            stmtFT.setString(10, futureTicket.getCheckinTime());
            stmtFT.setInt(11, futureTicket.getStatus());
            stmtFT.setInt(12, futureTicket.getUserId());
            stmtFT.setInt(13, futureTicket.getResourcesId());
            stmtFT.setInt(14, futureTicket.getSync());
            stmtFT.setString(15, futureTicket.getCreatedAt());

            int successFT = stmtFT.executeUpdate();

            if (successFT > 0) {
                boolean checkInsertROV = true;

                for (FutureTicketValue futureTicketValue : futureTicket.getFutureTicketValues()) {
                    stmtFTV = connection.prepareStatement(SQL_NEW_ORDER_VALUES);

                    stmtFTV.setInt(1, futureTicketValue.getFutureTicketId());
                    stmtFTV.setInt(2, futureTicketValue.getPriceId());
                    stmtFTV.setInt(3, futureTicketValue.getValue());
                    stmtFTV.setString(4, futureTicketValue.getCreatedAt());

                    int successROV = stmtFTV.executeUpdate();

                    if (!(successROV > 0)) {
                        checkInsertROV = false;
                        break;
                    }
                }

                if (checkInsertROV) {
                    insertSuccess = true;
                }
            }

            if (insertSuccess) connection.commit();
            else connection.rollback();

            connection.close();

        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return insertSuccess;
    }

    public ArrayList<Integer> findUpdatedToSync() throws SQLException {
        Connection connection = createConnection();
        String SQL_UPDATED_TO_SYNC = "SELECT * FROM future_ticket WHERE sync = ?";
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATED_TO_SYNC);

        statement.setInt(1, SyncStatus.UPDATE);

        ResultSet resultSet = statement.executeQuery();

        ArrayList<Integer> updatedToSync = new ArrayList<Integer>();
        while (resultSet.next()) {
            updatedToSync.add(resultSet.getInt(FutureTicketCol.futureTicketId));
        }

        connection.close();

        return updatedToSync;
    }

    public boolean updateSyncStatus(ArrayList<Integer> idsToUpdate, int status) throws SQLException {
        Connection connection = createConnection();
        connection.setAutoCommit(false);

        if (idsToUpdate.size() == 0) return true;

        String UPDATE_SYNC_STATUS = "UPDATE future_ticket SET sync = ? WHERE id = ?";


        PreparedStatement statement = connection.prepareStatement(UPDATE_SYNC_STATUS);

        for (int ticketId : idsToUpdate) {
            statement.setInt(1, status);
            statement.setInt(2, ticketId);

            statement.addBatch();
        }

        int[] inserted = statement.executeBatch();

        connection.commit();
        connection.close();

        return inserted.length > 0;
    }

    public void updateStatus(int status, int id) throws SQLException {
        Connection connection = createConnection();
        String SQL = "UPDATE future_ticket SET status = ?, sync = 2 WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(SQL);

        statement.setInt(1, status);
        statement.setInt(2, id);

        statement.executeUpdate();

        connection.close();
    }

    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "INSERT INTO future_ticket (id, full_name, phone_number, email, code, service_id, total_price, pre_money, type_pay, checkin_time, status, user_id, resources_id, sync, created_at, discount)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject futureTicket = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);
            statement.setObject(1, futureTicket.isNull(FutureTicketCol.id)? null: futureTicket.get(FutureTicketCol.id));
            statement.setString(2, futureTicket.isNull(FutureTicketCol.fullName)? null: futureTicket.getString(FutureTicketCol.fullName));
            statement.setString(3, futureTicket.isNull(FutureTicketCol.phoneNumber)? null: futureTicket.getString(FutureTicketCol.phoneNumber));
            statement.setString(4, futureTicket.isNull(FutureTicketCol.email)? null: futureTicket.getString(FutureTicketCol.email));
            statement.setString(5,  futureTicket.isNull(FutureTicketCol.code)? null: futureTicket.getString(FutureTicketCol.code));
            statement.setObject(6, futureTicket.isNull(FutureTicketCol.serviceId)? null: futureTicket.get(FutureTicketCol.serviceId));
            statement.setObject(7, futureTicket.isNull(FutureTicketCol.totalPrice)? null: futureTicket.get(FutureTicketCol.totalPrice));
            statement.setObject(8, futureTicket.isNull(FutureTicketCol.preMoney)? null: futureTicket.get(FutureTicketCol.preMoney));
            statement.setObject(9, futureTicket.isNull(FutureTicketCol.typePay)? null: futureTicket.get(FutureTicketCol.typePay));
            statement.setString(10, futureTicket.isNull(FutureTicketCol.checkinTime)? null: futureTicket.getString(FutureTicketCol.checkinTime));
            statement.setObject(11, futureTicket.isNull(FutureTicketCol.status)? null: futureTicket.get(FutureTicketCol.status));
            statement.setObject(12, futureTicket.isNull(FutureTicketCol.userId)? null: futureTicket.get(FutureTicketCol.userId));
            statement.setObject(13, futureTicket.isNull(FutureTicketCol.resourcesId)? null: futureTicket.get(FutureTicketCol.resourcesId));
            statement.setObject(14, 1);
            statement.setString(15, futureTicket.isNull(FutureTicketCol.createdAt)? null: futureTicket.getString(FutureTicketCol.createdAt));
            statement.setObject(16, futureTicket.isNull(FutureTicketCol.discount)? null: futureTicket.get(FutureTicketCol.discount));

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
                "SET full_name=?, phone_number=?, email=?, code=?, service_id=?, total_price=?, pre_money=?, type_pay=?, checkin_time=?, status=?, user_id=?, resources_id=?, sync=?, created_at=?, discount=?, kid=?, updated_at=? " +
                "WHERE id=?;";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject futureTicket = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

            statement.setString(1, futureTicket.isNull(FutureTicketCol.fullName)? null: futureTicket.getString(FutureTicketCol.fullName));
            statement.setString(2, futureTicket.isNull(FutureTicketCol.phoneNumber)? null: futureTicket.getString(FutureTicketCol.phoneNumber));
            statement.setString(3, futureTicket.isNull(FutureTicketCol.email)? null: futureTicket.getString(FutureTicketCol.email));
            statement.setString(4, futureTicket.isNull(FutureTicketCol.code)? null: futureTicket.getString(FutureTicketCol.code));
            statement.setObject(5, futureTicket.isNull(FutureTicketCol.serviceId)? null: futureTicket.get(FutureTicketCol.serviceId));
            statement.setObject(6, futureTicket.isNull(FutureTicketCol.totalPrice)? null: futureTicket.get(FutureTicketCol.totalPrice));
            statement.setObject(7, futureTicket.isNull(FutureTicketCol.preMoney)? null: futureTicket.get(FutureTicketCol.preMoney));
            statement.setObject(8, futureTicket.isNull(FutureTicketCol.typePay)? null: futureTicket.get(FutureTicketCol.typePay));
            statement.setString(9, futureTicket.isNull(FutureTicketCol.checkinTime)? null: futureTicket.getString(FutureTicketCol.checkinTime));
            statement.setObject(10, futureTicket.isNull(FutureTicketCol.status)? null: futureTicket.get(FutureTicketCol.status));
            statement.setObject(11, futureTicket.isNull(FutureTicketCol.userId)? null: futureTicket.get(FutureTicketCol.userId));
            statement.setObject(12, futureTicket.isNull(FutureTicketCol.resourcesId)? null: futureTicket.get(FutureTicketCol.resourcesId));
            statement.setObject(13, 1);
            statement.setString(14, futureTicket.isNull(FutureTicketCol.createdAt)? null: futureTicket.getString(FutureTicketCol.createdAt));
            statement.setObject(15, futureTicket.isNull(FutureTicketCol.discount)? null: futureTicket.get(FutureTicketCol.discount));
            statement.setObject(16, futureTicket.isNull(FutureTicketCol.kid)? null: futureTicket.get(FutureTicketCol.kid));
            statement.setObject(17, futureTicket.isNull(FutureTicketCol.updatedAt)? null: futureTicket.get(FutureTicketCol.updatedAt));
            statement.setObject(18, futureTicket.isNull(FutureTicketCol.id)? null: futureTicket.get(FutureTicketCol.id));
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
    public JSONArray findToSync(int syncStatus) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "SELECT id, status, updated_at FROM future_ticket WHERE sync=?";

        PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);
        statement.setObject(1, syncStatus);
        ResultSet resultSet = statement.executeQuery();

        JSONArray futureTickets = new JSONArray();
        while (resultSet.next()) {
            JSONObject futureTicket = new JSONObject();
            futureTicket.put(FutureTicketCol.id, resultSet.getInt(FutureTicketCol.id));
            futureTicket.put(FutureTicketCol.status, resultSet.getInt(FutureTicketCol.status));
            futureTicket.put(FutureTicketCol.updatedAt, resultSet.getString(FutureTicketCol.updatedAt));

            futureTickets.put(futureTicket);
        }
        connection.close();

        return futureTickets;
    }

    @Override
    public boolean updateSyncStatus(JSONArray idsSuccess, int sync) throws SQLException {

        String SQL = "UPDATE future_ticket SET sync=? WHERE id=? ";
        if (idsSuccess.length() == 0) return true;

        int[] inserted = new int[0];
        Connection connection = createConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(SQL);

            for (Object futureTicketId : idsSuccess) {
                statement.setInt(1, sync);
                statement.setString(2, futureTicketId.toString());

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
