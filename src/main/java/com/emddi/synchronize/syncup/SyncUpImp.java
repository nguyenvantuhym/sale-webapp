package com.emddi.synchronize.syncup;


import com.emddi.constants.ResponseStatus;
import com.emddi.constants.StaticConfig;
import com.emddi.constants.SyncStatus;
import com.emddi.dao.IDAOSyncUp;
import com.emddi.synchronize.ApiConst;
import com.emddi.synchronize.CloudEndpoint;
import com.emddi.synchronize.jsonKey.ResponseKey;
import com.emddi.utils.DateSQL;
import com.emddi.utils.Md5;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;

import static com.emddi.synchronize.ApiCaller.client;
import static com.emddi.synchronize.sync.task.SyncTask.LOGGER;

public class SyncUpImp implements ISyncUp{
    @Override
    public void syncUp(IDAOSyncUp idaoSyncUp, String targetTable, int syncStatus) throws IOException {

        JSONArray dataToSync = idaoSyncUp.findToSync(syncStatus);
        Response response = syncUpCallAPI(dataToSync, targetTable, syncStatus);

        assert response.body() != null;

        if (!response.isSuccessful()) {
            System.out.println("Sync " + targetTable +" up: Error req " + response.code());
            return;
        }

        JSONObject body = new JSONObject(response.body().string());

        if (body.getString(ResponseKey.code).equals(ResponseStatus.SUCCESSFUL)) {
            JSONObject data = body.getJSONObject(ResponseKey.data);
            JSONArray idsSuccess = data.getJSONArray(ResponseKey.success);
            JSONArray idsError = data.getJSONArray(ResponseKey.error);

            boolean syncDone = idaoSyncUp.updateSyncStatus(idsSuccess, SyncStatus.SYNC);
            if (syncDone) {
                System.out.println("Sync " + targetTable + "  up: Done " + DateSQL.currentDateTime());
                LOGGER.log(Level.INFO, "Done : total {0} - sync {1}, failed {2}",
                        new Object[]{dataToSync.length(), idsSuccess.length(), idsError.length()});
                System.out.println(idsSuccess);
                System.out.println(idsError);
            }
        }
    }

    @Override
    public Response syncUpCallAPI(JSONArray dataToSync, String targetTable, int syncStatus) throws IOException {
        String method = null;
        HttpUrl urlBuilder = null;
        if(syncStatus == SyncStatus.NEW ){
             urlBuilder = new CloudEndpoint().insertBuilder().build();
             method = "insert";
        } else {
            urlBuilder = new CloudEndpoint().updateBuilder().build();
            method = "update";
        }
        // build body of request
        JSONObject formBodyData = new JSONObject();
        formBodyData.put(ApiConst.PARAM_TARGET, targetTable);
        formBodyData.put(method, dataToSync);
        String checkSum = StaticConfig.checksumHashKey + "." + targetTable + "." + dataToSync;
        formBodyData.put(ApiConst.PARAM_CHECKSUM, Md5.hash(checkSum));
        RequestBody formBody = RequestBody.create(ApiConst.JSON, formBodyData.toString());
        // call API
        Request request = new Request.Builder().url(urlBuilder).post(formBody).build();
        System.out.println("Sync ticket " + "params" + " up: Start req " + DateSQL.currentDateTime());
        Response response = client.newCall(request).execute();
        System.out.println("Sync ticket " + "params" + " up: End req " + DateSQL.currentDateTime());

        return response;
    }


}
