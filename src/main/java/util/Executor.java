package util;

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
    private static final String fileSeparator = "/";
    private static Executor executor;
    private String method = "";
    private Object params;

    private Executor() {
    }

    public static Executor getInstance() {
        if (executor == null) {
            synchronized (Executor.class) {
                if (executor == null) {
                    executor = new Executor();
                    logger.log(Level.INFO, "Prepared the executor instance.");
                }
            }
        }
        return executor;
    }

    public void runJavaClasses(final String package_) throws IllegalAccessException,
            InstantiationException,
            InvocationTargetException, ClassNotFoundException {
        final String[] files = openPackage(package_).list();
        if (files == null) {
            return;
        }
        for (String file : files) {
            if (openPackage(buildFromPackage(package_, file)).list() != null) {
                Executor.getInstance().runJavaClasses(buildFromPackage(package_, file));
            } else {
                execute(package_.replaceFirst(Executor.fileSeparator, "")
                        .replaceAll(Executor.fileSeparator, "."), file);
            }
        }
    }

    private File openPackage(final String package_) {
        return new File(Executor.this.getClass().getResource(package_).getFile());
    }

    private String buildFromPackage(final String package_, final String file) {
        return package_ + Executor.fileSeparator + file;
    }

    private void execute(final String package_, String clazzName) throws IllegalAccessException,
            InstantiationException, ClassNotFoundException, InvocationTargetException {
        // don't try to invoke non-runnable files and subclasses
        if (clazzName.contains(".class") && (!clazzName.contains("$"))) {
            clazzName = clazzName.substring(0, clazzName.length() - (".class").length());
            clazzName = package_ + "." + clazzName;
            final Class<?> clazz = Class.forName(clazzName);
            // invoke only the unit test classes
            try {
                final Method method = getMethod(clazz, executor.method, params);
                method.setAccessible(true);
                invokeMethod(clazz, method, params);
                logger.log(Level.INFO, clazz.getName() + " method " + executor.method + " invoked.");
            } catch (final NoSuchMethodException exception) {
                logger.log(Level.WARNING, clazz.getName() + " doesn't have the method " + executor.method);
            } catch (final InstantiationException exception2) {
                logger.log(Level.WARNING, clazz.getName() + " is an abstract class.");
            }
        }
    }

    public void setMethod(String method, Object params) {
        this.method = method;
        this.params = params;
    }

    private Method getMethod(final Class<?> clazz,
                             final String method, final Object params)
            throws NoSuchMethodException {
        if (params == null) {
            return clazz.getDeclaredMethod(method);
        }
        return clazz.getDeclaredMethod(method, params.getClass());
    }

    private Object invokeMethod(final Class<?> clazz,
                                final Method method, final Object params) throws
            IllegalAccessException, InstantiationException, InvocationTargetException {
        if (params == null) {
            return method.invoke(clazz.newInstance());
        }
        return method.invoke(clazz.newInstance(), params);
    }
}
