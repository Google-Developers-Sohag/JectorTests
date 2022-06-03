package test;

import core.threads.Work;
import core.threads.command.RunOn;
import core.threads.impl.Threads;

public final class TestThreads implements Work {

    @Override
    @RunOn(thread = Threads.BACKGROUND)
    public Object async() {
        String work = "";
        work += "Foo";
        work += "-";
        work += "Bar";

        return work;
    }

    @Override
    @RunOn(thread = Threads.LOOPER)
    public void start(Object asyncReturn) {
        System.out.println("Using shared data " + asyncReturn);
    }
}
