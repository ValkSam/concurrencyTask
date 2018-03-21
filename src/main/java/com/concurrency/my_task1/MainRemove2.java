package com.concurrency.my_task1;

import java.util.concurrent.LinkedBlockingQueue;

public class MainRemove2 {

    static LinkedBlockingQueue<MyData> linkedBlockingQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 1; i < 1000; i++) {
            linkedBlockingQueue.clear();

            linkedBlockingQueue.add(new MyData(1, true));
            linkedBlockingQueue.add(new MyData(2, true));
            linkedBlockingQueue.add(new MyData(3, true));
            linkedBlockingQueue.add(new MyData(4, true));
            linkedBlockingQueue.add(new MyData(5, true));

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("1 ");
                    for (MyData myData : linkedBlockingQueue) {
                        synchronized (linkedBlockingQueue) {
                            if (!linkedBlockingQueue.contains(myData)) {
                                continue;
                            }
                        }
                        if (myData.getField1()==1) {
                            System.out.println(myData);
                        }
                    }
                }

            });

            Thread thread3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.print("3 ");
                    synchronized (linkedBlockingQueue) {
                        linkedBlockingQueue.remove(linkedBlockingQueue.removeIf(e -> e.getField1() == 1));
                    }
                }
            });

            thread1.start();
            thread3.start();

            thread1.join();

            System.out.print(".");

        }
    }

    static class MyData {
        private int field1;
        private boolean field2;

        public MyData(int field1, boolean field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public int getField1() {
            return field1;
        }

        public void setField1(int field1) {
            this.field1 = field1;
        }

        public boolean isField2() {
            return field2;
        }

        public void setField2(boolean field2) {
            this.field2 = field2;
        }

        @Override
        public String toString() {
            return "MyData{" +
                    "field1=" + field1 +
                    ", field2=" + field2 +
                    '}';
        }
    }


}
