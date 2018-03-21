package com.concurrency.task5.blocking;

import com.concurrency.utils.SysUtil;

import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.String.format;

public class BlockingProducer implements Runnable {

    private final String name;
    private final LinkedBlockingQueue<String> queue;
    private final int count;

    private boolean slowProducing;

    public BlockingProducer(String name, LinkedBlockingQueue<String> queue, int count) {
        this.name = name;
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println(name + " Started");
        for (int i = 0; i < count; i++) {
            if (slowProducing) {
                SysUtil.sleepRandom(1, 2);
            }
            try {
                String element = name + " - " + i;
                queue.put(element);
                System.out.println(format("  Element %s is put", element));
            } catch (InterruptedException e) {
                System.out.println("I am interrupted!");
                Thread.currentThread().interrupt();
            }
        }
    }

    public void turnOnSlowProducing() {
        this.slowProducing = true;
        System.out.println(name + " slow mode is on");
    }
}
