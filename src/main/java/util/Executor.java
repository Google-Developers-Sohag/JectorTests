package util;

import test.core.UnitTest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author pavl_g.
 */
public final class Executor {
    private static final Logger logger = Logger.getLogger(Executor.class.getName());

    private static Executor executor;
    private final String package_;


    private Executor(final String package_) {
        this.package_ = package_;
    }

    public static Executor getInstance(final String package_) {
        if (executor == null) {
            synchronized (Executor.class) {
                if (executor == null) {
                    executor = new Executor(package_);
                    logger.log(Level.INFO, "Prepared the executor instance.");
                }
            }
        }
        return executor;
    }

    public void executeMemberClasses() throws IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException, ClassNotFoundException {
        final File file = new File(Executor.this.getClass().getResource("/" + package_).getFile());
        final String[] filePaths = file.list();
        if (filePaths != null) {
            for (String path : filePaths) {
                // don't try to invoke non-runnable files and subclasses
                if (path.contains(".class") && (!path.contains("$"))) {
                    path = path.substring(0, path.length() - (".class").length());
                    path = package_ + "." + path;
                    final Class<?> clazz = Class.forName(path);
                    // invoke only the unit test classes
                    if (clazz.newInstance() instanceof UnitTest) {
                        final Method method = clazz.getDeclaredMethod("runTest");
                        method.setAccessible(true);
                        method.invoke(clazz.newInstance());
                        logger.log(Level.INFO, clazz.getName() + " test invoked.");
                    } else {
                        logger.log(Level.WARNING, clazz.getName() + " is not a unit test.");
                    }
                }
            }
        }
    }
}
