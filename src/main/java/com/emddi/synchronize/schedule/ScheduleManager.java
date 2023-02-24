package com.emddi.synchronize.schedule;


import com.emddi.synchronize.sync.task.controlDevice.SyncControlDeviceInsert;
import com.emddi.synchronize.sync.task.controlDevice.SyncControlDeviceUpdate;
import com.emddi.synchronize.sync.task.employee.SyncEmployeeInsert;
import com.emddi.synchronize.sync.task.employee.SyncEmployeeUpdate;
import com.emddi.synchronize.sync.task.SyncTask;
import com.emddi.synchronize.sync.task.futureTicket.SyncFutureTicketInsert;
import com.emddi.synchronize.sync.task.futureTicket.SyncFutureTicketUpdate;
import com.emddi.synchronize.sync.task.futureTicketValue.SyncFutureTicketValueInsert;
import com.emddi.synchronize.sync.task.futureTicketValue.SyncFutureTicketValueUpdate;
import com.emddi.synchronize.sync.task.gate.SyncGateInsert;
import com.emddi.synchronize.sync.task.gate.SyncGateUpdate;
import com.emddi.synchronize.sync.task.price.SyncPriceInsert;
import com.emddi.synchronize.sync.task.price.SyncPriceUpdate;
import com.emddi.synchronize.sync.task.ticket.SyncTicketInsert;
import com.emddi.synchronize.sync.task.ticket.SyncTicketUpdate;
import com.emddi.synchronize.syncup.SyncUpTicketInsert;
import com.emddi.synchronize.syncup.SyncUpTicketUpdate;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleManager {
    private static ScheduledThreadPoolExecutor scheduler;

    public static ArrayList<SyncTask> tasks = new ArrayList<>();
    public static void restartTaskByTask(SyncTask task) {
        task.restart(scheduler);
    }

    public ScheduleManager(){
        this.scheduler = new ScheduledThreadPoolExecutor(5);
        this.scheduler.setRemoveOnCancelPolicy(true);
    }
    public void run() {
        SyncEmployeeInsert.init(0,1, TimeUnit.MINUTES);
        SyncEmployeeInsert.getInstance().start(this.scheduler);
        tasks.add(SyncEmployeeInsert.getInstance());

        SyncEmployeeUpdate.init(0,1, TimeUnit.MINUTES);
        SyncEmployeeUpdate.getInstance().start(this.scheduler);
        tasks.add(SyncEmployeeUpdate.getInstance());

        SyncControlDeviceInsert.init(0,1, TimeUnit.MINUTES);
        SyncControlDeviceInsert.getInstance().start(this.scheduler);
        tasks.add(SyncControlDeviceInsert.getInstance());

        SyncControlDeviceUpdate.init(0,1, TimeUnit.MINUTES);
        SyncControlDeviceUpdate.getInstance().start(this.scheduler);
        tasks.add(SyncControlDeviceUpdate.getInstance());

        SyncFutureTicketInsert.init(0,1, TimeUnit.MINUTES);
        SyncFutureTicketInsert.getInstance().start(this.scheduler);
        tasks.add(SyncFutureTicketInsert.getInstance());

        SyncFutureTicketUpdate.init(0,1, TimeUnit.MINUTES);
        SyncFutureTicketUpdate.getInstance().start(this.scheduler);
        tasks.add(SyncFutureTicketUpdate.getInstance());

        SyncFutureTicketValueInsert.init(0,1, TimeUnit.MINUTES);
        SyncFutureTicketValueInsert.getInstance().start(this.scheduler);
        tasks.add(SyncFutureTicketValueInsert.getInstance());

        SyncFutureTicketValueUpdate.init(0,1, TimeUnit.MINUTES);
        SyncFutureTicketValueUpdate.getInstance().start(this.scheduler);
        tasks.add(SyncFutureTicketValueUpdate.getInstance());

        SyncGateInsert.init(0,1, TimeUnit.MINUTES);
        SyncGateInsert.getInstance().start(this.scheduler);
        tasks.add(SyncGateInsert.getInstance());

        SyncGateUpdate.init(0,1, TimeUnit.MINUTES);
        SyncGateUpdate.getInstance().start(this.scheduler);
        tasks.add(SyncGateUpdate.getInstance());


        SyncPriceInsert.init(0,1, TimeUnit.MINUTES);
        SyncPriceInsert.getInstance().start(this.scheduler);
        tasks.add(SyncPriceInsert.getInstance());

        SyncPriceUpdate.init(0,1, TimeUnit.MINUTES);
        SyncPriceUpdate.getInstance().start(this.scheduler);
        tasks.add(SyncPriceUpdate.getInstance());

        SyncUpTicketUpdate.init(0,1, TimeUnit.MINUTES);
        SyncUpTicketUpdate.getInstance().start(this.scheduler);
        tasks.add(SyncUpTicketUpdate.getInstance());

        SyncUpTicketInsert.init(0,1, TimeUnit.MINUTES);
        SyncUpTicketInsert.getInstance().start(this.scheduler);
        tasks.add(SyncUpTicketInsert.getInstance());

        SyncTicketInsert.init(0,1, TimeUnit.MINUTES);
        SyncTicketInsert.getInstance().start(this.scheduler);
        tasks.add(SyncTicketInsert.getInstance());

        SyncTicketUpdate.init(0,1, TimeUnit.MINUTES);
        SyncTicketUpdate.getInstance().start(this.scheduler);
        tasks.add(SyncTicketUpdate.getInstance());

    }


    public void shutdownNow() {
        scheduler.shutdownNow();
    }
}
