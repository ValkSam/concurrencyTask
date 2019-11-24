package com.concurrency.concurrent_set;

import com.concurrency.utils.SysUtil;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SynchronizedSetUsingContains {


    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting with JDK's synchronizedSet");
        new ThreadsLauncher(Collections.synchronizedSet(new HashSet<>())).launch().join();
        System.out.println();
        System.out.println();
        System.out.println("Starting with Concurrency synchronizedSet");
        new ThreadsLauncher(new ConcurrentHashMap<String, Boolean>().newKeySet()).launch().join();
    }

    static class ThreadsLauncher {

        private final Set<Integer> set;

        public ThreadsLauncher(Set<Integer> set) {
            this.set = set;
        }

        Thread launch() {

            set.add(0);
            set.add(1);
            set.add(2);
            set.add(3);
            set.add(4);
            set.add(5);

            Thread oddItemsRemover = new Thread(new Runnable() {
                @Override
                public void run() {
                    int setSize = set.size();
                    for (int i = 0; i < setSize; i++) {
                        if (i % 2 == 0) {
                            continue;
                        }
                        SysUtil.sleepMillisecs(20);
                        set.remove(i);
                    }
                    System.out.println("set modification is completed");
                }
            });

            Thread setIterator = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int p = 0; p < 2; p++) {
                        System.out.println("passing " + p);
                        try {
                            int setSize = set.size();
                            for (int i = 0; i < setSize; i++) {
                                System.out.println(i + " >> " + set.contains(i));
                                SysUtil.sleepMillisecs(50);
                            }
                            System.out.println("     !!! No problem detected !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
                        } catch (ConcurrentModificationException exception) {
                            System.out.println("     !!! ConcurrentModificationException detected !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
                        }
                        try {
                            oddItemsRemover.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            setIterator.start();
            oddItemsRemover.start();

            return setIterator;

        }


    }


}
