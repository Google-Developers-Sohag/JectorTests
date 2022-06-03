package core.threads.impl;

import core.threads.AppThread;
import core.threads.command.RunOn;

/**
 * The types of threads used by {@link RunOn#thread()}.
 * <p>
 * Either {@link Daemon} OR {@link Looper}.
 *
 * @author pavl_g
 */
public enum Threads {
    BACKGROUND(Daemon.class), LOOPER(Looper.class);

    private final Class<? extends AppThread> threadClass;

    Threads(final Class<? extends AppThread> threadClass) {
        this.threadClass = threadClass;
    }

    public Class<? extends AppThread> getThreadClass() {
        return threadClass;
    }
}
