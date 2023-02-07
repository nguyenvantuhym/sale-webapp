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

public class ControlDeviceDao extends Database implements IDAO{
    public static final Logger LOGGER = Logger.getLogger(ControlDeviceDao.class.getName());
//    public ControlDevice getDevice(String serialNumber) {
//        Connection connection = createConnection();
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM control_device WHERE serial_number = ?");
//            statement.setString(1, serialNumber);
//            ResultSet resultSet = statement.executeQuery();
//
//
//            if (resultSet.next()) {
//                ControlDevice controlDevice = ControlDevice.builder().build();
//
//                controlDevice.setId(resultSet.getInt(ControlDeviceCol.id));
//                controlDevice.setSerialNumber(resultSet.getString(ControlDeviceCol.serialNumber));
//                controlDevice.setConfig(resultSet.getString(ControlDeviceCol.config));
//                controlDevice.setRegistryCode(resultSet.getString(ControlDeviceCol.registryCode));
//                controlDevice.setName(resultSet.getString(ControlDeviceCol.name));
//                controlDevice.setZoneId(resultSet.getInt(ControlDeviceCol.zoneId));
//                controlDevice.setWay(resultSet.getInt(ControlDeviceCol.way));
//
//                connection.close();
//                return controlDevice;
//            } else {
//                connection.close();
//                return null;
//            }
//        } catch (SQLException e) {
//            LOGGER.info((e + ": " + e.getMessage()));
//        }
//
//        return null;
//    }
//
//    public boolean save(ControlDevice device) {
//        boolean result = false;
//        Connection connection = createConnection();
//        try {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO control_device (serial_number, config, registry_code) VALUES (?,?,?)");
//
//            statement.setString(1, device.getSerialNumber());
//            statement.setString(2, device.getConfig());
//            statement.setString(3, device.getRegistryCode());
//
//            int success = statement.executeUpdate();
//            connection.close();
//
//            result = success > 0;
//
//        } catch (SQLException e) {
//            LOGGER.info((e + ": " + e.getMessage()));
//        }
//
//        return result;
//    }

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
