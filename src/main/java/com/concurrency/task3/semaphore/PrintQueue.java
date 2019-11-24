package com.concurrency.task3.semaphore;

import com.concurrency.utils.SysUtil;

import java.util.concurrent.Semaphore;

public class PrintQueue {

    private final Semaphore semaphore;

    public PrintQueue(int countPrints) {
        this.semaphore = new Semaphore(countPrints);
    }

    public void printJob() {
        try {
            semaphore.acquire();
            System.out.printf("%s: PrintQueue: Printing ... \n", Thread.currentThread().getName());
            SysUtil.sleepRandom(2, 5);
            System.out.printf("%s: PrintQueue: Doc has been printed ... \n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

}
