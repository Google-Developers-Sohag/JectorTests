package annotations.action;

public enum ActionImpl {
    IMPL((pointer) -> {
        System.out.println("Empty Action Dependency ! " + pointer);
    });

    private Action action;

    ActionImpl(final Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public ActionImpl setAction(Action action) {
        this.action = action;
        return this;
    }
}
