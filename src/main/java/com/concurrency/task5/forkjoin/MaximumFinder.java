package com.concurrency.task5.forkjoin;

import java.util.concurrent.RecursiveTask;

import static java.lang.Integer.MIN_VALUE;

public class MaximumFinder extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 150000;
    private static Integer[] data;
    private final int start;
    private final int end;

    public MaximumFinder(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public MaximumFinder(Integer[] array) {
        data = array;
        this.start = 0;
        this.end = data.length;
    }

    @Override
    protected Integer compute() {
        int length = end - start;
        if (length < THRESHOLD) {
            return computeDirectly();
        }

        int middle = length >>> 1;

        MaximumFinder left = new MaximumFinder(start, start + middle);
        MaximumFinder right = new MaximumFinder(start + middle, end);

        left.fork();
        Integer rightResult = right.compute();

        return Math.max(rightResult, left.join());
    }

    private Integer computeDirectly() {
        int max = MIN_VALUE;
        for (int i = start; i < end; i++) {
            max = Math.max(max, data[i]);
        }
        return max;
    }


}