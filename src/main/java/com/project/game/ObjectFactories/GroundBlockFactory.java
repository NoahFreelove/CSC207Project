package com.project.game.ObjectFactories;

import com.project.engine.Core.GameObject;
import com.project.engine.Rendering.SpriteRenderer;
import com.project.game.Scripts.GroundStats;
import com.project.game.Scripts.SimpleCollider;

import javax.naming.spi.ObjectFactory;

public class GroundBlockFactory extends AbstractObjectFactory {
    private final String DEFAULT_GROUND_ASSET = "assets/ground_brick.png";

    protected GroundBlockFactory() {
        super();
    }

    public GameObject generate(double x, double y, int z, double width, double height, double friction) {
        return produceGameObject(x, y, z, width, height, friction, DEFAULT_GROUND_ASSET);
    }

    public GameObject generate(double x, double y, int z, double width, double height, String asset) {
        return produceGameObject(x, y, z, width, height, 0.5, asset);
    }

    public GameObject generate(double x, double y, int z, double width, double height, double friction, String asset) {
        return produceGameObject(x, y, z, width, height, friction, asset);
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        return produceGameObject(x, y, z, width, height, 0.5, DEFAULT_GROUND_ASSET);
    }

    protected GameObject produceGameObject(double x, double y, int z, double width, double height, double friction, String asset) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("ground");

        obj.getTransform().update(obj, 0); // This call allows obj to have right tiling scale.
        SpriteRenderer sr = new SpriteRenderer(asset, 128,128);
        sr.setTile(true);
        sr.setTileX(obj.getTransform().getScaleX());
        sr.setTileY(obj.getTransform().getScaleY());
        obj.addRenderable(sr);


        obj.addBehavior(new GroundStats(friction));
        obj.addBehavior(new SimpleCollider());

        return obj;
    }
}
