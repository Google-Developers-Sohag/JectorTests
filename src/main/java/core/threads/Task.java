package core.threads;

public abstract class Task implements Runnable {
    private volatile boolean executed;

    public boolean isExecuted() {
        return executed;
    }

    protected void setExecuted() {
        this.executed = true;
    }
}
