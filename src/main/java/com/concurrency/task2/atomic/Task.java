package com.concurrency.task2.atomic;

import com.concurrency.task2.atomic.counters.Counter;

import java.util.concurrent.CountDownLatch;

public class Task implements Runnable {

    private Counter atomicCounter;
    private CountDownLatch latch;

    public Task(Counter atomicCounter, CountDownLatch latch) {
        this.atomicCounter = atomicCounter;
        this.latch = latch;
    }

    @Override
    public void run() {
        atomicCounter.increment();
        latch.countDown();
    }

}
