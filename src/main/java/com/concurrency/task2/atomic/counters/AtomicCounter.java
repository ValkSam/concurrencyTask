package com.concurrency.task2.atomic.counters;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter extends Counter {

    private static final String NAME = "AtomicCounter";

    private AtomicInteger i = new AtomicInteger();

    @Override
    public void increment() {
        i.incrementAndGet();
    }

    @Override
    public int getValue() {
        return i.intValue();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
