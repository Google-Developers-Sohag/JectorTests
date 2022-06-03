package core.threads;

public abstract class Task implements Runnable {
    private volatile boolean executed;

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
