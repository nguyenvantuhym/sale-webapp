package com.emddi.synchronize.syncdown;

import com.emddi.dao.IDAO;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

public interface ISyncDownUpdate {

    void syncDownUpdate(String target, IDAO dao) throws IOException, SQLException, JSONException;
}
