package util;

import test.TestInjector;
import test.TestLifecycle;

/**
 * @author pavl_g.
 */
public class Executor {
    public static void main(String[] args) throws NoSuchMethodException {
        new TestInjector().runTest();
        new TestLifecycle().runTest();
    }
}
