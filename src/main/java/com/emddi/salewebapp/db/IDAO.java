package com.emddi.salewebapp.db;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;

public interface IDAO {
    JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException;
    JSONObject syncDownUpdateDB(JSONArray jsonArray) throws SQLException;

}
