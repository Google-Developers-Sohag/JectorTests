package test;

import annotations.lifecycle.Lifecycle;
import annotations.lifecycle.LifecycleImpl;
import annotations.lifecycle.LifecycleOwner;
import test.core.UnitTest;

public class TestLifecycle extends UnitTest {

    private final LifecycleOwner lifecycleOwner;

    @LifecycleOwner(LifecycleImpl.IMPL)
    public TestLifecycle() throws NoSuchMethodException {
        this.lifecycleOwner = getClass().getConstructor().getAnnotation(LifecycleOwner.class);
    }

    public void setLifecycle(Lifecycle lifecycle) {
        lifecycleOwner.value().setLifecycle(lifecycle);
    }

    public Lifecycle getLifecycle() {
        return lifecycleOwner.value().getLifecycle();
    }

    @Override
    protected void runTest() {
        try {
            final TestLifecycle testLifecycle = new TestLifecycle();
            testLifecycle.setLifecycle(new Lifecycle() {
                @Override
                public void onStart(Object startPtr) {
                    super.onStart(startPtr);
                }

                @Override
                public void onStop(Object stopPtr) {
                    super.onStop(stopPtr);
                }

                @Override
                public void onResume(Object resumePtr) {
                    super.onResume(resumePtr);
                }

                @Override
                public void onSaved(Object savePtr) {
                    super.onSaved(savePtr);
                }
            });
            for (int i = 0; i < 3; i++) {
                testLifecycle.getLifecycle().onResume(i);
                Thread.sleep(200);
                testLifecycle.getLifecycle().onStart(i);
                Thread.sleep(450);
                testLifecycle.getLifecycle().onStop(i);
                Thread.sleep(450);
                testLifecycle.getLifecycle().onDestroy(i);
            }
        } catch (NoSuchMethodException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
