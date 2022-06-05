package core.threads.impl.monkeythreads;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import core.threads.impl.jvmthreads.Task;
import java.util.ArrayList;

public class MonkeyLooper extends BaseAppState {

    private final ArrayList<Task> tasks = new ArrayList<>();
    private volatile boolean terminate;

    @Override
    protected void initialize(Application app) {

    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {
        if (isTerminated()) {
            // self detachment onTermination
            getStateManager().detach(this);
            return;
        }
        runTasks();
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
    }

    public boolean isTerminated() {
        return terminate;
    }
}
