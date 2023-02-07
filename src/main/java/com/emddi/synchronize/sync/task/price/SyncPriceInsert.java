package com.emddi.synchronize.sync.task.price;


import com.emddi.dao.EmployeeDao;
import com.emddi.dao.IDAO;
import com.emddi.dao.PriceDao;
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
public class SyncPriceInsert extends SyncTask {

    public String name = "Price sync down insert";
    private static SyncPriceInsert instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncPriceInsert(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncPriceInsert(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public SyncPriceInsert(int initDelay, int period, TimeUnit unit) {
        super.setInitDelay(initDelay);
        super.setPeriod(period);
        super.setUnit(unit);
    }

    @Override
    public Runnable taskRunnable() {
        return () -> {
            try {

                IDAO dao = new PriceDao();
                this.syncDownInsert(ApiConst.TARGET_PRICE, dao);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        };
    }


}
