package com.emddi.salewebapp.db;

import com.emddi.salewebapp.constants.SyncStatus;
import com.emddi.salewebapp.db.tablecol.CardCol;
import com.emddi.salewebapp.models.Card;
import com.emddi.salewebapp.util.DateSQL;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CardDao extends Database {
    public static final Logger LOGGER = Logger.getLogger(CardDao.class.getName());

    public Card getByCardNo(String cardNo) throws SQLException {
        Card card = null;

        Connection connection = createConnection();
        String query = "SELECT * FROM card WHERE card_no = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, cardNo);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            card = Card.builder()
                    .cardId(resultSet.getInt(CardCol.cardId))
                    .cardNo(resultSet.getString(CardCol.cardNo))
                    .fullName(resultSet.getString(CardCol.fullName))
                    .status(resultSet.getInt(CardCol.status))
                    .zone(resultSet.getString(CardCol.zone))
                    .build();
        }


        connection.close();
        return card;
    }

    public JSONObject insertFromCloud(ArrayList<Card> cards) {
        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        System.out.println("Card::insertFromCloud - start " + DateSQL.currentDateTime());
        Connection connection = createConnection();

        for (Card card : cards) {
            String query = "INSERT INTO card (card_id, card_no, office_id, full_name, phone_number, status, zone, created_at, sync) VALUE (?,?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, card.getCardId());
                statement.setString(2, card.getCardNo());
                statement.setInt(3, card.getOfficeId());
                statement.setString(4, card.getFullName());
                statement.setString(5, card.getPhoneNumber());
                statement.setInt(6, card.getStatus());
                statement.setString(7, card.getZone());
                statement.setString(8, card.getCreatedAt());
                statement.setInt(9, SyncStatus.SYNC);

                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(card.getCardId());
                } else {
                    idsFailed.add(card.getCardId());
                }

            } catch (SQLException e) {
                idsFailed.add(card.getCardId());
            }

        }

        try {
            connection.close();
        } catch (SQLException ignored) {
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        System.out.println("Card::insertFromCloud - SUCCESS " + idsSuccess.size() + " - FAILED " + idsFailed.size() + " " + DateSQL.currentDateTime());
        return jsonObject;
    }

    public JSONObject updateFromCloud(ArrayList<Card> cards) {
        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        System.out.println("Card::updateFromCloud - START " + DateSQL.currentDateTime());
        Connection connection = createConnection();

        for (Card card : cards) {
            String query = "UPDATE card SET card_no = ?, office_id = ?, full_name = ?, phone_number = ?, status = ?, zone = ?, created_at = ?, sync = ? WHERE card_id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, card.getCardNo());
                statement.setInt(2, card.getOfficeId());
                statement.setString(3, card.getFullName());
                statement.setString(4, card.getPhoneNumber());
                statement.setInt(5, card.getStatus());
                statement.setString(6, card.getZone());
                statement.setString(7, card.getCreatedAt());
                statement.setInt(8, SyncStatus.SYNC);
                statement.setInt(9, card.getCardId());

                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(card.getCardId());
                } else {
                    idsFailed.add(card.getCardId());
                }

            } catch (SQLException e) {
                idsFailed.add(card.getCardId());
            }

        }

        try {
            connection.close();
        } catch (SQLException ignored) {
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        System.out.println("Card::updateFromCloud - SUCCESS " + idsSuccess.size() + " - FAILED " + idsFailed.size() + " " + DateSQL.currentDateTime());
        return jsonObject;
    }
}
