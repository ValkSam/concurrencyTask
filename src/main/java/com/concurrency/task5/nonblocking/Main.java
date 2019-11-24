package com.concurrency.task5.nonblocking;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    private static int QUEUE_SIZE = 10;
    private static ConcurrentLinkedQueue<String> QUEUE = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws Exception {
        int firstProducerCount = QUEUE_SIZE >>> 1;

        Thread producer1 = new Thread(new ProducerNoBlocking1(QUEUE, firstProducerCount));
        Thread producer2 = new Thread(new ProducerNoBlocking2(QUEUE, QUEUE_SIZE - firstProducerCount));

        Thread consumer = new Thread(new Consumer(QUEUE, QUEUE_SIZE));

        producer1.start();
        producer2.start();

        consumer.start();

        consumer.join();

        System.out.println("Completed. Queue size is " + QUEUE.size());
    }

}
