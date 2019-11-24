package com.concurrency.task5.deadlock;

import com.concurrency.utils.SysUtil;

public class Consumer implements Runnable {

    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        System.out.println("Consumer Started");
        while (true) {
            SysUtil.sleepRandomMillisec(1, 5);
            String element = buffer.get();
            if (element != null) {
                System.out.println(element);
            }
        }
    }

}

