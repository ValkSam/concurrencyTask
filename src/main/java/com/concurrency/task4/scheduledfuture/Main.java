package com.concurrency.task4.scheduledfuture;


import com.concurrency.utils.SysUtil;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    public static final int DELAY = 5;

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println(format("Will start after %s seconds. Now %s", DELAY, new Date()));
        scheduledExecutorService.schedule(new Task(), DELAY, SECONDS);
        SysUtil.sleep(15);
        scheduledExecutorService.shutdownNow();
    }

}
