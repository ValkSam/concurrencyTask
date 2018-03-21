package com.concurrency.task5.deadlock.race;

import java.util.ArrayList;

public class Consumer implements Runnable {

    private final ArrayList<String> queue;
    private final int count;

    public Consumer(ArrayList<String> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("Consumer Started");
        for (int i = 0; i < count; i++) {
            while (queue.size() == 0) {
                System.out.println("Consumer wait ...");
                //next line is to force deadlock
                //SysUtil.sleepMillisecs(1);
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
            queue.get(0);
            queue.remove(0);
            System.out.println("  Consumed: " + element);
            if (queue.size() == count - 1) {
                synchronized (queue) {
                    queue.notifyAll();
                }
            }
        }
    }

}

