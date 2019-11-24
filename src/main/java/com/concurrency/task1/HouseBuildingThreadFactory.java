package com.concurrency.task1;

import com.concurrency.task1.threads.Construction;
import com.concurrency.task1.threads.OrdinaryConstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import static java.lang.String.format;

public class HouseBuildingThreadFactory implements ThreadFactory {

    private final List<Construction> constructions = new ArrayList<>();

    private final List<Thread> constructionTasks = new ArrayList<>();

    @Override
    public Thread newThread(Runnable r) {
        storeConstruction(r);

        Thread task = createAndStoreTask();

        return task;
    }

    public Thread getLastTask() {
        return constructionTasks.get(constructionTasks.size() - 1);
    }

    public List<Construction> getConstructions() {
        return constructions;
    }

    private void storeConstruction(Runnable r) {
        Construction construction = (Construction) r;
        if (constructionTasks.size() > 0) {
            ((OrdinaryConstruction) construction).setConstructionTaskToBeWaitedFor(constructionTasks.get(constructionTasks.size() - 1));
        }
        this.constructions.add(construction);
    }

    private Thread createAndStoreTask() {
        Thread thread = new Thread(constructions.get(constructions.size() - 1));
        thread.setName(format("Thread %s", constructionTasks.size() + 1));
        this.constructionTasks.add(thread);
        return thread;
    }

}
