package core.threads.impl.jvmthreads.util;

import core.threads.impl.jvmthreads.Task;
import core.threads.Work;
import core.threads.command.RunOn;
import core.threads.impl.jvmthreads.Daemon;
import core.threads.impl.jvmthreads.Looper;
import core.threads.Threads;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Creates an IPC (inter-process communication) pattern between 2 threads, a daemon thread and a looper (could be ui) thread.
 *
 * @author pavl_g.
 */
public final class IPBinder {

    private static final IPBinder ipBinder = new IPBinder();
    private final Daemon daemonThread = new Daemon();
    private final Looper looperThread = new Looper();

    private IPBinder() {
        daemonThread.start();
        looperThread.start();
    }

    public static IPBinder getInstance() {
        return ipBinder;
    }

    public void execute(Work work) {
        final Method[] methods = work.getClass().getDeclaredMethods();
        final Method[] workMethods = new Method[2];
        final Object[] resultStore = new Object[1];

        for (Method method : methods) {
            RunOn annotation = method.getAnnotation(RunOn.class);
            if (annotation == null) {
                continue;
            }
            if (annotation.thread() == Threads.DAEMON) {
                if (workMethods[0] != null) {
                    throw new IllegalThreadStateException("Your class should have only one background tagged method to do the IPC !");
                }
                workMethods[0] = method;
            } else {
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
        daemonThread.addTask(new Task() {
            @Override
            public void run() {
                try {
                    resultStore[0] = workMethods[0].invoke(work);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        // wait until a data is returned then add a task for the looper thread
        while (!isUpdated(resultStore[0]) && !daemonThread.isTerminated());

        looperThread.addTask(new Task() {
            @Override
            public void run() {
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

    public Looper getLooperThread() {
        return looperThread;
    }
}