package com.concurrency.utils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SysUtil {

    private static final Random rand = new Random();

    public static void sleepRandom(int minSleep, int maxSleep) {
        try {
            TimeUnit.SECONDS.sleep(rand.nextInt(maxSleep) + minSleep);
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    public static void sleepRandomMillisec(int minSleep, int maxSleep) {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(maxSleep) + minSleep);
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    public static void sleep(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    public static void sleepMillisecs(int msec) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            System.out.println("I am interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    public static void sleepRandomThrowable(int minSleep, int maxSleep) throws InterruptedException {
        TimeUnit.SECONDS.sleep(rand.nextInt(maxSleep) + minSleep);
    }

}
