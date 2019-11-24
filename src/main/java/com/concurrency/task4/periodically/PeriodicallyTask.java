package com.concurrency.task4.periodically;

import java.util.Date;

public class PeriodicallyTask implements Runnable {

    private String name;

    public PeriodicallyTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name+"Spaceship has launched " + new Date());
    }
}
