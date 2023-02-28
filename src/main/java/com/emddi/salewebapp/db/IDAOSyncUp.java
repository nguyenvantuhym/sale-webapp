package com.emddi.salewebapp.db;

import org.json.JSONArray;

import java.sql.SQLException;

public interface IDAOSyncUp {

        JSONArray findToSync(int syncStatus) throws SQLException;

        boolean updateSyncStatus(JSONArray idsSuccess, int sync) throws SQLException;
}
