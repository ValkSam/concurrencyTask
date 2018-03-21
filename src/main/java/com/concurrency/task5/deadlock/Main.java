package com.concurrency.task5.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    private static Buffer buffer = new Buffer();


    public static void main(String[] args) throws Exception {

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                if (threadMXBean.findDeadlockedThreads() != null) {
                    System.out.println("DEADLOCK !");
                    System.exit(0);
                }
            }
        }, 0, 1, SECONDS);

        try {

            Thread producer = new Thread(new Producer(buffer));
            producer.setDaemon(true);

            Thread consumer = new Thread(new Consumer(buffer));

            producer.start();

            consumer.start();

            consumer.join();

            System.out.println("END");

        } finally {
            executor.shutdown();
        }
    }

}
