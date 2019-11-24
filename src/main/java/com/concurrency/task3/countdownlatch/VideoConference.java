package com.concurrency.task3.countdownlatch;


import java.util.concurrent.CountDownLatch;

public class VideoConference implements Runnable {

    private final CountDownLatch latch;

    public VideoConference(int number) {
        latch = new CountDownLatch(number);
    }

    public void arrive(String name) {
        System.out.printf("%s has arrived.\n", name);
        latch.countDown();
    }

    public void run() {
        System.out.printf("VideoConference: Waiting for %d participants...\n", latch.getCount());
        try {
            latch.await();
            System.out.printf("VideoConference: All participants are here. Let's start\n", latch.getCount());
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        }
    }

}
