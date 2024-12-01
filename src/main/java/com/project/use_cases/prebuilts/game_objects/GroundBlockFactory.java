package com.project.use_cases.prebuilts.game_objects;

import com.project.entity.core.GameObject;
import com.project.entity.rendering.SpriteRenderer;
import com.project.use_cases.play_prebuilt_levels.scripts.GroundStats;
import com.project.use_cases.play_prebuilt_levels.scripts.SimpleCollider;

public class GroundBlockFactory extends AbstractObjectFactory {
    private final String DEFAULT_GROUND_ASSET = "assets/ground_brick.png";

    protected GroundBlockFactory() {
        super();
    }

    public GameObject generate(double x, double y, int z, double width, double height, double friction) {
        return produceGameObject(x, y, z, width, height, friction, DEFAULT_GROUND_ASSET);
    }

    public GameObject generate(double x, double y, int z, double width, double height, String asset) {
        return produceGameObject(x, y, z, width, height, 1.5, asset);
    }

    public GameObject generate(double x, double y, int z, double width, double height, double friction, String asset) {
        return produceGameObject(x, y, z, width, height, friction, asset);
    }

    @Override
    protected GameObject produceGameObject(double x, double y, int z, double width, double height) {
        return produceGameObject(x, y, z, width, height, 2, DEFAULT_GROUND_ASSET);
    }

    protected GameObject produceGameObject(double x, double y, int z, double width, double height, double friction, String asset) {
        GameObject obj = super.produceGameObject(x, y, z, width, height);
        obj.addTag("ground");

        obj.getTransform().update(obj, 0); // This call allows obj to have right tiling scale.
        SpriteRenderer sr = new SpriteRenderer(asset, 64,64);
        sr.setTile(true);
        sr.setTileX(obj.getTransform().getScaleX());
        sr.setTileY(obj.getTransform().getScaleY());
        obj.addRenderable(sr);


        obj.addBehavior(new GroundStats(friction));
        obj.addBehavior(new SimpleCollider());

        return obj;
    }
}
