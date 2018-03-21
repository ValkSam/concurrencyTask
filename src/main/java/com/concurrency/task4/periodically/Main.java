package com.concurrency.task4.periodically;

import com.concurrency.utils.SysUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    public static final int DELAY = 5;

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println(format("Will start each %s seconds.", DELAY));
        scheduledExecutorService.scheduleAtFixedRate(
                new PeriodicallyTask("periodicall task"),
                0, DELAY, SECONDS);
        SysUtil.sleep(25);
        scheduledExecutorService.shutdownNow();
    }


}
