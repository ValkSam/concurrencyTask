package com.concurrency.task5.nonblocking;

import com.concurrency.utils.SysUtil;

import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.String.format;

public class ProducerNoBlocking1 implements Runnable {

    private final ConcurrentLinkedQueue<String> queue;
    private final int count;

    public ProducerNoBlocking1(ConcurrentLinkedQueue<String> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("ProducerNoBlocking1 Started");
        for (int i = 0; i < count; i++) {
            SysUtil.sleepRandom(1, 5);
            String element = "ProducerNoBlocking1 " + i;
            System.out.println(format("      %s was produced", element));
            queue.add(element);
        }
    }
}
