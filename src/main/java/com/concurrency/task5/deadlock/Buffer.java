package com.concurrency.task5.deadlock;

import java.util.ArrayList;

import static java.lang.String.format;

public class Buffer {
    private final ArrayList<String> queue = new ArrayList<>();
    private Integer elementProducedBreakPoint = 100;
    private Integer elementProducedCount = 0;
    private Integer elementConsumedCount = 0;

    public ArrayList<String> getQueue() {
        return queue;
    }

    public void put(String element) {
        synchronized (queue) {
            queue.add(element);
            synchronized (elementProducedCount) {
                elementProducedCount++;
                System.out.println("  produced elements: " + elementProducedCount);
            }
        }
    }

    public void putNoDeadlock(String element) {
        synchronized (elementProducedCount) {
            elementProducedCount++;
            System.out.println("  produced elements: " + elementProducedCount);
            synchronized (queue) {
                queue.add(element);
            }
        }
    }

    public String get() {
        synchronized (elementProducedCount) {
            if (elementProducedCount >= elementProducedBreakPoint) {
                throw new RuntimeException(format("Producing breakpoint is reached but %s elements are not consumed",
                        elementProducedBreakPoint - elementConsumedCount));
            }
            synchronized (queue) {
                String result = queue.size() == 0 ? null : queue.get(0);
                if (result != null) {
                    elementConsumedCount++;
                    queue.remove(0);
                }
                return result;
            }
        }
    }

}
