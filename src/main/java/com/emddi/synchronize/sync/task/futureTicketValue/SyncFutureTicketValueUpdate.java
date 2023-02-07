package com.emddi.synchronize.sync.task.futureTicketValue;


import com.emddi.dao.EmployeeDao;
import com.emddi.dao.FutureTicketValueDao;
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
public class SyncFutureTicketValueUpdate extends SyncTask {

    public String name = "FutureTicketValue sync down update";
    private static SyncFutureTicketValueUpdate instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncFutureTicketValueUpdate(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncFutureTicketValueUpdate(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public SyncFutureTicketValueUpdate(int initDelay, int period, TimeUnit unit) {
        super.setInitDelay(initDelay);
        super.setPeriod(period);
        super.setUnit(unit);
    }

    @Override
    public Runnable taskRunnable() {
        return () -> {
            try {

                IDAO dao = new FutureTicketValueDao();
                this.syncDownUpdate(ApiConst.TARGET_FUTURE_TICKET_VALUE, dao);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        };
    }


}
