package com.concurrency.task2.atomic.counters;

public class UnsafeCounter extends Counter {

    private static final String NAME = "UnsafeCounter";

    private int i = 0;

    @Override
    public void increment() {
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
