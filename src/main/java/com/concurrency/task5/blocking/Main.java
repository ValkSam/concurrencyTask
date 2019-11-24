package com.concurrency.task5.blocking;

import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static int QUEUE_SIZE = 10;
    private static LinkedBlockingQueue<String> QUEUE = new LinkedBlockingQueue<>(QUEUE_SIZE);

    public static void main(String[] args) throws Exception {

        BlockingProducer producer1 = new BlockingProducer("ProducerBlocking1", QUEUE, QUEUE_SIZE);
        BlockingProducer producer2 = new BlockingProducer("ProducerBlocking2", QUEUE, QUEUE_SIZE);
        Thread producerThread1 = new Thread(producer1);
        Thread producerThread2 = new Thread(producer2);

        Thread consumer = new Thread(new Consumer(QUEUE, QUEUE_SIZE * 2));
        producerThread1.start();
        producerThread2.start();

        waitQueueFilling();

        speedUpProducers(producer1, producer2);

        System.out.println("Queue size is " + QUEUE.size());

        consumer.start();

        consumer.join();

        System.out.println("Completed. Queue size is " + QUEUE.size());
    }

    private static void speedUpProducers(BlockingProducer... producers) {
        for (BlockingProducer producer : producers) {
            producer.turnOnSlowProducing();
        }
    }

    private static void waitQueueFilling() {
        while (QUEUE.size() < QUEUE_SIZE) {

        }
    }
}
