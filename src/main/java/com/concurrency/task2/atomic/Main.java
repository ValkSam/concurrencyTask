package com.concurrency.task2.atomic;

import com.concurrency.task2.atomic.counters.AtomicCounter;
import com.concurrency.task2.atomic.counters.Counter;
import com.concurrency.task2.atomic.counters.SynchronizedBlockCounter;
import com.concurrency.task2.atomic.counters.SynchronizedCounter;
import com.concurrency.task2.atomic.counters.UnsafeCounter;
import com.concurrency.task2.atomic.counters.VolatileCounter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;
import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

public class Main {

    private static final int INCREMENT_COUNT = 90000;
    private static final int TEST_COUNT = 5;
    private static final Map<String, AverageTestingData> averageTestingDataMap = new TreeMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < TEST_COUNT; i++) {
            testUnsafe();
            testAtomic();
            testSynchronized();
            testSynchronizedBlock();
            testVolatile();
        }

        printAverageResult();
    }

    private static void testUnsafe() {
        testCounter(new UnsafeCounter(), "testUnsafe");
    }

    private static void testAtomic() {
        testCounter(new AtomicCounter(), "testAtomic");
    }

    private static void testSynchronized() {
        testCounter(new SynchronizedCounter(), "testSynchronized");
    }

    private static void testSynchronizedBlock() {
        testCounter(new SynchronizedBlockCounter(), "testSynchronizedBlock");
    }

    private static void testVolatile() {
        testCounter(new VolatileCounter(), "testVolatile");
    }

    private static void testCounter(Counter counter, String testName) {
        CountDownLatch latch = new CountDownLatch(INCREMENT_COUNT);

        Thread thread[] = new Thread[INCREMENT_COUNT];
        for (int i = 0; i < INCREMENT_COUNT; i++) {
            thread[i] = new Thread(new Task(counter, latch), "Thread " + i);
        }

        CounterProfiler profiler = new CounterProfiler();
        profiler.start();

        for (int i = 0; i < INCREMENT_COUNT; i++) {
            thread[i].start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        }

        profiler.stop();

        out.println("\nExpected = " + INCREMENT_COUNT + " Actual " + counter.getName() + " = " + counter.getValue());
        out.println(testName + " = " + profiler.getSpentTimeMilliseconds() + " milliseconds");

        collectResultSkippingFirstOne(testName, profiler, counter);
    }

    private static void collectResultSkippingFirstOne(String testName, CounterProfiler profiler, Counter counter) {
        if (isNull(averageTestingDataMap.get(testName))) {
            averageTestingDataMap.put(testName, new AverageTestingData());
        } else {
            averageTestingDataMap.get(testName).time += profiler.getSpentTimeMilliseconds();
            averageTestingDataMap.get(testName).value += counter.getValue();
        }
    }

    private static void printAverageResult() {
        averageTestingDataMap.forEach((key, value) -> {
            value.time = value.time / (TEST_COUNT - 1);
            value.value = value.value / (TEST_COUNT - 1);
        });
        Map<String, AverageTestingData> sortedResult = averageTestingDataMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparing(e -> e.time)))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        out.println();
        out.println(sortedResult);
    }

    static class CounterProfiler {
        long startTime;
        long endTime;

        void start() {
            this.startTime = System.currentTimeMillis();
        }

        void stop() {
            this.endTime = System.currentTimeMillis();
        }

        long getSpentTimeMilliseconds() {
            return endTime - startTime;
        }
    }

    static class AverageTestingData {
        long time;
        long value;

        @Override
        public String toString() {
            return "AverageTestingData{" +
                    "time=" + time +
                    ", value=" + value +
                    "}\n";
        }

    }

}
