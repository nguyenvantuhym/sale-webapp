package com.emddi.dao;

import com.emddi.database.Database;
import com.emddi.database.tableCol.ControlDeviceCol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

@TableName("control_device")
public class ControlDeviceDao extends Database implements IDAO{
    public static final Logger LOGGER = Logger.getLogger(ControlDeviceDao.class.getName());

    @Override
    public JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException {
        Connection connection = createConnection();

        String SQL_NEW_ORDER = "INSERT INTO control_device (serial_number, zone_id, name, config, registry_code, way, " +
//                " created_at, " +
                "id)" +
                " VALUES (?,?,?,?,?,?,?)";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject controlDevice = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);

            statement.setString(1, controlDevice.isNull(ControlDeviceCol.serialNumber)? null: controlDevice.getString(ControlDeviceCol.serialNumber));
            statement.setObject(2, controlDevice.isNull(ControlDeviceCol.zoneId)? null: controlDevice.get(ControlDeviceCol.zoneId));
            statement.setString(3, controlDevice.isNull(ControlDeviceCol.name)? null: controlDevice.getString(ControlDeviceCol.name));
            statement.setString(4, controlDevice.isNull(ControlDeviceCol.config)? null: controlDevice.getString(ControlDeviceCol.config));
            statement.setString(5, controlDevice.isNull(ControlDeviceCol.registryCode)? null: controlDevice.getString(ControlDeviceCol.registryCode));
            statement.setObject(6, controlDevice.isNull(ControlDeviceCol.way)? null: controlDevice.get(ControlDeviceCol.way));
//            statement.setString(7, controlDevice.isNull(ControlDeviceCol.createdAt)? null: controlDevice.getString(ControlDeviceCol.createdAt));
            statement.setObject(7, controlDevice.isNull(ControlDeviceCol.id)? null: controlDevice.get(ControlDeviceCol.id));


            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(controlDevice.getInt(ControlDeviceCol.id));
                }
            } catch (SQLException e){
                idsFailed.add(controlDevice.getInt(ControlDeviceCol.id));
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

        String SQL_NEW_ORDER = "UPDATE control_device SET serial_number=?, zone_id=?, name=?, config=?, registry_code=?, way=? " +
//                ", created_at=?" +
                " WHERE id=?";

        ArrayList<Integer> idsSuccess = new ArrayList<>();
        ArrayList<Integer> idsFailed = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject controlDevice = (JSONObject) jsonArray.get(i);
            PreparedStatement statement = connection.prepareStatement(SQL_NEW_ORDER);

            statement.setString(1, controlDevice.isNull(ControlDeviceCol.serialNumber)? null: controlDevice.getString(ControlDeviceCol.serialNumber));
            statement.setObject(2, controlDevice.isNull(ControlDeviceCol.zoneId)? null: controlDevice.get(ControlDeviceCol.zoneId));
            statement.setString(3, controlDevice.isNull(ControlDeviceCol.name)? null: controlDevice.getString(ControlDeviceCol.name));
            statement.setString(4, controlDevice.isNull(ControlDeviceCol.config)? null: controlDevice.getString(ControlDeviceCol.config));
            statement.setString(5, controlDevice.isNull(ControlDeviceCol.registryCode)? null: controlDevice.getString(ControlDeviceCol.registryCode));
            statement.setObject(6, controlDevice.isNull(ControlDeviceCol.way)? null: controlDevice.get(ControlDeviceCol.way));
//            statement.setString(7, controlDevice.isNull(ControlDeviceCol.createdAt)? null: controlDevice.getString(ControlDeviceCol.createdAt));
            statement.setObject(7, controlDevice.isNull(ControlDeviceCol.id)? null: controlDevice.get(ControlDeviceCol.id));


            try {
                int success = statement.executeUpdate();
                if (success > 0) {
                    idsSuccess.add(controlDevice.getInt(ControlDeviceCol.id));
                }
            } catch (SQLException e){
                idsFailed.add(controlDevice.getInt(ControlDeviceCol.id));
            }
        }

        connection.close();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", idsSuccess);
        jsonObject.put("error", idsFailed);
        return jsonObject;
    }
}
