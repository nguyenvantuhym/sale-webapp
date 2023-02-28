package com.emddi.salewebapp.db;

import com.emddi.salewebapp.db.tablecol.ZoneCol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ZoneDao extends Database implements IDAO {
    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "INSERT INTO zone (office_id, name, status, created_at, sync, zone_id )" +
                " VALUES (?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject zone = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);

            statement.setObject(1, zone.isNull(ZoneCol.officeId)? null: zone.get(ZoneCol.officeId));
            statement.setString(2, zone.isNull(ZoneCol.name)? null: zone.getString(ZoneCol.name));
            statement.setObject(3, zone.isNull(ZoneCol.status)? null: zone.get(ZoneCol.status));
            statement.setString(4, zone.isNull(ZoneCol.createdAt)? null: zone.getString(ZoneCol.createdAt));
            statement.setObject(5, 1);
            statement.setObject(6, zone.isNull(ZoneCol.zoneId)? null: zone.get(ZoneCol.zoneId));


            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(zone.getInt(ZoneCol.zoneId));
                }
            } catch (SQLException e){
                idsFailed.add(zone.getInt(ZoneCol.zoneId));
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

        String SQL_NEW_ORDER = "UPDATE zone SET office_id=?, name=?, status=?, created_at=?, sync=? " +
                " WHERE zone_id=? ";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject zone = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);

            statement.setObject(1, zone.isNull(ZoneCol.officeId)? null: zone.get(ZoneCol.officeId));
            statement.setString(2, zone.isNull(ZoneCol.name)? null: zone.getString(ZoneCol.name));
            statement.setObject(3, zone.isNull(ZoneCol.status)? null: zone.get(ZoneCol.status));
            statement.setString(4, zone.isNull(ZoneCol.createdAt)? null: zone.getString(ZoneCol.createdAt));
            statement.setObject(5, 1);
            statement.setObject(6, zone.isNull(ZoneCol.zoneId)? null: zone.get(ZoneCol.zoneId));


            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(zone.getInt(ZoneCol.zoneId));
                }
            } catch (SQLException e){
                idsFailed.add(zone.getInt(ZoneCol.zoneId));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }
}
