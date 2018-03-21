package com.concurrency.task5.forkjoin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static java.lang.Integer.MIN_VALUE;
import static java.lang.String.format;

public class Main {

    private static final int[] PARALLELISMS = {1, 4, 8, 40, 80, 160};

    private static Integer[] array = new Integer[1_000_000];

    public static void main(String[] args) throws Exception {
        initData(array);
        for (int parallelism : PARALLELISMS) {
            ForkJoinPool pool = null;
            try {
                pool = new ForkJoinPool(parallelism);
                MaximumFinderProfiler profiler = new MaximumFinderProfiler();
                array = shuffleArray();
                profiler.start();
                int max = pool.invoke(new MaximumFinder(array));
                profiler.stop();
                System.out.println(format("Actual max value is %s for parallelism %s. Time %s", max, parallelism, profiler.getSpentTimeMilliseconds()));
            } finally {
                if (pool != null) {
                    pool.shutdown();
                }
            }
        }

    }

    private static Integer[] shuffleArray() {
        List<Integer> list = Arrays.asList(array);
        Collections.shuffle(list);
        return (Integer[]) list.toArray();
    }

    private static void initData(Integer[] data) {
        int max = MIN_VALUE;
        final Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            int value = random.nextInt(1_000_000 * 100);
            data[i] = value;
            max = Math.max(max, value);
        }
        System.out.println("Expected max value is " + max);
    }

    static class MaximumFinderProfiler {
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

}
