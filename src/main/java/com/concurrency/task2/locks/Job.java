package com.concurrency.task2.locks;

import com.concurrency.utils.SysUtil;

import java.util.concurrent.locks.Lock;

public class Job implements Runnable {

    private final Lock printJobLock;

    public Job(Lock printJobLock) {
        this.printJobLock = printJobLock;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print a document\n", Thread.currentThread().getName());
        workProcess();
        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
    }


    private void workProcess() {
        printJobLock.lock();
        try {
            int duration = (int) (Math.random() * 3);
            System.out.println(Thread.currentThread().getName() + ":PrintQueue: Printing a Job during " + duration + " seconds");
            SysUtil.sleep(duration);
        } finally {
            printJobLock.unlock();
        }
    }

}
