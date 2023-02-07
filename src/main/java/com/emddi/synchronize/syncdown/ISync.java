package com.emddi.synchronize.syncdown;

import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public interface ISync {
     void synRep(HttpUrl http, JSONObject jsonObject, String target) throws IOException, JSONException;
     JSONArray syncDownCallAPI(HttpUrl url, String tableTarget, String flag) throws IOException, JSONException;
//
//     void syncDownInsert(String target, IDAO dao) throws SQLException, IOException, JSONException;
//
//     void syncDownUpdate(String target, IDAO dao) throws IOException, SQLException, JSONException ;
}
