package com.concurrency.task5.deadlock.race;

import java.util.ArrayList;

public class Main {

    private static int QUEUE_SIZE = 10;
    private static ArrayList<String> QUEUE = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        Thread thread = new Thread(new RacingDeadlockDetector());
        thread.setDaemon(true);
        thread.start();

        Thread producer = new Thread(new Producer(QUEUE, QUEUE_SIZE * 10000));

        Thread consumer = new Thread(new Consumer(QUEUE, QUEUE_SIZE * 10000));

        producer.start();

        consumer.start();

        consumer.join();

        System.out.println("Completed. Queue size is " + QUEUE.size());
    }

}
