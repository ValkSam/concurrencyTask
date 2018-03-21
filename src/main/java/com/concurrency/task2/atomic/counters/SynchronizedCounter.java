package com.concurrency.task2.atomic.counters;

public class SynchronizedCounter extends Counter {

    private static final String NAME = "SynchronizedCounter";

    private int i = 0;

    @Override
    public synchronized void increment() {
        i = i + 1;
    }

    @Override
    public int getValue() {
        return i;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
