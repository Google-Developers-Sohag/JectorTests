package annotations.lifecycle;

public enum LifecycleImpl {
    IMPL(new Lifecycle() {});

    private Lifecycle lifecycle;

    LifecycleImpl(final Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public void setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Lifecycle getLifecycle() {
        return lifecycle;
    }
}
