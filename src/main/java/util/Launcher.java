package util;

import java.lang.reflect.InvocationTargetException;

public final class Launcher {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Executor executor = Executor.getInstance();
        executor.setMethod("runTest", null);
        executor.runJavaClasses("/" + "test");
    }
}
