package com.emddi.dao;

import com.emddi.database.Database;
import com.emddi.database.tableCol.PriceCol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PriceDao extends Database implements IDAO {
    private static final String INSERT_OR_UPDATE_SQL = "REPLACE INTO price (price_id, price_name, price, usd_price, status, sync) VALUES (?,?,?,?,?,?)";

//    public ArrayList<Price> getAllActive() {
//        ArrayList<Price> priceArrayList = new ArrayList<>();
//        String sql = "SELECT price_id, price_name, price, usd_price, p.service_id" +
//                        " FROM price p JOIN service s on p.service_id = s.service_id" +
//                        " WHERE p.status = ? AND apply_for IN (0, 2) ORDER BY s.iorder, p.iorder";
//
//        try {
//            Connection connection = createConnection();
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, PriceStatus.ACTIVE);
//
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Price price = Price.builder()
//                        .priceId(resultSet.getInt(PriceCol.priceId))
//                        .priceName(resultSet.getString(PriceCol.priceName))
//                        .price(resultSet.getInt(PriceCol.price))
//                        .usdPrice(resultSet.getInt(PriceCol.usdPrice))
//                        .serviceId(resultSet.getInt(PriceCol.serviceId))
//                        .build();
//                priceArrayList.add(price);
//            }
//
//            connection.close();
//
//        } catch (Exception ignored) {
//        }
//        return priceArrayList;
//    }
//
//    public ArrayList<Price> getAllActiveByEmployee(int empId) {
//        ArrayList<Price> priceArrayList = new ArrayList<>();
//        String sql = "SELECT price_id, price_name, price, usd_price, p.service_id FROM price p" +
//                " JOIN service s ON p.service_id = s.service_id" +
//                " JOIN employee_service es on s.service_id = es.service_id" +
//                " WHERE p.status = ? AND es.employee_id = ? AND apply_for IN (0, 2) ORDER BY s.iorder, p.iorder";
//
//        try {
//            Connection connection = createConnection();
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, PriceStatus.ACTIVE);
//            statement.setInt(2, empId);
//
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Price price = Price.builder()
//                        .priceId(resultSet.getInt(PriceCol.priceId))
//                        .priceName(resultSet.getString(PriceCol.priceName))
//                        .price(resultSet.getInt(PriceCol.price))
//                        .usdPrice(resultSet.getInt(PriceCol.usdPrice))
//                        .serviceId(resultSet.getInt(PriceCol.serviceId))
//                        .build();
//                priceArrayList.add(price);
//            }
//
//            connection.close();
//
//        } catch (Exception ignored) {
//        }
//        return priceArrayList;
//    }
//
//    public boolean insertOrUpdate(Price price) throws SQLException {
//        Connection connection = createConnection();
//        PreparedStatement statement = connection.prepareStatement(INSERT_OR_UPDATE_SQL);
//
//        statement.setInt(1, price.getPriceId());
//        statement.setString(2, price.getPriceName());
//        statement.setInt(3, price.getPrice());
//        statement.setInt(4, price.getUsdPrice());
//        statement.setInt(5, price.getStatus());
//        statement.setInt(6, SyncStatus.SYNC);
//
//        int success = statement.executeUpdate();
//
//        connection.close();
//
//        return success > 0;
//    }
    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW = "INSERT INTO price (service_id ,price_name ,status ,price ,usd_price ,type ,apply_for " +
                ",invoice_info ,description ,image ,created_at ,updated_at, sync, iorder, price_id ) " +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject price = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW);
            statement.setObject(1, price.isNull(PriceCol.serviceId)? null: price.get(PriceCol.serviceId));
            statement.setString(2, price.isNull(PriceCol.priceName)? null: price.getString(PriceCol.priceName));
            statement.setObject(3, price.isNull(PriceCol.status)? null: price.get(PriceCol.status));
            statement.setObject(4, price.isNull(PriceCol.price)? null: price.get(PriceCol.price));
            statement.setObject(5, price.isNull(PriceCol.usdPrice)? null: price.getFloat(PriceCol.usdPrice));
            statement.setObject(6, price.isNull(PriceCol.type)? null: price.get(PriceCol.type));
            statement.setObject(7, price.isNull(PriceCol.applyFor)? null: price.get(PriceCol.applyFor));
            statement.setString(8, price.isNull(PriceCol.invoiceInfo)? null: price.getString(PriceCol.invoiceInfo));
            statement.setString(9, price.isNull(PriceCol.description)? null: price.getString(PriceCol.description));
            statement.setString(10, price.isNull(PriceCol.image)? null: price.getString(PriceCol.image));
            statement.setString(11, price.isNull(PriceCol.createdAt)? null: price.getString(PriceCol.createdAt));
            statement.setString(12, price.isNull(PriceCol.updatedAt)? null: price.getString(PriceCol.updatedAt));
            statement.setObject(13, 1);

            statement.setObject(14, price.isNull(PriceCol.iorder)? 0: price.get(PriceCol.iorder));
            statement.setObject(15, price.isNull(PriceCol.priceId)? null: price.get(PriceCol.priceId));


            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(price.getInt(PriceCol.priceId));
                }
            } catch (SQLException e){
                idsFailed.add(price.getInt(PriceCol.priceId));
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

        String SQL_NEW = "UPDATE price SET service_id =? ,price_name =? ,status =? ,price =? ,usd_price =? ,type =? ," +
                "apply_for =? ,invoice_info =? ,description =? ,image =? ,created_at =? ,updated_at=? , sync=? , iorder=? " +
                "WHERE price_id=? ";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject price = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW);
            statement.setObject(1, price.isNull(PriceCol.serviceId)? null: price.get(PriceCol.serviceId));
            statement.setString(2, price.isNull(PriceCol.priceName)? null: price.getString(PriceCol.priceName));
            statement.setObject(3, price.isNull(PriceCol.status)? null: price.get(PriceCol.status));
            statement.setObject(4, price.isNull(PriceCol.price)? null: price.get(PriceCol.price));
            statement.setObject(5, price.isNull(PriceCol.usdPrice)? null: price.getFloat(PriceCol.usdPrice));
            statement.setObject(6, price.isNull(PriceCol.type)? null: price.get(PriceCol.type));
            statement.setObject(7, price.isNull(PriceCol.applyFor)? null: price.get(PriceCol.applyFor));
            statement.setString(8, price.isNull(PriceCol.invoiceInfo)? null: price.getString(PriceCol.invoiceInfo));
            statement.setString(9, price.isNull(PriceCol.description)? null: price.getString(PriceCol.description));
            statement.setString(10, price.isNull(PriceCol.image)? null: price.getString(PriceCol.image));
            statement.setString(11, price.isNull(PriceCol.createdAt)? null: price.getString(PriceCol.createdAt));
            statement.setString(12, price.isNull(PriceCol.updatedAt)? null: price.getString(PriceCol.updatedAt));
            statement.setObject(13, 1);
            statement.setObject(14, price.isNull(PriceCol.iorder)? 0: price.get(PriceCol.iorder));
            statement.setObject(15, price.isNull(PriceCol.priceId)? null: price.get(PriceCol.priceId));

            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(price.getInt(PriceCol.priceId));
                }
            } catch (SQLException e){
                idsFailed.add(price.getInt(PriceCol.priceId));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }

}
