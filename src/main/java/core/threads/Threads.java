package core.threads;

import core.threads.command.RunOn;
import core.threads.impl.jvmthreads.AppThread;
import core.threads.impl.jvmthreads.Daemon;
import core.threads.impl.jvmthreads.Looper;

/**
 * The types of threads used by {@link RunOn#thread()}.
 * <p>
 * Either {@link Daemon} OR {@link Looper}.
 *
 * @author pavl_g
 */
public enum Threads {
    DAEMON(Daemon.class), LOOPER(Looper.class);

    private final Class<? extends AppThread> threadClass;

    Threads(final Class<? extends AppThread> threadClass) {
        this.threadClass = threadClass;
    }

    public Class<? extends AppThread> getThreadClass() {
        return threadClass;
    }
}
