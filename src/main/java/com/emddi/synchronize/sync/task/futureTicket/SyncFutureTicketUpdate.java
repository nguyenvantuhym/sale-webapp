package com.emddi.synchronize.sync.task.futureTicket;


import com.emddi.dao.EmployeeDao;
import com.emddi.dao.FutureTicketDao;
import com.emddi.dao.IDAO;
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
public class SyncFutureTicketUpdate extends SyncTask {

    public String name = "FutureTicket sync down update";
    private static SyncFutureTicketUpdate instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncFutureTicketUpdate(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncFutureTicketUpdate(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public SyncFutureTicketUpdate(int initDelay, int period, TimeUnit unit) {
        super.setInitDelay(initDelay);
        super.setPeriod(period);
        super.setUnit(unit);
    }

    @Override
    public Runnable taskRunnable() {
        return () -> {
            try {

                IDAO dao = new FutureTicketDao();
                this.syncDownUpdate(ApiConst.TARGET_FUTURE_TICKET, dao);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        };
    }


}
