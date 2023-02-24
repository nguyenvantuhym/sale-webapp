package com.emddi.synchronize.sync.task.price;


import com.emddi.dao.GateDao;
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
public class SyncPriceUpdate extends SyncTask {

    public String name = "Price sync down update";
    private static SyncPriceUpdate instance = null;
    public static synchronized final void init(int initDelay, int period, TimeUnit unit){
        if (instance == null) {
            instance = new SyncPriceUpdate(initDelay, period, unit);
        }
    }
    public static synchronized final SyncTask getInstance() {
        if (instance == null) {
            // default 1 , 1 , hours
            instance = new SyncPriceUpdate(1,1, TimeUnit.HOURS);
        }
        return instance;
    }

    public SyncPriceUpdate(int initDelay, int period, TimeUnit unit) {
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
                IDAO dao = new PriceDao();
                this.syncDownUpdate(ApiConst.TARGET_PRICE, dao);
            } catch (SQLException | JSONException | IOException e) {
                System.out.println(e);
            }
        };
    }


}
