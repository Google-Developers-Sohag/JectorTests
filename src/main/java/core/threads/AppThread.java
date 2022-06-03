package core.threads;

import java.util.ArrayList;

/**
 * Represents the base implementation of an app thread.
 *
 * @author pavl_g
 * @see core.threads.impl.Daemon
 * @see core.threads.impl.Looper
 */
public abstract class AppThread extends Thread {

    private final ArrayList<Task> tasks = new ArrayList<>();
    private volatile boolean terminate;
    private final String name;

    public AppThread(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void run() {
        while (!isTerminated()){
            runTasks();
        }
    }

    /**
     * Adds a new task to the tasks stack.
     *
     * @param task a task instance
     */
    public synchronized void addTask(Task task) {
        tasks.add(task);
    }

    private synchronized void runTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).isExecuted()) {
                tasks.remove(tasks.get(i));
                continue;
            }
            tasks.get(i).run();
            tasks.get(i).setExecuted();
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void terminate() {
        this.terminate = true;
        System.out.println("IPBinder: AppThread " + name + " is terminated.");
    }

    public boolean isTerminated() {
        return terminate;
    }
}
