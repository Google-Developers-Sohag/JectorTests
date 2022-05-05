package test;

import annotations.action.Action;
import annotations.action.ActionImpl;
import annotations.action.Injector;
import test.core.UnitTest;

import java.util.Arrays;

public class TestInjector extends UnitTest {

    private final Injector injector;

    @Injector(ActionImpl.IMPL)
    public TestInjector() throws NoSuchMethodException {
        this.injector = getClass().getConstructor().getAnnotation(Injector.class);
    }

    public void invoke(final Object pointer) {
        injector.value().getAction().invoke(pointer);
    }

    public void setAction(final Action action) {
        injector.value().setAction(action);
    }

    @Override
    protected void runTest() {
        try {
            final TestInjector testInjector = new TestInjector();
            testInjector.setAction(pointer -> {
                if (pointer instanceof String[]) {
                    System.out.println(Arrays.toString((String[]) pointer));
                } else if (pointer instanceof Integer) {
                    System.out.println((int) pointer);
                }
            });
            testInjector.invoke(new String[]{
                    "A", "call", "from", "an", "annotation", "!"
            });
            for (int i = 0; i < 5; i++) {
                testInjector.invoke(i);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}