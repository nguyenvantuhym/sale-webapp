package com.emddi.dao;

import org.json.JSONArray;

public interface IDAOSyncUp {
    JSONArray findToSync(int syncStatus);

    boolean updateSyncStatus(JSONArray idsSuccess, int sync);
}
