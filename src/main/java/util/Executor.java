package util;

import test.core.UnitTest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author pavl_g.
 */
public final class Executor {
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
                if (path.contains("Test") && (!path.contains("$"))) {
                    path = path.substring(0, path.length() - (".class").length());
                    path = "test." + path;
                    final Class<? extends UnitTest> clazz = (Class<? extends UnitTest>) Class.forName(path);
                    final Method method = clazz.getDeclaredMethod("runTest");
                    method.setAccessible(true);
                    method.invoke(clazz.newInstance());
                }
            }
        }
    }
}
