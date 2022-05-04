package annotations.lifecycle;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Lifecycle {
    public void onStart(final Object startPtr) {
        ILogger.INSTANCE.log(Level.INFO, "onStart invoked with data object " + startPtr.getClass().getSimpleName());
    }
    public void onStop(final Object stopPtr) {
        ILogger.INSTANCE.log(Level.INFO, "onStop invoked with data object " + stopPtr.getClass().getSimpleName());
    }
    public void onResume(final Object resumePtr) {
        ILogger.INSTANCE.log(Level.INFO, "onResume invoked with data object " + resumePtr.getClass().getSimpleName());
    }
    public void onSaved(final Object savePtr) {
        ILogger.INSTANCE.log(Level.INFO, "onSaved invoked with data object " + savePtr.getClass().getSimpleName());
    }
    public void onDestroy(final Object destroyPtr) {
        ILogger.INSTANCE.log(Level.INFO, "onDestroy invoked with data object " + destroyPtr.getClass().getSimpleName());
    }

    static class ILogger {
        private static final Logger INSTANCE = Logger.getLogger(Lifecycle.class.getName());
    }
}
