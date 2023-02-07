package com.emddi.synchronize.syncdown;

import com.emddi.dao.IDAO;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

public interface ISyncDownInsert {

    void syncDownInsert(String target, IDAO dao) throws SQLException, IOException, JSONException;
}
