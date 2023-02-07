package com.emddi.synchronize.sync.task;

import com.emddi.dao.IDAO;
import com.emddi.synchronize.CloudEndpoint;
import com.emddi.synchronize.syncdown.ISyncDownInsert;
import com.emddi.synchronize.syncdown.ISyncDownUpdate;
import com.emddi.synchronize.syncdown.SyncImp;
import lombok.Getter;
import lombok.Setter;
import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Getter
@Setter
public abstract class SyncTask extends SyncImp implements ISyncDownInsert, ISyncDownUpdate {

    public static final Logger LOGGER = Logger.getLogger("");


    private boolean isStarted = false;

    private int initDelay;

    private int period;

    private TimeUnit unit;

    private ScheduledFuture<?> task;

    public void start(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        if(!scheduledThreadPoolExecutor.isShutdown()) {
            isStarted = true;
            try {
                this.stop();
                this.task = scheduledThreadPoolExecutor.scheduleAtFixedRate(this.taskRunnable(),this.initDelay, this.period, this.unit);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        }
    }

    public void restart(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        if(scheduledThreadPoolExecutor != null && !scheduledThreadPoolExecutor.isShutdown()) {
            if (this.task != null) {
                this.stop();
            }
            start(scheduledThreadPoolExecutor);
        }
    }

    public void stop() {
        if(this.task != null) this.task.cancel(true);
    }

    public abstract Runnable taskRunnable() throws SQLException, JSONException, IOException;

    @Override
    public void syncDownInsert(String target, IDAO dao) throws SQLException, IOException, JSONException {
        LOGGER.info(target + ": ....syncDownInsert.....");
        HttpUrl urlBuilder = new CloudEndpoint().syncInsertDownBuilder().build();
        JSONArray jsonArray = this.syncDownCallAPI(urlBuilder, target, "insert");
        LOGGER.info(target + ": ....syncDownCallAPI....syncDownInsert...." + jsonArray.toString());
        if (jsonArray != null && jsonArray.length() > 0) {
            JSONObject jsonObject = dao.syncDownInsertDB(jsonArray);
            urlBuilder = new CloudEndpoint().syncRepInsertDownBuilder().build();
            synRep(urlBuilder, jsonObject,target);
        }
    }

    @Override
    public void syncDownUpdate(String target, IDAO dao) throws IOException, SQLException, JSONException {
        LOGGER.info(target + ": ....syncDownUpdate...");
        HttpUrl urlBuilder = new CloudEndpoint().syncUpdateDownBuilder().build();
        JSONArray jsonArray = this.syncDownCallAPI(urlBuilder, target, "update");
        LOGGER.info(target + ": ....syncDownCallAPI....syncDownUpdate...." + jsonArray.toString());
        if (jsonArray != null && jsonArray.length() > 0) {
            JSONObject jsonObject = dao.syncDownUpdateDB(jsonArray);
                urlBuilder = new CloudEndpoint().syncRepUpdateDownBuilder().build();
            synRep(urlBuilder, jsonObject, target);
        }
    }

}
