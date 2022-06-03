package core.threads;

public interface Work {
    Object async();
    void start(Object asyncReturn);
}
