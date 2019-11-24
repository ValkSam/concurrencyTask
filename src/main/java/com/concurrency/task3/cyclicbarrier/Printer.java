package com.concurrency.task3.cyclicbarrier;


import java.util.concurrent.BrokenBarrierException;

import static java.lang.System.out;

public class Printer implements Runnable {

    private String name;
    private PrintQueue printQueue;

    public Printer(PrintQueue printQueue, String name) {
        this.name = name;
        this.printQueue = printQueue;
    }

    public void run() {
        while (true) {
            try {
                printQueue.printJob();
                printQueue.recharge(name);
                return;
            } catch (BrokenBarrierException e) {
                out.printf("%s: was removed from queue. Will be attempted again ... \n", Thread.currentThread().getName());
            }
        }
    }

}

