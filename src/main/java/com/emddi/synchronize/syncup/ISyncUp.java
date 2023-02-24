package com.emddi.synchronize.syncup;

import com.emddi.dao.IDAOSyncUp;
import okhttp3.Response;
import org.json.JSONArray;

import java.io.IOException;

interface ISyncUp {


     void syncUp(IDAOSyncUp idaoSyncUp, String targetTable, int syncStatus) throws IOException;

     Response syncUpCallAPI(JSONArray dataToSync, String targetTable, int syncStatus) throws IOException;
}
