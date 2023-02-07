package com.emddi.synchronize.schedule;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ScheduleStarter implements ServletContextListener {
    private ScheduleManager schedulerManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Sync Running.....");
        schedulerManager = new ScheduleManager();
        schedulerManager.runSyncEmployee();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        schedulerManager.shutdownNow();
    }
}
