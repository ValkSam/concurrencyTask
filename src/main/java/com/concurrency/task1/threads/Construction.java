package com.concurrency.task1.threads;

import com.concurrency.utils.SysUtil;

public abstract class Construction implements Runnable {

    boolean built;

    abstract String getName();

    abstract int getMinBuildingTime();

    abstract int getMaxBuildingTime();

    void build() throws InterruptedException {
        SysUtil.sleepRandomThrowable(getMinBuildingTime(), getMaxBuildingTime());
    }

    void sayResult() {
        System.out.format("%s %s has been built\n", Thread.currentThread().getName(), getName());
    }

    void sayInterruption() {
        System.out.format("%s %s building has been interrupted\n", Thread.currentThread().getName(), getName());
    }

    public boolean isBuilt() {
        return built;
    }

}
