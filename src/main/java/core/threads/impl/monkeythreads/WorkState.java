package core.threads.impl.monkeythreads;

import core.threads.impl.jvmthreads.AppThread;
import core.threads.Work;

public class WorkState {
    private Work work;
    private boolean executed;
    private AppThread appThread;

    public WorkState() {
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted() {
        this.executed = true;
    }

    public AppThread getAppThread() {
        return appThread;
    }

    public void setAppThread(AppThread appThread) {
        this.appThread = appThread;
    }
}
