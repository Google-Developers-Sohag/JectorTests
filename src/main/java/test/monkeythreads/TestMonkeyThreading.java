package test.monkeythreads;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import core.threads.impl.monkeythreads.util.MonkeyBinder;

import javax.swing.*;

public class TestMonkeyThreading extends SimpleApplication {

    private MonkeyBinder assetLoaderBinder;
    private MonkeyBinder heavyDutyBinder;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        TestMonkeyThreading app = new TestMonkeyThreading();
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My Awesome Game");
        app.setSettings(settings);

        app.start();
    }

    @Override
    public void simpleInitApp() {
        heavyDutyBinder = MonkeyBinder.createMonkeyIPC(stateManager, "Heavy Duty Stuff");
        heavyDutyBinder.addDaemonWork(new TestMonkeyDaemonBinder());

        assetLoaderBinder = MonkeyBinder.createMonkeyIPC(stateManager, "Load Assets");
        assetLoaderBinder.addDaemonWork(new TestMonkeyAssetLoader(this));


    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void destroy() {
        super.destroy();
        heavyDutyBinder.terminate();
        assetLoaderBinder.terminate();
    }
}
