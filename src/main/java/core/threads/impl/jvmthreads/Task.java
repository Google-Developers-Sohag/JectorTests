package core.threads.impl.jvmthreads;

public abstract class Task implements Runnable {
    private volatile boolean executed;

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted() {
        this.executed = true;
    }
}
