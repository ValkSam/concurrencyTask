package com.concurrency.task2.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static final int SIZE = 5;

    private static final Lock printJobLock = new ReentrantLock();

    public static void main(String args[]) {
        Thread thread[] = new Thread[SIZE];
        for (int i = 0; i < SIZE; i++) {
            thread[i] = new Thread(new Job(printJobLock), "Printer " + i);
        }

        for (int i = 0; i < SIZE; i++) {
            thread[i].start();
        }
    }

}
