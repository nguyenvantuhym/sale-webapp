package com.emddi.synchronize.syncdown;

import com.emddi.dao.IDAO;
import com.emddi.synchronize.CloudEndpoint;
import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class SyncDownUpdateImp extends SyncImp implements ISyncDownUpdate{
    @Override
    public void syncDownUpdate(String target, IDAO dao) throws IOException, SQLException, JSONException {
        System.out.println(target + ": ....syncDownUpdate...");
        HttpUrl urlBuilder = new CloudEndpoint().syncUpdateDownBuilder().build();
        JSONArray jsonArray = this.syncDownCallAPI(urlBuilder, target, "update");
        System.out.println(target + ": ....syncDownCallAPI...." + jsonArray.toString());
        if (jsonArray != null && jsonArray.length() > 0) {
            JSONObject jsonObject = dao.syncDownUpdateDB(jsonArray);
                urlBuilder = new CloudEndpoint().syncRepUpdateDownBuilder().build();
            this.synRep(urlBuilder, jsonObject, target);
        }
    }
}
