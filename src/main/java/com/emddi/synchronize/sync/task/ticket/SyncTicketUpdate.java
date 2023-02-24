package com.emddi.synchronize.sync.task.ticket;


import com.emddi.dao.FutureTicketValueDao;
import com.emddi.dao.IDAO;
import com.emddi.dao.TicketDao;
import com.emddi.synchronize.ApiConst;
import com.emddi.synchronize.sync.task.SyncTask;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class SyncTicketUpdate extends SyncTask {

    public String name = "ticket sync down update";
    private static SyncTicketUpdate instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncTicketUpdate(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncTicketUpdate(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public SyncTicketUpdate(int initDelay, int period, TimeUnit unit) {
        super.setInitDelay(initDelay);
        super.setPeriod(period);
        super.setUnit(unit);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public Runnable taskRunnable() {
        return () -> {
            try {

                IDAO dao = new TicketDao();
                this.syncDownUpdate(ApiConst.TARGET_TICKET, dao);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        };
    }


}
