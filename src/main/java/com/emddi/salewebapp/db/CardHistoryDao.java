package com.emddi.salewebapp.db;

import com.emddi.salewebapp.db.tablecol.CardHistoryCol;
import com.emddi.salewebapp.models.CardHistory;
import com.emddi.salewebapp.util.DateSQL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardHistoryDao extends Database {
    public void insert(CardHistory cardHistory) throws SQLException {
        Connection connection = createConnection();
        String query = "INSERT INTO card_history (card_id, status, device_id, full_name, scan_time) VALUE (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, cardHistory.getCardId());
        statement.setInt(2, cardHistory.getStatus());
        statement.setInt(3, cardHistory.getDeviceId());
        statement.setString(4, cardHistory.getFullName());
        statement.setString(5, cardHistory.getScanTime());

        statement.executeUpdate();

        connection.close();

    }

    public JSONArray getNewToSync() {
        JSONArray data = new JSONArray();
        System.out.println("CardHistoryDao::getNewToSync - START " + DateSQL.currentDateTime());
        Connection connection = createConnection();
        String query = "SELECT * FROM card_history WHERE sync = 0 LIMIT 100";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                JSONObject item = new JSONObject();

                item.put(CardHistoryCol.id, rs.getInt(CardHistoryCol.id));
                item.put(CardHistoryCol.cardId, rs.getInt(CardHistoryCol.cardId));
                item.put(CardHistoryCol.status, rs.getInt(CardHistoryCol.status));
                item.put(CardHistoryCol.deviceId, rs.getInt(CardHistoryCol.deviceId));
                item.put(CardHistoryCol.fullName, rs.getString(CardHistoryCol.fullName));
                item.put(CardHistoryCol.scanTime, rs.getString(CardHistoryCol.scanTime));

                data.put(item);
            }

        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        System.out.println("CardHistoryDao::getNewToSync - END - data " + data.length() + " " + DateSQL.currentDateTime());
        return data;
    }

    public void updateSyncStatus(JSONArray cardIds, int status) {
        if (cardIds.length() == 0) return;

        System.out.println("CardHistoryDao::updateSyncStatus - START " + DateSQL.currentDateTime());
        Connection connection = createConnection();
        try {
            connection.setAutoCommit(false);

            String query = "UPDATE card_history SET sync =? WHERE id =?";

            PreparedStatement statement = connection.prepareStatement(query);

            for (Object uuid : cardIds) {
                statement.setInt(1, status);
                statement.setString(2, uuid.toString());

                statement.addBatch();
            }

            int[] inserted = statement.executeBatch();

            if (inserted.length > 0) {
                connection.commit();
                System.out.println("CardHistoryDao::updateSyncStatus - DONE " + inserted.length + " " + DateSQL.currentDateTime());
            } else {
                System.out.println("CardHistoryDao::updateSyncStatus - DONE: rollback " + DateSQL.currentDateTime());
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
