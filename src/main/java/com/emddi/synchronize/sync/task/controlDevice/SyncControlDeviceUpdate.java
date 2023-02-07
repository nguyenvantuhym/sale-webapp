package com.emddi.synchronize.sync.task.controlDevice;


import com.emddi.dao.ControlDeviceDao;
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
public class SyncControlDeviceUpdate extends SyncTask {

    public String name = "ControlDevice sync down update";
    private static SyncControlDeviceUpdate instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncControlDeviceUpdate(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncControlDeviceUpdate(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public SyncControlDeviceUpdate(int initDelay, int period, TimeUnit unit) {
        super.setInitDelay(initDelay);
        super.setPeriod(period);
        super.setUnit(unit);
    }

    @Override
    public Runnable taskRunnable() {
        return () -> {
            try {

                IDAO controlDevice = new ControlDeviceDao();
                this.syncDownUpdate(ApiConst.TARGET_CONTROL_DEVICE, controlDevice);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        };
    }


}