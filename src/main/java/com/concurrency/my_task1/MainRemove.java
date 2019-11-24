package com.concurrency.my_task1;

import java.util.concurrent.LinkedBlockingQueue;

public class MainRemove {

    static LinkedBlockingQueue<MyData> linkedBlockingQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {

        linkedBlockingQueue.add(new MyData(1, true));
        linkedBlockingQueue.add(new MyData(2, true));
        linkedBlockingQueue.add(new MyData(3, true));
        linkedBlockingQueue.add(new MyData(4, true));
        linkedBlockingQueue.add(new MyData(5, true));

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (MyData myData : linkedBlockingQueue) {
//                    synchronized (linkedBlockingQueue) {
                    if (!linkedBlockingQueue.contains(myData)) {
                        continue;
                    }
//                    }
                    System.out.println(myData);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (MyData myData : linkedBlockingQueue) {
                    System.out.println("remove: " + myData);
//                    synchronized (linkedBlockingQueue) {
                    linkedBlockingQueue.remove(myData);
//                    }
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
//                synchronized (linkedBlockingQueue) {
                linkedBlockingQueue.remove(linkedBlockingQueue.removeIf(e -> e.getField1() == 1));
//                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

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
