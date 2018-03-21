package com.concurrency.task5.deadlock.race;

import com.concurrency.utils.SysUtil;

public class RacingDeadlockDetector implements Runnable {

    @Override
    public void run() {
        SysUtil.sleep(3);
        System.out.println("DEADLOCK !!! ");
        System.exit(1);
    }
}
