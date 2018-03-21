package com.concurrency.task2.atomic.counters;

public class SynchronizedBlockCounter extends Counter {

    private static final String NAME = "SynchronizedBlockCounter";

    private int i = 0;

    @Override
    public void increment() {
        synchronized (this) {
            i = i + 1;
        }
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
