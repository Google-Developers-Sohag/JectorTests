package core.threads.impl.jvmthreads;

import java.util.logging.Logger;

/**
 * A Daemon thread to execute result tasks and communicate the results back to the {@link Looper} thread.
 *
 * @author pavl_g
 */
public class Daemon extends AppThread {

    private static final Logger logger = Logger.getLogger(Daemon.class.getName());

    public Daemon() {
        super(Daemon.class.getName());
    }

    @Override
    public synchronized void start() {
        super.start();
        logger.info("Daemon Started");
    }
}
