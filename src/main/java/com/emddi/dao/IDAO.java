package com.emddi.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public interface IDAO {
    JSONObject syncDownInsertDB(JSONArray jsonArray) throws SQLException, JSONException;
    JSONObject syncDownUpdateDB(JSONArray jsonArray) throws SQLException, JSONException;

}
