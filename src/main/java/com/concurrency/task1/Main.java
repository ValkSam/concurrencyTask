package com.concurrency.task1;

import com.concurrency.task1.threads.Foundation;
import com.concurrency.task1.threads.Roof;
import com.concurrency.task1.threads.Walls;

public class Main {


    public static void main(String[] args) {

        HouseBuildingThreadFactory factory = new HouseBuildingThreadFactory();

        createAndStartTasks(factory);

        waitForBuildingResult(factory);

    }

    private static void createAndStartTasks(HouseBuildingThreadFactory factory) {
        factory.newThread(new Foundation()).start();
        factory.newThread(new Walls()).start();
        factory.newThread(new Roof()).start();
    }

    private static void waitForBuildingResult(HouseBuildingThreadFactory factory) {
        try {
            factory.getLastTask().join();
            if (factory.getConstructions().stream().anyMatch(e -> !e.isBuilt())) {
                System.out.println("The house has NOT been built!");
                return;
            }
            System.out.println("The house has been built.");
        } catch (InterruptedException e) {
            System.out.println("I cant say if the house has been built! Sorry.");
        }
    }


}
