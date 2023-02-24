package com.emddi.synchronize.sync.task.gate;


import com.emddi.dao.EmployeeDao;
import com.emddi.dao.GateDao;
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
public class SyncGateInsert extends SyncTask {

    public String name = "Gate sync down insert";
    private static SyncGateInsert instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncGateInsert(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncGateInsert(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public SyncGateInsert(int initDelay, int period, TimeUnit unit) {
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

                IDAO dao = new GateDao();
                this.syncDownInsert(ApiConst.TARGET_GATE, dao);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        };
    }


}
