package com.emddi.synchronize.syncup;

import com.emddi.constants.SyncStatus;
import com.emddi.dao.IDAOSyncUp;
import com.emddi.dao.TicketDao;
import com.emddi.synchronize.sync.task.SyncTask;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class SyncUpTicketUpdate extends SyncTask {
    public String name = "Sync up ticket update";
    private static SyncUpTicketUpdate instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncUpTicketUpdate(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncUpTicketUpdate(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public SyncUpTicketUpdate(int initDelay, int period, TimeUnit unit) {
        super.setInitDelay(initDelay);
        super.setPeriod(period);
        super.setUnit(unit);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public Runnable taskRunnable() throws SQLException, JSONException, IOException {
        return () -> {

            try {
                IDAOSyncUp dao = new TicketDao();
                new SyncUpImp().syncUp(dao, "ticket", SyncStatus.NEW);
            } catch (IOException e) {
                System.out.println(e);
            }
        };
    }
}
