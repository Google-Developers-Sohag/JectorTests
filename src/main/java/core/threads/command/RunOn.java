package core.threads.command;

import core.threads.impl.Threads;
import java.lang.annotation.*;

/**
 * Determines the directionality of execution of some methods inside an {@link core.threads.AppThread}.
 *
 * @author pavl_g
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunOn {
    Threads thread();
}
