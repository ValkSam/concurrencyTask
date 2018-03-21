package com.concurrency.task5.blocking;

import java.util.concurrent.LinkedBlockingQueue;

public class Consumer implements Runnable {

    private final LinkedBlockingQueue<String> queue;
    private final int count;

    public Consumer(LinkedBlockingQueue<String> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("Consumer Started");
        for (int i = 0; i < count; i++) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                System.out.println("I am interrupted!");
                Thread.currentThread().interrupt();
            }
        }
    }
}

