package com.concurrency.task5.deadlock.race;

import java.util.ArrayList;

public class Producer implements Runnable {

    private final ArrayList<String> queue;
    private final int count;

    public Producer(ArrayList<String> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("ProducerNoBlocking1 Started");
        for (int i = 0; i < count; i++) {
            while (queue.size() == count) {
                System.out.println("Producer wait ...");
                synchronized (queue) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        System.out.println("I am interrupted!");
                        Thread.currentThread().interrupt();
                    }
                }
            }
            String element = "Producer" + i;
            queue.add(element);
            System.out.println("Produced: " + element);
            if (queue.size() == 1) {
                synchronized (queue) {
                    queue.notifyAll();
                }
            }
        }
    }
}
