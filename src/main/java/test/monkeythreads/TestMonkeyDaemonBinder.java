package test.monkeythreads;

import core.threads.Threads;
import core.threads.Work;
import core.threads.command.RunOn;

public final class TestMonkeyDaemonBinder implements Work {

    @Override
    @RunOn(thread = Threads.DAEMON)
    public Object async() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    @Override
    @RunOn(thread = Threads.LOOPER)
    public void start(Object asyncReturn) {
        System.out.println("Finished loading heavy duty stuff with return " + asyncReturn);
    }
}
