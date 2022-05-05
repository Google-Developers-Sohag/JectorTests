package test.core;

import java.util.ArrayList;

/**
 * For unit testing.
 *
 * @author pavl_g.
 */
public abstract class UnitTest {
    private final ArrayList<UnitTest> subClasses = new ArrayList<>();
    protected void runTest() {
        subClasses.add(this);
    }
}
