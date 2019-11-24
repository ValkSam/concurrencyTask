package com.concurrency.task1.threads;

public abstract class OrdinaryConstruction extends Construction {

    Thread constructionTaskToBeWaitedFor;

    @Override
    public void run() {
        try {
            build();
            waitForSuperConstruction();
            sayResult();
            built = true;
        } catch (InterruptedException e) {
            sayInterruption();
        }

    }

    public void setConstructionTaskToBeWaitedFor(Thread constructionTask) {
        this.constructionTaskToBeWaitedFor = constructionTask;
    }

    private void waitForSuperConstruction() throws InterruptedException {
        constructionTaskToBeWaitedFor.join();
    }

}
