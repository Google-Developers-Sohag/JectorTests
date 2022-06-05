package core.threads.impl.monkeythreads.util;

import com.jme3.app.state.AppStateManager;
import core.threads.impl.jvmthreads.AppThread;
import core.threads.impl.jvmthreads.Task;
import core.threads.Threads;
import core.threads.Work;
import core.threads.command.RunOn;
import core.threads.impl.jvmthreads.Daemon;
import core.threads.impl.monkeythreads.MonkeyLooper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;
import core.threads.impl.monkeythreads.WorkState;

/**
 * An IPC Pattern for jMonkeyEngine multithreading.
 *
 * @author pavl_g
 */
public final class MonkeyBinder extends Thread {

    private final Logger logger;
    private final Daemon daemonThread = new Daemon();
    private final MonkeyLooper looperThread = new MonkeyLooper();
    private final ArrayList<WorkState> works = new ArrayList<>();
    private volatile boolean terminate;

    private MonkeyBinder(final AppStateManager stateManager, final String processName) {
        super(processName);
        logger = Logger.getLogger(processName);
        setName(processName);

        daemonThread.start();
        stateManager.attach(looperThread);

        start();

        logger.info("Created a new Monkey IPC");
    }

    /**
     * Creates a new inter-process thread binder.
     *
     * @param stateManager
     * @param processName
     * @return
     */
    public static MonkeyBinder createMonkeyIPC(AppStateManager stateManager, String processName) {
        return new MonkeyBinder(stateManager, processName);
    }

    @Override
    public void run() {
        while (!isTerminate()) {
            runQueuedWorks();
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        logger.info("Started Monkey IPC");
    }

    /**
     * Add a new work to the current daemon process.
     *
     * @param work a work to be executed within this daemon.
     */
    public synchronized void addDaemonWork(Work work) {
        final WorkState workState = new WorkState();
        workState.setWork(work);
        workState.setAppThread(daemonThread);
        works.add(workState);
    }

    public void terminate() {
        this.terminate = true;

        daemonThread.terminate();

        logger.info("Terminated");
    }

    public boolean isTerminate() {
        return terminate;
    }

    private synchronized void runQueuedWorks() {
        for (int i = 0; i < works.size(); i++) {
            if (works.get(i).isExecuted()) {
                works.remove(works.get(i));
                continue;
            }
            executeOnThread(works.get(i).getWork(), works.get(i).getAppThread());
            works.get(i).setExecuted();
        }
    }

    private synchronized void executeOnThread(Work work, AppThread thread) {
        final Method[] methods = work.getClass().getDeclaredMethods();
        final Method[] workMethods = new Method[2];
        final Object[] resultStore = new Object[1];

        for (Method method : methods) {
            RunOn annotation = method.getAnnotation(RunOn.class);
            if (annotation == null) {
                continue;
            }
            if (annotation.thread().getThreadClass() == thread.getClass()) {
                if (workMethods[0] != null) {
                    throw new IllegalThreadStateException("Your class should have only one background tagged method to do the IPC !");
                }
                workMethods[0] = method;
            } else if (annotation.thread() == Threads.LOOPER) {
                if (workMethods[1] != null) {
                    throw new IllegalThreadStateException("Your class should have only one looper tagged method to do the IPC !");
                }
                workMethods[1] = method;
            }
        }

        if (workMethods[0] == null || workMethods[1] == null) {
            return;
        }

        // execute a task on the daemon thread
        logger.info("Running into " + Thread.currentThread().getName());
        try {
            resultStore[0] = workMethods[0].invoke(work);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // wait until a data is returned then add a task for the looper thread
        while (!isUpdated(resultStore[0]) && !daemonThread.isTerminated());

        looperThread.addTask(new Task() {
            @Override
            public void run() {
                logger.info("Running into " + Thread.currentThread().getName());
                try {
                    workMethods[1].invoke(work, resultStore[0]);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isUpdated(Object object) {
        return object != null;
    }

    public Daemon getDaemonThread() {
        return daemonThread;
    }

    public MonkeyLooper getLooperThread() {
        return looperThread;
    }
}
