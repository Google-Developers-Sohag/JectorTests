package core.threads.command;

import core.threads.Threads;
import core.threads.impl.jvmthreads.AppThread;

import java.lang.annotation.*;

/**
 * Determines the directionality of execution of some methods inside an {@link AppThread}.
 *
 * @author pavl_g
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunOn {
    Threads thread();
}
