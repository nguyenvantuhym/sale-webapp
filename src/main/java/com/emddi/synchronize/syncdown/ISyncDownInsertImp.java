package com.emddi.synchronize.syncdown;

import com.emddi.dao.IDAO;
import com.emddi.synchronize.CloudEndpoint;
import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class ISyncDownInsertImp extends SyncImp implements ISyncDownInsert{
    @Override
    public void syncDownInsert(String target, IDAO dao) throws SQLException, IOException, JSONException {
        System.out.println(target + ": ....syncDownInsert.....");
        HttpUrl urlBuilder = new CloudEndpoint().syncInsertDownBuilder().build();
        JSONArray jsonArray = this.syncDownCallAPI(urlBuilder, target, "insert");
        System.out.println(target + ": ....syncDownCallAPI...." + jsonArray.toString());
        if (jsonArray != null && jsonArray.length() > 0) {
            JSONObject jsonObject = dao.syncDownInsertDB(jsonArray);
            urlBuilder = new CloudEndpoint().syncRepInsertDownBuilder().build();
            synRep(urlBuilder, jsonObject,target);
        }
    }
}
