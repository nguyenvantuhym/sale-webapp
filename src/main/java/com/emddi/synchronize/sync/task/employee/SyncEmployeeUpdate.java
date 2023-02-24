package com.emddi.synchronize.sync.task.employee;


import com.emddi.dao.EmployeeDao;
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
public class SyncEmployeeUpdate extends SyncTask {

    public String name = "Employee sync down update";
    private static SyncEmployeeUpdate instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncEmployeeUpdate(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncEmployeeUpdate(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public String getName(){
        return this.name;
    }

    public SyncEmployeeUpdate(int initDelay, int period, TimeUnit unit) {
        super.setInitDelay(initDelay);
        super.setPeriod(period);
        super.setUnit(unit);
    }

    @Override
    public Runnable taskRunnable() {
        return () -> {
            try {

                IDAO employeeDao = new EmployeeDao();
                this.syncDownUpdate(ApiConst.TARGET_EMPLOYEE, employeeDao);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        };
    }


}
