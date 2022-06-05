package test.monkeythreads;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import core.threads.Threads;
import core.threads.Work;
import core.threads.command.RunOn;

public class TestMonkeyAssetLoader implements Work {
    private final SimpleApplication application;

    public TestMonkeyAssetLoader(final SimpleApplication application) {
        this.application = application;
    }

    @RunOn(thread = Threads.DAEMON)
    @Override
    public Object async() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        return geom;
    }

    @Override
    @RunOn(thread = Threads.LOOPER)
    public void start(Object asyncReturn) {
        application.getRootNode().attachChild((Spatial) asyncReturn);
        System.out.println("Finished loading my asset " + ((Spatial) asyncReturn).getName());
    }
}
