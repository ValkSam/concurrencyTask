package com.concurrency.task5.deadlock;

import com.concurrency.utils.SysUtil;

public class Producer implements Runnable {

    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        System.out.println("ProducerNoBlocking1 Started");
        for (int i = 0; ; i++) {
            SysUtil.sleepRandomMillisec(1, 5);
            buffer.put("element-" + (i + 1));
//            buffer.putNoDeadlock("element-" + (i + 1));
        }
    }
}
