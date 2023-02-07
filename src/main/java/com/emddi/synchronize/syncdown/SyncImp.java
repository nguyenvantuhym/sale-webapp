package com.emddi.synchronize.syncdown;

import com.emddi.constants.ResponseStatus;
import com.emddi.constants.StaticConfig;
import com.emddi.synchronize.ApiCaller;
import com.emddi.synchronize.ApiConst;
import com.emddi.synchronize.jsonKey.ResponseKey;
import com.emddi.utils.Md5;
import com.emddi.utils.RandomString;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.function.Supplier;
import java.util.logging.Logger;


public abstract class SyncImp implements ISync {
    public static final Logger LOGGER = Logger.getLogger(SyncImp.class.getSimpleName());
    @Override
    public void synRep(HttpUrl http, JSONObject jsonObject, String target) throws IOException, JSONException {
        LOGGER.info(target + "....synRep... error: " +
                jsonObject.getJSONArray("error").toString()+
                " success: " + jsonObject.getJSONArray("success").toString());
        jsonObject.put(ApiConst.PARAM_TARGET, target);
        String checkSum = StaticConfig.checksumHashKey + "." + target +
                "." + jsonObject.getJSONArray("success").toString() + "." + jsonObject.getJSONArray("error").toString();
        jsonObject.put(ApiConst.PARAM_CHECKSUM, Md5.hash(checkSum));

        RequestBody requestBody =  RequestBody.create(ApiConst.JSON, jsonObject.toString());
        Request request = new Request.Builder().url(http).post(requestBody).build();
        Response response = ApiCaller.client.newCall(request).execute();
        JSONObject body = new JSONObject(response.body().string());
    }

    @Override
    public JSONArray syncDownCallAPI(HttpUrl url, String tableTarget, String flag) throws JSONException, IOException { // flag = [insert, update]
        try {
            String timestamp = "1" + new RandomString(9, new SecureRandom(), RandomString.digits).nextString();
            String checkSum = StaticConfig.checksumHashKey + "." + tableTarget+ "." + timestamp;
            JSONObject formBodyData = new JSONObject();
            formBodyData.put(ApiConst.PARAM_TIME, timestamp);
            formBodyData.put(ApiConst.PARAM_TARGET, tableTarget);
            formBodyData.put(ApiConst.PARAM_CHECKSUM, Md5.hash(checkSum).toUpperCase());

            RequestBody requestBody =  RequestBody.create(ApiConst.JSON, formBodyData.toString());

            Request request = new Request.Builder().url(url).post(requestBody).build();
            JSONObject body = new JSONObject();

            try {
                Response response = ApiCaller.client.newCall(request).execute();
                body = new JSONObject(response.body().string());
            } catch (Exception e) {
                LOGGER.info((Supplier<String>) e);
            }

            if (body.getString(ResponseKey.code).equals(ResponseStatus.SUCCESSFUL)) {
                JSONArray jsonArray = body.getJSONObject("data").getJSONArray(flag);
                return jsonArray;
            }

        }
        catch (Exception e) {
            LOGGER.info((Supplier<String>) e);
        }
        return null;
    }


//
//    @Override
//    public void syncDownInsert(String target, IDAO dao) throws SQLException, IOException, JSONException {
//        System.out.println(target + ": ....syncDownInsert.....");
//        HttpUrl urlBuilder = new CloudEndpoint().syncInsertDownBuilder().build();
//        JSONArray jsonArray = this.syncDownCallAPI(urlBuilder, target, "insert");
//        System.out.println(target + ": ....syncDownCallAPI...." + jsonArray.toString());
//        if (jsonArray != null && jsonArray.length() > 0) {
//            JSONObject jsonObject = dao.syncDownInsertDB(jsonArray);
//            urlBuilder = new CloudEndpoint().syncRepInsertDownBuilder().build();
//            synRep(urlBuilder, jsonObject,target);
//        }
//    }
//
//    @Override
//    public void syncDownUpdate(String target, IDAO dao) throws IOException, SQLException, JSONException {
//        System.out.println(target + ": ....syncDownUpdate...");
//        HttpUrl urlBuilder = new CloudEndpoint().syncUpdateDownBuilder().build();
//        JSONArray jsonArray = this.syncDownCallAPI(urlBuilder, target, "update");
//        System.out.println(target + ": ....syncDownCallAPI...." + jsonArray.toString());
//        if (jsonArray != null && jsonArray.length() > 0) {
//            JSONObject jsonObject = dao.syncDownUpdateDB(jsonArray);
//                urlBuilder = new CloudEndpoint().syncRepUpdateDownBuilder().build();
//            synRep(urlBuilder, jsonObject, target);
//        }
//    }
}
