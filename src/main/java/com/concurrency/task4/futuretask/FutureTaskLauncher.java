package com.concurrency.task4.futuretask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.MINUTES;

public class FutureTaskLauncher {

    private static final int LAUNCH_COUNT = 5;

    public static void main(String[] args) {
        runWithFutureTask();
        out.println();
        runWithCompletableFuture();

    }

    private static void runWithFutureTask() {
        List<FutureTask<String>> spaceCraftTasks = new ArrayList<>();
        for (int i = 0; i < LAUNCH_COUNT; i++) {
            spaceCraftTasks.add(new FutureTask<>(new SpaceCraft()));
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (FutureTask<String> futureTask : spaceCraftTasks) {
                executorService.submit(futureTask);
            }
            executorService.shutdown();
            executorService.awaitTermination(1, MINUTES);
            for (Future<String> result : spaceCraftTasks) {
                out.println(result.get());
            }
            out.println("Rockets are launched !");
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    private static void runWithCompletableFuture() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < LAUNCH_COUNT; i++) {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> new SpaceCraft().call(), executorService);
            completableFuture.thenAccept(out::println);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, MINUTES);
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        }
        out.println("Rockets are launched !");
    }

}
