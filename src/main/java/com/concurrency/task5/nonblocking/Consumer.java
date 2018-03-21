package com.concurrency.task5.nonblocking;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.concurrency.utils.SysUtil.sleepMillisecs;

public class Consumer implements Runnable {

    private final ConcurrentLinkedQueue<String> queue;
    private final int count;

    public Consumer(ConcurrentLinkedQueue<String> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("Consumer Started");
        String element;
        for (int i = 0; i < count; i++) {
            while ((element = queue.poll()) == null) {
                System.out.println("   Nothing to get. Wait ...");
                sleepMillisecs(100);
            }
            System.out.println(element);
        }
    }

}

