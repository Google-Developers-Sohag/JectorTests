package test.applauncher;

import util.Executor;

import java.lang.reflect.InvocationTargetException;

public final class Launcher {
    public static void main(String[] args) throws InvocationTargetException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        final Executor executor = Executor.getInstance();
        executor.setMethod("runTest", null);
        executor.runJavaClasses("/" + "test");

//        IPBinder.getInstance().execute(new TestMonkeyDaemonBinder());
//        IPBinder.getInstance().execute(new TestMonkeyDaemonBinder());
//        IPBinder.getInstance().execute(new TestMonkeyDaemonBinder());
//        IPBinder.getInstance().execute(new TestMonkeyDaemonBinder());
//        IPBinder.getInstance().execute(new TestThreads2());
//        IPBinder.getInstance().getLooperThread().addTask(new Task() {
//            @Override
//            public void run() {
//                System.out.println("Hi from looper");
//            }
//        });
//        IPBinder.getInstance().execute(new TestMonkeyDaemonBinder());
//        IPBinder.getInstance().execute(new TestMonkeyDaemonBinder());
//        IPBinder.getInstance().execute(new TestThreads2());
//
//        IPBinder.getInstance().execute(new TestThreads2());
//        IPBinder.getInstance().getDaemonThread().terminate();
//        IPBinder.getInstance().getLooperThread().terminate();
    }
}
