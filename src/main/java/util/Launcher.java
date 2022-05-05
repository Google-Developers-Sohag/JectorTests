package util;

import java.lang.reflect.InvocationTargetException;

public final class Launcher {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Executor.getInstance("test").executeMemberClasses();
    }
}
