package com.concurrency.task3.cyclicbarrier;


import com.concurrency.utils.SysUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class PrintQueue {

    private final CyclicBarrier barrier;
    private final Semaphore semaphore;
    private final int hardWorking;

    private final AtomicInteger printJobDoneCount = new AtomicInteger();

    public PrintQueue(int hardWorking) {
        this.hardWorking = hardWorking;
        this.semaphore = new Semaphore(hardWorking);
        this.barrier = new CyclicBarrier(hardWorking, new Thread() {
            @Override
            public void run() {
                out.printf("%s: PrintQueue: Queue is full. %s docs have already printed. Go Printing more ... \n", Thread.currentThread().getName(), printJobDoneCount.get());
            }
        });
    }

    public void printJob() throws BrokenBarrierException {
        try {
            //only 5 task can be running
            semaphore.acquire();
            out.printf("%s: PrintQueue: Free printer detected. Waiting for queue filling ... \n", Thread.currentThread().getName());
            barrier.await();
            out.printf("%s: PrintQueue: Printing ... \n", Thread.currentThread().getName());
            SysUtil.sleepRandom(2, 5);
            out.printf("%s: PrintQueue: Doc has been printed ... \n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public void recharge(String name) {
        //reset after each 5-th task
        if (printJobDoneCount.incrementAndGet() == hardWorking) {
            printJobDoneCount.set(0);
            barrier.reset();
        }
    }

}
