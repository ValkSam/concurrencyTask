package com.concurrency.task1.threads;

public class Foundation extends Construction {

    private static final String NAME = "foundation";

    private static final int MIN_BUILDING_TIME_SECONDS = 1;
    private static final int MAX_BUILDING_TIME_SECONDS = 10;

    @Override
    String getName() {
        return NAME;
    }

    @Override
    int getMinBuildingTime() {
        return MIN_BUILDING_TIME_SECONDS;
    }

    @Override
    int getMaxBuildingTime() {
        return MAX_BUILDING_TIME_SECONDS;
    }

    @Override
    public void run() {
        try {
            build();
            sayResult();
            built = true;
        } catch (InterruptedException e) {
            sayInterruption();
        }
    }

}
